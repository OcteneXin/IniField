package com.octenexin.inifield.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.octenexin.inifield.IniField;
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
    private String curMainLine="";
    private String curLeftLine="";
    private String curRightLine="";
    private int process;
    private boolean lastChoice;

    public int getProcess() {
        return process;
    }

    public void increaseProcess(){
        ++process;
    }

    public boolean isRight() {
        return isRight;
    }

    public boolean getLastChoice(){return lastChoice;}

    public void setCurMainLine(String curMainLine) {
        this.curMainLine = curMainLine;
        this.curLeftLine="";
        this.curRightLine="";
    }

    public void setCurLines(String curMainLine,String curLeftLine,String curRightLine) {
        this.curMainLine = curMainLine;
        this.curLeftLine=curLeftLine;
        this.curRightLine=curRightLine;

        IniField.LOGGER.debug(curMainLine);
        IniField.LOGGER.debug(curLeftLine);
        IniField.LOGGER.debug(curRightLine);
    }

    private SimpleDialog(String textureLoc,int defaultSplit) {
        super(new TranslationTextComponent(""));
        this.textureLoc=new ResourceLocation("inifield:textures/gui/" + textureLoc);
        this.defaultSplit=defaultSplit;
    }

    public SimpleDialog(String textureLoc,String str1,String str2,String str3,int defaultSplit) {
        this(textureLoc,defaultSplit);
        setCurLines(str1,str2,str3);
    }

    public SimpleDialog(String textureLoc,String str1,int defaultSplit){
        this(textureLoc,defaultSplit);
        setCurMainLine(str1);
    }

    protected void init() {
        delayTicker = 0;
        oldDelayTicker=0;
        charSum=0;
        isActive=false;
        isRight=true;
        process=0;
        lastChoice=true;
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
        if(curLeftLine.length()!=0){
            renderChoicesDialog(p_230430_1_,curMainLine,curLeftLine,curRightLine);
        }else {
            renderLine(p_230430_1_,curMainLine);
        }
        RenderSystem.popMatrix();

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }


    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {

        IniField.LOGGER.debug("x="+p_231044_1_+",y="+p_231044_3_);
        IniField.LOGGER.debug(curMainLine);
        IniField.LOGGER.debug(curLeftLine);
        IniField.LOGGER.debug(curRightLine);
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
            if(p_231046_1_== 263||p_231046_1_==262){//left,right
                isRight=!isRight;
            }

            if(p_231046_1_==257){//enter
                if(curLeftLine.length()!=0){
                    lastChoice= isRight;
                }

                toggleDialog();
            }
        }


        IniField.LOGGER.debug(p_231046_1_);

        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    public void toggleDialog(){
        if(isRight()){
            if(process==0){
                reset();
                setCurMainLine("inifield.look.overworld.cobblestone");
                ++process;
            }else {
                this.minecraft.setScreen((Screen)null);
            }

        }else {
            this.minecraft.setScreen((Screen)null);
        }
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
