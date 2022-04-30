package world.cepi.sabre.server.loaders

import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.utils.callback.CommandCallback
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.commands.StopCommand

internal fun commandLoader() {

    if (config.unknownMessage != "") {
        MinecraftServer.getCommandManager().unknownCommandCallback =
            CommandCallback { sender: CommandSender, command: String ->
                if (command != "") {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(config.unknownMessage))
                }
            }
    }

    MinecraftServer.getCommandManager().register(StopCommand)
}
