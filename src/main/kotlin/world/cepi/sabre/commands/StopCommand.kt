package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.entity.Player

object StopCommand : CommandProcessor {
    override fun getCommandName(): String = "stop"

    override fun getAliases(): Array<String> = arrayOf()

    override fun process(sender: CommandSender?, command: String?, args: Array<out String>?): Boolean {
        MinecraftServer.stopCleanly()
        return true
    }

    override fun hasAccess(player: Player): Boolean = player.permissionLevel >= 4
}



