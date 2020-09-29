package world.cepi.sabre.utils

import khttp.get
import java.util.*

/**
 * Turns a UUID with no hyphens (`-`) to a UUID containing hyphens
 */
private fun toUuidString(string: String): String {
    require(string.length == 32) { "invalid input string!" }
    val input = string.toCharArray()
    val output = CharArray(36)
    System.arraycopy(input, 0, output, 0, 8)
    System.arraycopy(input, 8, output, 9, 4)
    System.arraycopy(input, 12, output, 14, 4)
    System.arraycopy(input, 16, output, 19, 4)
    System.arraycopy(input, 20, output, 24, 12)
    output[8] = '-'
    output[13] = '-'
    output[18] = '-'
    output[23] = '-'
    return String(output)
}

/**
 * Gets a UUID from a [Player]'s username
 *
 * @param username The username to get the UUID from
 * @return A [UUID] retrieved from the `Mojang` API.
 */
fun getUUID(username: String): UUID {
    val request = get(
            url = "https://api.mojang.com/users/profiles/minecraft/$username",
            params = mapOf("at" to (System.currentTimeMillis() / 1000).toString())
    ).jsonObject
    return UUID.fromString(toUuidString(request["id"] as String))
}