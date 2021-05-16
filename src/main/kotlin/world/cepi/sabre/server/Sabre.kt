package world.cepi.sabre.server

import kotlinx.serialization.decodeFromString
import net.minestom.server.MinecraftServer
import org.jetbrains.annotations.ApiStatus
import world.cepi.sabre.server.loaders.loadLoaders
import java.io.File

object Sabre {

    /**
     * Internal method for booting sabre without a bootloader.
     * @see world.cepi.sabre.SabreLoader.boot
     */
    @ApiStatus.Internal
    @JvmStatic
    fun boot(config: Config? = null) {
        val server = MinecraftServer.init()

        // Initialize config
        Config.config = config ?: Config.format.decodeFromString(File(CONFIG_LOCATION).readText())

        // Load the loaders.
        loadLoaders()

        // The IP and port are grabbed from the config file
        server.start(Config.config.ip, Config.config.port)
    }

    const val CONFIG_LOCATION = "./sabre-config.json"
    const val OP_LOCATION = "./ops.json"

}