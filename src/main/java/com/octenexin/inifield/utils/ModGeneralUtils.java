package com.octenexin.inifield.utils;

import com.mojang.datafixers.util.Pair;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.mixin.ModBiomeGenerationSettingsAccessor;
import com.octenexin.inifield.world.dimension.AetherTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class ModGeneralUtils {

    //copied from: Item
    public static BlockRayTraceResult getPlayerPOVHitResult(World p_219968_0_, PlayerEntity p_219968_1_, RayTraceContext.FluidMode p_219968_2_) {
        float f = p_219968_1_.xRot;
        float f1 = p_219968_1_.yRot;
        Vector3d vector3d = p_219968_1_.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = p_219968_1_.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();;
        Vector3d vector3d1 = vector3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return p_219968_0_.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, p_219968_2_, p_219968_1_));
    }


    // Weighted Random from: https://stackoverflow.com/a/6737362
    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, Random random) {
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }


    /**
     * Helper method to make WB biomes mutable to add stuff to it later
     */
    public static void makeBiomeMutable(Biome biome){
        // Make the structure and features list mutable for modification late
        List<List<Supplier<ConfiguredFeature<?, ?>>>> tempFeature = ((ModBiomeGenerationSettingsAccessor)biome.getGenerationSettings()).inifield_getFeatures();
        List<List<Supplier<ConfiguredFeature<?, ?>>>> mutableGenerationStages = new ArrayList<>();

        // Fill in generation stages so there are at least 10 or else Minecraft crashes.
        // (we need all stages for adding features/structures to the right stage too)
        for(int currentStageIndex = 0; currentStageIndex < Math.max(GenerationStage.Decoration.values().length, tempFeature.size()); currentStageIndex++){
            if(currentStageIndex >= tempFeature.size()){
                mutableGenerationStages.add(new ArrayList<>());
            }else{
                mutableGenerationStages.add(new ArrayList<>(tempFeature.get(currentStageIndex)));
            }
        }

        // Make the Structure and GenerationStages (features) list mutable for modification later
        ((ModBiomeGenerationSettingsAccessor)biome.getGenerationSettings()).inifield_setFeatures(mutableGenerationStages);
    }

    private static RegistryKey<World> getDestination(Entity entity) {
        RegistryKey<World> aether = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID,"aether"));

        return !entity.level.dimension().location().equals(aether.location())
                ? aether : World.OVERWORLD;
    }

    public static void attemptSendPlayer(Entity entity, boolean forcedEntry) {

        IniField.LOGGER.info("beginSend");
        if (!entity.isAlive() || entity.level.isClientSide) {
            return;
        }
        IniField.LOGGER.info("return1");

        if (entity.isPassenger() || !entity.canChangeDimensions()) {
            return;
        }
        IniField.LOGGER.info("return2");

        if (!forcedEntry || entity.isOnPortalCooldown()) {
            return;
        }
        IniField.LOGGER.info("return3");

        // set a cooldown before this can run again
        entity.setPortalCooldown();

        RegistryKey<World> destination = getDestination(entity);
        ServerWorld aether = Objects.requireNonNull(entity.getServer()).getLevel(destination);


        if(aether == null){
            IniField.LOGGER.info("IniField:Please restart the server. The dimension hasn't been made yet due to this bug: https://bugs.mojang.com/browse/MC-195468. Believe me, a restart will fix this.");
            return;
        }


        entity.changeDimension(aether, new AetherTeleporter(destination));

        //if (destination ==  RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID)) && entity instanceof ServerPlayerEntity && forcedEntry) {
        //ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
        // set respawn point for TF dimension to near the arrival portal, only if we spawn here on world creation
        //playerMP.setRespawnPosition(destination, playerMP.getPosition(), playerMP.getRespawnAngle(), true, false);
        //}
        IniField.LOGGER.info("endSend");

    }
}
