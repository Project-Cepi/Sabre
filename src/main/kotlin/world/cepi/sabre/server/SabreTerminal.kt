package world.cepi.sabre.server

import net.minestom.server.MinecraftServer
import org.jline.reader.*
import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import org.tinylog.Logger
import java.io.IOException
import kotlin.system.exitProcess

object SabreTerminal {
    fun processInput(input: String) {
        val command = input.trim { it <= ' ' }
        if (command.isNotEmpty()) {
            MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().consoleSender, command)
        }
    }

    fun buildReader(builder: LineReaderBuilder): LineReader {
        val reader = builder
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
        reader.setOpt(LineReader.Option.DISABLE_EVENT_EXPANSION)
        reader.unsetOpt(LineReader.Option.INSERT_TAB)
        return reader
    }

    fun start() {
        try {
            readCommands(TerminalBuilder.builder().system(true).build())
        } catch (e: IOException) {
            Logger.error("Failed to read console input", e)
        }
    }

    private fun readCommands(terminal: Terminal) {
        val reader = buildReader(LineReaderBuilder.builder().terminal(terminal))
        try {
            var line: String?
            while (!MinecraftServer.isStopping()) {
                line = try {
                    reader.readLine("")
                } catch (ignored: EndOfFileException) {
                    // Continue reading after EOT
                    continue
                }
                if (line == null) {
                    break
                }
                processInput(line)
            }
        } catch (e: UserInterruptException) {
            exitProcess(0)
        } finally {
            terminal.close()
        }
    }
}
