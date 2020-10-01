package world.cepi.sabre.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class UUIDManagerKtTest {
    @Test
    fun `should turn an invalid UUID to an actual UUID`() {
        val uuid = UUID.randomUUID()

        assertEquals(toValidUuid(uuid.toString().replace("-", "")), uuid)
    }
}