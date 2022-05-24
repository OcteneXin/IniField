package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FeatureScatteredStone extends Feature<NoFeatureConfig> {

    private static final BlockState COBBLESTONE=Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState STONE_BUTTON=Blocks.STONE_BUTTON.defaultBlockState().setValue(StoneButtonBlock.FACE, AttachFace.FLOOR).setValue(StoneButtonBlock.FACING, Direction.EAST);
    private static final BlockState STONE_BUTTON_N=Blocks.STONE_BUTTON.defaultBlockState().setValue(StoneButtonBlock.FACE, AttachFace.FLOOR).setValue(StoneButtonBlock.FACING, Direction.NORTH);
    private static final BlockState PODZOL=Blocks.PODZOL.defaultBlockState();
    private static final BlockState COARSE_DIRT=Blocks.COARSE_DIRT.defaultBlockState();
    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        BlockPos pos=new BlockPos(p_241855_4_.getX(),findRightPos(p_241855_1_,p_241855_4_.getX(),p_241855_4_.getZ()),p_241855_4_.getZ());
        BlockState state=p_241855_1_.getBlockState(pos);
        if(!state.is(Blocks.GRASS_BLOCK)){
            return false;
        }


        for(int j = p_241855_4_.getX()-5; j <= p_241855_4_.getX() + 5; ++j) {
            for(int k = p_241855_4_.getZ()-5; k <= p_241855_4_.getZ() + 5; ++k) {

                if((j-p_241855_4_.getX())*(j-p_241855_4_.getX())+(k-p_241855_4_.getZ())*(k-p_241855_4_.getZ())<=25){
                    BlockPos blockpos = new BlockPos(j, findRightPos(p_241855_1_,j,k), k);

                    if(p_241855_1_.getBlockState(blockpos).is(Blocks.GRASS_BLOCK)){
                        int temp=p_241855_3_.nextInt(20);
                        if(temp==0){
                            p_241855_1_.setBlock(blockpos,PODZOL,2);
                        }else if(temp==2){
                            p_241855_1_.setBlock(blockpos,COARSE_DIRT,2);
                        }else if(temp==4){
                            p_241855_1_.setBlock(blockpos,COBBLESTONE,2);
                        }else if(temp==6){
                            p_241855_1_.setBlock(blockpos.above(),STONE_BUTTON,2);
                        }else if(temp==8){
                            p_241855_1_.setBlock(blockpos.above(),STONE_BUTTON_N,2);
                        }

                    }
                }

            }
        }

        return true;

    }

    public FeatureScatteredStone(Codec p_i231953_1_) {
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
