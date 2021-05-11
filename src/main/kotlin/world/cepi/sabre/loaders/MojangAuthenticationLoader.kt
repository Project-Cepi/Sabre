package world.cepi.sabre.loaders

import net.minestom.server.extras.MojangAuth
import world.cepi.sabre.Config.Companion.config

internal fun mojangAuthenticationLoader() {

    if (config.onlineMode)
        MojangAuth.init()

}