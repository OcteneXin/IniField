package com.octenexin.inifield.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class Glasses extends Item {

    public Glasses(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }
}
