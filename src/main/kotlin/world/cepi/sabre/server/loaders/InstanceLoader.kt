package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerSpawnEvent
import world.cepi.sabre.server.Config.Companion.config

internal fun instanceLoader() {

    val node = MinecraftServer.getGlobalEventHandler()

    if (config.shouldRespawnAtSpawnPoint) {
        node.addListener(PlayerSpawnEvent::class.java) {
            it.player.teleport(it.player.respawnPoint)
        }
    }
}
