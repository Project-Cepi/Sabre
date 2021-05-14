package world.cepi.sabre.server.utils

import java.lang.Long.parseUnsignedLong
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

internal const val uuidRadix = 16

/**
 * Turns a UUID with no hyphens (`-`) to a UUID containing hyphens
 *
 * @param string The UUID as a String
 *
 * @return A valid UUID
 */
fun toValidUuid(string: String) = UUID(
    parseUnsignedLong(string.substring(0, uuidRadix), uuidRadix),
    parseUnsignedLong(string.substring(uuidRadix), uuidRadix)
)

private const val commaChar = ','.code

/**
 * Gets a UUID from a [net.minestom.server.entity.Player]'s username
 *
 * @param username The username to get the UUID from
 *
 * @return A [UUID] retrieved from the `Mojang` API.
 */
fun getUUID(username: String): UUID? {
    val connection = URL("https://api.mojang.com/users/profiles/minecraft/$username").openConnection() as HttpURLConnection

    val inputStream = if (connection.responseCode == 200) {
        connection.inputStream
    } else {
        return null
    }

    var idFound = false
    while (!idFound) {

        if (inputStream.available() == 0) return null

        // find the first comma
        if (inputStream.read() == commaChar) {
            // skip the next quote ("id":")
            inputStream.skip(6)

            // mark the UUID as found
            idFound = true
        }
    }

    // Can break at any time.
    return toValidUuid(String(inputStream.readNBytes(uuidRadix * 2))) // reads the length of the UUID
}