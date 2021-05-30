package world.cepi.sabre.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.MinecraftServer
import org.jetbrains.annotations.ApiStatus
import world.cepi.sabre.server.loaders.loadLoaders
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

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
        Config.config = config ?: if (CONFIG_PATH.exists()) Config.format.decodeFromString(CONFIG_PATH.readText()) else run {
            CONFIG_PATH.writeText(Config.format.encodeToString(Config()))
            Config()
        }

        // Load the loaders.
        loadLoaders()

        // The IP and port are grabbed from the config file
        server.start(Config.config.ip, Config.config.port)
    }

    val CONFIG_PATH: Path = Path.of("./sabre-config.json")

    val OP_PATH: Path = Path.of("./ops.json")

}