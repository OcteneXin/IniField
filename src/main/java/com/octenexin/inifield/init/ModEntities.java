package com.octenexin.inifield.init;

import com.octenexin.inifield.entity.CbBlockEntity;
import com.octenexin.inifield.entity.Ender;
import com.octenexin.inifield.entity.Notch;
import com.octenexin.inifield.entity.ThrowableTNTEntity;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MOD_ID);
    public static final RegistryObject<EntityType<Notch>> NOTCH = ENTITY_TYPES.register("notch", () -> EntityType.Builder.of(Notch::new, EntityClassification.MONSTER).sized(1, 2).build("notch"));
    public static final RegistryObject<EntityType<ThrowableTNTEntity>> THROWABLE_TNT = ENTITY_TYPES.register("throwable_tnt", () -> EntityType.Builder.<ThrowableTNTEntity>of(ThrowableTNTEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("throwable_tnt"));
    public static final RegistryObject<EntityType<CbBlockEntity>> CB_BLOCK_ENTITY = ENTITY_TYPES.register("cb_block", () -> EntityType.Builder.<CbBlockEntity>of(CbBlockEntity::new, EntityClassification.MONSTER).sized(0.75F, 0.75F).build("cb_block"));
    public static final RegistryObject<EntityType<Ender>> ENDER = ENTITY_TYPES.register("ender", () -> EntityType.Builder.<Ender>of(Ender::new, EntityClassification.MONSTER).sized(0.75F, 2.4F).build("ender"));


    @SubscribeEvent
    public static void setupAttributes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(NOTCH.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 40D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.MOVEMENT_SPEED, 0.2D).build());
            GlobalEntityTypeAttributes.put(ENDER.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 40D).add(Attributes.ATTACK_DAMAGE,15D).add(Attributes.MOVEMENT_SPEED, 0.4D).build());
        });
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(CB_BLOCK_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 2D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.MOVEMENT_SPEED, 0.2D).build());
        });
    }
}