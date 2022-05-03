package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.decorators.BeeDungeonPlacer;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPlacements {

    public static final DeferredRegister<Placement<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, Reference.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> BEE_DUNGEON_PLACER = DECORATORS.register("bee_dungeon_placer", () -> new BeeDungeonPlacer(NoPlacementConfig.CODEC));


}
