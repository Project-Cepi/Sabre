package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.Config.Companion.config

/** Loads view distances of both entities and chunks */
object ViewLoader : Loader {

    override fun invoke() {
        MinecraftServer.setEntityViewDistance(config.entityDistance)
        MinecraftServer.setChunkViewDistance(config.renderDistance)
    }

}