package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.server.Config.Companion.config

internal fun thresholdLoader() {

    MinecraftServer.setCompressionThreshold(config.compressionThreshold)
    MinecraftServer.setPacketCaching(config.cachePackets)

}