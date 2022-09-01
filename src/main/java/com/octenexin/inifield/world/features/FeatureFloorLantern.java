package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FeatureFloorLantern extends Feature<NoFeatureConfig> {

    private static final BlockState WOODEN_FENCE= Blocks.OAK_FENCE.defaultBlockState();
    private static final BlockState LANTREN= Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING,true);


    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        int r=p_241855_3_.nextInt(4);
        BlockState state=p_241855_1_.getBlockState(p_241855_4_.below());
        BlockState Estate=p_241855_1_.getBlockState(p_241855_4_.below().east());
        BlockState Sstate=p_241855_1_.getBlockState(p_241855_4_.below().south());
        BlockState Wstate=p_241855_1_.getBlockState(p_241855_4_.below().west());
        BlockState Nstate=p_241855_1_.getBlockState(p_241855_4_.below().north());

        if(state.is(Blocks.GRASS_BLOCK)&&(Estate.is(Blocks.GRASS_BLOCK)&&Sstate.is(Blocks.GRASS_BLOCK)&&Wstate.is(Blocks.GRASS_BLOCK)&&Nstate.is(Blocks.GRASS_BLOCK))){

            p_241855_1_.setBlock(p_241855_4_,WOODEN_FENCE,2);
            switch (r){
                case 0:{
                    p_241855_1_.setBlock(p_241855_4_.above(),WOODEN_FENCE.setValue(FenceBlock.NORTH,true),2);
                    p_241855_1_.setBlock(p_241855_4_.above().north(),WOODEN_FENCE.setValue(FenceBlock.SOUTH,true),2);
                    p_241855_1_.setBlock(p_241855_4_.north(),LANTREN,2);
                }break;
                case 1:{
                    p_241855_1_.setBlock(p_241855_4_.above(),WOODEN_FENCE.setValue(FenceBlock.EAST,true),2);
                    p_241855_1_.setBlock(p_241855_4_.above().east(),WOODEN_FENCE.setValue(FenceBlock.WEST,true),2);
                    p_241855_1_.setBlock(p_241855_4_.east(),LANTREN,2);
                }break;
                case 2:{
                    p_241855_1_.setBlock(p_241855_4_.above(),WOODEN_FENCE.setValue(FenceBlock.SOUTH,true),2);
                    p_241855_1_.setBlock(p_241855_4_.above().south(),WOODEN_FENCE.setValue(FenceBlock.NORTH,true),2);
                    p_241855_1_.setBlock(p_241855_4_.south(),LANTREN,2);
                }break;
                case 3:{
                    p_241855_1_.setBlock(p_241855_4_.above(),WOODEN_FENCE.setValue(FenceBlock.WEST,true),2);
                    p_241855_1_.setBlock(p_241855_4_.above().west(),WOODEN_FENCE.setValue(FenceBlock.EAST,true),2);
                    p_241855_1_.setBlock(p_241855_4_.west(),LANTREN,2);
                }break;
                default:
                    return false;
            }

            return true;
        }else
        {
            return false;
        }

    }


    public FeatureFloorLantern(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }
}
