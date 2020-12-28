package world.cepi.sabre.instances.generators.flat

import net.minestom.server.instance.block.Block

/** Represents a layer used in [Flat] generation. */
data class FlatLayer(
        /** The block enum to use to represent the layer */
        val block: Block,

        /** The thickness in blocks */
        val thickness: Int
)