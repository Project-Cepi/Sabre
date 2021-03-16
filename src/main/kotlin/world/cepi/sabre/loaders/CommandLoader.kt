package world.cepi.sabre.loaders

import com.google.common.base.Strings
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.utils.callback.CommandCallback
import world.cepi.sabre.commands.security.DeopCommand
import world.cepi.sabre.commands.security.OpCommand
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.commands.StopCommand

object CommandLoader : Loader {

    override fun invoke() {

        val commandManager = MinecraftServer.getCommandManager()

        commandManager.unknownCommandCallback =
                CommandCallback { sender: CommandSender, command: String ->
                    if (!Strings.isNullOrEmpty(command)) {
                        sender.sendMessage(Component.text(config.unknownMessage))
                    }
                }

        commandManager.register(StopCommand)
        commandManager.register(OpCommand)
        commandManager.register(DeopCommand)
    }

}