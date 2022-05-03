package com.octenexin.inifield.mixin;


import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Mixin(BiomeGenerationSettings.class)
public interface ModBiomeGenerationSettingsAccessor {
    @Accessor("features")
    List<List<Supplier<ConfiguredFeature<?, ?>>>> inifield_getFeatures();

    @Mutable
    @Accessor("features")
    void inifield_setFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features);
}
