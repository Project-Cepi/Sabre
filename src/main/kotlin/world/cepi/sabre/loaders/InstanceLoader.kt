package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.Position
import world.cepi.sabre.commands.security.getPermissionLevel
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.flat.Flat
import world.cepi.sabre.instances.generators.flat.FlatLayer
import world.cepi.kstom.addEventCallback
import world.cepi.sabre.commands.security.isWhitelisted

object InstanceLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        connectionManager.addPlayerInitialization {
            try {

                if (config.useFlatGenerator) {
                    it.respawnPoint = Position(0.0, 64.0, 0.0)
                    it.addEventCallback(PlayerLoginEvent::class) {
                        setSpawningInstance(Instances.createInstanceContainer(Flat(
                                FlatLayer(Block.BEDROCK, 1),
                                FlatLayer(Block.STONE, 25),
                                FlatLayer(Block.DIRT, 7),
                                FlatLayer(Block.GRASS_BLOCK, 1)
                        )))

                        spawningInstance!!.loadChunk(0, 0)
                    }
                }

                it.addEventCallback(PlayerLoginEvent::class) {

                    // Kicks the player if they are not on the whitelist
                    if (config.whitelist && !player.uuid.isWhitelisted()) player.kick("You are not on the whitelist for this server!")

                    // OPs players when they join if they are on the ops list
                    player.permissionLevel = getPermissionLevel(player) ?: 0
                }

                it.addEventCallback(PlayerSpawnEvent::class) {
                    player.teleport(player.respawnPoint)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}