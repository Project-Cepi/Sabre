package world.cepi.sabre.commands

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player

class GamemodeCommand : Command("gamemode") {

    init {
        val gamemode = ArgumentType.Word("gamemode").from(*GameMode.values().map { it.name.toLowerCase() }.toList().toTypedArray())

        setCondition { sender, _ ->
            if (!sender.isPlayer) {
                sender.sendMessage("The command is only available for players!")
                false
            } else true
        }

        setDefaultExecutor { source, _ ->
            source.sendMessage("Please enter a gamemode! <creative, survival, adventure, spectator>")
        }

        addSyntax({ commandSender, arguments ->
            (commandSender as Player).gameMode = GameMode.values().first { it.name.toLowerCase() == arguments.getWord("gamemode")}
        }, gamemode)

    }

}