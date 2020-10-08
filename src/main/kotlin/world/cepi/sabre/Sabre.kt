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
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.Flat
import world.cepi.sabre.utils.getUUID
import java.io.FileReader
import java.util.*

fun main() {
    val server = MinecraftServer.init()
    val connectionManager = MinecraftServer.getConnectionManager()
    val storageManager = MinecraftServer.getStorageManager()

    // Basically this sets the default storage manager to be a filesystem
    // As opposed to a database or something, I think
    storageManager.defineDefaultStorageSystem { FileStorageSystem() }

    // This code basically teleports the player to an ethereal instance stored in RAM.
    // I don't know how to keep track of the things so it gets deleted on a restart
    var currentInstance: Instance? = null
    connectionManager.addPlayerInitialization {
        it.addEventCallback(PlayerLoginEvent::class.java) { event ->
            event.spawningInstance = currentInstance ?: Instances.createInstanceContainer(Flat())
            currentInstance = event.spawningInstance
        }

        it.addEventCallback(PlayerSpawnEvent::class.java) { event ->
            val player = event.entity as Player
            player.teleport(Position(0F, 64F, 0F))
        }
    }

    // We have to set a different UUID provider because Mojang's API is not used by default
    connectionManager.setUuidProvider { _, username ->
        return@setUuidProvider getUUID(username)
    }

    // The IP and port are currently grabbed from the config file
    server.start(config.ip, config.port)
}

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val INSTANCE_STORAGE_LOCATION = "./instances"
    const val WHITELIST_LOCATION = "./whitelist.json"
}

fun getPlayer(name: String): Player? {
    for (player in MinecraftServer.getConnectionManager().onlinePlayers) {
        if (player.username == name) return player
    }
    return null
}

fun getPlayer(uuid: UUID): Player? {
    val connectionManager = MinecraftServer.getConnectionManager()
    for (player in connectionManager.onlinePlayers) {
        if (player.uuid == uuid) return player
    }
    return null
}