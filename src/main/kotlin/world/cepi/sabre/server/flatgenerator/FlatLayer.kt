package world.cepi.sabre.server.flatgenerator

import kotlinx.serialization.Serializable
import net.minestom.server.instance.block.Block

/** Represents a layer used in [Flat] generation. */
@Serializable
data class FlatLayer(
        /** The block enum to use to represent the layer */
        @Serializable(with = BlockSerializer::class)
        val block: Block,

        /** The thickness in blocks */
        val thickness: Int
)