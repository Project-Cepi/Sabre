package world.cepi.sabre

import com.beust.klaxon.Klaxon
import java.io.File
import java.io.FileReader
import java.util.logging.Logger


// This class represents Sabre's config, and contains all the properties that can be configured in Sabre
class Config {
    /**
     * The IP that Minestom is hosted on -- For local hosting, feel free to use `0.0.0.0` or `localhost`
     */
    var ip = ""

    /**
     * The port the server is hosted on. The universal default is `25565`
     */
    var port = 25565

    // Whether or not the whitelist is enabled. Defaults to false
    var whitelist = false

    fun save() {
        val jsonStr = Klaxon().toJsonString(this)

        val configFile = File(Sabre.CONFIG_LOCATION)
        configFile.writeText(jsonStr)
    }

    companion object {

        /**
         * Configuration object acting as a singleton
         */
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
 */
fun config(): Config {
    return Config.config
}