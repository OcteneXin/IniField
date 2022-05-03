package com.octenexin.inifield.world.features.trees;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.init.ModTreeFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldTreeTrunkDecorator extends TreeDecorator {

    public static final Codec<WorldTreeTrunkDecorator> CODEC;
    public static final WorldTreeTrunkDecorator INSTANCE = new WorldTreeTrunkDecorator();

    protected TreeDecoratorType<?> type() {
        return ModTreeFeatures.WORLD_TREE_VINE;
    }

    public void place(ISeedReader p_225576_1_, Random p_225576_2_, List<BlockPos> p_225576_3_, List<BlockPos> p_225576_4_, Set<BlockPos> p_225576_5_, MutableBoundingBox p_225576_6_) {
        p_225576_3_.forEach((pos) -> {
            if (p_225576_2_.nextInt(12) == 0) {
                BlockPos blockpos = pos.above();
                if (p_225576_1_.isEmptyBlock(blockpos)) {
                    this.setBlock(p_225576_1_,blockpos,Blocks.GLOWSTONE.defaultBlockState(),p_225576_5_,p_225576_6_);
                }
            }

        });
    }


    static {
        CODEC = Codec.unit(() -> {
            return INSTANCE;
        });
    }

}
