package com.octenexin.inifield.world.features.configs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class NbtFeatureConfig implements IFeatureConfig {
    public static final Codec<NbtFeatureConfig> CODEC = RecordCodecBuilder.<NbtFeatureConfig>create((configInstance) -> configInstance.group(
            ResourceLocation.CODEC.fieldOf("processors").forGetter(nbtFeatureConfig -> nbtFeatureConfig.processor),
            ResourceLocation.CODEC.fieldOf("post_processors").orElse(new ResourceLocation("minecraft:empty")).forGetter(nbtFeatureConfig -> nbtFeatureConfig.postProcessor),
            Codec.mapPair(ResourceLocation.CODEC.fieldOf("resourcelocation"), Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight")).codec().listOf().fieldOf("dungeon_nbt_entries").forGetter(nbtFeatureConfig -> nbtFeatureConfig.nbtResourcelocationsAndWeights),
            Codec.INT.fieldOf("structure_y_offset").orElse(0).forGetter(nbtFeatureConfig -> nbtFeatureConfig.structureYOffset)
    ).apply(configInstance, NbtFeatureConfig::new));

    public final List<Pair<ResourceLocation, Integer>> nbtResourcelocationsAndWeights;
    public final ResourceLocation processor;
    public final ResourceLocation postProcessor;
    public final int structureYOffset;

    public NbtFeatureConfig(ResourceLocation processor,
                            ResourceLocation postProcessor,
                            List<Pair<ResourceLocation, Integer>> nbtResourceLocationsAndWeights,
                            int structureYOffset)
    {
        this.nbtResourcelocationsAndWeights = nbtResourceLocationsAndWeights;
        this.processor = processor;
        this.postProcessor = postProcessor;
        this.structureYOffset = structureYOffset;
    }
}
