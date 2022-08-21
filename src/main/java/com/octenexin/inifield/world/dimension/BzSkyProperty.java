package com.octenexin.inifield.world.dimension;

import com.octenexin.inifield.client.ModSkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;
import java.util.logging.Level;

// CLIENT-SIDED
public class BzSkyProperty extends DimensionRenderInfo {

    float worldTime;
    private ISkyRenderHandler skyRenderer;
    private Minecraft mc;

    public BzSkyProperty() {
        super(32.0F, false, DimensionRenderInfo.FogType.NORMAL, false, false);
        mc=Minecraft.getInstance();
    }

    public Vector3d getBrightnessDependentFogColor(Vector3d p_230494_1_, float p_230494_2_) {
        worldTime=p_230494_2_;
        return p_230494_1_.multiply((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
    }

    public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
        if(mc.level.isRaining()){
            return true;
        }
        return !(worldTime > 0.5F);
    }

    @Nullable
    public net.minecraftforge.client.ISkyRenderHandler getSkyRenderHandler() {
        if (skyRenderer == null)
            skyRenderer = new ModSkyRenderer();
        return skyRenderer;
    }
}
