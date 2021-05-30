package world.cepi.sabre.server

import kotlinx.serialization.Serializable
import net.minestom.server.instance.block.Block
import world.cepi.sabre.server.flatgenerator.FlatLayer
import world.cepi.sabre.server.loaders.Forwarder
import java.lang.IllegalArgumentException

/** This class represents Sabre's config, and contains all the properties that can be configured in Sabre */
@Serializable
class Config(
    /** The IP that Minestom is hosted on -- For local hosting, feel free to use `0.0.0.0` or `localhost` */
    val ip: String = "0.0.0.0",

    /** The port the server is hosted on. The universal default is `25565` */
    val port: Int = 25565,

    /** Default op level. Defaults to 4 */
    val opLevel: Int = 4,

    /** If the server should use Mojang Authentication or not. */
    val onlineMode: Boolean = true,

    /** Whether or not the server should use forwarding. */
    val proxy: Forwarder = Forwarder.NONE,

    /** The secret for velocity. Not used if bungeecord is set as the proxy value. */
    val velocitySecret: String = "",

    /** The base view distance of all chunks. */
    val renderDistance: Int = 8,

    /** How far the player can see entities. */
    val entityDistance: Int = 8,

    /** Use the built in Dynamic flat generator */
    val useFlatGenerator: Boolean = true,

    /** The flat layers the flat generator should use */
    val flatLayers: Array<FlatLayer> = arrayOf(
        FlatLayer(Block.BEDROCK, 1),
        FlatLayer(Block.STONE, 25),
        FlatLayer(Block.DIRT, 7),
        FlatLayer(Block.GRASS_BLOCK, 1)
    ),

    /**
     * Fixes a crash with old Optifine clients by registering certain biomes.
     * If you specifically do not want these biomes to be registered, set to false.
     * But be aware that older Optifine clients will crash when they connect to the server
     * unless you specify them
     */
    val optifineSupport: Boolean = true,

    /** The unknown message for an unknown command. */
    val unknownMessage: String = "Unknown command.",

    /** Uses Minestom's vanilla block rules to place blocks correctly. */
    val useBlockRules: Boolean = true,

    /**
     * The compression threshold for the network.
     * Useful to set if you're behind a proxy.
     * Set to -1 to disable.
     */
    val compressionThreshold: Int = 256,

    /** If the server should cache packets. */
    val cachePackets: Boolean = true,

    /** If the player should respawn at their spawn point. */
    val shouldRespawnAtSpawnPoint: Boolean = true,

    /**
     * How fast time moves (flat generator).
     * Set to 0 to not move. (will look laggy unless time is set to negative)
     */
    val timeRate: Int = 1,

    /**
     * The starting time of (flat generator).
     * Set to negative (as well as timeRate to 0) to disable daylight cycle.
     */
    val time: Long = 0,

    /** If the OP command and loader should be used. */
    val opUtilities: Boolean = true,

    /** If Sabre should use the default file storage system */
    val useFileStorageSystem: Boolean = true,
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
