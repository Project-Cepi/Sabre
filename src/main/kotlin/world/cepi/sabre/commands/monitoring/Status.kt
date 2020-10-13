package world.cepi.sabre.commands.monitoring

import net.minestom.server.command.builder.Command

class Status : Command("status") {

    init {
        setDefaultExecutor { source, _ ->

            val totalMemory = Runtime.getRuntime().totalMemory()/1024/1024
            val memory = totalMemory - Runtime.getRuntime().freeMemory()/1024/1024
            source.sendMessage("RAM: ${memory}mb/${totalMemory}mb")
        }
    }

}