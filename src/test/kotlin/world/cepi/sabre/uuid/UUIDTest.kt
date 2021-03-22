package world.cepi.sabre.uuid

import org.junit.jupiter.api.Test
import world.cepi.sabre.utils.getUUID
import world.cepi.sabre.utils.toValidUuid
import java.util.*

class UUIDTest {

    @Test
    fun `UUIDs should be parsed correctly`() {

        val uuid = UUID.randomUUID()
        val uuidAsString = uuid.toString().replace("-", "")

        assert(uuid == toValidUuid(uuidAsString))

    }

    @Test
    fun `verify UUID connection to http`() {
        assert(toValidUuid("45f50155c09f4fdcb5cee30af2ebd1f0") == getUUID("_jeb"))
    }

}