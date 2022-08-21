package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FeatureScatteredIce extends Feature<NoFeatureConfig> {

    private static final BlockState ICE=Blocks.ICE.defaultBlockState();
    private static final BlockState SNOW_BLOCK=Blocks.SNOW_BLOCK.defaultBlockState();
    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        BlockPos pos=new BlockPos(p_241855_4_.getX(),findRightPos(p_241855_1_,p_241855_4_.getX(),p_241855_4_.getZ()),p_241855_4_.getZ());
        BlockState state=p_241855_1_.getBlockState(pos);
        if(!state.is(ModBlocks.LIGHT_CLOUD.get())){
            return false;
        }
        if(p_241855_3_.nextInt(3)==0){
            return false;
        }


        for(int j = p_241855_4_.getX()-4; j <= p_241855_4_.getX() + 4; ++j) {
            for(int k = p_241855_4_.getZ()-4; k <= p_241855_4_.getZ() + 4; ++k) {

                if((j-p_241855_4_.getX())*(j-p_241855_4_.getX())+(k-p_241855_4_.getZ())*(k-p_241855_4_.getZ())<=16){
                    BlockPos blockpos = new BlockPos(j, findRightPos(p_241855_1_,j,k), k);

                    int temp=p_241855_3_.nextInt(20);
                    if(temp==0){
                        p_241855_1_.setBlock(blockpos.above(),ICE,2);
                        p_241855_1_.setBlock(blockpos.above(2),ICE,2);
                        p_241855_1_.setBlock(blockpos.above(3),ICE,2);
                    }else if(temp==1){
                        p_241855_1_.setBlock(blockpos.above(),ICE,2);
                        p_241855_1_.setBlock(blockpos.above(2),ICE,2);
                    } else if (temp==2) {
                        p_241855_1_.setBlock(blockpos.above(),SNOW_BLOCK,2);
                        p_241855_1_.setBlock(blockpos.above(2),SNOW_BLOCK,2);
                    }else if (temp==3) {
                        p_241855_1_.setBlock(blockpos.above(),SNOW_BLOCK,2);
                    }
                }

            }
        }

        return true;

    }

    public FeatureScatteredIce(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }

    private int findRightPos(ISeedReader world,int x,int z){
        BlockPos newBlockPos=new BlockPos(x,255,z);

        int i=255;
        for(;world.isEmptyBlock(newBlockPos)&&i>0;i--){
            newBlockPos=newBlockPos.below();
        }

        return i;
    }
}
