package world.cepi.sabre.loaders

import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import world.cepi.sabre.Forwarder
import world.cepi.sabre.config

val config = config()
var proxy: Forwarder = Forwarder.NONE

object VelocityLoader : Loader {
    override fun load() {
        if (checkConfig() && proxy == Forwarder.VELOCITY ) VelocityProxy.enable(config().velocitySecret)
    }
}

object BungeeLoader : Loader {
    override fun load() {
        if (checkConfig() && proxy == Forwarder.BUNGEE) BungeeCordProxy.enable()
    }
}

private fun checkConfig(): Boolean = try {proxy = Forwarder.valueOf(config.proxy.toUpperCase()); true}
catch (e: IllegalArgumentException) {
    println("ERROR! Configured proxy does not match one of the following: BungeeCord, Velocity, None"); false
}