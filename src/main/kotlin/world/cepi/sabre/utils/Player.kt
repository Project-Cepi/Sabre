package world.cepi.sabre.utils

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import java.util.*

fun getPlayer(name: String): Player? {
    return MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.username.equals(name, true) }
}

fun getPlayer(uuid: UUID): Player? {
    return MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.uuid == uuid }
}