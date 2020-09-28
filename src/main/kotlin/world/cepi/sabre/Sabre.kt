package world.cepi.sabre

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.storage.systems.FileStorageSystem
import net.minestom.server.utils.Position
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.Flat
import java.io.FileReader

fun main(args: Array<String>) {
    val server = MinecraftServer.init()
    val connectionManager = MinecraftServer.getConnectionManager()
    val storageManager = MinecraftServer.getStorageManager()
    val config = Gson().fromJson(FileReader(Sabre.CONFIG_LOCATION), Config::class.java)

    // Basically this sets the default storage manager to be a filesystem
    // As opposed to a database or something, I think
    storageManager.defineDefaultStorageSystem{FileStorageSystem()}

    // This code basically teleports the player to an ethereal instance stored in RAM.
    // I don't know how to keep track of the things so it gets deleted on a restart
    var currentInstance: Instance? = null
    connectionManager.addPlayerInitialization {
        it.addEventCallback(PlayerLoginEvent::class.java) {event ->
            event.spawningInstance = currentInstance ?: Instances.createInstanceContainer(Flat())
            currentInstance = event.spawningInstance
        }

        it.addEventCallback(PlayerSpawnEvent::class.java) {event ->
            val player = event.entity as Player
            player.teleport(Position(0F, 64F, 0F))
        }
    }

    // The IP and port are currently grabbed from the config file
    server.start(config.ip, config.port)
}

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val INSTANCE_STORAGE_LOCATION = "./instances"
}
