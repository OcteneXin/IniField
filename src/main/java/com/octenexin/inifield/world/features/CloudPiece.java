package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;


public class CloudPiece extends NbtFeature {

    public CloudPiece(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NbtFeatureConfig config) {

        BlockPos temp=new BlockPos(pos.getX(),255,pos.getZ());
        while(temp.getY()>0&& world.isEmptyBlock(temp)){
            temp=temp.below();
        }

        if (temp.getY()==0) {

            int y= 81+ random.nextInt(10);
            super.place(world, generator, random, new BlockPos(pos.getX(),y,pos.getZ()), config);
            IniField.LOGGER.debug("---------------------------------------place success------------------------------------");
            return true;
        } else {
            IniField.LOGGER.debug("---------------------------------------place fail---------------------------------------");
            return false;
        }



    }
}
