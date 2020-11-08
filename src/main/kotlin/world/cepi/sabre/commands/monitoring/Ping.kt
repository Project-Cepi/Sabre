package world.cepi.sabre.commands.monitoring

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.sabre.utils.getPlayer

class Ping : Command("ping") {

    init {

        val target = ArgumentType.String("player")

        setCondition { sender, _ ->
            if (!sender.isPlayer) {
                sender.sendMessage("The command is only available for players!")
                false
            } else true
        }

        setDefaultExecutor { source, _ ->
            val player = source as Player
            player.sendMessage("Pong! Latency is ${player.latency}")
        }

        addSyntax({ sender, args ->
            val player = getPlayer(args.getWord("player"))
            if (player != null) {
                sender.sendMessage("Pong! ${player.username}'s Latency is ${player.latency}")
            } else {
                sender.sendMessage("This player does not exist!")
            }
        }, target)

    }

}