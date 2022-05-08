package com.octenexin.inifield.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.DecisionNode;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nullable;

public class SimpleDialog extends Screen {

    private final ResourceLocation textureLoc;
    private int delayTicker;
    private int oldDelayTicker;
    private int charSum;
    private int defaultSplit;
    private boolean isActive;
    private boolean isRight;
    private int mainLineLen;

    private DecisionNode root;

    public SimpleDialog(String textureLoc, int defaultSplit, DecisionNode root) {
        super(new TranslationTextComponent(""));
        this.textureLoc=new ResourceLocation("inifield:textures/gui/" + textureLoc);
        this.defaultSplit=defaultSplit;
        this.root=root;
    }

    protected void init() {
        delayTicker = 0;
        oldDelayTicker=0;
        charSum=0;
        isActive=false;
        isRight=true;
    }

    public void reset(){
        delayTicker = 0;
        oldDelayTicker=0;
        charSum=0;
        isActive=false;
        isRight=true;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        //background
        RenderSystem.pushMatrix();
        this.minecraft.getTextureManager().bind(textureLoc);
        int i = (this.width - 256) / 2;
        this.blit(p_230430_1_, i, 115, 0, 0, 256, 256);

        //render line
        RenderSystem.scalef(1.5F, 1.5F, 1.5F);
        if(root.isLine()){
            renderLine(p_230430_1_,root.getMainLine());
        }else {
            renderChoicesDialog(p_230430_1_,root.getMainLine(), root.getLeftChoice(),root.getRightChoice());
        }
        RenderSystem.popMatrix();

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }


    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {

        IniField.LOGGER.debug("x="+p_231044_1_+",y="+p_231044_3_);

        //this.minecraft.setScreen((Screen)null);
        return true;
    }

    /**
     * code table
     *
     * enter:257
     * r_enter:225
     * backspace:259
     *
     * up:265
     * down:264
     * left:263
     * right:262
     * delete:261
     *
     * shift:340
     * r_shift:-1
     * ctrl:341
     * r_ctrl:345
     * alt:342
     * r_alt:346
     * win:343
     *
     * `:96
     * a:97
     * capslock,a:A:65
     *
     * capslock:280
     *
     * esc:256
     *
     * f1~f12:290~301(if not collide)
     * */
    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if(!isActive){
            if (p_231046_1_==88){//x, jump dialog
                charSum=mainLineLen-1;
                isActive=true;
            }
        }

        if(isActive){
            if((!root.isLine())&&(p_231046_1_== 263||p_231046_1_==262)){//left,right
                isRight=!isRight;
            }

            if(p_231046_1_==257){//enter

                //select road
                if(isRight){
                    root=root.getRight();
                }else {
                    root=root.getLeft();
                }
                reset();
                if(root==null)this.minecraft.setScreen((Screen)null);


            }
        }


        IniField.LOGGER.debug(p_231046_1_);

        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
    }

    public void renderLine(MatrixStack p_230430_1_,String locationStr){


        ITextComponent finalDialog=new TranslationTextComponent(locationStr);
        ITextComponent finalDialog2=null;
        String finalStr=finalDialog.getString();

        int len=finalStr.length();
        mainLineLen=len;

        //update charSum adequately
        if(charSum<len-1){
            if (oldDelayTicker!=delayTicker){
                oldDelayTicker=delayTicker;
                ++charSum;
                if((finalStr.charAt(charSum)==' ')&&(charSum<len-1))++charSum;
            }
        }else {
            isActive=true;
        }


        if(len>defaultSplit){
            int realSplit=defaultSplit;
            if(finalStr.charAt(defaultSplit)!=' '){
                //find proper split
                for(int i=defaultSplit;i>0;i--){
                    if(finalStr.charAt(i)==' '){
                        realSplit=i;
                        break;
                    }
                }
            }

            finalDialog=new StringTextComponent(finalStr.substring(0,realSplit+1));
            finalDialog2=new StringTextComponent(finalStr.substring(realSplit+1,len));


            if(charSum<realSplit){
                finalDialog=new StringTextComponent(finalStr.substring(0,charSum+1));
                drawString(p_230430_1_,font,finalDialog,(this.width - 256) / 4+30, 90, 16777215);
            }else {
                finalDialog2=new StringTextComponent(finalStr.substring(realSplit+1,charSum+1));
                drawString(p_230430_1_,font,finalDialog,(this.width - 256) / 4+30, 90, 16777215);
                drawString(p_230430_1_,font,finalDialog2,(this.width - 256) / 4+30, 105, 16777215);
            }

        }else {
            if(charSum<len){
                finalDialog=new StringTextComponent(finalStr.substring(0,charSum+1));
            }
            drawString(p_230430_1_,font,finalDialog,(this.width - 256) / 4+40, 90, 16777215);
        }


    }

    public void renderChoicesDialog(MatrixStack p_230430_1_,String locationStr,String query1,String query2){
        renderLine(p_230430_1_, locationStr);

        if(isActive){
            if(isRight){
                drawString(p_230430_1_,font,new TranslationTextComponent(query1),(this.width - 256) / 4+40, 125, 16777215);
                drawString(p_230430_1_,font,new TranslationTextComponent(query2).withStyle(TextFormatting.GREEN),(this.width - 256) / 4+120, 125, 16777215);
            }else {
                drawString(p_230430_1_,font,new TranslationTextComponent(query1).withStyle(TextFormatting.GREEN),(this.width - 256) / 4+40, 125, 16777215);
                drawString(p_230430_1_,font,new TranslationTextComponent(query2),(this.width - 256) / 4+120, 125, 16777215);
            }
        }

    }





}
