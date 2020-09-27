package world.cepi.sabre

import com.google.gson.Gson
import java.io.File


// This class represents Sabre's config, and is designed to be serialized by Gson. DO NOT initialize it on your own
class Config {
    var ip = ""
    var port = 0

    fun save() {
        val jsonStr = Gson().toJson(this)

        val configFile = File("./sabre-config.json")
        configFile.writeText(jsonStr)
    }
}