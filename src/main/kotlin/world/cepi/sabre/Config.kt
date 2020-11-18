package world.cepi.sabre

import com.beust.klaxon.Klaxon
import java.io.File
import java.io.FileReader


// This class represents Sabre's config, and contains all the properties that can be configured in Sabre
class Config {
    /** The IP that Minestom is hosted on -- For local hosting, feel free to use `0.0.0.0` or `localhost` */
    var ip = ""

    /** The port the server is hosted on. The universal default is `25565` */
    var port = 25565

    /** Whether or not the whitelist is enabled. Defaults to false */
    var whitelist = false

    /** Default op level. Defaults to 4 */
    var opLevel = 4

    /** If the server should use Mojang Authentication or not. */
    var onlineMode = true

    /** Whether or not the server should use forwarding.*/
    var proxy = "NONE"

    /** The secret for velocity. Not used if bungeecord is set as the proxy value. */
    var velocitySecret = ""

    /** The base view distance of all chunks. */
    var renderDistance = 8

    /** How far the player can see entities. */
    var entityDistance = 8

    /** Fixes a crash with Optifine clients by registering certain biomes.
     * If you specifically do not want these biomes to be registered, set to false.
     * But be aware that Optifine clients will crash when they connect to the server.*/
    var optifineSupport = true

    fun save() {
        val jsonStr = Klaxon().toJsonString(this)

        val configFile = File(Sabre.CONFIG_LOCATION)
        configFile.writeText(jsonStr)
    }

    companion object {

        /** Configuration object acting as a singleton */
        var config: Config
            private set

        init {
            // If it already exists, parse as normal
            if (exists()) {
                config = Klaxon().parse<Config>(FileReader(Sabre.CONFIG_LOCATION))!!
            } else {
                // Create a new config and save it.
                config = Config()
                config.save()
            }
        }

        private fun exists(): Boolean = File(Sabre.CONFIG_LOCATION).exists()
    }

}

/**
 * Exposed configuration so no one has to do `Config.config`
 *
 * @return The configuration from the internal [Config] object. Shorthand for [Config.config]
 */
fun config(): Config {
    return Config.config
}

enum class Forwarder {
    BUNGEE,
    VELOCITY,
    NONE
}