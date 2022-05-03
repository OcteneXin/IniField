package com.octenexin.inifield.init;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.client.particles.WeedFurryParticle;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MOD_ID);

    public static RegistryObject<ParticleType<WeedFurryParticleData>> WEED_FURRY = PARTICLE_TYPES.register("weed_furry", () -> {
        return new ParticleType<WeedFurryParticleData>(false,new WeedFurryParticleData.Deserializer()) {
            @Override
            public Codec<WeedFurryParticleData> codec() {
                return WeedFurryParticleData.codecWeedFurry();
            }
        };
    });



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.WEED_FURRY.get(), (sprite) -> {
            return new WeedFurryParticle.WeedParticleFactory(sprite);
        });
    }







}
