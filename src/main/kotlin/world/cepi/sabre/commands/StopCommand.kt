package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command

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



