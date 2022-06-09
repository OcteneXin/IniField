package com.octenexin.inifield.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.structures.CountrysideStructure;
import com.octenexin.inifield.world.structures.HighwayStructure;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModStructures {

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE;

    public static final RegistryObject<Structure<NoFeatureConfig>> HIGHWAY;

    static {
        DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MOD_ID);
        HIGHWAY = DEFERRED_REGISTRY_STRUCTURE.register("highway", () -> {
            return new HighwayStructure(NoFeatureConfig.CODEC);
        });
    }



    public static void setupStructures(){
        setupMapSpacingAndLand((Structure)HIGHWAY.get(),new StructureSeparationSettings(25,10,354567),false);

    }



    /*copy of When Dungeons Arise*/
    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>,StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.DEFAULTS)
                .put(structure, structureSeparationSettings)
                .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach((settings) -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = ((DimensionSettings)settings.getValue()).structureSettings().structureConfig;
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                ((DimensionSettings)settings.getValue()).structureSettings().structureConfig = tempMap;
            } else {
                structureMap.put(structure, structureSeparationSettings);
            }

        });
    }




}
