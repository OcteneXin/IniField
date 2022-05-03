package com.octenexin.inifield.world.features.trees;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.stream.Stream;

public class WorldTreePlacement extends Placement<ChanceConfig> {

    public WorldTreePlacement(Codec<ChanceConfig> p_i232084_1_) {
        super(p_i232084_1_);
    }


    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper p_241857_1_, Random p_241857_2_, ChanceConfig p_241857_3_, BlockPos p_241857_4_) {
        if (p_241857_2_.nextInt(p_241857_3_.chance) == 0) {
            int i = p_241857_2_.nextInt(16) + p_241857_4_.getX();
            int j = p_241857_2_.nextInt(16) + p_241857_4_.getZ();
            int k = p_241857_2_.nextInt(p_241857_1_.getGenDepth());
            return Stream.of(new BlockPos(i, k, j));
        } else {
            return Stream.empty();
        }
    }
}
