package com.octenexin.inifield.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(DimensionStructuresSettings.class)
public interface DimensionStructuresSettingsAccessor {
    @Mutable
    @Accessor("DEFAULTS")
    static void inifield_setDEFAULTS(ImmutableMap<Structure<?>, StructureSeparationSettings> DEFAULTS) {
        throw new UnsupportedOperationException();
    }

}