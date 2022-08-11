package world.cepi.sabre.server.loaders

import net.minestom.server.extras.velocity.VelocityProxy
import world.cepi.sabre.server.Config.Companion.config

internal fun proxyLoader() {
    when (config.proxy) {
        Forwarder.VELOCITY -> VelocityProxy.enable(config.velocitySecret)
        Forwarder.NONE -> Unit
    }
}

/**
 * Represents the type of proxy used.
 */
enum class Forwarder {
    VELOCITY,
    NONE
}
