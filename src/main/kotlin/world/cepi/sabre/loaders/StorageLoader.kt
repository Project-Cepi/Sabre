package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.storage.systems.FileStorageSystem

object StorageLoader : Loader {

    override fun invoke() {
        // Basically this sets the default storage manager to be a filesystem
        // As opposed to a database or something, I think
        val storageManager = MinecraftServer.getStorageManager()
        storageManager.defineDefaultStorageSystem { FileStorageSystem() }
    }

}