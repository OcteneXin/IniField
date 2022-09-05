package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.block.WaterCandleBlock;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FloatingBoatFeature extends NbtFeature {

    public FloatingBoatFeature(Codec<NbtFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NbtFeatureConfig config)
    {

        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                if(!(worldIn.isWaterAt(pos.offset(i,-1,j))&&isAir(worldIn,pos.offset(i,0,j)))){
                    return false;
                }
            }
        }


        return super.place(worldIn, generator, rand, pos, config);
    }

}
