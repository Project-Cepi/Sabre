package world.cepi.sabre.utils

import com.google.gson.Gson
import khttp.get
import org.http4k.client.JettyClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.json.JSONObject
import java.math.BigInteger
import java.util.*


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
    val client = JettyClient()

    val response = client(Request(Method.GET, "https://api.mojang.com/users/profiles/minecraft/$username"))

    return if (response.status == Status.NO_CONTENT) null else toValidUuid(JSONObject(response.bodyString())["id"].toString())
}