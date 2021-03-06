package com.octenexin.inifield.world.templatepools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.octenexin.inifield.IniField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.ProcessorLists;

public class CountrysideWheatPools {

    public static final JigsawPattern START = JigsawPatternRegistry.register(
            new JigsawPattern(
                    new ResourceLocation("village/savanna/town_centers"),//name
                    new ResourceLocation("empty"), //fallback
                    ImmutableList.of(
                            Pair.of(
                                    JigsawPiece.legacy("village/savanna/town_centers/savanna_meeting_point_1"), 100
                            ),
                            Pair.of(
                                    JigsawPiece.legacy("village/savanna/town_centers/savanna_meeting_point_2"), 50
                            ),
                            Pair.of(JigsawPiece.legacy("village/savanna/town_centers/savanna_meeting_point_3"), 150),
                            Pair.of(JigsawPiece.legacy("village/savanna/town_centers/savanna_meeting_point_4"), 150)),//list
                    JigsawPattern.PlacementBehaviour.RIGID));//behaviour

    public static void bootstrap() {
        IniField.LOGGER.debug("Countryside wheat pools Bootstrap!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    static {
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/streets"),
                        new ResourceLocation("village/savanna/terminators"),
                        ImmutableList.of(
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/corner_01", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/corner_03", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_02", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_04", ProcessorLists.STREET_SAVANNA), 7),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_05", ProcessorLists.STREET_SAVANNA), 3),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_06", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_08", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_09", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_10", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/straight_11", ProcessorLists.STREET_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_02", ProcessorLists.STREET_SAVANNA), 1),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_03", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_04", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_05", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_06", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/crossroad_07", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/split_01", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/split_02", ProcessorLists.STREET_SAVANNA), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/streets/turn_01", ProcessorLists.STREET_SAVANNA), 3)),
                        JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/houses"),
                        new ResourceLocation("village/savanna/terminators"),
                        ImmutableList.of(Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_2"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_3"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_4"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_5"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_6"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_7"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_house_8"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_medium_house_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_medium_house_2"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_butchers_shop_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_butchers_shop_2"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_tool_smith_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_fletcher_house_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_shepherd_1"), 7),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_armorer_1"), 1),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_fisher_cottage_1"), 3),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_tannery_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_cartographer_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_library_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_mason_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_weaponsmith_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_weaponsmith_2"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_temple_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_temple_2"), 3),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_large_farm_1", ProcessorLists.FARM_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_large_farm_2", ProcessorLists.FARM_SAVANNA), 6),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_small_farm", ProcessorLists.FARM_SAVANNA), 4),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_animal_pen_1"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_animal_pen_2"), 2),
                                Pair.of(JigsawPiece.legacy("village/savanna/houses/savanna_animal_pen_3"), 2),
                                Pair.of(JigsawPiece.empty(), 5))
                        , JigsawPattern.PlacementBehaviour.RIGID));
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/terminators"),
                        new ResourceLocation("empty"),
                        ImmutableList.of(
                                Pair.of(JigsawPiece.legacy("village/plains/terminators/terminator_01", ProcessorLists.STREET_SAVANNA), 1),
                                Pair.of(JigsawPiece.legacy("village/plains/terminators/terminator_02", ProcessorLists.STREET_SAVANNA), 1),
                                Pair.of(JigsawPiece.legacy("village/plains/terminators/terminator_03", ProcessorLists.STREET_SAVANNA), 1),
                                Pair.of(JigsawPiece.legacy("village/plains/terminators/terminator_04", ProcessorLists.STREET_SAVANNA), 1),
                                Pair.of(JigsawPiece.legacy("village/savanna/terminators/terminator_05", ProcessorLists.STREET_SAVANNA), 1)),
                        JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/trees"),
                        new ResourceLocation("empty"),
                        ImmutableList.of(Pair.of(JigsawPiece.feature(Features.ACACIA), 1)),
                        JigsawPattern.PlacementBehaviour.RIGID));
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/decor"),
                        new ResourceLocation("empty"),
                        ImmutableList.of(
                                Pair.of(JigsawPiece.legacy("village/savanna/savanna_lamp_post_01"), 4),
                                Pair.of(JigsawPiece.feature(Features.ACACIA), 4),
                                Pair.of(JigsawPiece.feature(Features.PILE_HAY), 4),
                                Pair.of(JigsawPiece.feature(Features.PILE_MELON), 1),
                                Pair.of(JigsawPiece.empty(), 4)),
                        JigsawPattern.PlacementBehaviour.RIGID));
        JigsawPatternRegistry.register(
                new JigsawPattern(
                        new ResourceLocation("village/savanna/villagers"),
                        new ResourceLocation("empty"),
                        ImmutableList.of(
                                Pair.of(JigsawPiece.legacy("village/savanna/villagers/nitwit"), 1),
                                Pair.of(JigsawPiece.legacy("village/savanna/villagers/baby"), 1),
                                Pair.of(JigsawPiece.legacy("village/savanna/villagers/unemployed"), 10)),
                        JigsawPattern.PlacementBehaviour.RIGID));
        }


}
