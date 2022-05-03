package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(creativeTab);
    }

    public static ItemGroup creativeTab = new ItemGroup(Reference.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.COMMAND_BLOCK.asItem());
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,Reference.MOD_ID);

/*    public static void registerBlockItems(RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(blockItem(ModBlocks.LIGHT_CLOUD));

    }

    //"class" block item
    private static <B extends Block> Item blockItem(RegistryObject<B> block) {
        return makeBlockItem(new BlockItem(block.get(), defaultBuilder()), block);
    }

    private static Item makeBlockItem(Item blockitem, RegistryObject<? extends Block> block) {
        return blockitem.setRegistryName(Objects.requireNonNull(block.getId()));
    }


    //all about creative tab
    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(creativeTab);
    }

    public static ItemGroup creativeTab = new ItemGroup(Reference.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.COMMAND_BLOCK.asItem());
        }
    };*/
}
