package world.cepi.sabre.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.MinecraftServer
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.loaders.loadLoaders
import java.nio.file.Path
import kotlin.concurrent.thread
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

var terminalThread: Thread? = null

fun main() {
    // Initialize config
    config = Sabre.initConfigFile(CONFIG_PATH, Config())

    System.setProperty("minestom.tps", (config.tps).toString())
    System.setProperty("minestom.entity-view-distance", config.entityDistance.toString())
    System.setProperty("minestom.chunk-view-distance", config.renderDistance.toString())

    val server = MinecraftServer.init()

//
//        System.setProperty("debug", "true")
//        System.setProperty("debuggame", "lobby")

    MinecraftServer.setTerminalEnabled(false)
    MinecraftServer.setBrandName("§dMinestom ${MinecraftServer.VERSION_NAME}")

    // Load the loaders.
    loadLoaders()

    // The IP and port are grabbed from the config file
    server.start(config.ip, config.port)

    terminalThread = thread(start = true, isDaemon = true, name = "SabreConsole") {
        SabreTerminal.start()
    }
}

val CONFIG_PATH: Path = Path.of("./sabre-config.json")

object Sabre {

    inline fun <reified T : Any> initConfigFile(path: Path, emptyObj: T): T {
        return if (path.exists()) ConfigurationHelper.format.decodeFromString(path.readText()) else run {
            path.writeText(ConfigurationHelper.format.encodeToString(emptyObj))
            emptyObj
        }
    }



}
