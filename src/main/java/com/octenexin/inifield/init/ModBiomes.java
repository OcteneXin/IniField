package com.octenexin.inifield.init;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Reference.MOD_ID);

    // Dummy biomes to reserve the numeric ID safely for the json biomes to overwrite.
    // No static variable to hold as these dummy biomes should NOT be held and referenced elsewhere.
    // Technically, this isn't needed at all for my mod. Legacy code.
    static {
        createBiome("aether_plain", BiomeMaker::theVoidBiome);
        createBiome("furry_cloud_plain", BiomeMaker::theVoidBiome);
        createBiome("metasequoia_patch", BiomeMaker::theVoidBiome);
        createBiome("countryside_wheat", BiomeMaker::theVoidBiome);
        createBiome("city", BiomeMaker::theVoidBiome);
        createBiome("extrem_plain", BiomeMaker::theVoidBiome);
        createBiome("extrem_frontier_plate", BiomeMaker::theVoidBiome);
    }

    public static RegistryObject<Biome> createBiome(String name, Supplier<Biome> biome) {
        return BIOMES.register(name, biome);
    }

    public static final RegistryKey<Biome> COUNTRYSIDE_WHEAT=registerBiome("countryside_wheat");
    public static final RegistryKey<Biome> CITY=registerBiome("city");
    public static final RegistryKey<Biome> EXTREM_PLAIN=registerBiome("extrem_plain");
    public static final RegistryKey<Biome> EXTREM_FRONTIER_PLAIN=registerBiome("extrem_frontier_plain");
    //more

    public static RegistryKey<Biome> registerBiome(String biomeName) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, IniField.locate(biomeName));
    }

    //not use, we add them at CommonSetupEvent.java
/*    public static void registerBiomes(){
        BiomeManager.addAdditionalOverworldBiomes(COUNTRYSIDE_WHEAT);
        BiomeManager.addAdditionalOverworldBiomes(CITY);
    }*/

}
