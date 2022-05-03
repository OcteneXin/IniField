package com.octenexin.inifield.world.dimension;

import com.octenexin.inifield.IniField;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BzDimension {
    public static final RegistryKey<World> AETHER = RegistryKey.create(Registry.DIMENSION_REGISTRY, IniField.locate("aether"));

    public static void setupDimension() {
        //BzChunkGenerator.registerChunkgenerator();
        BzBiomeProvider.registerBiomeProvider();
    }

}
