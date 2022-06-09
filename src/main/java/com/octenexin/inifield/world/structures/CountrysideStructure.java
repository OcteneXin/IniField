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
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class CountrysideStructure extends JigsawStructure {
    public CountrysideStructure(Codec<VillageConfig> p_i241978_1_) {
        super(p_i241978_1_, 0,true,true);
    }

    @Override
    public IStartFactory<VillageConfig> getStartFactory() {
        return CountrysideStructure.Start::new;
    }

    @Override
    public boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, VillageConfig config) {
        return true;
    }

    /**
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart<VillageConfig> {

        private final long seed;

        public Start(Structure<VillageConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
            seed = seedIn;
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, VillageConfig config) {
            int x = chunkX << 4+7;
            int z = chunkZ << 4+7;

            IBlockReader columnOfBlocks=chunkGenerator.getBaseColumn(x,z);
            BlockPos.Mutable mutable = new BlockPos.Mutable(x,255,z);
            BlockState state;
            int y;
            for(y=255;y>0;y--){
                state=columnOfBlocks.getBlockState(mutable);
                if(!state.isAir()||!state.getFluidState().isEmpty())break;
                mutable.move(Direction.DOWN);
            }
            if(y==0){
                y=64;
            }

            BlockPos centerPos = new BlockPos(x, y+1, z);

            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(Reference.MOD_ID,"broken_barn")), 7),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    centerPos,
                    this.pieces,
                    this.random,
                    false,
                    false);


            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for(StructurePiece structurePiece : this.pieces){
                // centers the whole structure to structureCenter
                structurePiece.move(xOffset, 0, zOffset);
            }

            // Sets the bounds of the structure once you are finished.
            this.calculateBoundingBox();

            IniField.LOGGER.debug("generated countryside_wheat at "
                    +this.pieces.get(0).getBoundingBox().x0+" "
                    +this.pieces.get(0).getBoundingBox().y0+" "
                    +this.pieces.get(0).getBoundingBox().z0);
        }

    }
}
