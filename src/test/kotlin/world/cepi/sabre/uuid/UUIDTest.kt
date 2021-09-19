package world.cepi.sabre.uuid

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.sabre.server.utils.getUUID
import world.cepi.sabre.server.utils.toValidUuid
import java.util.*

class UUIDTest : StringSpec({
    "UUIDs should parse correctly" {
        val uuid = UUID.randomUUID()
        val uuidAsString = uuid.toString().replace("-", "")

        uuidAsString.toValidUuid() shouldBe uuid
    }

    "Connections should work correctly" {
        getUUID("_jeb") shouldBe "45f50155c09f4fdcb5cee30af2ebd1f0".toValidUuid()
        getUUID("").shouldBeNull()
        getUUID("hypixel") shouldBe "f7c77d999f154a66a87dc4a51ef30d19".toValidUuid()
    }
})