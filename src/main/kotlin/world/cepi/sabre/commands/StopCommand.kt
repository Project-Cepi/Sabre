package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.entity.Player
import world.cepi.kstom.command.KommandProcessor


class ShutdownCommand : KommandProcessor("stop",
        listOf(),
        { it.permissionLevel >= 4 || it.hasPermission("sabre.stop") },
        lambda@{ sender, _, _ ->
            if (sender.hasPermission("sabre.stop") || (sender is Player && sender.permissionLevel >= 4) || (sender is ConsoleSender)) {
                MinecraftServer.stopCleanly()
                return@lambda true
            }

            false
        })
