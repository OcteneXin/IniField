package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class WheatFarmlandFeature extends Feature<NoFeatureConfig> {
    public WheatFarmlandFeature(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    private static BlockState FARMLAND= Blocks.FARMLAND.defaultBlockState().setValue(FarmlandBlock.MOISTURE,0);
    private static BlockState WOODEN_SLAB= Blocks.OAK_PLANKS.defaultBlockState();
    private static BlockState WATER=Blocks.WATER.defaultBlockState();
    private static BlockState WHEAT=Blocks.WHEAT.defaultBlockState().setValue(CropsBlock.AGE,7);

    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {

        for(int j = p_241855_4_.getX(); j <= p_241855_4_.getX() + 15; ++j) {
            for(int k = p_241855_4_.getZ(); k <= p_241855_4_.getZ() + 15; ++k) {
                int l = j - p_241855_4_.getX();
                int i1 = k - p_241855_4_.getZ();
                BlockPos blockpos = new BlockPos(j, findRightPos(p_241855_1_,j,k), k);

                if(p_241855_1_.getBlockState(blockpos).is(Blocks.FARMLAND)){
                    if(checkClosing(p_241855_1_,blockpos)){
                        if(p_241855_3_.nextInt(300)==0){
                            p_241855_1_.setBlock(blockpos.above(), Blocks.COMPOSTER.defaultBlockState(), 2);
                        }else{
                            p_241855_1_.setBlock(blockpos.above(), WHEAT, 2);
                        }
                    }else {
                        p_241855_1_.setBlock(blockpos,WOODEN_SLAB,2);
                        if(checkClosing(p_241855_1_,blockpos.below())){
                            p_241855_1_.setBlock(blockpos.below(),WATER,2);
                        }

                        if(p_241855_3_.nextInt(200)==0){
                            p_241855_1_.setBlock(blockpos,Blocks.DIRT.defaultBlockState(),2);
                            Features.OAK.place(p_241855_1_,p_241855_2_,p_241855_3_,blockpos.above());
                        }

                        if(p_241855_3_.nextInt(100)==0){
                            p_241855_1_.setBlock(blockpos,Blocks.JACK_O_LANTERN.defaultBlockState(),2);
                        }
                    }

                    if(j==p_241855_4_.getX()+7&&k== p_241855_4_.getZ()+7){
                        if(checkClosing(p_241855_1_,blockpos)){
                            p_241855_1_.setBlock(blockpos,WATER,2);
                        }
                    }

                }

            }
        }

        return true;
    }

    private int findRightPos(ISeedReader world,int x,int z){
        BlockPos newBlockPos=new BlockPos(x,255,z);

        int i=255;
        for(;world.isEmptyBlock(newBlockPos)&&i>0;i--){
            newBlockPos=newBlockPos.below();
        }

        return i;
    }

    private boolean checkClosing(ISeedReader world,BlockPos pos){
        return (!world.isEmptyBlock(pos.east()))&&(!world.isEmptyBlock(pos.west()))&&(!world.isEmptyBlock(pos.north()))&&(!world.isEmptyBlock(pos.south()));
    }
}
