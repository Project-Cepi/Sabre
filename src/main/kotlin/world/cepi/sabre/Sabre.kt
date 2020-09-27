package world.cepi.sabre

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.storage.systems.FileStorageSystem
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.Flat
import java.io.FileReader

fun main(args: Array<String>) {
    val server = MinecraftServer.init()
    val connectionManager = MinecraftServer.getConnectionManager()
    val storageManager = MinecraftServer.getStorageManager()
    val config = Gson().fromJson(FileReader(Sabre.CONFIG_LOCATION), Config::class.java)

    storageManager.defineDefaultStorageSystem{FileStorageSystem()}

    // This code basically teleports the player to an ethereal instance stored in RAM.
    // I don't know how to keep track of the things so it gets deleted on a restart
    val currentInstance: InstanceContainer? = null
    connectionManager.addPlayerInitialization {
        it.addEventCallback(PlayerLoginEvent::class.java) {event ->
            event.spawningInstance = currentInstance ?: Instances.createInstanceContainer(Flat())
            event.player.sendMessage("Welcome to the Cepi dev server! This universe is fleeting, and will be removed when the server restarts")
        }
    };

    // The IP and port are currently grabbed from the config file
    server.start(config.ip, config.port)
}

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val INSTANCE_STORAGE_LOCATION = "./instances"
}
