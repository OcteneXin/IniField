package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.*;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
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
    public static final RegistryObject<Feature<NoFeatureConfig>> WHEAT_FARMLAND = FEATURES.register("wheat_farmland", () -> new WheatFarmlandFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> BROKEN_BARN = FEATURES.register("broken_barn", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> SMALL_BARN = FEATURES.register("small_barn", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> REDSTONE_LIGHT = FEATURES.register("redstone_light", () -> new FeatureRedstoneLight(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> WHEAT_MILL = FEATURES.register("wheat_mill", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SPRINKLER_IRRIGATION = FEATURES.register("sprinkler_irrigation", () -> new FeatureSprinklerIrrigation(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SCATTERED_STONE = FEATURES.register("scattered_stone", () -> new FeatureScatteredStone(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> BREAKFAST_STORE = FEATURES.register("breakfast_store", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> MILKTEA_STORE = FEATURES.register("milktea_store", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> COUNTRY_MARKET = FEATURES.register("country_market", () -> new FeatureBarn(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> ROUND_SPACE = FEATURES.register("round_space", () -> new FeatureBarn(NbtFeatureConfig.CODEC));

}
