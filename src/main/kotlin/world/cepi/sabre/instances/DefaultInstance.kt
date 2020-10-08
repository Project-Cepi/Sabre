package world.cepi.sabre.instances

import net.minestom.server.MinecraftServer
import net.minestom.server.instance.ChunkGenerator
import net.minestom.server.instance.InstanceContainer

/**
 * Create the instances for Sabre, thus allowing a world to exist.
 */
fun createInstances() {
    val defaultInstanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer()
    defaultInstanceContainer.chunkGenerator
}