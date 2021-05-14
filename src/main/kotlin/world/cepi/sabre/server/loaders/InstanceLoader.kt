package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.utils.Position
import world.cepi.kstom.Manager
import world.cepi.sabre.server.commands.security.getPermissionLevel
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.flatgenerator.Flat
import world.cepi.kstom.addEventCallback

internal fun instanceLoader() {

    val connectionManager = MinecraftServer.getConnectionManager()

    val instance = if (config.useFlatGenerator) {
        val instanceManager = Manager.instance
        val instance = instanceManager.createInstanceContainer()
        instance.chunkGenerator = Flat(*config.flatLayers)
        instance.enableAutoChunkLoad(true)
        instance
    } else {
        null
    }

    connectionManager.addPlayerInitialization {
        if (config.useFlatGenerator) {
            it.respawnPoint = Position(0.0, 64.0, 0.0)
            it.addEventCallback<PlayerLoginEvent> {
                setSpawningInstance(instance!!)

                spawningInstance!!.loadChunk(0, 0)
                spawningInstance!!.timeRate = config.timeRate
                spawningInstance!!.time = config.time
            }
        }

        if (config.opUtilities) {
            it.addEventCallback<PlayerLoginEvent> {

                // OPs players when they join if they are on the ops list
                player.permissionLevel = getPermissionLevel(player)
            }
        }

        if (config.shouldRespawnAtSpawnPoint) {
            it.addEventCallback<PlayerSpawnEvent> {
                player.teleport(player.respawnPoint)
            }
        }
    }

}