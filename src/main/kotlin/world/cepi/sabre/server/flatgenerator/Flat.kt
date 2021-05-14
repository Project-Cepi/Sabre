package world.cepi.sabre.server.flatgenerator

import net.minestom.server.MinecraftServer
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.ChunkGenerator
import net.minestom.server.instance.ChunkPopulator
import net.minestom.server.instance.batch.ChunkBatch
import net.minestom.server.world.biomes.Biome
import java.util.*

/** Flat world generation based off of [ChunkGenerator] */
class Flat(
    /** List of [FlatLayer]s used to generate the flat world. */
    private vararg val layers: FlatLayer,

    /** List of chunk populaters to be used for chunk generation. */
    private val chunkPopulators: List<ChunkPopulator> = listOf()
) : ChunkGenerator {

    override fun generateChunkData(batch: ChunkBatch, chunkX: Int, chunkZ: Int) {
        for (x in 0 until Chunk.CHUNK_SIZE_X) for (z in 0 until Chunk.CHUNK_SIZE_Z) {
            var y = 0
            for (layer in layers) for (yLoop in y until y + layer.thickness) {
                batch.setBlock(x, y, z, layer.block)
                y++
            }
        }
    }

    override fun fillBiomes(biomes: Array<Biome>, chunkX: Int, chunkZ: Int) =
        Arrays.fill(biomes, MinecraftServer.getBiomeManager().getById(0))


    override fun getPopulators() = chunkPopulators
}