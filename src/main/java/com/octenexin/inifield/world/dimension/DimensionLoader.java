package com.octenexin.inifield.world.dimension;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.gen.AetherChunkGen;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionLoader {

    public static final RegistryKey<World> AETHER = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID,"aether"));

    public static void setupDimension() {
        //AetherChunkGen.registerChunkgenerator();
    }
}
