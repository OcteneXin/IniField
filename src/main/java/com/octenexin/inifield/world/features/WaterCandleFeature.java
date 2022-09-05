package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.block.WaterCandleBlock;
import com.octenexin.inifield.block.WeedGrass;
import com.octenexin.inifield.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class WaterCandleFeature extends Feature<NoFeatureConfig> {

    public WaterCandleFeature(Codec<NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        boolean flag=false;

        int l = rand.nextInt(8) - rand.nextInt(8);
        int i1 = rand.nextInt(8) - rand.nextInt(8);
        int j1 = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
        BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
        BlockState blockstate = ModBlocks.WATER_CANDLE.get().defaultBlockState().setValue(WaterCandleBlock.TYPE, rand.nextInt(4)).setValue(WaterCandleBlock.AGE,rand.nextInt(2));
        if (blockstate.canSurvive(worldIn, blockpos)) {
            if(worldIn.isWaterAt(blockpos)){
                worldIn.setBlock(blockpos, blockstate.setValue(WaterCandleBlock.WATERLOGGED,true), 2);
            }else{
                worldIn.setBlock(blockpos, blockstate,2);
            }

            flag=true;
        }

        return flag;
    }

}
