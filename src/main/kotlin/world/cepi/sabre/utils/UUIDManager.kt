package world.cepi.sabre.utils

import net.minestom.server.MinecraftServer
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.math.BigInteger
import java.util.*

private val client = OkHttpClient()

/**
 * Turns a UUID with no hyphens (`-`) to a UUID containing hyphens
 *
 * [Note: This is from StackOverflow](https://stackoverflow.com/questions/18871980)
 *
 * @param string The UUID as a string
 *
 * @return A valid UUID
 */
fun toValidUuid(string: String): UUID {
    return UUID(
            BigInteger(string.substring(0, 16), 16).toLong(),
            BigInteger(string.substring(16), 16).toLong())
}

/**
 * Gets a UUID from a [net.minestom.server.entity.Player]'s username
 *
 * @param username The username to get the UUID from
 * @return A [UUID] retrieved from the `Mojang` API.
 */
fun getUUID(username: String): UUID? {
    val response = client.newCall(Request.Builder()
            .url("https://api.mojang.com/users/profiles/minecraft/$username")
            .build()).execute()

    return if (response.code == 204) null
    else toValidUuid(JSONObject(response.body?.string())["id"].toString())
}

fun getUUIDInternally(username: String): UUID? = MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.username == username }?.uuid