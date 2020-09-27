package world.cepi.sabre.instances.generators

import net.minestom.server.instance.ChunkGenerator
import net.minestom.server.instance.ChunkPopulator
import net.minestom.server.instance.batch.ChunkBatch
import net.minestom.server.world.biomes.Biome

class Flat : ChunkGenerator {
    override fun generateChunkData(batch: ChunkBatch?, chunkX: Int, chunkZ: Int) {
        TODO("Not yet implemented")
    }

    override fun fillBiomes(biomes: Array<out Biome>?, chunkX: Int, chunkZ: Int) {
        TODO("Not yet implemented")
    }

    override fun getPopulators(): MutableList<ChunkPopulator> {
        TODO("Not yet implemented")
    }
}