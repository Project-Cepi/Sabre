package world.cepi.sabre.utils

import khttp.get
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
    val request = get(
            url = "https://api.mojang.com/users/profiles/minecraft/$username"
    )
    val requestJson = request.jsonObject

    return if (request.statusCode == 204) null else toValidUuid(requestJson["id"] as String)

}