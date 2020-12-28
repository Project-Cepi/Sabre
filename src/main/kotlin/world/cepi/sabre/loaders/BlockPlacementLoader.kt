package world.cepi.sabre.loaders

import net.minestom.server.extras.PlacementRules
import world.cepi.sabre.Config.Companion.config

object BlockPlacementLoader : Loader {

    override fun load() {
        if (config.useBlockRules)
            PlacementRules.init()
    }
}