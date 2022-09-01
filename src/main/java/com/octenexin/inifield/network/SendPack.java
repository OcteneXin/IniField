package com.octenexin.inifield.network;

import com.octenexin.inifield.IniField;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class SendPack {
    private String message;

    public SendPack(PacketBuffer buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public SendPack(String message) {
        this.message = message;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            IniField.LOGGER.info(this.message);
        });
        ctx.get().setPacketHandled(true);
    }
}