package world.cepi.sabre.server.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.SimpleCommand
import net.minestom.server.entity.Player
import world.cepi.kstom.command.default
import kotlin.system.exitProcess

object StopCommand : Command("stop") {
    init {
        setCondition { sender, _ ->
            sender.hasPermission("sabre.stop")
                    || (sender is Player && sender.permissionLevel >= 4)
                    || (sender is ConsoleSender)
        }

        default { _, _ ->
            MinecraftServer.stopCleanly()
            exitProcess(1)
        }
    }

}