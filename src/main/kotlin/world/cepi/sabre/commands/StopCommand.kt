package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.SimpleCommand
import net.minestom.server.entity.Player

object StopCommand : SimpleCommand("stop") {
    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        MinecraftServer.stopCleanly()
        return true
    }

    override fun hasAccess(sender: CommandSender, commandString: String?): Boolean {
        return sender.hasPermission("sabre.stop") || (sender is Player && sender.permissionLevel >= 4) || (sender is ConsoleSender)
    }

}