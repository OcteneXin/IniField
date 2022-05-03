package com.octenexin.inifield.item;

import com.octenexin.inifield.block.WeedGrass;
import com.octenexin.inifield.init.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.octenexin.inifield.block.WeedGrass.*;

public class WeedGrassItem extends BlockItem {
    public WeedGrassItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }


    private boolean onProperBlock(Block block){
        return block== Blocks.DIRT||block==Blocks.GRASS_BLOCK||block==Blocks.SAND||block==Blocks.COARSE_DIRT||block==Blocks.PODZOL||block==Blocks.GRAVEL;
    }

    //place pos is "future"
    @Override
    public ActionResultType place(BlockItemUseContext p_195942_1_) {
        if (!p_195942_1_.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = this.updatePlacementContext(p_195942_1_);
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockPos blockpos = blockitemusecontext.getClickedPos();
                World world = blockitemusecontext.getLevel();
                BlockState blockstate1 = world.getBlockState(blockpos);


                BlockPos blockpos1 = blockpos.relative(p_195942_1_.getClickedFace().getOpposite());
                Block clickedBlock = world.getBlockState(blockpos1).getBlock();

                PlayerEntity playerentity = blockitemusecontext.getPlayer();
                ItemStack itemstack = blockitemusecontext.getItemInHand();

                WeedGrass weed= (WeedGrass) ModBlocks.WEED_GRASS.get();

                //first place
                if(onProperBlock(world.getBlockState(blockpos.below()).getBlock())&&world.isWaterAt(blockpos)){
                    world.setBlock(blockpos,weed.defaultBlockState().setValue(TYPE,0).setValue(WATERLOGGED,true).setValue(AGE,0),11);
                }else if(clickedBlock.is(ModBlocks.WEED_GRASS.get())){
                    weed.growWeed(blockstate1,world,blockpos);
                }


                SoundType soundtype = blockstate1.getSoundType(world, blockpos, p_195942_1_.getPlayer());
                world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, p_195942_1_.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                if (playerentity == null || !playerentity.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                return ActionResultType.SUCCESS;
            }
        }
    }
}
