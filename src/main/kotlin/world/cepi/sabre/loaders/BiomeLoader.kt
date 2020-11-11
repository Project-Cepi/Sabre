package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.utils.NamespaceID
import net.minestom.server.world.biomes.Biome


/** Fixes an issue with Optifine where the client would crash due to unregistered swamp biomes*/
object BiomeLoader : Loader {
    override fun load() {
        val biomeManager = MinecraftServer.getBiomeManager()
        biomeManager.addBiome(Biome.builder().name(NamespaceID.from("swamp")).build())
        biomeManager.addBiome(Biome.builder().name(NamespaceID.from("swamp_hills")).build())
    }
}