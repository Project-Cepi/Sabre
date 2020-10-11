package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.entity.Player

class StopCommand : Command("stop") {
    init {
        setDefaultExecutor { _, _ ->
            MinecraftServer.stopCleanly()
        }

//        setCondition {
//            return it !is Player || (it as Player).permissionLevel >= 4
//        }
    }
}



