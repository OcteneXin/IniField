package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FeatureRedstoneLight extends Feature<NoFeatureConfig> {

    private static final BlockState WOODEN_FENCE=Blocks.OAK_FENCE.defaultBlockState();
    private static final BlockState REDSTONE_LIGHT=Blocks.REDSTONE_LAMP.defaultBlockState();
    private static final BlockState MOONLIGHT_DETECTOR=Blocks.DAYLIGHT_DETECTOR.defaultBlockState().setValue(DaylightDetectorBlock.INVERTED,true);

    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        BlockState state=p_241855_1_.getBlockState(p_241855_4_.below());
        BlockState Estate=p_241855_1_.getBlockState(p_241855_4_.below().east());
        BlockState Sstate=p_241855_1_.getBlockState(p_241855_4_.below().south());
        BlockState Wstate=p_241855_1_.getBlockState(p_241855_4_.below().west());
        BlockState Nstate=p_241855_1_.getBlockState(p_241855_4_.below().north());

        if((!state.is(Blocks.GRASS_PATH))&&(Estate.is(Blocks.GRASS_PATH)||Sstate.is(Blocks.GRASS_PATH)||Wstate.is(Blocks.GRASS_PATH)||Nstate.is(Blocks.GRASS_PATH))){
            for(int i=0;i<6;i++){
                p_241855_1_.setBlock(p_241855_4_.above(i),WOODEN_FENCE,2);
            }
            p_241855_1_.setBlock(p_241855_4_.above(6),REDSTONE_LIGHT,2);
            p_241855_1_.setBlock(p_241855_4_.above(7),MOONLIGHT_DETECTOR,2);


            return true;
        }else
        {
            return false;
        }

    }

    public FeatureRedstoneLight(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }
}
