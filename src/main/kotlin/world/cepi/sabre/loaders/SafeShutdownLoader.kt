package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.network.ConnectionManager

object SafeShutdownLoader {

    fun load(connectionManager: ConnectionManager) {
        MinecraftServer.getSchedulerManager().buildShutdownTask {
            connectionManager.onlinePlayers.forEach { player: Player ->
                player.kick("Server is closing.")
                connectionManager.removePlayer(player.playerConnection)
            }
        }
    }

}