package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.utils.getUUID

object UUIDLoader : Loader {

    override fun load() {

        val connectionManager = MinecraftServer.getConnectionManager()

        // We have to set a different UUID provider because Mojang's API is not used by default
        connectionManager.setUuidProvider { _, username ->
            return@setUuidProvider getUUID(username)
        }
    }

}