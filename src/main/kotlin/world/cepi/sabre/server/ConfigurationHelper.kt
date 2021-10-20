package world.cepi.sabre.server

import kotlinx.serialization.json.Json

object ConfigurationHelper {

    val format = Json {
        encodeDefaults = true
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

}
