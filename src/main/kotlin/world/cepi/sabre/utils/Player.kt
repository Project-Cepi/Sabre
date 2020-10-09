package world.cepi.sabre.utils

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import java.util.*

fun getPlayer(name: String): Player? {
    for (player in MinecraftServer.getConnectionManager().onlinePlayers) {
        if (player.username == name) return player
    }
    return null
}

fun getPlayer(uuid: UUID): Player? {
    val connectionManager = MinecraftServer.getConnectionManager()
    for (player in connectionManager.onlinePlayers) {
        if (player.uuid == uuid) return player
    }
    return null
}