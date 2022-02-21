package world.cepi.sabre

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi
import org.tinylog.Level
import org.tinylog.core.LogEntry
import org.tinylog.core.LogEntryValue
import org.tinylog.writers.Writer
import world.cepi.sabre.server.Config
import world.cepi.sabre.server.Sabre
import java.text.SimpleDateFormat
import java.util.*


class SabreWriter(properties: Map<String?, String>) : Writer {

    override fun getRequiredLogEntryValues(): Collection<LogEntryValue> {
        return EnumSet.of(
            LogEntryValue.LEVEL, LogEntryValue.MESSAGE,
            LogEntryValue.CLASS, LogEntryValue.METHOD,
            LogEntryValue.THREAD, LogEntryValue.DATE
        )
    }

    fun formatLog(log: Level, ansi: Ansi): Ansi {
        val name = log.name.lowercase()
        val paddedName = name.padEnd(Level.values().maxOf { it.name.length } + 1, ' ')

        return when(log) {
            Level.INFO -> ansi.fgBlue().a(paddedName).reset()
            Level.WARN -> ansi.fgYellow().a(paddedName).reset()
            Level.ERROR -> ansi.fgRed().a(paddedName).reset()
            Level.TRACE -> ansi.fgCyan().a(paddedName).reset()
            Level.DEBUG -> ansi.fgGreen().a(paddedName).reset()
            Level.OFF -> ansi
        }
    }

    val dateFormat = SimpleDateFormat("HH:mm:ss")
    val packageRegex = Regex("\\w+(?!.)")

    override fun write(logEntry: LogEntry) {
        if (logEntry.level.ordinal < Level.INFO.ordinal) return
        print(
            formatLog(logEntry.level, ansi().cursorToColumn(0).eraseLine(Ansi.Erase.ALL))
                .bg(Ansi.Color.BLACK).a(" " + dateFormat.format(logEntry.timestamp.toDate()) + " ").reset()
                .fgBlack().a(" [").reset()
                .a(logEntry.thread.name)
                .fgBlack().a("]").reset()
                .let {
                    if (try {
                            Config.config.detailedConsole && logEntry.level.ordinal >= Level.INFO.ordinal
                    } catch (e: Exception) {
                      false
                    }) {
                        it.a(" ${
                            packageRegex.find(logEntry.className)?.value ?: logEntry.className
                        }.${logEntry.methodName}")
                    } else {
                        it
                    }
                }
                .a(" - ${logEntry.message}")
                .newline()
                .a("> ")
        )
    }

    override fun flush() {
        System.out.flush()
    }

    override fun close() {
        Sabre.terminalThread?.interrupt()
        println()
        println("tertetwetetetet2t32")
    }
}