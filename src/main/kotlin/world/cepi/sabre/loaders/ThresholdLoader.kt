package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import world.cepi.sabre.Config.Companion.config

internal fun thresholdLoader() {

    MinecraftServer.setCompressionThreshold(config.compressionThreshold)
    MinecraftServer.setPacketCaching(config.cachePackets)

}