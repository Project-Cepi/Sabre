package world.cepi.sabre.server.loaders

import net.minestom.server.extras.optifine.OptifineSupport
import world.cepi.sabre.server.Config.Companion.config

internal fun optifineLoader() {

    if (config.optifineSupport)
        OptifineSupport.enable()

}
