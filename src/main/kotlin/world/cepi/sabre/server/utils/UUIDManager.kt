package world.cepi.sabre.server.utils

import java.lang.Long.parseUnsignedLong
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

internal const val UUID_RADIX = 16

/**
 * Turns a UUID with no hyphens (`-`) to a UUID containing hyphens
 *
 * @param string The UUID as a String
 *
 * @return A valid UUID
 */
fun String.toValidUuid() = UUID(
    parseUnsignedLong(substring(0, UUID_RADIX), UUID_RADIX),
    parseUnsignedLong(substring(UUID_RADIX), UUID_RADIX)
)

/** Comma character */
private const val COMMA_CHARACTER = ','.code

/** HTTP Success char */
private const val successCode = 200;

/**
 * Gets a UUID from a [net.minestom.server.entity.Player]'s username
 *
 * @param username The username to get the UUID from
 *
 * @return A [UUID] retrieved from the `Mojang` API.
 */
fun getUUID(username: String): UUID? {
    val connection = URL("https://api.mojang.com/users/profiles/minecraft/$username").openConnection() as HttpURLConnection

    val inputStream = if (connection.responseCode == successCode) {
        connection.inputStream
    } else {
        return null
    }

    var idFound = false
    while (!idFound) {

        if (inputStream.available() == 0) return null

        // find the first comma
        if (inputStream.read() == COMMA_CHARACTER) {
            // skip the next quote ("id":")
            inputStream.skip(6)

            // mark the UUID as found
            idFound = true
        }
    }

    // Can break at any time.
    return String(inputStream.readNBytes(UUID_RADIX * 2)).toValidUuid() // reads the length of the UUID
}