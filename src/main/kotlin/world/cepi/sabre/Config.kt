package world.cepi.sabre

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import world.cepi.sabre.instances.generators.flat.Flat
import java.io.File
import java.io.FileReader


// This class represents Sabre's config, and contains all the properties that can be configured in Sabre
@Serializable
class Config(
        /** The IP that Minestom is hosted on -- For local hosting, feel free to use `0.0.0.0` or `localhost` */
        val ip: String = "",

        /** The port the server is hosted on. The universal default is `25565` */
        val port: Int = 25565,

        /** Whether or not the whitelist is enabled. Defaults to false */
        val whitelist: Boolean = false,

        /** Default op level. Defaults to 4 */
        val opLevel: Int = 4,

        /** If the server should use Mojang Authentication or not. */
        val onlineMode: Boolean = true,

        /** Whether or not the server should use forwarding.*/
        val proxy: Forwarder = Forwarder.NONE,

        /** The secret for velocity. Not used if bungeecord is set as the proxy value. */
        val velocitySecret: String = "",

        /** The base view distance of all chunks. */
        val renderDistance: Int = 8,

        /** How far the player can see entities. */
        val entityDistance: Int = 8,

        /** Fixes a crash with Optifine clients by registering certain biomes.
         * If you specifically do not want these biomes to be registered, set to false.
         * But be aware that Optifine clients will crash when they connect to the server.*/
        val optifineSupport: Boolean = true,
) {
    fun save() = File(Sabre.CONFIG_LOCATION).writeText(Json.encodeToString(this))

    companion object {

        /** Configuration object acting as a singleton */
        var config: Config
            private set

        init {
            // If it already exists, parse as normal
            if (exists()) {
                config = Json.decodeFromString(File(Sabre.CONFIG_LOCATION).readText())
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