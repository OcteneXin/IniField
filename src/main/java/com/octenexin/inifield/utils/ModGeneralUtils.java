package com.octenexin.inifield.utils;

import com.mojang.datafixers.util.Pair;
import com.octenexin.inifield.mixin.ModBiomeGenerationSettingsAccessor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ModGeneralUtils {
    // Weighted Random from: https://stackoverflow.com/a/6737362
    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, Random random) {
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }


    /**
     * Helper method to make WB biomes mutable to add stuff to it later
     */
    public static void makeBiomeMutable(Biome biome){
        // Make the structure and features list mutable for modification late
        List<List<Supplier<ConfiguredFeature<?, ?>>>> tempFeature = ((ModBiomeGenerationSettingsAccessor)biome.getGenerationSettings()).inifield_getFeatures();
        List<List<Supplier<ConfiguredFeature<?, ?>>>> mutableGenerationStages = new ArrayList<>();

        // Fill in generation stages so there are at least 10 or else Minecraft crashes.
        // (we need all stages for adding features/structures to the right stage too)
        for(int currentStageIndex = 0; currentStageIndex < Math.max(GenerationStage.Decoration.values().length, tempFeature.size()); currentStageIndex++){
            if(currentStageIndex >= tempFeature.size()){
                mutableGenerationStages.add(new ArrayList<>());
            }else{
                mutableGenerationStages.add(new ArrayList<>(tempFeature.get(currentStageIndex)));
            }
        }

        // Make the Structure and GenerationStages (features) list mutable for modification later
        ((ModBiomeGenerationSettingsAccessor)biome.getGenerationSettings()).inifield_setFeatures(mutableGenerationStages);
    }
}
