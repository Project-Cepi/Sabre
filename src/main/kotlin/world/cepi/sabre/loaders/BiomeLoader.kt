package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.utils.NamespaceID
import net.minestom.server.world.biomes.Biome
import net.minestom.server.world.biomes.BiomeEffects


/** Fixes an issue with Optifine where the client would crash due to unregistered swamp biomes*/
object BiomeLoader : Loader {

    // TODO clean this hot mess
    override fun load() {
        val biomeManager = MinecraftServer.getBiomeManager()
        biomeManager.addBiome(Biome.builder()
                .category(Biome.Category.NONE)
                .name(NamespaceID.from("minecraft:swamp"))
                .temperature(0.8F)
                .downfall(0.4F)
                .depth(0.125F)
                .scale(0.05F)
                .effects(BiomeEffects.builder()
                        .fog_color(0xC0D8FF)
                        .sky_color(0x78A7FF)
                        .water_color(0x3F76E4)
                        .water_fog_color(0x50533)
                        .build())
                .build())
        biomeManager.addBiome(Biome.builder()
                .category(Biome.Category.NONE)
                .name(NamespaceID.from("minecraft:swamp_hills"))
                .temperature(0.8F)
                .downfall(0.4F)
                .depth(0.125F)
                .scale(0.05F)
                .effects(BiomeEffects.builder()
                        .fog_color(0xC0D8FF)
                        .sky_color(0x78A7FF)
                        .water_color(0x3F76E4)
                        .water_fog_color(0x50533)
                        .build())
                .build())
        biomeManager.addBiome(Biome.builder()
                .category(Biome.Category.NONE)
                .name(NamespaceID.from("minecraft:jungle"))
                .temperature(0.8F)
                .downfall(0.4F)
                .depth(0.125F)
                .scale(0.05F)
                .effects(BiomeEffects.builder()
                        .fog_color(0xC0D8FF)
                        .sky_color(0x78A7FF)
                        .water_color(0x3F76E4)
                        .water_fog_color(0x50533)
                        .build())
                .build())
    }
}