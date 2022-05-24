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

    public static final ConfiguredFeature<?, ?> BROKEN_BARN = ModFeatures.BROKEN_BARN.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "broken_barn"), 1)),
                    -1
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(40);


    public static final ConfiguredFeature<?, ?> SMALL_BARN = ModFeatures.SMALL_BARN.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "small_barn"), 1)),
                    -3
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(50);

    public static final ConfiguredFeature<?, ?> WHEAT_MILL = ModFeatures.WHEAT_MILL.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_mill"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(100);

    public static final ConfiguredFeature<?, ?> ROUND_SPACE = ModFeatures.ROUND_SPACE.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "round_space"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(80);

    public static final ConfiguredFeature<?, ?> COUNTRY_MARKET = ModFeatures.COUNTRY_MARKET.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "country_market"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(200);

    public static final ConfiguredFeature<?, ?> BREAKFAST_STORE = ModFeatures.BREAKFAST_STORE.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "breakfast_store"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(150);

    public static final ConfiguredFeature<?, ?> MILKTEA_STORE = ModFeatures.MILKTEA_STORE.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "milktea_store"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(100);




    public static final ConfiguredFeature<?, ?> WHEAT_FARMLAND = ModFeatures.WHEAT_FARMLAND.get().configured(
            IFeatureConfig.NONE
    );

    public static final ConfiguredFeature<?, ?> REDSTONE_LIGHT = ModFeatures.REDSTONE_LIGHT.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4);

    public static final ConfiguredFeature<?, ?> SPRINKLER_IRRIGATION = ModFeatures.SPRINKLER_IRRIGATION.get().configured(
            IFeatureConfig.NONE
    ).chance(20);

    public static final ConfiguredFeature<?, ?> SCATTERED_STONE = ModFeatures.SCATTERED_STONE.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(5);




    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

        Registry.register(registry, IniField.locate("weed_grass"), WEED_GRASS);
        Registry.register(registry, IniField.locate("patch_command_block"),PATCH_COMMAND_BLOCK);
        Registry.register(registry,IniField.locate("world_tree"),WORLD_TREE);
        Registry.register(registry,IniField.locate("patch_snow_drop_flower"),PATCH_SNOW_DROP_FLOWER);
        Registry.register(registry,IniField.locate("cmd_cube"),CMD_CUBE);
        Registry.register(registry,IniField.locate("simple_st_nc"),SIMPLE_ST_NC);
        Registry.register(registry,IniField.locate("simple_st_hb"),SIMPLE_ST_HB);
        Registry.register(registry,IniField.locate("wheat_farmland"),WHEAT_FARMLAND);
        Registry.register(registry,IniField.locate("small_barn"),SMALL_BARN);
        Registry.register(registry,IniField.locate("broken_barn"),BROKEN_BARN);
        Registry.register(registry,IniField.locate("redstone_light"),REDSTONE_LIGHT);
        Registry.register(registry,IniField.locate("wheat_mill"),WHEAT_MILL);
        Registry.register(registry,IniField.locate("sprinkler_irrigation"),SPRINKLER_IRRIGATION);
        Registry.register(registry,IniField.locate("scattered_stone"),SCATTERED_STONE);
        Registry.register(registry,IniField.locate("country_market"),COUNTRY_MARKET);
        Registry.register(registry,IniField.locate("milktea_store"),MILKTEA_STORE);
        Registry.register(registry,IniField.locate("breakfast_store"),BREAKFAST_STORE);
        Registry.register(registry,IniField.locate("round_space"),ROUND_SPACE);

   }
}
