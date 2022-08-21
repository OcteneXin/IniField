package com.octenexin.inifield.init;

import com.octenexin.inifield.block.*;
import com.octenexin.inifield.item.WeedGrassItem;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    public static final RegistryObject<Block> LIGHT_CLOUD = BLOCKS.register("light_cloud", () -> new CloudBlock(Block.Properties.of(Material.SNOW).strength(0.3F).sound(SoundType.WOOL).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Item> LIGHT_CLOUD_ITEM = ModItems.ITEMS.
            register(LIGHT_CLOUD.getId().getPath(), () -> new BlockItem(LIGHT_CLOUD.get(),ModItems.defaultBuilder()));

    public static final RegistryObject<Block> LIGHT_RAINING_CLOUD = BLOCKS.register("light_raining_cloud", () -> new CloudBlock(Block.Properties.of(Material.SNOW).strength(0.3F).sound(SoundType.WOOL).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Item> LIGHT_RAINING_CLOUD_ITEM = ModItems.ITEMS.
            register(LIGHT_RAINING_CLOUD.getId().getPath(), () -> new BlockItem(LIGHT_RAINING_CLOUD.get(),ModItems.defaultBuilder()));

    public static final RegistryObject<Block> NORMAL_CLOUD = BLOCKS.register("normal_cloud", () -> new CloudBlock(Block.Properties.of(Material.SNOW).strength(0.3F).sound(SoundType.WOOL).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Item> NORMAL_CLOUD_ITEM = ModItems.ITEMS.
            register(NORMAL_CLOUD.getId().getPath(), () -> new BlockItem(NORMAL_CLOUD.get(),ModItems.defaultBuilder()));

    public static final RegistryObject<Block> NORMAL_RAINING_CLOUD = BLOCKS.register("normal_raining_cloud", () -> new CloudBlock(Block.Properties.of(Material.SNOW).strength(0.3F).sound(SoundType.WOOL).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Item> NORMAL_RAINING_CLOUD_ITEM = ModItems.ITEMS.
            register(NORMAL_RAINING_CLOUD.getId().getPath(), () -> new BlockItem(NORMAL_RAINING_CLOUD.get(),ModItems.defaultBuilder()));

    public static final RegistryObject<Block> WEED_GRASS = BLOCKS.register("weed_grass", () -> new WeedGrass(Block.Properties.of(Material.PLANT).strength(0.1F).sound(SoundType.CROP).noOcclusion().isValidSpawn(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Item> WEED_GRASS_ITEM = ModItems.ITEMS.
            register(WEED_GRASS.getId().getPath(), () -> new WeedGrassItem(WEED_GRASS.get(),ModItems.defaultBuilder()));


    public static final RegistryObject<Block> SNOW_DROP_FLOWER=BLOCKS.register("snow_drop_flower",()->new SnowDropFlower(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((p_235464_0_) -> {
        return 9;
    })));

    public static final RegistryObject<Block> LIGHTING_BLOCK = BLOCKS.register("lighting_block", () -> new LightingBlock(Block.Properties.of(Material.AIR).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).lightLevel((i)->{
        return 15;
    })));

    public static final RegistryObject<Item> LIGHTING_BLOCK_ITEM = ModItems.ITEMS.
            register(LIGHTING_BLOCK.getId().getPath(), () -> new BlockItem(LIGHTING_BLOCK.get(),ModItems.defaultBuilder()));


    public static final RegistryObject<Item> SNOW_DROP_FLOWER_ITEM = ModItems.ITEMS.
            register(SNOW_DROP_FLOWER.getId().getPath(), () -> new BlockItem(SNOW_DROP_FLOWER.get(),ModItems.defaultBuilder()));

    public static final RegistryObject<Block> AETHER_PORTAL=BLOCKS.register("aether_portal",()->new AetherPortal(AbstractBlock.Properties.of(Material.PORTAL).sound(SoundType.GRASS).lightLevel((p_235464_0_) -> {
        return 15;
    })));
    public static final RegistryObject<Item> AETHER_PORTAL_ITEM = ModItems.ITEMS.
            register(AETHER_PORTAL.getId().getPath(), () -> new BlockItem(AETHER_PORTAL.get(),ModItems.defaultBuilder()));


    private static Boolean never(BlockState p_235427_0_, IBlockReader p_235427_1_, BlockPos p_235427_2_, EntityType<?> p_235427_3_) {
        return (boolean)false;
    }

    private static boolean never(BlockState p_235436_0_, IBlockReader p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

}
