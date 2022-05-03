package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.ScatteredWeedGrassFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

    public static final RegistryObject<Feature<ProbabilityConfig>> SCATTERED_WEED_GRASS = FEATURES.register("scattered_weed_grass", () -> new ScatteredWeedGrassFeature(ProbabilityConfig.CODEC));

}
