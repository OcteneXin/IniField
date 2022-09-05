package com.octenexin.inifield.world.dimension.layer;

import com.octenexin.inifield.world.dimension.AetherBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.Random;


public enum BzBiomeNonstandardLayer implements IAreaTransformer0 {
    INSTANCE;

    Random random=new Random(1605);

    public int applyPixel(INoiseRandom noise, int x, int z) {
        if(random.nextInt(40)==0){
            return AetherBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    AetherBiomeProvider.LAYERS_BIOME_REGISTRY.get(AetherBiomeProvider.METASEQUOIA_PATCH));
        }else {
            return -1;
        }
    }
}