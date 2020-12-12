package world.cepi.sabre.loaders

import net.minestom.server.extras.PlacementRules
import net.minestom.server.instance.block.rule.BlockPlacementRule
import world.cepi.sabre.config

object BlockPlacementLoader : Loader {

    override fun load() {
        if (config().useBlockRules)
            PlacementRules.init()
    }
}