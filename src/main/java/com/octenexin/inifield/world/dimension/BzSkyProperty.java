package com.octenexin.inifield.world.dimension;

import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.logging.Level;

// CLIENT-SIDED
public class BzSkyProperty extends DimensionRenderInfo {

    float worldTime;

    public BzSkyProperty() {
        super(32.0F, false, DimensionRenderInfo.FogType.NORMAL, false, false);
    }

    public Vector3d getBrightnessDependentFogColor(Vector3d p_230494_1_, float p_230494_2_) {
        worldTime=p_230494_2_;
        return p_230494_1_.multiply((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
    }

    public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
        return !(worldTime > 0.5F);
    }
}
