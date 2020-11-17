package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.commands.*
import world.cepi.sabre.commands.security.DeopCommand
import world.cepi.sabre.commands.security.OpCommand
import world.cepi.sabre.commands.security.WhitelistCommand

object CommandLoader : Loader {

    override fun load() {

        val commandManager = MinecraftServer.getCommandManager()

        commandManager.register(StopCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
        commandManager.register(WhitelistCommand())
    }

}