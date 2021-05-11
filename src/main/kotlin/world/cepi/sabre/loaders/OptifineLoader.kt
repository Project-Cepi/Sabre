package world.cepi.sabre.loaders

import net.minestom.server.extras.optifine.OptifineSupport
import world.cepi.sabre.Config.Companion.config

internal fun optifineLoader() {

    if (config.optifineSupport)
        OptifineSupport.enable()

}