package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FeatureScarecrow extends Feature<NoFeatureConfig> {

    private static final BlockState WOODEN_FENCE=Blocks.OAK_FENCE.defaultBlockState();
    private static final BlockState HAY_BLOCK=Blocks.HAY_BLOCK.defaultBlockState();
    private static final BlockState JACK_O_LANTERN=Blocks.JACK_O_LANTERN.defaultBlockState();
    private static final BlockState CARVED_PUMPKIN=Blocks.CARVED_PUMPKIN.defaultBlockState();

    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        BlockState state=p_241855_1_.getBlockState(p_241855_4_.below());
        BlockState Estate=p_241855_1_.getBlockState(p_241855_4_.below().east());
        BlockState Sstate=p_241855_1_.getBlockState(p_241855_4_.below().south());
        BlockState Wstate=p_241855_1_.getBlockState(p_241855_4_.below().west());
        BlockState Nstate=p_241855_1_.getBlockState(p_241855_4_.below().north());

        if((state.is(Blocks.FARMLAND))&&(Estate.is(Blocks.FARMLAND)&&Sstate.is(Blocks.FARMLAND)&&Wstate.is(Blocks.FARMLAND)&&Nstate.is(Blocks.FARMLAND))){

            p_241855_1_.setBlock(p_241855_4_,WOODEN_FENCE,2);
            p_241855_1_.setBlock(p_241855_4_.above(),WOODEN_FENCE,2);
            p_241855_1_.setBlock(p_241855_4_.above(2),HAY_BLOCK,2);
            p_241855_1_.setBlock(p_241855_4_.above(2).north(),WOODEN_FENCE.setValue(FenceBlock.SOUTH,true),2);
            p_241855_1_.setBlock(p_241855_4_.above(2).south(),WOODEN_FENCE.setValue(FenceBlock.NORTH,true),2);
            if(p_241855_3_.nextFloat()>=0.5F){
                p_241855_1_.setBlock(p_241855_4_.above(3),JACK_O_LANTERN.setValue(CarvedPumpkinBlock.FACING,Direction.EAST),2);
            }else{
                p_241855_1_.setBlock(p_241855_4_.above(3),CARVED_PUMPKIN.setValue(CarvedPumpkinBlock.FACING,Direction.EAST),2);

            }

            return true;
        }else
        {
            return false;
        }

    }

    public FeatureScarecrow(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }
}
