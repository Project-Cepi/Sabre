package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.utils.Position
import world.cepi.kstom.Manager
import world.cepi.kstom.event.listenOnly
import world.cepi.sabre.server.commands.security.getPermissionLevel
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.flatgenerator.Flat

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

    val node = Manager.globalEvent

    if (config.useFlatGenerator) {

        connectionManager.addPlayerInitialization {
            it.respawnPoint = Position(0.0, 64.0, 0.0)
        }

        node.listenOnly<PlayerLoginEvent> {
            setSpawningInstance(instance!!)

            spawningInstance!!.loadChunk(0, 0)
            spawningInstance!!.timeRate = config.timeRate
            spawningInstance!!.time = config.time
        }
    }

    if (config.opUtilities) {
        node.listenOnly<PlayerLoginEvent> {

            // OPs players when they join if they are on the ops list
            player.permissionLevel = getPermissionLevel(player)

        }
    }

    if (config.shouldRespawnAtSpawnPoint) {
        node.listenOnly<PlayerSpawnEvent> {
            player.teleport(player.respawnPoint)
        }
    }

}