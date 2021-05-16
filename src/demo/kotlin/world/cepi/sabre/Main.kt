package world.cepi.sabre

import net.minestom.server.instance.block.Block
import world.cepi.sabre.server.Config
import world.cepi.sabre.server.flatgenerator.FlatLayer

fun main(args: Array<String>) = SabreLoader.boot(Config(
    flatLayers = arrayOf(
        FlatLayer(Block.BEDROCK, 40)
    )
))
