package com.octenexin.inifield.story;

import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.data.ModWorldSavedData;
import com.octenexin.inifield.utils.DecisionNode;
import com.octenexin.inifield.utils.ModGeneralUtils;
import com.octenexin.inifield.utils.PlayerDataStore;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class VillagerStory {


    public static void dialog_1(PlayerEntity player){

        World world=player.level;
        int times=0;
        if(!world.isClientSide){
            ModWorldSavedData data=ModWorldSavedData.getInstance(world);
            times=data.getOrCreate("ask_other_people_time");
        }


        double r=Math.random();
        int idx=(int)(r*11);
        String[][] dialogTable ={
                {"inifield.talk.01","inifield.talk.02"},
                {"inifield.talk.03","inifield.talk.04","inifield.talk.05"},
                {"inifield.talk.16","inifield.talk.17","inifield.talk.18"},
                {"inifield.talk.19","inifield.talk.20","inifield.talk.21"},
                {"inifield.talk.22","inifield.talk.23"},
                {"inifield.talk.24","inifield.talk.25","inifield.talk.26"},
                {"inifield.talk.33","inifield.talk.34","inifield.talk.35","inifield.talk.36"},
                {"inifield.talk.37"},
                {"inifield.talk.38"},
                {"inifield.talk.57","inifield.talk.58","inifield.talk.59","inifield.talk.60"},
                {"inifield.talk.06","inifield.talk.07","inifield.talk.08","inifield.talk.09","inifield.talk.10","inifield.talk.11"},//0
                {"inifield.talk.14","inifield.talk.15"},//1
                {"inifield.talk.27","inifield.talk.28","inifield.talk.29","inifield.talk.30","inifield.talk.31","inifield.talk.32"},//2
                {"inifield.talk.61","inifield.talk.62","inifield.talk.63","inifield.talk.64","inifield.talk.65","inifield.talk.66","inifield.talk.67","inifield.talk.68","inifield.talk.69","inifield.talk.70"}//3
        };

        DecisionNode node39=new DecisionNode(true,"inifield.talk.39");
        DecisionNode node40=new DecisionNode(true,"inifield.talk.40");
        DecisionNode node41=new DecisionNode(true,"inifield.talk.41");
        DecisionNode node42=new DecisionNode(false,"inifield.talk.42","inifield.talk.43","inifield.talk.44");
        DecisionNode node45=new DecisionNode(true,"inifield.talk.45");
        DecisionNode node46=new DecisionNode(true,"inifield.talk.46");
        DecisionNode node47=new DecisionNode(true,"inifield.talk.47");
        DecisionNode node48=new DecisionNode(true,"inifield.talk.48");
        DecisionNode node49=new DecisionNode(true,"inifield.talk.49");
        DecisionNode node50=new DecisionNode(true,"inifield.talk.50");
        DecisionNode node51=new DecisionNode(true,"inifield.talk.51");
        DecisionNode node52=new DecisionNode(true,"inifield.talk.52");
        node39.setRight(node40);
        node40.setRight(node41);
        node41.setRight(node42);
        node42.setRight(node45);
        node45.setRight(node46);
        node46.setRight(node47);
        node47.setRight(node50);
        node50.setRight(node51);
        node51.setRight(node52);
        node42.setLeft(node48);
        node48.setRight(node49);
        node49.setRight(node50);

        DecisionNode root=null;
        if(times==0) {
            root=ModGeneralUtils.genLinkedList(dialogTable[10]);
            if(!world.isClientSide){
                ModWorldSavedData data=ModWorldSavedData.getInstance(world);
                data.setData("ask_other_people_time",1);
            }
        }else if(times==1){
            root=ModGeneralUtils.genLinkedList(dialogTable[11]);
            if(!world.isClientSide){
                ModWorldSavedData data=ModWorldSavedData.getInstance(world);
                data.setData("ask_other_people_time",2);
            }
        }else if(times==2){
            root=ModGeneralUtils.genLinkedList(dialogTable[12]);
            if(!world.isClientSide){
                ModWorldSavedData data=ModWorldSavedData.getInstance(world);
                data.setData("ask_other_people_time",3);
            }
        }else{
            if(idx>9){
                root=node39;
            }else {
                root=ModGeneralUtils.genLinkedList(dialogTable[idx]);
            }
        }

        Minecraft.getInstance().setScreen(new SimpleDialog("small_dialog.png",25,root));
    }

    public static void dialog_2(PlayerEntity player){
        double r=Math.random();
        String[][] dialogTable ={
                {"inifield.talk.12"},
                {"inifield.talk.13"},
                {"inifield.talk.53","inifield.talk.54","inifield.talk.55","inifield.talk.56"},
        };
        DecisionNode root=null;
        if(r<0.33){
            root=ModGeneralUtils.genLinkedList(dialogTable[0]);
        }else if(r<0.66){
            root=ModGeneralUtils.genLinkedList(dialogTable[1]);
        }else{
            root=ModGeneralUtils.genLinkedList(dialogTable[2]);
        }

        Minecraft.getInstance().setScreen(new SimpleDialog("small_dialog.png",25,root));
    }
}
