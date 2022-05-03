package com.octenexin.inifield.init;

import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.processors.BeeDungeonProcessor;
import com.octenexin.inifield.world.processors.RemoveFloatingBlocksProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class ModProcessors {
    public static IStructureProcessorType<BeeDungeonProcessor> BEE_DUNGEON_PROCESSOR = () -> BeeDungeonProcessor.CODEC;
    public static IStructureProcessorType<RemoveFloatingBlocksProcessor> REMOVE_FLOATING_BLOCKS_PROCESSOR = () -> RemoveFloatingBlocksProcessor.CODEC;

    public static void registerProcessors() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(Reference.MOD_ID, "bee_dungeon_processor"), BEE_DUNGEON_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(Reference.MOD_ID, "remove_floating_blocks_processor"), REMOVE_FLOATING_BLOCKS_PROCESSOR);

    }
}
