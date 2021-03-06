package world.cepi.sabre.server.loaders

import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import world.cepi.sabre.server.Config.Companion.config

enum class Forwarder {
    BUNGEE,
    VELOCITY,
    NONE
}

internal fun proxyLoader() {
    when (config.proxy) {
        Forwarder.VELOCITY -> VelocityProxy.enable(config.velocitySecret)
        Forwarder.BUNGEE -> BungeeCordProxy.enable()
    }
}