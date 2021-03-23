package world.cepi.sabre.utils

import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

const val radix = 16

fun uuidOptimizedParseLong(s: CharSequence): Long {

    var i = 0

    var result = 0L

    while (i < radix - 1) { // Length of UUID
        // Accumulating negatively avoids surprises near MAX_VALUE
        val digit = Character.digit(s[i], radix).toLong()
        result *= radix.toLong()
        i++
        result += digit
    }

    return result
}

fun uuidOptimizedParseUnsignedLong(s: String): Long {

    val first = uuidOptimizedParseLong(s)
    val second = Character.digit(s[radix - 1], radix)

    return first * radix + second

}

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
        uuidOptimizedParseUnsignedLong(string.substring(0, radix)),
        uuidOptimizedParseUnsignedLong(string.substring(radix))
    )
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

    inputStream.skipNBytes(21) // Skips the first few characters in JSON

    // Can break at any time.
    return toValidUuid(String(inputStream.readNBytes(radix * 2), StandardCharsets.UTF_8)) // reads the length of the UUID
}

