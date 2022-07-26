package world.cepi.sabre.server.flatgenerator

import kotlinx.serialization.Serializable
import net.minestom.server.instance.block.Block

/** Represents a layer used in [flat] generation. */
@Serializable
@JvmRecord
data class FlatLayer(
    /** The block enum to use to represent the layer */
    @Serializable(with = BlockSerializer::class)
    val block: Block,

    /** The thickness in blocks */
    val thickness: Int
)
