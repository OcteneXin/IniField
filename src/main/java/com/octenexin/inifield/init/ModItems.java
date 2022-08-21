package com.octenexin.inifield.init;

import com.octenexin.inifield.item.CbBlockItem;
import com.octenexin.inifield.item.EnderPickaxe;
import com.octenexin.inifield.item.Glasses;
import com.octenexin.inifield.item.TNTCaster;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
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

    public static final RegistryObject<Item> TNT_CASTER = ITEMS.register("tnt_caster", ()->new TNTCaster((new Item.Properties()).stacksTo(16).tab(creativeTab)));
    public static final RegistryObject<Item> CB_BLOCK_ITEM = ITEMS.register("cb_block", ()->new CbBlockItem((new Item.Properties()).stacksTo(16).tab(creativeTab)));
    public static final RegistryObject<EnderPickaxe> ENDER_PICKAXE = ITEMS.register("ender_pickaxe", ()->new EnderPickaxe(ItemTier.NETHERITE,1,-2.8F,((new Item.Properties()).tab(ItemGroup.TAB_TOOLS))));
    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", ()->new Glasses((new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));


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
