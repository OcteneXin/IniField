package com.octenexin.inifield.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.utils.Reference;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class AetherChunkGen extends ChunkGenerator {
    public static void registerChunkgenerator() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Reference.MOD_ID, "chunk_generator"), AetherChunkGen.CODEC);
    }

    public static final Codec<AetherChunkGen> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
                    DimensionStructuresSettings.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getSettings))
            ).apply(instance, instance.stable(AetherChunkGen::new)));

    private static final float[] field_16649 = Util.make(new float[13824], (array) -> {
        for(int i = 0; i < 24; ++i) {
            for(int j = 0; j < 24; ++j) {
                for(int k = 0; k < 24; ++k) {
                    array[i * 24 * 24 + j * 24 + k] = (float)method_16571(j - 12, k - 12, i - 12);
                }
            }
        }
    });

    private static final int LERP_RANGE = 3;
    private static final float[] HEIGHT_LERP = Util.make(new float[(LERP_RANGE * 2 + 1) * (LERP_RANGE * 2  + 1)], (fs) -> {
        for(int i = -LERP_RANGE; i <= LERP_RANGE; ++i) {
            for(int j = -LERP_RANGE; j <= LERP_RANGE; ++j) {
                float f = 1.0F / MathHelper.sqrt((float)(i * i + j * j) + 0.2F);
                fs[i + LERP_RANGE + (j + LERP_RANGE) * (LERP_RANGE * 2 + 1)] = f;
            }
        }
    });

    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
    protected final BlockState defaultBlock;
    protected final BlockState defaultFluid;
    private final int verticalNoiseResolution;
    private final int horizontalNoiseResolution;
    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;
    protected final SharedSeedRandom random;
    private final OctavesNoiseGenerator lowerInterpolatedNoise;
    private final OctavesNoiseGenerator upperInterpolatedNoise;
    private final OctavesNoiseGenerator interpolationNoise;
    private final INoiseGenerator surfaceDepthNoise;
    private final OctavesNoiseGenerator field_24776;
    private final int height;

    public AetherChunkGen(BiomeProvider biomeSource, DimensionStructuresSettings dimensionStructuresSettings) {
        this(biomeSource, biomeSource, dimensionStructuresSettings);
    }

    private AetherChunkGen(BiomeProvider biomeSource, BiomeProvider biomeSource2, DimensionStructuresSettings dimensionStructuresSettings) {
        super(biomeSource, biomeSource2, dimensionStructuresSettings, 0);

        // we need the seed passed into here
        this.random = new SharedSeedRandom(0);

        this.height = 256;
        this.verticalNoiseResolution = 8;
        this.horizontalNoiseResolution = 4;
        this.defaultBlock = Blocks.HONEYCOMB_BLOCK.defaultBlockState();
        this.defaultFluid = Blocks.WATER.defaultBlockState();
        this.noiseSizeX = 16 / this.horizontalNoiseResolution;
        this.noiseSizeY = this.height / this.verticalNoiseResolution;
        this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
        this.lowerInterpolatedNoise = new OctavesNoiseGenerator(this.random, IntStream.rangeClosed(-15, 0));
        this.upperInterpolatedNoise = new OctavesNoiseGenerator(this.random, IntStream.rangeClosed(-15, 0));
        this.interpolationNoise = new OctavesNoiseGenerator(this.random, IntStream.rangeClosed(-7, 0));
        this.surfaceDepthNoise = new PerlinNoiseGenerator(this.random, IntStream.rangeClosed(-3, 0));
        this.random.consumeCount(2620);
        this.field_24776 = new OctavesNoiseGenerator(this.random, IntStream.rangeClosed(-15, 0));
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    // CLIENT-SIDED
    public ChunkGenerator withSeed(long seed) {
        return new AetherChunkGen(this.biomeSource.withSeed(seed), this.getSettings());
    }

    private double sampleNoise(int x, int y, int z, double horizontalScaleX, double verticalScale, double horizontalScaleZ, double horizontalStretch, double verticalStretch) {
        double d = 0.0D;
        double e = 0.0D;
        double f = 0.0D;
        double g = 1.0D;

        for(int i = 0; i < 16; ++i) {
            double h = OctavesNoiseGenerator.wrap((double)x * horizontalScaleX * g);
            double j = OctavesNoiseGenerator.wrap((double)y * verticalScale * g);
            double k = OctavesNoiseGenerator.wrap((double)z * horizontalScaleZ * g);
            double l = verticalScale * g;
            ImprovedNoiseGenerator perlinNoiseSampler = this.lowerInterpolatedNoise.getOctaveNoise(i);
            if (perlinNoiseSampler != null) {
                d += perlinNoiseSampler.noise(h, j, k, l, (double)y * l) / g;
            }

            ImprovedNoiseGenerator perlinNoiseSampler2 = this.upperInterpolatedNoise.getOctaveNoise(i);
            if (perlinNoiseSampler2 != null) {
                e += perlinNoiseSampler2.noise(h, j, k, l, (double)y * l) / g;
            }

            if (i < 8) {
                ImprovedNoiseGenerator perlinNoiseSampler3 = this.interpolationNoise.getOctaveNoise(i);
                if (perlinNoiseSampler3 != null) {
                    f += perlinNoiseSampler3.noise(OctavesNoiseGenerator.wrap((double)x * horizontalStretch * g), OctavesNoiseGenerator.wrap((double)y * verticalStretch * g), OctavesNoiseGenerator.wrap((double)z * horizontalStretch * g), verticalStretch * g, (double)y * verticalStretch * g) / g;
                }
            }

            g /= 2.0D;
        }

        return MathHelper.clampedLerp(d / 512.0D, e / 512.0D, (f / 10.0D + 1.0D) / 2.0D);
    }

    private double[] sampleNoiseColumn(int x, int z) {
        double[] ds = new double[this.noiseSizeY + 1];
        this.sampleNoiseColumn(ds, x, z);
        return ds;
    }

    private void sampleNoiseColumn(double[] buffer, int x, int z) {
        double ac;
        double ad;
        double topSlideTarget;
        double topSlideSize;
        float g = 0.0F;
        float h = 0.0F;
        float i = 0.0F;
        int k = this.getSeaLevel();
        float l = this.biomeSource.getNoiseBiome(x, k, z).getDepth();

        for(int m = -LERP_RANGE; m <= LERP_RANGE; ++m) {
            for(int n = -LERP_RANGE; n <= LERP_RANGE; ++n) {
                Biome biome = this.biomeSource.getNoiseBiome(x + m, k, z + n);
                float o = biome.getDepth();
                float p = biome.getScale();

                float u = o > l ? 0.5F : 1.0F;
                float v = u * HEIGHT_LERP[m + LERP_RANGE + (n + LERP_RANGE) * (LERP_RANGE * 2 + 1)] / (3.0F);
                g += p * v;
                h += o * v;
                i += v;
            }
        }

        float w = h / i;
        float yy = g / i;
        topSlideTarget = w * 0.5F - 0.125F;
        topSlideSize = yy * 0.9F + 0.1F;
        ac = topSlideTarget * 0.265625D;
        ad = 96.0D / topSlideSize;

        double xScale = 2600D;
        double zScale = 250D;
        double yScale = 16D;
        double xzStretch = 8D;
        double yStretch = 2D;
        topSlideTarget = -10;
        topSlideSize = 3;
        double topSlideOffset = 0;
        double randomDensity = this.method_28553(x, z);
        double densityFactor = 1.0D;
        double densityOffset = -0.46875D;

        for(int y = 0; y <= this.noiseSizeY; ++y) {
            double as = this.sampleNoise(x, y*2, z, xScale, yScale, zScale, xzStretch, yStretch);
            double at = 1.0D - (double)y * 2.0D / (double)this.noiseSizeY + randomDensity;
            double au = at * densityFactor + densityOffset;
            double av = (au + ac) * ad;
            if (av > 0.0D) {
                as += av * 4.0D;
            } else {
                as += av;
            }

            double ax;
            ax = ((double)(this.noiseSizeY - y) - topSlideOffset) / topSlideSize;
            as = MathHelper.clampedLerp(topSlideTarget, as, ax);
            buffer[y] = as;
        }

    }

    private double method_28553(int i, int j) {
        double d = this.field_24776.getValue(i * 200, 10.0D, j * 200, 1.0D, 0.0D, true);
        double f;
        if (d < 0.0D) {
            f = -d * 0.3D;
        } else {
            f = d;
        }

        double g = f * 24.575625D - 2.0D;
        return g < 0.0D ? g * 0.009486607142857142D : Math.min(g, 1.0D) * 0.006640625D;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return this.sampleHeightmap(x, z, null, heightmapType.isOpaque());
    }

    @Override
    public IBlockReader getBaseColumn(int x, int z) {
        BlockState[] blockStates = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
        this.sampleHeightmap(x, z, blockStates, null);
        return new Blockreader(blockStates);
    }

    private int sampleHeightmap(int x, int z, BlockState[] states, Predicate<BlockState> predicate) {
        int i = Math.floorDiv(x, this.horizontalNoiseResolution);
        int j = Math.floorDiv(z, this.horizontalNoiseResolution);
        int k = Math.floorMod(x, this.horizontalNoiseResolution);
        int l = Math.floorMod(z, this.horizontalNoiseResolution);
        double d = (double)k / (double)this.horizontalNoiseResolution;
        double e = (double)l / (double)this.horizontalNoiseResolution;
        double[][] ds = new double[][]{this.sampleNoiseColumn(i, j), this.sampleNoiseColumn(i, j + 1), this.sampleNoiseColumn(i + 1, j), this.sampleNoiseColumn(i + 1, j + 1)};

        for(int m = this.noiseSizeY - 1; m >= 0; --m) {
            double f = ds[0][m];
            double g = ds[1][m];
            double h = ds[2][m];
            double n = ds[3][m];
            double o = ds[0][m + 1];
            double p = ds[1][m + 1];
            double q = ds[2][m + 1];
            double r = ds[3][m + 1];

            for(int s = this.verticalNoiseResolution - 1; s >= 0; --s) {
                double t = (double)s / (double)this.verticalNoiseResolution;
                double u = MathHelper.lerp3(t, d, e, f, o, h, q, g, p, n, r);
                int v = m * this.verticalNoiseResolution + s;
                BlockState blockState = this.getBlockState(u, v);
                if (states != null) {
                    states[v] = blockState;
                }

                if (predicate != null && predicate.test(blockState)) {
                    return v + 1;
                }
            }
        }

        return 0;
    }

    protected BlockState getBlockState(double density, int y) {
        BlockState blockState3;
        if (density > 0.0D) {
            blockState3 = this.defaultBlock;
        } else if (y < this.getSeaLevel()) {
            blockState3 = this.defaultFluid;
        } else {
            blockState3 = CAVE_AIR;
        }

        return blockState3;
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.x;
        int j = chunkPos.z;
        SharedSeedRandom chunkRandom = new SharedSeedRandom();
        chunkRandom.setBaseChunkSeed(i, j);
        ChunkPos chunkPos2 = chunk.getPos();
        int k = chunkPos2.getMinBlockX();
        int l = chunkPos2.getMinBlockZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int m = 0; m < 16; ++m) {
            for(int n = 0; n < 16; ++n) {
                int o = k + m;
                int p = l + n;
                int q = chunk.getHeight(Heightmap.Type.WORLD_SURFACE_WG, m, n) + 1;
                double e = this.surfaceDepthNoise.getSurfaceNoiseValue((double)o * 0.0625D, (double)p * 0.0625D, 0.0625D, (double)m * 0.0625D) * 15.0D;
                region.getBiome(mutable.set(k + m, q, l + n)).buildSurfaceAt(chunkRandom, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), region.getSeed());
            }
        }

        this.makeCeilingAndFloor(chunk, chunkRandom);
    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager accessor, IChunk chunk) {
        ObjectList<StructurePiece> objectList = new ObjectArrayList<>(10);
        ObjectList<JigsawJunction> objectList2 = new ObjectArrayList<>(32);
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.x;
        int j = chunkPos.z;
        int k = i << 4;
        int l = j << 4;

        for (Structure<?> feature : Structure.NOISE_AFFECTING_FEATURES) {
            accessor.startsForFeature(SectionPos.of(chunkPos, 0), feature).forEach((start) -> {
                Iterator<StructurePiece> structurePiecesIterator = start.getPieces().iterator();

                while (true) {
                    StructurePiece structurePiece;
                    do {
                        if (!structurePiecesIterator.hasNext()) {
                            return;
                        }

                        structurePiece = structurePiecesIterator.next();
                    } while (!structurePiece.isCloseToChunk(chunkPos, 12));

                    if (structurePiece instanceof AbstractVillagePiece) {
                        AbstractVillagePiece poolStructurePiece = (AbstractVillagePiece) structurePiece;
                        JigsawPattern.PlacementBehaviour projection = poolStructurePiece.getElement().getProjection();
                        if (projection == JigsawPattern.PlacementBehaviour.RIGID) {
                            objectList.add(poolStructurePiece);
                        }

                        for (JigsawJunction jigsawJunction : poolStructurePiece.getJunctions()) {
                            int kx = jigsawJunction.getSourceX();
                            int lx = jigsawJunction.getSourceZ();
                            if (kx > k - 12 && lx > l - 12 && kx < k + 15 + 12 && lx < l + 15 + 12) {
                                objectList2.add(jigsawJunction);
                            }
                        }
                    }
                    else {
                        objectList.add(structurePiece);
                    }
                }
            });
        }

        double[][][] ds = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

        for(int m = 0; m < this.noiseSizeZ + 1; ++m) {
            ds[0][m] = new double[this.noiseSizeY + 1];
            this.sampleNoiseColumn(ds[0][m], i * this.noiseSizeX, j * this.noiseSizeZ + m);
            ds[1][m] = new double[this.noiseSizeY + 1];
        }

        ChunkPrimer protoChunk = (ChunkPrimer)chunk;
        Heightmap heightmap = protoChunk.getOrCreateHeightmapUnprimed(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = protoChunk.getOrCreateHeightmapUnprimed(Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        ObjectListIterator<StructurePiece> objectListIterator = objectList.iterator();
        ObjectListIterator<JigsawJunction> objectListIterator2 = objectList2.iterator();

        int tempYSection;
        double d;
        double e ;
        double f;
        double g;
        double h;
        double r;
        double s;
        double t;
        for(int n = 0; n < this.noiseSizeX; ++n) {
            int p;
            for(p = 0; p < this.noiseSizeZ + 1; ++p) {
                this.sampleNoiseColumn(ds[1][p], i * this.noiseSizeX + n + 1, j * this.noiseSizeZ + p);
            }

            for(p = 0; p < this.noiseSizeZ; ++p) {
                ChunkSection chunkSection = protoChunk.getOrCreateSection(15);
                chunkSection.acquire();

                for(int ySection = this.noiseSizeY - 1; ySection >= 0; --ySection) {

                    /*
                     * When the noise is greater than 16 (chunks), begin the mirroring effect
                     */
                    if (ySection > 15) {
                        /*
                         * Move down one because we ended on that y chunk before and by mirroring that chunk, the transition between the lower
                         * half and upper half is smoother.
                         */
                        tempYSection = ySection;
                        ySection = 31 - ySection;

                        d = ds[0][p][ySection + 1];
                        e = ds[0][p + 1][ySection + 1];
                        f = ds[1][p][ySection + 1];
                        g = ds[1][p + 1][ySection + 1];
                        h = ds[0][p][ySection];
                        r = ds[0][p + 1][ySection];
                        s = ds[1][p][ySection];
                        t = ds[1][p + 1][ySection];

                        ySection = tempYSection;
                    } else {
                        /*
                         * Generate the y chunk as normal for y chunks 16 and below
                         */
                        d = ds[0][p][ySection];
                        e = ds[0][p + 1][ySection];
                        f = ds[1][p][ySection];
                        g = ds[1][p + 1][ySection];
                        h = ds[0][p][ySection + 1];
                        r = ds[0][p + 1][ySection + 1];
                        s = ds[1][p][ySection + 1];
                        t = ds[1][p + 1][ySection + 1];
                    }


                    for(int yInChunk = this.verticalNoiseResolution - 1; yInChunk >= 0; --yInChunk) {
                        int currentY = ySection * this.verticalNoiseResolution + yInChunk;
                        int yPosInChunkSection = currentY & 15;
                        int yChunkSection = currentY >> 4;
                        if (chunkSection.bottomBlockY() >> 4 != yChunkSection) {
                            chunkSection.release();
                            chunkSection = protoChunk.getOrCreateSection(yChunkSection);
                            chunkSection.acquire();
                        }

                        double y = (double)yInChunk / (double)this.verticalNoiseResolution;
                        double z = MathHelper.lerp(y, d, h);
                        double aa = MathHelper.lerp(y, f, s);
                        double ab = MathHelper.lerp(y, e, r);
                        double ac = MathHelper.lerp(y, g, t);

                        for(int ad = 0; ad < this.horizontalNoiseResolution; ++ad) {
                            int ae = k + n * this.horizontalNoiseResolution + ad;
                            int af = ae & 15;
                            double ag = (double)ad / (double)this.horizontalNoiseResolution;
                            double ah = MathHelper.lerp(ag, z, aa);
                            double ai = MathHelper.lerp(ag, ab, ac);

                            for(int aj = 0; aj < this.horizontalNoiseResolution; ++aj) {
                                int ak = l + p * this.horizontalNoiseResolution + aj;
                                int al = ak & 15;
                                double am = (double)aj / (double)this.horizontalNoiseResolution;
                                double an = MathHelper.lerp(am, ah, ai);
                                double ao = MathHelper.clamp(an / 200.0D, -1.0D, 1.0D);

                                int at;
                                int au;
                                int ar;
                                for(ao = ao / 2.0D - ao * ao * ao / 24.0D; objectListIterator.hasNext(); ao += method_16572(at, au, ar) * 0.8D) {
                                    StructurePiece structurePiece = objectListIterator.next();
                                    MutableBoundingBox blockBox = structurePiece.getBoundingBox();
                                    at = Math.max(0, Math.max(blockBox.x0 - ae, ae - blockBox.x1));
                                    au = currentY - (blockBox.y0 + (structurePiece instanceof AbstractVillagePiece ? ((AbstractVillagePiece)structurePiece).getGroundLevelDelta() : 0));
                                    ar = Math.max(0, Math.max(blockBox.z0 - ak, ak - blockBox.z1));
                                }

                                objectListIterator.back(objectList.size());

                                while(objectListIterator2.hasNext()) {
                                    JigsawJunction jigsawJunction = objectListIterator2.next();
                                    int as = ae - jigsawJunction.getSourceX();
                                    at = currentY - jigsawJunction.getSourceGroundY();
                                    au = ak - jigsawJunction.getSourceZ();
                                    ao += method_16572(as, at, au) * 0.4D;
                                }

                                objectListIterator2.back(objectList2.size());
                                BlockState blockState = this.getBlockState(ao, currentY);
                                if (blockState != CAVE_AIR) {
                                    if (blockState.getLightEmission() != 0) {
                                        mutable.set(ae, currentY, ak);
                                        protoChunk.addLight(mutable);
                                    }

                                    chunkSection.setBlockState(af, yPosInChunkSection, al, blockState, false);
                                    heightmap.update(af, currentY, al, blockState);
                                    heightmap2.update(af, currentY, al, blockState);
                                }
                            }
                        }
                    }
                }

                chunkSection.release();
            }

            double[][] es = ds[0];
            ds[0] = ds[1];
            ds[1] = es;
        }

    }

    private static double method_16572(int i, int j, int k) {
        int l = i + 12;
        int m = j + 12;
        int n = k + 12;
        if (l >= 0 && l < 24) {
            if (m >= 0 && m < 24) {
                return n >= 0 && n < 24 ? (double)field_16649[n * 24 * 24 + l * 24 + m] : 0.0D;
            } else {
                return 0.0D;
            }
        } else {
            return 0.0D;
        }
    }

    private static double method_16571(int i, int j, int k) {
        double d = i * i + k * k;
        double e = (double)j + 0.5D;
        double f = e * e;
        double g = Math.pow(2.718281828459045D, -(f / 16.0D + d / 16.0D));
        double h = -e * MathHelper.fastInvSqrt(f / 2.0D + d / 2.0D) / 2.0D;
        return h * g;
    }

    @Override
    public int getGenDepth() {
        return this.height;
    }

    /**
     * Dedicated to spawning slimes/bees when generating chunks initially.
     * This is so there's lots of bees and the slime can spawn despite the
     * slime having extremely restrictive spawning mechanics.
     * <p>
     * Also spawns bees with chance to bee full of pollen
     * <p>
     * This is mainly vanilla code but with biome$spawnlistentry changed to
     * use bee/slime and the restrictive terrain check called on the entity removed.
     * The height is also restricted so the mob cannot spawn on the ceiling of this
     * dimension as well.
     */


    /**
     * Creates the ceiling and floor that separates the dimension from the emptiness above y = 256 and below y = 0.
     * <p>
     * We use honeycomb blocks instead of Bedrock.
     */
    protected void makeCeilingAndFloor(IChunk chunk, Random random) {
        return;
    }

    @Override
    public int getSeaLevel() {
        return 40;
    }
}
