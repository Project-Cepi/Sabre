package world.cepi.sabre.server

import kotlinx.serialization.json.Json

val format = Json {
    encodeDefaults = true
    prettyPrint = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
}
