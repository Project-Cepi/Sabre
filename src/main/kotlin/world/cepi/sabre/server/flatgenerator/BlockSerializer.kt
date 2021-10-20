package world.cepi.sabre.server.flatgenerator

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.instance.block.Block

@Serializer(forClass = Block::class)
@OptIn(ExperimentalSerializationApi::class)
object BlockSerializer : KSerializer<Block> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("net.minestom.server.instance.block.Block") {
            element<String>("id")
        }

    override fun serialize(encoder: Encoder, value: Block) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.id())
        }
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): Block =
        decoder.decodeStructure(descriptor) {
            var id = 0

            if (decodeSequentially()) {
                id = decodeIntElement(descriptor, 0)
            } else while (true) {
                when (val index = decodeElementIndex((descriptor))) {
                    0 -> id = decodeIntElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            Block.fromBlockId(id) ?: error("Bad ID")
        }
}
