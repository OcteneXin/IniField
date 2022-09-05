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

public class ExtermBeaconFeature extends Feature<NoFeatureConfig> {

    private static final BlockState BEACON=Blocks.BEACON.defaultBlockState();
    private static final BlockState DIAMOND_BLOCK=Blocks.DIAMOND_BLOCK.defaultBlockState();
    private static final BlockState WHITE_STAINED_GLASS=Blocks.WHITE_STAINED_GLASS.defaultBlockState();


    @Override
    public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(p_241855_1_.getBlockState(p_241855_4_.offset(i,-1,j)).getBlock()!=Blocks.GRASS_BLOCK||p_241855_1_.getBlockState(p_241855_4_.offset(i,-2,j)).getBlock()!=Blocks.DIRT){
                    return false;
                }
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                p_241855_1_.setBlock(p_241855_4_.offset(i,-2,j),DIAMOND_BLOCK,2);
            }
        }
        p_241855_1_.setBlock(p_241855_4_.below(),BEACON,2);
        p_241855_1_.setBlock(p_241855_4_,WHITE_STAINED_GLASS,2);

        return true;
    }

    public ExtermBeaconFeature(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }


}
