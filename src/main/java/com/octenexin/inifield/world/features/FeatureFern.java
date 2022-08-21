package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public class FeatureFern extends NbtFeature{

    public FeatureFern(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NbtFeatureConfig config) {

        if(world.getBlockState(position.below()).is(ModBlocks.LIGHT_CLOUD.get())&&random.nextInt(20)==0){
            return super.place(world, chunkGenerator, random, position, config);
        }else {
            return false;
        }


    }
}
