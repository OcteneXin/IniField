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
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldTreeVineDecorator extends TreeDecorator {

    public static final Codec<WorldTreeVineDecorator> CODEC;
    public static final WorldTreeVineDecorator INSTANCE = new WorldTreeVineDecorator();

    protected TreeDecoratorType<?> type() {
        return ModTreeFeatures.WORLD_TREE_VINE;
    }

    public void place(ISeedReader p_225576_1_, Random p_225576_2_, List<BlockPos> p_225576_3_, List<BlockPos> p_225576_4_, Set<BlockPos> p_225576_5_, MutableBoundingBox p_225576_6_) {
        p_225576_4_.forEach((p_242866_5_) -> {
            if (p_225576_2_.nextInt(16) == 0) {
                BlockPos blockpos = p_242866_5_.west();
                if (checkBelow(p_225576_1_,blockpos)) {
                    this.addHangingVine(p_225576_1_, p_225576_2_,blockpos, VineBlock.EAST, p_225576_5_, p_225576_6_);
                }
            }

            if (p_225576_2_.nextInt(16) == 0) {
                BlockPos blockpos1 = p_242866_5_.east();
                if (checkBelow(p_225576_1_,blockpos1)) {
                    this.addHangingVine(p_225576_1_,  p_225576_2_,blockpos1, VineBlock.WEST, p_225576_5_, p_225576_6_);
                }
            }

            if (p_225576_2_.nextInt(16) == 0) {
                BlockPos blockpos2 = p_242866_5_.north();
                if (checkBelow(p_225576_1_,blockpos2)) {
                    this.addHangingVine(p_225576_1_, p_225576_2_, blockpos2, VineBlock.SOUTH, p_225576_5_, p_225576_6_);
                }
            }

            if (p_225576_2_.nextInt(16) == 0) {
                BlockPos blockpos3 = p_242866_5_.south();
                if (checkBelow(p_225576_1_,blockpos3)) {
                    this.addHangingVine(p_225576_1_, p_225576_2_, blockpos3, VineBlock.NORTH, p_225576_5_, p_225576_6_);
                }
            }

        });
    }

    private boolean checkBelow(ISeedReader p_225576_1_,BlockPos pos){
        for (int i=0;i<10;i++){
            if(!Feature.isAir(p_225576_1_,pos.below(i)))return false;
        }
        return true;
    }

    private void addHangingVine(IWorldGenerationReader p_227420_1_, Random rand, BlockPos p_227420_2_, BooleanProperty p_227420_3_, Set<BlockPos> p_227420_4_, MutableBoundingBox p_227420_5_) {
        this.placeVine(p_227420_1_, p_227420_2_, p_227420_3_, p_227420_4_, p_227420_5_);
        int i = 4+rand.nextInt(6);

        BlockPos blockpos = p_227420_2_.below();
        for(; Feature.isAir(p_227420_1_, blockpos) && i > 0; --i) {
            this.placeVine(p_227420_1_, blockpos, p_227420_3_, p_227420_4_, p_227420_5_);
            blockpos = blockpos.below();
        }

        if(rand.nextInt(3)==0){
            this.setBlock(p_227420_1_,blockpos, Blocks.GLOWSTONE.defaultBlockState(),p_227420_4_,p_227420_5_);
        }

    }

    static {
        CODEC = Codec.unit(() -> {
            return INSTANCE;
        });
    }

}
