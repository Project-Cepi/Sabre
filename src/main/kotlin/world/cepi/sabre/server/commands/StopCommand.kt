package world.cepi.sabre.server.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.entity.Player
import kotlin.system.exitProcess

internal object StopCommand : Command("stop") {
    init {
        setCondition { sender, _ ->
            sender is ConsoleSender
        }

        defaultExecutor = CommandExecutor { _, _ ->
            MinecraftServer.stopCleanly()
            exitProcess(0)
        }
    }

}
