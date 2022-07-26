package world.cepi.sabre.server.flatgenerator

import net.minestom.server.instance.generator.GenerationUnit

fun flat(
    unit: GenerationUnit,
    /** List of [FlatLayer]s used to generate the flat world. */
    vararg layers: FlatLayer
) {
    var y = 0
    layers.forEach {
        unit.modifier().fillHeight(y, y + it.thickness, it.block)
        y += it.thickness
    }
}
