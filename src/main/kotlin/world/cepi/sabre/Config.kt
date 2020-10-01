package world.cepi.sabre

import com.google.gson.Gson
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

    fun save() {
        val jsonStr = Gson().toJson(this)

        val configFile = File(Sabre.CONFIG_LOCATION)
        configFile.writeText(jsonStr)
    }

    companion object {

        /**
         * Configuration object acting as a singleton, as a `Gson` workaround.
         */
        var config: Config
            private set

        init {
            // If it already exists, parse as normal with GSON
            if (exists()) {
                config = Gson().fromJson(FileReader(Sabre.CONFIG_LOCATION), Config::class.java)
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