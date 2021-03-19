package world.cepi.sabre.utils

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
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
    val builder: StringBuilder = StringBuilder(string)

    /* Backwards adding to avoid index adjustments */
    builder.insert(20, '-')
    builder.insert(16, '-')
    builder.insert(12, '-')
    builder.insert(8, '-')

    return UUID.fromString(builder.toString())
}

/**
 * Gets a UUID from a [net.minestom.server.entity.Player]'s username
 *
 * @param username The username to get the UUID from
 *
 * @return A [UUID] retrieved from the `Mojang` API.
 */
fun getUUID(username: String): UUID? {
    val connection = URL("https://api.mojang.com/users/profiles/minecraft/$username").openConnection() as HttpURLConnection

    val inputStream: InputStream = if (connection.responseCode == 200) {
        connection.inputStream
    } else {
        return null
    }

    return toValidUuid(JSONObject(inputStream.bufferedReader().use(BufferedReader::readText))["id"].toString())
}

