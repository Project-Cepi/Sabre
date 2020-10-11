package world.cepi.sabre.utils

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import java.util.*

fun getPlayer(name: String): Player? {
    return try {
        MinecraftServer.getConnectionManager().onlinePlayers.first { it.username == name }
    } catch(e: NullPointerException) {
        null
    }
}

fun getPlayer(uuid: UUID): Player? {
    val connectionManager = MinecraftServer.getConnectionManager()
    for (player in connectionManager.onlinePlayers) {
        if (player.uuid == uuid) return player
    }
    return null
}