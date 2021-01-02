package world.cepi.sabre.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import world.cepi.kstom.KommandProcessor


class ShutdownCommand : KommandProcessor("stop", listOf(), { it.permissionLevel >= 4 }, { _, _, _ ->
    MinecraftServer.stopCleanly()
    true
})
