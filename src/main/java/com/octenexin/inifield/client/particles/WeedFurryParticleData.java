package com.octenexin.inifield.client.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.init.ModParticles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class WeedFurryParticleData implements IParticleData {
    public final int r;
    public final int g;
    public final int b;

    public WeedFurryParticleData(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public static Codec<WeedFurryParticleData> codecWeedFurry() {
        return RecordCodecBuilder.create((instance) -> instance.group(
                Codec.INT.fieldOf("r").forGetter((obj) -> obj.r),
                Codec.INT.fieldOf("g").forGetter((obj) -> obj.g),
                Codec.INT.fieldOf("b").forGetter((obj) -> obj.b))
                .apply(instance, WeedFurryParticleData::new));
    }

    @Nonnull
    @Override
    public ParticleType<?> getType() {
        return ModParticles.WEED_FURRY.get();
    }


    @Override
    public void writeToNetwork(PacketBuffer buffer) {
        buffer.writeDouble(this.r);
        buffer.writeDouble(this.g);
        buffer.writeDouble(this.b);
    }

    @Nonnull
    @Override
    public String writeToString() {
        return String.format("%d %d %d", r,g,b);
    }

    public static class Deserializer implements IParticleData.IDeserializer<WeedFurryParticleData> {

        @Override
        public WeedFurryParticleData fromCommand(ParticleType<WeedFurryParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            reader.expect(' ');
            int red = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int green = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int blue = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            return new WeedFurryParticleData(red,green,blue);
        }

        @Override
        public WeedFurryParticleData fromNetwork(ParticleType<WeedFurryParticleData> particleTypeIn, PacketBuffer buffer) {
            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            int red = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int green = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int blue = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            return new WeedFurryParticleData(red,green,blue);
        }
    };
}