package world.cepi.sabre.loaders

import net.minestom.server.extras.MojangAuth
import world.cepi.sabre.Config.Companion.config

object MojangAuthenticationLoader : Loader {

    override fun load() {
        if (config.onlineMode) {
            MojangAuth.init();
        }
    }

}