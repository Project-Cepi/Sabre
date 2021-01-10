package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.Position
import world.cepi.sabre.Config
import world.cepi.sabre.commands.security.getPermissionLevel
import world.cepi.sabre.commands.security.isWhitelisted
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.flat.Flat
import world.cepi.sabre.instances.generators.flat.FlatLayer

object InstanceLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        connectionManager.addPlayerInitialization {
            try {

                if (config.useFlatGenerator) {
                    it.respawnPoint = Position(0F, 64F, 0F)
                    it.addEventCallback(PlayerLoginEvent::class.java) { event ->
                        event.setSpawningInstance(Instances.createInstanceContainer(Flat(
                                FlatLayer(Block.BEDROCK, 1),
                                FlatLayer(Block.STONE, 25),
                                FlatLayer(Block.DIRT, 7),
                                FlatLayer(Block.GRASS_BLOCK, 1)
                        )))

                        event.spawningInstance!!.loadChunk(0, 0)
                    }
                }

                it.addEventCallback(PlayerLoginEvent::class.java) { event ->

                    // Kicks the player if they are not on the whitelist
                    if (config.whitelist && !event.player.uuid.isWhitelisted()) event.player.kick("You are not on the whitelist for this server!")

                    // OPs players when they join if they are on the ops list
                    event.player.permissionLevel = getPermissionLevel(event.player) ?: 0
                }

                it.addEventCallback(PlayerSpawnEvent::class.java) { event ->
                    event.player.teleport(event.player.respawnPoint)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}