package com.octenexin.inifield.world.dimension;

import com.octenexin.inifield.IniField;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModDimension {
    public static final RegistryKey<World> AETHER = RegistryKey.create(Registry.DIMENSION_REGISTRY, IniField.locate("aether"));
    public static final RegistryKey<World> EXTREM = RegistryKey.create(Registry.DIMENSION_REGISTRY, IniField.locate("extrem"));
    public static final RegistryKey<World> EXTREM_FRONTIER = RegistryKey.create(Registry.DIMENSION_REGISTRY, IniField.locate("extrem_frontier"));


    public static void setupDimension() {
        //BzChunkGenerator.registerChunkgenerator();
        AetherBiomeProvider.registerBiomeProvider();
    }

}
