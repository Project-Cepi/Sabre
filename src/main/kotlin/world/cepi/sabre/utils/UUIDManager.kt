package world.cepi.sabre.utils

import khttp.get
import java.util.*

fun getUUID(username: String): UUID {
    val request = get(
            url = "https://api.mojang.com/users/profiles/minecraft/$username",
            params = mapOf("at" to (System.currentTimeMillis() / 1000).toString())
    ).jsonObject
    return UUID.fromString(request["id"] as String)
}