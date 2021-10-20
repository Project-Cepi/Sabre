package world.cepi.sabre.server.loaders

import net.minestom.server.extras.MojangAuth
import world.cepi.sabre.server.Config.Companion.config

internal fun mojangAuthenticationLoader() {

    if (config.onlineMode)
        MojangAuth.init()

}
