package com.octenexin.inifield.init;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.structures.CountrysideStructure;
import com.octenexin.inifield.world.templatepools.CountrysideWheatPools;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.SavannaVillagePools;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class ModConfiguredStructures {

    public static StructureFeature<?, ?> CONFIGURED_HIGHWAY;

    static {
        CONFIGURED_HIGHWAY=ModStructures.HIGHWAY.get().configured(IFeatureConfig.NONE);
    }

    ModConfiguredStructures(){}


    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        IniField.LOGGER.debug("configured structures beg!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



        Registry.register(registry,IniField.locate("configured_highway"),CONFIGURED_HIGHWAY);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.HIGHWAY.get(),CONFIGURED_HIGHWAY);



        IniField.LOGGER.debug("configured structures end!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
