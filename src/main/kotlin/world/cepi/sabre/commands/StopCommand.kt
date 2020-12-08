package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player

class StopCommand : Command("stop") {
    init {
        setDefaultExecutor { _, _ ->
            MinecraftServer.stopCleanly()
        }

        setCondition { source, _ ->
            return@setCondition source !is Player || source.permissionLevel >= 4
        }
    }
}



