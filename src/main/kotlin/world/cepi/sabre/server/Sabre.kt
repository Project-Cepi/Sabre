package world.cepi.sabre.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.MinecraftServer
import net.minestom.server.exception.ExceptionHandler
import org.jetbrains.annotations.ApiStatus
import org.slf4j.LoggerFactory
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

    var terminalThread: Thread? = null

    val logger = LoggerFactory.getLogger("Sabre")

    /**
     * Internal method for booting sabre without a bootloader.
     * @see world.cepi.sabre.SabreLoader.boot
     */
    @ApiStatus.Internal
    @JvmStatic
    fun boot(config: Config? = null) {
        // Initialize config
        Config.config = config ?: initConfigFile(CONFIG_PATH, Config())

        val server = MinecraftServer.init()

//
//        System.setProperty("debug", "true")
//        System.setProperty("debuggame", "lobby")

        System.setProperty("minestom.tps", (config?.tps).toString())
        MinecraftServer.setTerminalEnabled(false)
        MinecraftServer.setBrandName("§dMinestom ${MinecraftServer.VERSION_NAME}")

        // Load the loaders.
        loadLoaders()

        // The IP and port are grabbed from the config file
        server.start(Config.config.ip, Config.config.port)

        MinecraftServer.getExceptionManager().exceptionHandler = ExceptionHandler {
            logger.error("An error has occurred", it)
        }

        terminalThread = thread(start = true, isDaemon = true, name = "SabreConsole") {
            SabreTerminal.start()
        }
    }

    val CONFIG_PATH: Path = Path.of("./sabre-config.json")

}
