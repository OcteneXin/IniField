package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
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
    }

    public static RegistryObject<Biome> createBiome(String name, Supplier<Biome> biome) {
        return BIOMES.register(name, biome);
    }
}
