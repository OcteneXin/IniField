package com.octenexin.inifield.item;

import com.octenexin.inifield.entity.CbBlockEntity;
import com.octenexin.inifield.entity.ThrowableTNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class CbBlockItem extends Item {

    public CbBlockItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);

        if (!p_77659_1_.isClientSide) {
            CbBlockEntity cbBlockEntity=new CbBlockEntity(p_77659_2_,p_77659_1_);
            cbBlockEntity.isReturn=false;
            cbBlockEntity.setNoGravity(false);
            cbBlockEntity.noPhysics=false;
            cbBlockEntity.isCreative=false;
            cbBlockEntity.shootFromRotation(p_77659_2_, p_77659_2_.xRot, p_77659_2_.yRot, 0.0F, 1.5F, 1.0F);
            p_77659_1_.addFreshEntity(cbBlockEntity);

        }

        if (!p_77659_2_.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
    }
}
