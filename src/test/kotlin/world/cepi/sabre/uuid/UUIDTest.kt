package world.cepi.sabre.uuid

import org.junit.jupiter.api.Test
import world.cepi.sabre.utils.toValidUuid
import java.util.*

class UUIDTest {

    @Test
    fun `UUIDs should be parsed correctly`() {

        val uuid = UUID.randomUUID()
        val uuidAsString = uuid.toString().replace("-", "")

        assert(uuid == toValidUuid(uuidAsString))

    }

}