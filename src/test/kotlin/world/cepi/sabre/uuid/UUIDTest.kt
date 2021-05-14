package world.cepi.sabre.uuid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.sabre.server.utils.getUUID
import world.cepi.sabre.server.utils.toValidUuid
import java.util.*

class UUIDTest {

    @Test
    fun `UUIDs should be parsed correctly`() {

        val uuid = UUID.randomUUID()
        val uuidAsString = uuid.toString().replace("-", "")

        assertEquals(uuid, toValidUuid(uuidAsString))

    }

    @Test
    fun `verify UUID connection to http`() {
        assertEquals(toValidUuid("45f50155c09f4fdcb5cee30af2ebd1f0"), getUUID("_jeb"))

        assertEquals(null, getUUID(""))

        assertEquals(toValidUuid("f7c77d999f154a66a87dc4a51ef30d19"), getUUID("hypixel"))
    }

}