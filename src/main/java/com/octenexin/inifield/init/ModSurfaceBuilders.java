package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.surfacebuilders.CitySurfaceBuilder;
import com.octenexin.inifield.world.surfacebuilders.CountrysideWheatSurfaceBuilder;
import com.octenexin.inifield.world.surfacebuilders.FurryCloudSurfaceBuilder;
import com.octenexin.inifield.world.surfacebuilders.GrassSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Reference.MOD_ID);

    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GRASS_SURFACE_BUILDER = SURFACE_BUILDERS.register("grass_surface_builder", () -> new GrassSurfaceBuilder(SurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> FURRY_CLOUD_SURFACE_BUILDER = SURFACE_BUILDERS.register("furry_cloud_surface_builder", () -> new FurryCloudSurfaceBuilder(SurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> COUNTRYSIDE_WHEAT_SURFACE_BUILDER = SURFACE_BUILDERS.register("countryside_wheat_surface_builder", () -> new CountrysideWheatSurfaceBuilder(SurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> CITY_SURFACE_BUILDER = SURFACE_BUILDERS.register("city_surface_builder", () -> new CitySurfaceBuilder(SurfaceBuilderConfig.CODEC));

}
