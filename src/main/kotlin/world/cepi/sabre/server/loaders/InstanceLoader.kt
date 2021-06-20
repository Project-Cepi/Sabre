package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.utils.Position
import world.cepi.sabre.server.commands.security.getPermissionLevel
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.flatgenerator.Flat

internal fun instanceLoader() {

    val instance = if (config.useFlatGenerator) {
        val instanceManager = MinecraftServer.getInstanceManager()
        val instance = instanceManager.createInstanceContainer()
        instance.chunkGenerator = Flat(*config.flatLayers)
        instance.enableAutoChunkLoad(true)
        instance
    } else {
        null
    }

    val node = MinecraftServer.getGlobalEventHandler()

    if (config.useFlatGenerator) {

        node.addListener(PlayerLoginEvent::class.java) {
            it.player.respawnPoint = Position(0.0, 64.0, 0.0)
            it.setSpawningInstance(instance!!)

            it.spawningInstance!!.loadChunk(0, 0)
            it.spawningInstance!!.timeRate = config.timeRate
            it.spawningInstance!!.time = config.time
        }
    }

    if (config.opUtilities) {
        node.addListener(PlayerLoginEvent::class.java) {

            // OPs players when they join if they are on the ops list
            it.player.permissionLevel = getPermissionLevel(it.player)

        }
    }

    if (config.shouldRespawnAtSpawnPoint) {
        node.addListener(PlayerSpawnEvent::class.java) {
            it.player.teleport(it.player.respawnPoint)
        }
    }

}