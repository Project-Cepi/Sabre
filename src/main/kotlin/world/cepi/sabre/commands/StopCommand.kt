package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player


class ShutdownCommand : CommandProcessor {
    override fun getCommandName(): String {
        return "stop"
    }

    override fun getAliases(): Array<String> {
        return arrayOf()
    }

    override fun process(sender: CommandSender, command: String, args: Array<String>): Boolean {
        MinecraftServer.stopCleanly()
        return true
    }

    override fun hasAccess(player: Player): Boolean {
        return player.permissionLevel >= 4
    }
}

