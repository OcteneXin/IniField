package com.octenexin.inifield.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import com.octenexin.inifield.world.features.trees.WorldTreeFoliagePlacer;
import com.octenexin.inifield.world.features.trees.WorldTreeTrunkPlacer;
import com.octenexin.inifield.world.features.trees.WorldTreeVineDecorator;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class ModConfiguredFeatures {


//    private static final LiquidsConfig SUGAR_WATER_SPRING_CONFIG = new LiquidsConfig(BzFluids.SUGAR_WATER_FLUID.get().defaultFluidState(), true, 4, 1, ImmutableSet.of(Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK));

      public static final ConfiguredFeature<?, ?> WATER_CANDLE = ModFeatures.WATER_CANDLE.get().configured(IFeatureConfig.NONE).count(20).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);

      public static final ConfiguredFeature<?,?> FLOATING_BOAT=ModFeatures.FLOATING_BOAT.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "floating_boat"), 1)
                    ),-1)).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);

    public static final ConfiguredFeature<?, ?> DOCK = ModFeatures.DOCK.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);

    public static final ConfiguredFeature<?, ?> EXTERM_BEACON = ModFeatures.EXTERM_BEACON.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE).chance(20);

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

    public static final ConfiguredFeature<?, ?> WHEAT_HOUSES = ModFeatures.FEATURE_ON_GRASS.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "broken_barn"), 1),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_small_house_1"), 1),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_eating_area"), 2),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_small_house_2"), 2),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_small_house_3"), 1),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_map_house"), 1)
            ),0)).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(30);

    public static final ConfiguredFeature<?,?> FANCY_SPRUCE_TREE = ModFeatures.FEATURE_ON_GRASS.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "fancy_spruce_tree"), 2),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "big_fancy_spruce_tree"), 1)
                    ),-1)
            ).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(5);


    public static final ConfiguredFeature<?,?> VILLAGERS=ModFeatures.FEATURE_ON_DIRT.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "big_villager"), 5),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "small_villager"), 1)
                    ),0)).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(5);

    public static final ConfiguredFeature<?,?> WHEAT_LAND_LIGHT=ModFeatures.FEATURE_ON_GRASS.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "wheat_land_light"), 1)
                    ),0)).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(5);



    public static final ConfiguredFeature<?, ?> WHEAT_FARMLAND = ModFeatures.WHEAT_FARMLAND.get().configured(
            IFeatureConfig.NONE
    );

    public static final ConfiguredFeature<?, ?> REDSTONE_LIGHT = ModFeatures.REDSTONE_LIGHT.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4);

    public static final ConfiguredFeature<?, ?> FLOOR_LANTERN = ModFeatures.FLOOR_LANTERN.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE);

    public static final ConfiguredFeature<?, ?> SCARECROW = ModFeatures.SCARECROW.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(10);

    public static final ConfiguredFeature<?, ?> SPRINKLER_IRRIGATION = ModFeatures.SPRINKLER_IRRIGATION.get().configured(
            IFeatureConfig.NONE
    ).chance(20);

    public static final ConfiguredFeature<?, ?> SCATTERED_STONE = ModFeatures.SCATTERED_STONE.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(5);


    public static final ConfiguredFeature<?, ?> FROZEN_FERN = ModFeatures.FROZEN_FERN.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "big_frozen_fern"), 1),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "small_frozen_fern"), 2),
                            Pair.of(new ResourceLocation(Reference.MOD_ID, "tiny_frozen_fern"), 5)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2);

    public static final ConfiguredFeature<?, ?> NOTCH_SCULPTURE = ModFeatures.NOTCH_SCULPTURE.get().configured(
            new NbtFeatureConfig(
                    new ResourceLocation("empty"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(new ResourceLocation(Reference.MOD_ID, "notch_sculpture"), 1)),
                    0
            )).decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(10);

    public static final ConfiguredFeature<?, ?> SCATTERED_ICE = ModFeatures.SCATTERED_ICE.get().configured(
            IFeatureConfig.NONE
    ).decorated(Features.Placements.HEIGHTMAP_SQUARE);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

        Registry.register(registry, IniField.locate("water_candle"),WATER_CANDLE);
        Registry.register(registry, IniField.locate("floating_boat"),FLOATING_BOAT);
        Registry.register(registry, IniField.locate("dock"),DOCK);
        Registry.register(registry, IniField.locate("exterm_beacon"),EXTERM_BEACON);
        Registry.register(registry, IniField.locate("patch_command_block"),PATCH_COMMAND_BLOCK);
        Registry.register(registry,IniField.locate("world_tree"),WORLD_TREE);
        Registry.register(registry,IniField.locate("patch_snow_drop_flower"),PATCH_SNOW_DROP_FLOWER);
        Registry.register(registry,IniField.locate("wheat_farmland"),WHEAT_FARMLAND);
        Registry.register(registry,IniField.locate("redstone_light"),REDSTONE_LIGHT);
        Registry.register(registry,IniField.locate("wheat_land_light"),WHEAT_LAND_LIGHT);
        Registry.register(registry,IniField.locate("floor_lantern"),FLOOR_LANTERN);
        Registry.register(registry,IniField.locate("scarecrow"),SCARECROW);
        Registry.register(registry,IniField.locate("fancy_spruce_tree"),FANCY_SPRUCE_TREE);
        Registry.register(registry,IniField.locate("sprinkler_irrigation"),SPRINKLER_IRRIGATION);
        Registry.register(registry,IniField.locate("scattered_stone"),SCATTERED_STONE);
        Registry.register(registry,IniField.locate("wheat_houses"),WHEAT_HOUSES);
        Registry.register(registry,IniField.locate("villagers"),VILLAGERS);
        Registry.register(registry,IniField.locate("frozen_fern"),FROZEN_FERN);
        Registry.register(registry,IniField.locate("notch_sculpture"),NOTCH_SCULPTURE);
        Registry.register(registry,IniField.locate("scattered_ice"),SCATTERED_ICE);

        IniField.LOGGER.debug("configured feature!!!!!!!!!!!!!!!!!!!!!!!!!!");

   }
}
