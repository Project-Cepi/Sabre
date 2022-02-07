package world.cepi.sabre.server

import net.minecrell.terminalconsole.SimpleTerminalConsole
import net.minestom.server.MinecraftServer
import org.jline.reader.Candidate
import org.jline.reader.LineReaderBuilder

object SabreTerminal : SimpleTerminalConsole() {

    override fun isRunning() = !MinecraftServer.isStopping()

    override fun buildReader(builder: LineReaderBuilder) =
        builder
            .appName("SabreConsole")
            .completer { _, parsedLine, list ->
                list.addAll(MinecraftServer.getCommandManager().dispatcher.commands
                    .filter {
                        parsedLine.line().contains(it.name) ||
                                it.aliases?.filterNotNull()?.any { alias -> parsedLine.line().contains(alias) } == true
                    }
                    .map { command ->
                        ((command.aliases?.filterNotNull() ?: listOf()) + command.name)
                            .filter { parsedLine.line().contains(it) }
                            .map { Candidate(it) }
                    }.flatten())
            }.build()

    override fun runCommand(command: String) {
        MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().consoleSender, command)
    }

    override fun shutdown() {
        MinecraftServer.stopCleanly()
    }

}