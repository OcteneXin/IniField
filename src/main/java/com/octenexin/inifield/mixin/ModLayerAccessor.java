package com.octenexin.inifield.mixin;

import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Layer.class)
public interface ModLayerAccessor {

    @Accessor("area")
    LazyArea inifield_getSampler();
}
