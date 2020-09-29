package world.cepi.sabre

import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.util.logging.Logger


// This class represents Sabre's config, and contains all the properties that can be configured in Sabre
class Config {
    var ip = ""
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
 * Configuration for all classes
 */
fun Any.config(): Config {
    return Config.config
}