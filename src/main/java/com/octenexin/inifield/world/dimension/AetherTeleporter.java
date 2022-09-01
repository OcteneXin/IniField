package com.octenexin.inifield.world.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class AetherTeleporter implements ITeleporter {
    RegistryKey<World> dest;

    public AetherTeleporter(RegistryKey<World> dest){
        this.dest=dest;
    }

    @Override
    public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
        return false;
    }

    /*
    public final Vector3d pos;
    public final Vector3d speed;
    public final float yRot;
    public final float xRot;
    * */
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
        if(entity instanceof LivingEntity){
            ((LivingEntity)entity).addEffect(new EffectInstance(Effects.SLOW_FALLING, 600, 0));
        }

        return new PortalInfo(new Vector3d(entity.position().x,255,entity.position().z), Vector3d.ZERO, entity.yRot, entity.xRot);
    }


}
