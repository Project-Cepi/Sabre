package world.cepi.sabre.server.loaders

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
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
                    sender.sendMessage(
                        Component.text()
                            .append(Component.translatable("command.unknown.command", NamedTextColor.RED))
                            .append(Component.newline())
                            .append(Component.text("/", NamedTextColor.GRAY))
                            .append(Component.text(command, NamedTextColor.RED, TextDecoration.UNDERLINED))
                            .append(Component.translatable("command.context.here", NamedTextColor.RED, TextDecoration.ITALIC))
                            .build()
                    )
                }
            }
    }

    MinecraftServer.getCommandManager().register(StopCommand)
}
