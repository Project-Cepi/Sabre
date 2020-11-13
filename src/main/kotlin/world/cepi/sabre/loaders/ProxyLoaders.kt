package world.cepi.sabre.loaders

import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import world.cepi.sabre.Forwarder
import world.cepi.sabre.config

val proxy: Forwarder
    get() {
        return try {
            Forwarder.valueOf(config().proxy.toUpperCase())
        } catch(exception: Exception) {
            println("ERROR! Configured proxy does not match one of the following: BungeeCord, Velocity, None")
            Forwarder.NONE
        }
    }

object VelocityLoader : Loader {
    override fun load() {
        if (proxy == Forwarder.VELOCITY) VelocityProxy.enable(config().velocitySecret)
    }
}

object BungeeLoader : Loader {
    override fun load() {
        if (proxy == Forwarder.BUNGEE) BungeeCordProxy.enable()
    }
}