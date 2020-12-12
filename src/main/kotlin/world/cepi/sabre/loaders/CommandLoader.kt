package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.utils.callback.CommandCallback
import world.cepi.sabre.commands.ShutdownCommand
import world.cepi.sabre.commands.security.DeopCommand
import world.cepi.sabre.commands.security.OpCommand
import world.cepi.sabre.commands.security.WhitelistCommand
import world.cepi.sabre.config

object CommandLoader : Loader {

    override fun load() {

        val commandManager = MinecraftServer.getCommandManager()

        commandManager.unknownCommandCallback =
                CommandCallback { sender: CommandSender, _ ->
                    sender.sendMessage(config().unknownMessage)
                }

        commandManager.register(ShutdownCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
        commandManager.register(WhitelistCommand())
    }

}