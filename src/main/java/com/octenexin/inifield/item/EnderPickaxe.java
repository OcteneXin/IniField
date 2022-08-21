package com.octenexin.inifield.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EnderPickaxe extends PickaxeItem {


    public EnderPickaxe(IItemTier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_);
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 15;
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        p_77659_2_.startUsingItem(p_77659_3_);
        return ActionResult.consume(itemstack);
    }

}
