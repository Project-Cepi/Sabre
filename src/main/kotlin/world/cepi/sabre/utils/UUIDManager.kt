package world.cepi.sabre.utils

import khttp.get
import java.util.*


// This is a convenience function to fetch a UUID from Mojang's servers, based on a player's username
fun getUUID(username: String): UUID {
    val request = get(
            url = "https://api.mojang.com/users/profiles/minecraft/$username",
            params = mapOf("at" to (System.currentTimeMillis() / 1000).toString())
    ).jsonObject
    return UUID.fromString(request["id"] as String)
}