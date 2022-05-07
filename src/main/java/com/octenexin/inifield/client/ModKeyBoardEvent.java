package com.octenexin.inifield.client;

import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.entity.Notch;
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
                    Minecraft.getInstance().setScreen(new SimpleDialog("nc_dialog.png","inifield.talk.overworld.notch.pacific.first.stage_0",25){

                        @Override
                        public void toggleDialog(){
                            assert this.minecraft != null;
                            if(getProcess()==0){
                                reset();
                                setCurMainLine("inifield.talk.overworld.notch.pacific.first.stage_1");
                                increaseProcess();
                            }else if(getProcess()==1){
                                reset();
                                setCurLines("inifield.talk.overworld.notch.pacific.first.stage_2","inifield.talk.overworld.notch.pacific.first.pl_choice0","inifield.talk.overworld.notch.pacific.first.pl_choice1");
                                increaseProcess();
                            }
                            else if(getProcess()==2){
                                if(isRight()){
                                    reset();
                                    setCurMainLine("inifield.talk.overworld.notch.pacific.first.ver1_stage_3");
                                    increaseProcess();
                                }else {
                                    reset();
                                    setCurMainLine("inifield.talk.overworld.notch.pacific.first.ver0_stage_3");
                                    increaseProcess();
                                }
                            }
                            else if(getProcess()==3){
                                if(getLastChoice()){
                                    reset();
                                    setCurMainLine("inifield.talk.overworld.notch.pacific.first.ver1_stage_4");
                                    increaseProcess();
                                }else{
                                    this.minecraft.setScreen((Screen)null);
                                }
                            }
                            else {
                                this.minecraft.setScreen((Screen)null);
                            }
                        }
                    });
                }

            }
            else if(result.getType()==RayTraceResult.Type.BLOCK){
                block=world.getBlockState(((BlockRayTraceResult)result).getBlockPos()).getBlock();

                if(block== Blocks.GRASS_BLOCK){
                    Minecraft.getInstance().setScreen(new SimpleDialog("small_dialog.png","inifield.look.overworld.grass","inifield.test.pl_choice0","inifield.test.pl_choice1",25){

                        @Override
                        public void toggleDialog(){
                            if(isRight()){
                                if(getProcess()==0){
                                    reset();
                                    setCurMainLine("inifield.look.overworld.cobblestone");
                                    increaseProcess();
                                }else if(getProcess()==1){
                                    reset();
                                    setCurMainLine("inifield.look.overworld.stone");
                                    increaseProcess();
                                }else {
                                    this.minecraft.setScreen((Screen)null);
                                }

                            }else {
                                this.minecraft.setScreen((Screen)null);
                            }
                        }
                    });
                }
            }



        }
    }
}
