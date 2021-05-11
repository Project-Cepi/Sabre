package world.cepi.sabre.loaders

import net.minestom.server.extras.PlacementRules
import world.cepi.sabre.Config.Companion.config

internal fun blockPlacementLoader() {
    if (config.useBlockRules)
        PlacementRules.init()
}