package world.cepi.sabre.server.loaders

import net.minestom.server.storage.systems.FileStorageSystem
import world.cepi.kstom.Manager
import world.cepi.sabre.server.Config.Companion.config

internal fun storageLoader() {

    // This sets the default storage manager to use rocksDB
    if (config.useFileStorageSystem)
        Manager.storage.defineDefaultStorageSystem { FileStorageSystem() }

}