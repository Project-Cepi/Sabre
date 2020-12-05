package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.entity.EntityDeathEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerRespawnEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.Position
import world.cepi.sabre.Config
import world.cepi.sabre.commands.security.getPermissionLevel
import world.cepi.sabre.commands.security.isWhitelisted
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.flat.Flat
import world.cepi.sabre.instances.generators.flat.FlatLayer

object InstanceLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        var currentInstance: Instance? = null
        connectionManager.addPlayerInitialization {
            try {

                it.respawnPoint = Position(0F, 64F, 0F)
                it.addEventCallback(PlayerLoginEvent::class.java) { event ->
                    event.setSpawningInstance(currentInstance ?: Instances.createInstanceContainer(Flat(
                            FlatLayer(Block.BEDROCK, 1),
                            FlatLayer(Block.STONE, 25),
                            FlatLayer(Block.DIRT, 7),
                            FlatLayer(Block.GRASS_BLOCK, 1)
                    )))
                    currentInstance = event.spawningInstance

                    event.spawningInstance!!.loadChunk(0, 0)

                    // Kicks the player if they are not on the whitelist
                    if (Config.config.whitelist && !event.player.uuid.isWhitelisted()) event.player.kick("You are not on the whitelist for this server")

                    // OPs players when they join if they are on the ops list
                    event.player.permissionLevel = getPermissionLevel(event.player) ?: 0
                }

                it.addEventCallback(PlayerSpawnEvent::class.java) { event ->
                    val player = event.entity as Player
                    player.teleport(player.respawnPoint)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}