package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.server.Config.Companion.config

/** Loads view distances of both entities and chunks */
internal fun viewLoader() {

    MinecraftServer.setEntityViewDistance(config.entityDistance)
    MinecraftServer.setChunkViewDistance(config.renderDistance)

}
