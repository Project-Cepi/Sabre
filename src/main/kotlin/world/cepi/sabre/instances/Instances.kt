package world.cepi.sabre.instances

import net.minestom.server.MinecraftServer
import net.minestom.server.instance.ChunkGenerator
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.SharedInstance
import net.minestom.server.storage.StorageLocation
import world.cepi.sabre.Sabre

/**
 * These functions creates an instance.
 * The instance container has its own chunks and entities, and
 * the shred instance shares chunks with a container
 * (which must be passed into the function)
 * By default, the chunks are stored in RAM but you can tell it to store to disk
 * You can also pass in a custom storage location; if you don't the default will be used
 */
object Instances {
    fun createInstanceContainer(generator: ChunkGenerator, storeChunks: Boolean = false, storageLocation: StorageLocation = MinecraftServer.getStorageManager().getLocation(Sabre.INSTANCE_STORAGE_LOCATION)): InstanceContainer {
        val instanceManager = MinecraftServer.getInstanceManager()
        val instance: InstanceContainer
        instance = if (storeChunks) {
            instanceManager.createInstanceContainer(storageLocation)
        }
        else {
            instanceManager.createInstanceContainer()
        }
        instance.chunkGenerator = generator
        instance.enableAutoChunkLoad(true)
        return instance
    }

    fun createSharedInstance(parent: InstanceContainer): SharedInstance {
        val instanceManager = MinecraftServer.getInstanceManager()
        return instanceManager.createSharedInstance(parent)
    }
}

