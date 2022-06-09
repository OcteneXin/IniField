package com.octenexin.inifield.world.structures;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class HighwayStructure extends Structure<NoFeatureConfig> {

    public HighwayStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return HighwayStructure.Start::new;
    }

    @Override
    public boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {

        return true;
    }


    /**
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart<NoFeatureConfig>  {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            BlockPos blockpos = new BlockPos(x, 0, z);
            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    new VillageConfig(
                            () -> {
                                return (JigsawPattern)dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(IniField.locate("bridges"));
                            },
                            6
                    ),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos,
                    pieces,
                    random,
                    false,
                    true);
//            this.pieces.forEach((piece) -> {
//                piece.move(0, 32, 0);
//            });
            this.calculateBoundingBox();
        }


    }
}