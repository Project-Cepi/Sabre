package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.storage.systems.FileStorageSystem
import world.cepi.sabre.server.Config.Companion.config

internal fun storageLoader() {

    // This sets the default storage manager to use rocksDB
    if (config.useFileStorageSystem)
        MinecraftServer.getStorageManager().defineDefaultStorageSystem { FileStorageSystem() }

}