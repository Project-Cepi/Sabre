package world.cepi.sabre

import com.google.gson.Gson
import java.io.File

class Config {
    var ip = ""
    var port = 0

    fun save() {
        val jsonStr = Gson().toJson(this)

        val configFile = File("./sabre-config.json")
        configFile.writeText(jsonStr)
    }
}