package world.cepi.sabre.commands

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.sabre.utils.getPlayer

class KillCommand: Command("kill") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /kill <player>")
        }

        val target = ArgumentType.Word("target")

        addSyntax({source, args ->
            val player = getPlayer(args.getWord("target")!!)
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