package com.octenexin.inifield.client;

import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.entity.Notch;
import com.octenexin.inifield.utils.DecisionNode;
import com.octenexin.inifield.utils.ModGeneralUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ModKeyBoardEvent {

    public static final KeyBinding INVESTIGATE_KEY=new KeyBinding("key.investigate", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_Z,"key.category.inifield");


    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event){
        if(INVESTIGATE_KEY.isDown()){

            assert Minecraft.getInstance().player!=null;
            PlayerEntity player=Minecraft.getInstance().player;
            World world=player.level;
            //BlockRayTraceResult blockraytraceresult = ModGeneralUtils.getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
            //How to trace water itself

            RayTraceResult result=Minecraft.getInstance().hitResult;
            Entity entity=null;
            Block block=Blocks.AIR;
            if(result.getType()==RayTraceResult.Type.ENTITY){
                entity=((EntityRayTraceResult)result).getEntity();

                if(entity instanceof Notch){

                    //gen decision tree
                    DecisionNode node1=new DecisionNode(true,"inifield.talk.overworld.notch.pacific.first.stage_1");
                    DecisionNode node2=new DecisionNode(false,"inifield.talk.overworld.notch.pacific.first.stage_2","inifield.talk.overworld.notch.pacific.first.pl_choice0","inifield.talk.overworld.notch.pacific.first.pl_choice1");
                    DecisionNode node3=new DecisionNode(true,"inifield.talk.overworld.notch.pacific.first.ver0_stage_3");
                    DecisionNode node4=new DecisionNode(true,"inifield.talk.overworld.notch.pacific.first.ver1_stage_3");
                    DecisionNode node5=new DecisionNode(true,"inifield.talk.overworld.notch.pacific.first.ver1_stage_4");
                    //link node
                    node1.setRight(node2);
                    node2.setLeft(node3);
                    node2.setRight(node4);
                    node4.setRight(node5);


                    Minecraft.getInstance().setScreen(new SimpleDialog("nc_dialog.png",25,node1));
                }

            }
            else if(result.getType()==RayTraceResult.Type.BLOCK){
                block=world.getBlockState(((BlockRayTraceResult)result).getBlockPos()).getBlock();

                DecisionNode node1=new DecisionNode(true,"inifield.look.overworld.grass");
                if(block== Blocks.GRASS_BLOCK){
                    Minecraft.getInstance().setScreen(new SimpleDialog("small_dialog.png",30,node1));
                }
            }



        }
    }
}
