package world.cepi.sabre.server.loaders

import com.google.common.base.Strings
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.utils.callback.CommandCallback
import world.cepi.sabre.server.commands.security.DeopCommand
import world.cepi.sabre.server.commands.security.OpCommand
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.commands.StopCommand
import world.cepi.sabre.server.commands.UpdateCommand

internal fun commandLoader() {

    if (!Strings.isNullOrEmpty(config.unknownMessage)) {
        MinecraftServer.getCommandManager().unknownCommandCallback =
            CommandCallback { sender: CommandSender, command: String ->
                if (!Strings.isNullOrEmpty(command)) {
                    sender.sendMessage(Component.text(config.unknownMessage))
                }
            }
    }

    MinecraftServer.getCommandManager().register(StopCommand)
    MinecraftServer.getCommandManager().register(UpdateCommand)

    if (config.opUtilities) {
        MinecraftServer.getCommandManager().register(OpCommand)
        MinecraftServer.getCommandManager().register(DeopCommand)
    }
}
