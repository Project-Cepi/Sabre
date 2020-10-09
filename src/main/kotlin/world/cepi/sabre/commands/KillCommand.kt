package world.cepi.sabre.commands

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.sabre.utils.getPlayer

object KillCommand: Command("kill") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /kill <player>")
        }

        val target = ArgumentType.String("target")

        addSyntax({source, args ->
            if (args.getWord("target") == null && source is Player) source.kill()
        }, target)

        addSyntax({source, args ->
            val player = getPlayer(args.getWord("target"))
            if (player == null) {
                source.sendMessage("Could not find target!")
                return@addSyntax
            } else {
                player.kill()
                println("Killed player ${player.displayName}")
            }
        }, target)
    }
}