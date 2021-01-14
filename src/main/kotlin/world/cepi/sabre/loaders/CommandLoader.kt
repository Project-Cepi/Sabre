package world.cepi.sabre.loaders

import com.google.common.base.Strings
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.utils.callback.CommandCallback
import world.cepi.sabre.commands.ShutdownCommand
import world.cepi.sabre.commands.security.DeopCommand
import world.cepi.sabre.commands.security.OpCommand
import world.cepi.sabre.commands.security.WhitelistCommand
import world.cepi.sabre.Config.Companion.config

object CommandLoader : Loader {

    override fun load() {

        val commandManager = MinecraftServer.getCommandManager()

        commandManager.unknownCommandCallback =
                CommandCallback { sender: CommandSender, command: String ->
                    if (!Strings.isNullOrEmpty(command)) {
                        sender.sendMessage(config.unknownMessage)
                    }
                }

        commandManager.register(ShutdownCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
        commandManager.register(WhitelistCommand())
    }

}