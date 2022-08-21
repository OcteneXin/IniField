package com.octenexin.inifield.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Notch extends MonsterEntity{
    public Notch(EntityType<? extends MonsterEntity> type, World worldIn){
        super(type, worldIn);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtGoal(this,CbBlockEntity.class, 50.0F));
        //this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.getAttributes().getInstance(Attributes.MAX_HEALTH);
        this.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
        this.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
    }


    @Override
    public void die(DamageSource cause) {
            super.die(cause);
            if(cause.getEntity() instanceof PlayerEntity) {
            //if(Math.random()<0.2)
            //this.spawnAtLocation(new ItemStack(ItemLoader.ANCIENT_CORE.get(),1));
                this.spawnAtLocation(new ItemStack(Items.APPLE,1));
            }
    }
}