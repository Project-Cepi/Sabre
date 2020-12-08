package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player

object SafeShutdownLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        Runtime.getRuntime().addShutdownHook(Thread(MinecraftServer::stopCleanly));

        MinecraftServer.getSchedulerManager().buildShutdownTask {
            connectionManager.onlinePlayers.forEach { player: Player ->
                player.kick("Server is closing.")
            }
        }.schedule()
    }

}