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

public class FeatureSprinklerIrrigation extends Feature<NoFeatureConfig> {

    private static final BlockState CHAIN_X=Blocks.CHAIN.defaultBlockState().setValue(ChainBlock.AXIS, Direction.Axis.X);
    private static final BlockState CHAIN_Z=Blocks.CHAIN.defaultBlockState().setValue(ChainBlock.AXIS, Direction.Axis.Z);
    private static final BlockState CHAIN_Y=Blocks.CHAIN.defaultBlockState().setValue(ChainBlock.AXIS, Direction.Axis.Y);
    private static final BlockState IRON_BLOCK=Blocks.IRON_BLOCK.defaultBlockState();
    private static final BlockState LANTERN=Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING,true);
    private static final BlockState IRON_BAR=Blocks.IRON_BARS.defaultBlockState();
    private static final BlockState POLISHED_STONE=Blocks.POLISHED_ANDESITE.defaultBlockState();

    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {


        BlockPos pos=new BlockPos(p_241855_4_.getX(),findRightPos(p_241855_1_,p_241855_4_.getX(),p_241855_4_.getZ()),p_241855_4_.getZ());
        BlockPos originalPos=pos;
        BlockState state=p_241855_1_.getBlockState(pos);
        if((!state.is(Blocks.WHEAT))&&(!state.is(Blocks.FARMLAND))){
            return false;
        }

        //put triangle bar
        for(int i=0;i<=6;i++){
            p_241855_1_.setBlock(pos,IRON_BLOCK,2);
            p_241855_1_.setBlock(pos.above(),IRON_BLOCK,2);
            pos=pos.east().above(2);
        }
        p_241855_1_.setBlock(pos,IRON_BLOCK,2);
        p_241855_1_.setBlock(pos.above(),POLISHED_STONE,2);
        p_241855_1_.setBlock(pos.above(2),POLISHED_STONE,2);
        p_241855_1_.setBlock(pos.east(),IRON_BLOCK,2);
        pos=pos.east(2).below();

        for(int i=0;i<=6;i++){
            p_241855_1_.setBlock(pos,IRON_BLOCK,2);
            p_241855_1_.setBlock(pos.below(),IRON_BLOCK,2);
            pos=pos.east().below(2);
        }

        //put another triangle bar
        pos=originalPos.south(15);
        for(int i=0;i<=6;i++){
            p_241855_1_.setBlock(pos,IRON_BLOCK,2);
            p_241855_1_.setBlock(pos.above(),IRON_BLOCK,2);
            pos=pos.east().above(2);
        }
        p_241855_1_.setBlock(pos,IRON_BLOCK,2);
        p_241855_1_.setBlock(pos.above(),POLISHED_STONE,2);
        p_241855_1_.setBlock(pos.above(2),POLISHED_STONE,2);
        p_241855_1_.setBlock(pos.east(),IRON_BLOCK,2);
        pos=pos.east(2).below();

        for(int i=0;i<=6;i++){
            p_241855_1_.setBlock(pos,IRON_BLOCK,2);
            p_241855_1_.setBlock(pos.below(),IRON_BLOCK,2);
            pos=pos.east().below(2);
        }

        //put horizontal bar
        pos=originalPos.east(7).above(14).south();
        for(int i=0;i<14;i++){
            p_241855_1_.setBlock(pos,IRON_BLOCK,2);
            if(i%3==0){
                p_241855_1_.setBlock(pos.above(),IRON_BAR,2);
            }
            p_241855_1_.setBlock(pos.above(2),CHAIN_Z,2);
            if(i%4==0){
                placeLantern(p_241855_1_,pos.below());
            }

            pos=pos.south();
        }

        return true;

    }

    public FeatureSprinklerIrrigation(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }


    private void placeLantern(ISeedReader world,BlockPos pos){
        int i=12;
        for (;world.isEmptyBlock(pos)&&i>=0;--i){
            world.setBlock(pos,CHAIN_Y,2);
            pos=pos.below();
        }
        world.setBlock(pos.above(),LANTERN,2);
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
