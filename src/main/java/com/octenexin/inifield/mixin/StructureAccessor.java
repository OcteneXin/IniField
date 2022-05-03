package com.octenexin.inifield.mixin;

import net.minecraft.world.gen.feature.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Structure.class)
public interface StructureAccessor {
    @Mutable
    @Accessor("NOISE_AFFECTING_FEATURES")
    static void inifield_setNOISE_AFFECTING_FEATURES(List<Structure<?>> NOISE_AFFECTING_FEATURES) {
        throw new UnsupportedOperationException();
    }
}