package world.cepi.sabre

import net.minestom.server.MinecraftServer
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.loaders.load
import kotlin.system.exitProcess


fun main() {
    try {

        val server = MinecraftServer.init()

        load()

        // The IP and port are currently grabbed from the config file

        server.start(config.ip, config.port)
    } catch (e: Exception) {
        // graceful exit that doesn't fall back to bootstrap.
        e.printStackTrace()
        exitProcess(1)
    }
}

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val INSTANCE_STORAGE_LOCATION = "./instances"
    const val OP_LOCATION = "./ops.json"
}