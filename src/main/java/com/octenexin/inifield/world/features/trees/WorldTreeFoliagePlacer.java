package com.octenexin.inifield.world.features.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.init.ModTreeFeatures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;

import java.util.Random;
import java.util.Set;

public class WorldTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<WorldTreeFoliagePlacer> CODEC = RecordCodecBuilder.create((p_236776_0_) -> {
        return foliagePlacerParts(p_236776_0_).and(Codec.intRange(0, 16).fieldOf("height").forGetter((p_236777_0_) -> {
            return p_236777_0_.height;
        })).apply(p_236776_0_, WorldTreeFoliagePlacer::new);
    });
    protected final int height;

    public WorldTreeFoliagePlacer(FeatureSpread p_i242000_1_, FeatureSpread p_i242000_2_, int p_i242000_3_) {
        super(p_i242000_1_, p_i242000_2_);
        this.height = p_i242000_3_;
    }

    protected FoliagePlacerType<?> type() {
        return ModTreeFeatures.WORLD_TREE_FOLIAGE_PLACER;
    }


//    protected void createFoliage(IWorldGenerationReader p_230372_1_, Random p_230372_2_, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, FoliagePlacer.Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> p_230372_8_, int p_230372_9_, MutableBoundingBox p_230372_10_) {
//        int i = p_230372_5_.doubleTrunk() ? p_230372_6_ : 1 + p_230372_2_.nextInt(2);
//
//        for(int j = p_230372_9_; j >= p_230372_9_ - i; --j) {
//            int k = p_230372_7_ + p_230372_5_.radiusOffset() + 1 - j;
//            this.placeLeavesRow(p_230372_1_, p_230372_2_, p_230372_3_, p_230372_5_.foliagePos(), k, p_230372_8_, j, p_230372_5_.doubleTrunk(), p_230372_10_);
//        }
//
//    }
//
//    public int foliageHeight(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
//        return this.height;
//    }
//
//    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
//        if (p_230373_2_ + p_230373_4_ >= 7) {
//            return true;
//        } else {
//            return p_230373_2_ * p_230373_2_ + p_230373_4_ * p_230373_4_ > p_230373_5_ * p_230373_5_;
//        }
//    }

    protected void createFoliage(IWorldGenerationReader p_230372_1_, Random p_230372_2_, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, FoliagePlacer.Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> p_230372_8_, int p_230372_9_, MutableBoundingBox p_230372_10_) {
        for(int i = p_230372_9_; i >= p_230372_9_ - p_230372_6_; --i) {
            int j = p_230372_7_ + (i != p_230372_9_ && i != p_230372_9_ - p_230372_6_ ? 1 : 0);
            this.placeLeavesRow(p_230372_1_, p_230372_2_, p_230372_3_, p_230372_5_.foliagePos(), j, p_230372_8_, i, p_230372_5_.doubleTrunk(), p_230372_10_);
        }

    }

    public int foliageHeight(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return this.height;
    }

    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return (MathHelper.square((float)p_230373_2_ + 0.5F) + MathHelper.square((float)p_230373_4_ + 0.5F) > (float)(p_230373_5_ * p_230373_5_))||(p_230373_1_.nextInt(6)==0);
    }
}
