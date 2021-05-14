package world.cepi.sabre.server

import net.minestom.server.MinecraftServer
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.loaders.loadLoaders

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val OP_LOCATION = "./ops.json"

    fun boot() {
        val server = MinecraftServer.init()

        // Load the loaders.
        loadLoaders()

        // The IP and port are grabbed from the config file
        server.start(config.ip, config.port)
    }
}