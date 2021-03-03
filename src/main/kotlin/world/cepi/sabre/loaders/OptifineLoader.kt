package world.cepi.sabre.loaders

import net.minestom.server.extras.optifine.OptifineSupport
import world.cepi.sabre.Config.Companion.config

object OptifineLoader : Loader {
    override fun invoke() {
        if (config.optifineSupport)
            OptifineSupport.enable()
    }
}