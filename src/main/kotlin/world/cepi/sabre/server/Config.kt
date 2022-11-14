package world.cepi.sabre.server

import kotlinx.serialization.Serializable
import world.cepi.sabre.server.loaders.Forwarder

/** This class represents Sabre's config, and contains all the properties that can be configured in Sabre */
@Serializable
class Config(
    /** The IP that Minestom is hosted on -- For local hosting, feel free to use `0.0.0.0` or `localhost` */
    val ip: String = "0.0.0.0",

    /** The port the server is hosted on. The universal default is `25565` */
    val port: Int = 25565,

    /** If the server should use Mojang Authentication or not. */
    val onlineMode: Boolean = true,

    /** Whether or not the server should use forwarding. */
    val proxy: Forwarder = Forwarder.NONE,

    /** The secret for velocity. Unused if BungeeCord is set as the forwarder. */
    val velocitySecret: String = "",

    /** The base view distance of all chunks. */
    val renderDistance: Int = 8,

    /** How far the player can see entities. */
    val entityDistance: Int = 5,

    /** The unknown message for an unknown command. */
    val unknownMessage: String = "Unknown command",

    /** Uses Minestom's vanilla block rules to place blocks correctly. */
    val useBlockRules: Boolean = false,

    /** Whether ClassName.MethodName should print for info errors */
    val detailedConsole: Boolean = true,

    val tps: Int = 20,

    /**
     * The compression threshold for the network.
     * Useful to set if you're behind a proxy.
     * Set to 0 to disable.
     */
    val compressionThreshold: Int = 0,

    /** If the player should respawn at their spawn point. */
    val shouldRespawnAtSpawnPoint: Boolean = false,
) {

    companion object {

        // Allows for custom config setting during boot.
        private var _config: Config? = null

        var config: Config
            get() = _config ?: run {
                throw IllegalArgumentException("Config does not exist! Set it correctly or boot from a file.")
            }
            set(value) {
                _config = value
            }
    }

}
