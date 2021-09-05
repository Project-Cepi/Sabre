package world.cepi.sabre.server

import net.minecrell.terminalconsole.SimpleTerminalConsole
import net.minestom.server.MinecraftServer
import org.jline.reader.LineReaderBuilder
import org.slf4j.LoggerFactory

object SabreTerminal : SimpleTerminalConsole() {

    override fun isRunning() = !MinecraftServer.isStopping()

    override fun buildReader(builder: LineReaderBuilder) =
        builder
            .appName("Minestom")
            .build()

    override fun runCommand(command: String) {
        MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().consoleSender, command)
    }

    override fun shutdown() {
        MinecraftServer.stopCleanly()
    }

}