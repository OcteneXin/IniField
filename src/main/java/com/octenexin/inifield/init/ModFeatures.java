package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.NbtFeature;
import com.octenexin.inifield.world.features.ScatteredWeedGrassFeature;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

    public static final RegistryObject<Feature<ProbabilityConfig>> SCATTERED_WEED_GRASS = FEATURES.register("scattered_weed_grass", () -> new ScatteredWeedGrassFeature(ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> CMD_CUBE = FEATURES.register("cmd_cube", () -> new NbtFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> SIMPLE_ST_NC = FEATURES.register("simple_st_nc", () -> new NbtFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> SIMPLE_ST_HB = FEATURES.register("simple_st_hb", () -> new NbtFeature(NbtFeatureConfig.CODEC));

}
