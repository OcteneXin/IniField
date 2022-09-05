package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.block.WeedGrass;
import com.octenexin.inifield.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class ScatteredWeedGrassFeature extends Feature<ProbabilityConfig> {
    protected final BlockState plant;

    public ScatteredWeedGrassFeature(Codec<ProbabilityConfig> configFactoryIn)
    {
        super(configFactoryIn);
        this.plant = ModBlocks.WEED_GRASS.get().defaultBlockState().setValue(WeedGrass.WATERLOGGED,true);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityConfig config)
    {
        boolean flag=false;
        for (int j = 0; j < 64; ++j)
        {
            BlockPos blockpos = pos.offset(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (onProperBlock(worldIn.getBlockState(pos.below()).getBlock()) && worldIn.isWaterAt(pos))
            {
                worldIn.setBlock(blockpos, this.plant, 2);
                flag=true;
            }
        }

        return flag;
    }

    private boolean onProperBlock(Block block){
        return block== Blocks.DIRT||block==Blocks.GRASS_BLOCK||block==Blocks.SAND||block==Blocks.COARSE_DIRT||block==Blocks.PODZOL||block==Blocks.GRAVEL;
    }
}
