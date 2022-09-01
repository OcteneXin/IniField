package com.octenexin.inifield.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.*;

public class ModWorldSavedData extends WorldSavedData {

    private static final String NAME="InifieldWorldSavedData";
    private static final HashMap<String, Integer> DATA_TABLE=new HashMap<>();


    public ModWorldSavedData(){
        super(NAME);
    }


    public static ModWorldSavedData getInstance(World worldIn) {
        if (!(worldIn instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerWorld world = worldIn.getServer().overworld();
        /*
         *  If you want every level has its own World Saved Data.
         *  Replace the above with: ServerWorld world = (ServerWorld)world;
         */
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.get(ModWorldSavedData::new, NAME);
    }

    //interface
    public int getOrCreate(String s){
        if(DATA_TABLE.get(s)!=null){
            return DATA_TABLE.get(s);
        }else{
            DATA_TABLE.put(s,0);
            setDirty();
            return 0;
        }
    }

    public void setData(String s,int i){
        if(DATA_TABLE.get(s)!=null){//works only key exists
            DATA_TABLE.put(s,i);
            setDirty();
        }
    }

    @Override
    public void load(CompoundNBT nbt) {
        CompoundNBT dataTable=(CompoundNBT) nbt.get("data_table");
        Set<String> keys=dataTable.getAllKeys();
        for (String k:keys) {
            DATA_TABLE.put(k, dataTable.getInt(k));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {

        Set<Map.Entry<String,Integer>> set=DATA_TABLE.entrySet();

        CompoundNBT dataTable=new CompoundNBT();
        for (Map.Entry<String,Integer> e: set) {
            dataTable.putInt(e.getKey(),e.getValue());
        }

        nbt.put("data_table", dataTable);
        return nbt;

    }
}
