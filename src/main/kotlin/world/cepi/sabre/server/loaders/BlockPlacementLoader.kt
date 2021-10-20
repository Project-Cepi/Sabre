package world.cepi.sabre.server.loaders

import net.minestom.server.extras.PlacementRules
import world.cepi.sabre.server.Config.Companion.config

internal fun blockPlacementLoader() {
    if (config.useBlockRules)
        PlacementRules.init()
}
