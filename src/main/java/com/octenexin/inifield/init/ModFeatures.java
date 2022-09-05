package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.*;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> WATER_CANDLE = FEATURES.register("water_candle", () -> new WaterCandleFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> FLOATING_BOAT = FEATURES.register("floating_boat", () -> new FloatingBoatFeature(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> DOCK = FEATURES.register("dock", () -> new DockFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> EXTERM_BEACON = FEATURES.register("exterm_beacon", () -> new ExtermBeaconFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WHEAT_FARMLAND = FEATURES.register("wheat_farmland", () -> new WheatFarmlandFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> FEATURE_ON_GRASS = FEATURES.register("feature_on_grass", () -> new FeatureOnGrass(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> FEATURE_ON_DIRT = FEATURES.register("feature_on_dirt", () -> new FeatureOnGrass(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> REDSTONE_LIGHT = FEATURES.register("redstone_light", () -> new FeatureRedstoneLight(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> FLOOR_LANTERN = FEATURES.register("floor_lantern", () -> new FeatureFloorLantern(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new FeatureScarecrow(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SPRINKLER_IRRIGATION = FEATURES.register("sprinkler_irrigation", () -> new FeatureSprinklerIrrigation(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SCATTERED_STONE = FEATURES.register("scattered_stone", () -> new FeatureScatteredStone(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> FROZEN_FERN = FEATURES.register("frozen_fern", () -> new FeatureFern(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NbtFeatureConfig>> NOTCH_SCULPTURE = FEATURES.register("notch_sculpture", () -> new FeatureNotchSculpture(NbtFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SCATTERED_ICE = FEATURES.register("scattered_ice", () -> new FeatureScatteredIce(NoFeatureConfig.CODEC));

}
