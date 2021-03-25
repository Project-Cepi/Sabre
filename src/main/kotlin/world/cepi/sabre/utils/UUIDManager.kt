package world.cepi.sabre.utils

import java.lang.Long.parseUnsignedLong
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

const val uuidRadix = 16

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
        if (
            inputStream.read().toChar() == '"'
            && inputStream.read().toChar() == 'i'
            && inputStream.read().toChar() == 'd'
            && inputStream.read().toChar() == '"'
            && inputStream.read().toChar() == ':'
        ) {
            inputStream.skip(1)
            idFound = true
        }
    }

    // Can break at any time.
    return toValidUuid(String(inputStream.readNBytes(uuidRadix * 2))) // reads the length of the UUID
}