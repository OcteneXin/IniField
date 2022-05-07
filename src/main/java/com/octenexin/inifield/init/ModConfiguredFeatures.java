package com.octenexin.inifield.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import com.octenexin.inifield.world.features.trees.WorldTreeClearingDecorator;
import com.octenexin.inifield.world.features.trees.WorldTreeFoliagePlacer;
import com.octenexin.inifield.world.features.trees.WorldTreeTrunkPlacer;
import com.octenexin.inifield.world.features.trees.WorldTreeVineDecorator;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;

public class ModConfiguredFeatures {


//    private static final LiquidsConfig SUGAR_WATER_SPRING_CONFIG = new LiquidsConfig(BzFluids.SUGAR_WATER_FLUID.get().defaultFluidState(), true, 4, 1, ImmutableSet.of(Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK));
      public static final ConfiguredFeature<?, ?> WEED_GRASS = ModFeatures.SCATTERED_WEED_GRASS.get().configured(new ProbabilityConfig(0.8F)).count(80).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);
      public static final ConfiguredFeature<?, ?> PATCH_COMMAND_BLOCK = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(
            new WeightedBlockStateProvider().add(Blocks.COMMAND_BLOCK.defaultBlockState(),5).add(Blocks.CHAIN_COMMAND_BLOCK.defaultBlockState(),2).add(Blocks.REPEATING_COMMAND_BLOCK.defaultBlockState(),1), SimpleBlockPlacer.INSTANCE)).
            tries(64).
            whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).
            noProjection()
            .build())
            .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(12);

    public static final ConfiguredFeature<?, ?> PATCH_SNOW_DROP_FLOWER = Feature.RANDOM_PATCH
            .configured((new BlockClusterFeatureConfig.Builder
                    (new SimpleBlockStateProvider(ModBlocks.SNOW_DROP_FLOWER.get().defaultBlockState()),
                            SimpleBlockPlacer.INSTANCE)).tries(64).noProjection().build())
            .decorated(Features.Placements.HEIGHTMAP_SQUARE);


      public static final ConfiguredFeature<?, ?> WORLD_TREE =
              Feature.TREE.configured(
                      (new BaseTreeFeatureConfig.Builder(
                              new SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState()),
                              new SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultBlockState()),
                              new WorldTreeFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(2), 2),
                              new WorldTreeTrunkPlacer(30, 2, 5),
                              new TwoLayerFeature(1, 1, 2))
                              .decorators(ImmutableList.of(WorldTreeVineDecorator.INSTANCE))
                              .ignoreVines()

                      )
                              .build())
              .decorated(ModTreeFeatures.CONFIGURED_WORLD_TREE_PLACEMENT);


    public static final ConfiguredFeature<?, ?> CMD_CUBE = ModFeatures.CMD_CUBE.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "cmd_cube"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(32);

    public static final ConfiguredFeature<?, ?> SIMPLE_ST_NC = ModFeatures.SIMPLE_ST_NC.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "simple_st_nc"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(10);

    public static final ConfiguredFeature<?, ?> SIMPLE_ST_HB = ModFeatures.SIMPLE_ST_HB.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "simple_st_hb"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(10);

//
//    public static final ConfiguredFeature<?, ?> BEE_DUNGEON = BzFeatures.BEE_DUNGEON.get().configured(
//            new NbtFeatureConfig(
//                    new ResourceLocation(Bumblezone.MODID, "bee_dungeon_processors"),
//                    new ResourceLocation("empty"),
//                    ImmutableList.of(Pair.of(new ResourceLocation(Bumblezone.MODID, "bee_dungeon"), 1)),
//                    -4
//            )).decorated(BzPlacements.BEE_DUNGEON_PLACER.get().configured(IPlacementConfig.NONE));
//
//    public static final ConfiguredFeature<?, ?> SPIDER_INFESTED_BEE_DUNGEON = BzFeatures.SPIDER_INFESTED_BEE_DUNGEON.get().configured(
//            new NbtFeatureConfig(
//                    new ResourceLocation(Bumblezone.MODID, "spider_infested_bee_dungeon_processors"),
//                    new ResourceLocation("empty"),
//                    ImmutableList.of(Pair.of(new ResourceLocation(Bumblezone.MODID, "bee_dungeon"), 1)),
//                    -4
//            )).decorated(BzPlacements.BEE_DUNGEON_PLACER.get().configured(IPlacementConfig.NONE));

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

        Registry.register(registry, IniField.locate("weed_grass"), WEED_GRASS);
        Registry.register(registry, IniField.locate("patch_command_block"),PATCH_COMMAND_BLOCK);
        Registry.register(registry,IniField.locate("world_tree"),WORLD_TREE);
        Registry.register(registry,IniField.locate("patch_snow_drop_flower"),PATCH_SNOW_DROP_FLOWER);
        Registry.register(registry,IniField.locate("cmd_cube"),CMD_CUBE);
        Registry.register(registry,IniField.locate("simple_st_nc"),SIMPLE_ST_NC);
        Registry.register(registry,IniField.locate("simple_st_hb"),SIMPLE_ST_HB);

   }
}
