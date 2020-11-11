package world.cepi.sabre.loaders

import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import world.cepi.sabre.config
import javax.naming.ConfigurationException
import kotlin.jvm.Throws

object VelocityLoader : Loader {
    override fun load() {
        try { checkConfig() } catch (e: ConfigurationException) { e.printStackTrace(); return }
        if (config().enableVelocity )VelocityProxy.enable(config().velocitySecret)
    }
}

object BungeeLoader : Loader {
    override fun load() {
        try { checkConfig() } catch (e: ConfigurationException) { e.printStackTrace(); return }
        if (config().enableBungee) BungeeCordProxy.enable()
    }
}

@Throws(ConfigurationException::class)
private fun checkConfig() {
    val config = config()
    if (config.enableVelocity && config.enableBungee) throw ConfigurationException("Velocity and BungeeCord cannot be enabled at the same time!")
}