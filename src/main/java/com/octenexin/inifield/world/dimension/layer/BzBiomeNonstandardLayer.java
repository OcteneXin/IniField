package com.octenexin.inifield.world.dimension.layer;

import com.octenexin.inifield.world.dimension.BzBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.Random;


public enum BzBiomeNonstandardLayer implements IAreaTransformer0 {
    INSTANCE;

    Random random=new Random(1605);

    public int applyPixel(INoiseRandom noise, int x, int z) {
        if(random.nextInt(40)==0){
            return BzBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    BzBiomeProvider.LAYERS_BIOME_REGISTRY.get(BzBiomeProvider.METASEQUOIA_PATCH));
        }else {
            return -1;
        }
    }
}