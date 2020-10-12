package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.network.ConnectionManager
import world.cepi.sabre.utils.getUUID

object UUIDLoader {

    fun load(connectionManager: ConnectionManager) {

        // We have to set a different UUID provider because Mojang's API is not used by default
        connectionManager.setUuidProvider { _, username ->
            return@setUuidProvider getUUID(username)
        }
    }
}