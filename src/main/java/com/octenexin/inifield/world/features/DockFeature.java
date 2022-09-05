package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DockFeature extends Feature<NoFeatureConfig> {

    private static final BlockState CAMPFIRE=Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.FACING,Direction.EAST).setValue(CampfireBlock.LIT,false);
    private static final BlockState SPRUCE_LOG=Blocks.SPRUCE_LOG.defaultBlockState();
    private static final BlockState STRIPPED_SPRUCE_LOG=Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState();
    private static final BlockState SPRUCE_SLAB=Blocks.SPRUCE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
    private static final BlockState SPRUCE_FENCE_GATE=Blocks.SPRUCE_FENCE_GATE.defaultBlockState().setValue(FenceGateBlock.FACING,Direction.SOUTH);



    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
        if(p_241855_1_.isWaterAt(p_241855_4_.below())) return false;

        for (int i = 1; i < 10; i++) {
            if(!(p_241855_1_.isWaterAt(p_241855_4_.offset(i,-1,0))&&(!p_241855_1_.isWaterAt(p_241855_4_.offset(i,0,0))))){
                return false;
            }
        }

        //dock plate
        for (int i = -1; i <= 1; i++) {
            for (int j = 1; j < 10; j++) {
                p_241855_1_.setBlock(p_241855_4_.offset(j,0,i),CAMPFIRE,2);
            }
        }

        //fence
        for (int j = 1; j < 10; j++) {
            if(j%2!=0){
                placeLogPole(p_241855_1_,p_241855_4_.offset(j,0,-2));
                placeLogPole(p_241855_1_,p_241855_4_.offset(j,0,2));
            }else{
                p_241855_1_.setBlock(p_241855_4_.offset(j,0,-2),SPRUCE_FENCE_GATE,2);
                p_241855_1_.setBlock(p_241855_4_.offset(j,0,2),SPRUCE_FENCE_GATE,2);
            }
        }



        return true;
    }

    public DockFeature(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }

    private void placeLogPole(ISeedReader world,BlockPos pos){
        world.setBlock(pos.above(),SPRUCE_SLAB,2);
        world.setBlock(pos,STRIPPED_SPRUCE_LOG,2);

        BlockPos newPos=pos.below();
        while(world.isWaterAt(newPos)){
            world.setBlock(newPos,SPRUCE_LOG,2);
            newPos=newPos.below();
        }
    }
}
