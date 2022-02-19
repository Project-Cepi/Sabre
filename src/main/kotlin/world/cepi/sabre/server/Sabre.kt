package world.cepi.sabre.server

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.exception.ExceptionHandler
import org.jetbrains.annotations.ApiStatus
import org.slf4j.LoggerFactory
import world.cepi.sabre.ImportMap
import world.cepi.sabre.server.loaders.loadLoaders
import java.nio.file.Path
import kotlin.concurrent.thread
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object Sabre {

    inline fun <reified T : Any> initConfigFile(path: Path, emptyObj: T): T {
        return if (path.exists()) ConfigurationHelper.format.decodeFromString(path.readText()) else run {
            path.writeText(ConfigurationHelper.format.encodeToString(emptyObj))
            emptyObj
        }
    }

    val logger = LoggerFactory.getLogger("Sabre")

    /**
     * Internal method for booting sabre without a bootloader.
     * @see world.cepi.sabre.SabreLoader.boot
     */
    @ApiStatus.Internal
    @JvmStatic
    fun boot(config: Config? = null) {

        if (IMPORT_PATH.exists()) {

            // Import map
            ImportMap.importMap = ConfigurationHelper.format.decodeFromString(IMPORT_PATH.readText())

            runBlocking {
                ImportMap.loadExtensions()
            }

        }

        // Initialize config
        Config.config = config ?: initConfigFile(CONFIG_PATH, Config())

        val server = MinecraftServer.init()

        MinecraftServer.setTerminalEnabled(false)

        // Load the loaders.
        loadLoaders()

        // The IP and port are grabbed from the config file
        server.start(Config.config.ip, Config.config.port)

        MinecraftServer.getExceptionManager().exceptionHandler = ExceptionHandler {
            logger.error("An error has occurred", it)
        }

        thread(start = true, isDaemon = false, name = "SabreConsole") {
            SabreTerminal.start()
        }

        // Resend commands
        MinecraftServer.getConnectionManager().onlinePlayers.forEach(Player::refreshCommands)
    }

    val CONFIG_PATH: Path = Path.of("./sabre-config.json")

    val OP_PATH: Path = Path.of("./ops.json")

    val IMPORT_PATH: Path = Path.of("./import-map.json")

}
