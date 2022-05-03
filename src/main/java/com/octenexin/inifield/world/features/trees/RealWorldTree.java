package com.octenexin.inifield.world.features.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

/**
 * what makes a tree?
 *
 * BaseTreeFeatureConfig
 *    public final BlockStateProvider trunkProvider;
 *    public final BlockStateProvider leavesProvider;
 *    public final List<TreeDecorator> decorators;
 *    public transient boolean fromSapling;
 *    public final FoliagePlacer foliagePlacer;
 *    public final AbstractTrunkPlacer trunkPlacer;
 *    public final AbstractFeatureSizeType minimumSize;
 *    public final int maxWaterDepth;
 *    public final boolean ignoreVines;
 *    public final Heightmap.Type heightmap;
 *
 * TreeFeature
 *      doPlace
 *      place
 *      updateLeaves
 *
 * register
 *
 * ConfiguredFeature
 *     configured
 *      build:
 *          new
 *          decorators
 *          ignoreVines
 *
 *
 * register
 *
 *
 * public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> JUNGLE_TREE =
 *      register("jungle_tree",
 *      Feature.TREE.configured(
 *      (new BaseTreeFeatureConfig.Builder(
 *          new SimpleBlockStateProvider(Features.States.JUNGLE_LOG),
 *          new SimpleBlockStateProvider(Features.States.JUNGLE_LEAVES),
 *          new BlobFoliagePlacer(FeatureSpread.fixed(2),
 *          FeatureSpread.fixed(0), 3),
 *          new StraightTrunkPlacer(4, 8, 0),
 *          new TwoLayerFeature(1, 0, 1))).
 *      decorators(ImmutableList.of(
 *          new CocoaTreeDecorator(0.2F),
 *          TrunkVineTreeDecorator.INSTANCE,
 *          LeaveVineTreeDecorator.INSTANCE))
 *     .ignoreVines()
 *     .build()
 *     ));
 * */
//
//public class RealWorldTree extends Tree {
//    @Override
//    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean largeHive) {
//        return ConfiguredFeatures.CANOPY_TREE_BASE;
//    }
//}
