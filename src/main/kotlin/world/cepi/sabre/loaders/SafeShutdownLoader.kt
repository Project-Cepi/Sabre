package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player

object SafeShutdownLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        MinecraftServer.getSchedulerManager().buildShutdownTask {
            connectionManager.onlinePlayers.forEach { player: Player ->
                player.kick("Server is closing.")
            }
        }.schedule()
    }

}