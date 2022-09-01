package com.octenexin.inifield.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.DecisionNode;
import com.octenexin.inifield.utils.UNOManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class UNOGui extends Screen {

    private final ResourceLocation tableTextureLoc;
    private final ResourceLocation cardTextureLoc;

    UNOManager unoManager;
    int delayTicker;
    int oldDelayTicker;
    boolean playedCard;
    int selected;
    boolean autoManaged;
    int countdown;
    int leftOffset;
    int topOffset;
    double multiplication;

    public UNOGui() {
        super(new TranslationTextComponent(""));
        this.tableTextureLoc=new ResourceLocation("inifield:textures/gui/uno/card_table.png");
        this.cardTextureLoc=new ResourceLocation("inifield:textures/gui/uno/allcards.png");
        unoManager=new UNOManager();
        delayTicker=0;
        oldDelayTicker=0;
        playedCard=false;
        selected=-1;
        autoManaged=false;
        countdown=0;
        leftOffset=0;
        multiplication=0.0D;
        topOffset=0;
    }

    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void tick() {
        ++delayTicker;

        super.tick();
    }

    public void init(){
        delayTicker=0;
        oldDelayTicker=0;
        playedCard=false;
        selected=-1;
        autoManaged=false;
        countdown=0;
    }

    public void reset(){
        delayTicker=0;
        oldDelayTicker=0;
        playedCard=false;
        selected=-1;
        autoManaged=false;
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if(unoManager.currentPlayer==0){
            if(p_231046_1_==257){
                autoManaged=true;
            }
        }

        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        int size=unoManager.handCards.get(0).size();

        p_231044_3_/=multiplication;
        p_231044_1_/=multiplication;

        if(p_231044_3_>=topOffset&&p_231044_3_<=topOffset+31){
            if(p_231044_1_>=leftOffset&&p_231044_1_<=leftOffset+size*23){
                selected=(int)((p_231044_1_-leftOffset)/23.0);
                IniField.LOGGER.debug("0 selected "+selected);
                playedCard=true;
            }
        }
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {

        assert this.minecraft != null;
        double r=Math.random();

        //background
        RenderSystem.pushMatrix();
        RenderSystem.scalef(1.72F,1.2F,1.0F);
        this.minecraft.getTextureManager().bind(tableTextureLoc);
        this.blit(p_230430_1_, 0,0, 0, 0, 256, 256);
        RenderSystem.popMatrix();

        //dialog bg
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.2F,2F,2F);
        this.minecraft.getTextureManager().bind(cardTextureLoc);
        this.blit(p_230430_1_,0,105,23*2,31,23*9,16);
        RenderSystem.popMatrix();




        //handCard
        RenderSystem.pushMatrix();
        RenderSystem.scalef(1.5F,1.5F,1.5F);
        multiplication=1.5D;
        this.minecraft.getTextureManager().bind(cardTextureLoc);
        //own
        int size=unoManager.handCards.get(0).size();
        int i=36;
        leftOffset=36;
        topOffset=104;

        for (int k = 0; k < size; k++) {
            Point p=unoManager.getCardPosition(0,k);
            this.blit(p_230430_1_, i,104, p.x,p.y,23,31);
            i+=23;
        }
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        RenderSystem.scalef(1.2F,1.2F,1.2F);
        //left
        size=unoManager.handCards.get(1).size();
        i=20;
        for (int k = 0; k < size; k++) {
            this.blit(p_230430_1_, 5,i, 5*23,7*31,31,23);
            i+=11;
        }
        //up
        size=unoManager.handCards.get(2).size();
        i=40;
        for (int k = 0; k < size; k++) {
            this.blit(p_230430_1_, i,5, 4*23,7*31,23,31);
            i+=23;
        }
        //right
        size=unoManager.handCards.get(3).size();
        i=20;
        for (int k = 0; k < size; k++) {
            this.blit(p_230430_1_, 290,i, 5*23,7*31,31,23);
            i+=11;
        }



        RenderSystem.popMatrix();



        //logic when tick changed
        if(delayTicker!=oldDelayTicker){
            oldDelayTicker=delayTicker;
            if(unoManager.isEnd){
                ++countdown;
                if(countdown>=80){
                    this.minecraft.setScreen((Screen)null);
                }
            }else
            {
                if(unoManager.currentPlayer==0){

                    if(playedCard){
                        if(unoManager.checkSet(0).contains(selected)){
                            unoManager.playCard(unoManager.currentPlayer,selected);
                            reset();
                        }
                    }

                    if(autoManaged||delayTicker==80){
                        unoManager.playCard(unoManager.currentPlayer,unoManager.decide(unoManager.currentPlayer));
                        reset();
                    }

                }else {

                    if(playedCard){
                        if(unoManager.nextPlayer()==0||unoManager.oppositePlayer()==0){
                            if(unoManager.quickCheckSet(0).contains(selected)){
                                unoManager.isQuick=true;
                                unoManager.playCard(0,selected);
                                reset();
                            }
                        }
                    }

                    if(delayTicker==64){
                        unoManager.playCard(unoManager.currentPlayer,unoManager.decide(unoManager.currentPlayer));
                        reset();
                    }
                }

                if(delayTicker>=48){
                    if(r<=0.1D){
                        if(r<=0.5D){
                            if(unoManager.nextPlayer()!=0&&unoManager.nextPlayer()!=unoManager.getLastCardOwner()){
                                int quickResult=unoManager.quickDecide(unoManager.nextPlayer());
                                if(quickResult!=-1){
                                    unoManager.quickPlayCard(unoManager.nextPlayer(),unoManager.quickDecide(unoManager.nextPlayer()));
                                    reset();
                                }
                            }

                        }else{
                            if(unoManager.oppositePlayer()!=0&&unoManager.oppositePlayer()!=unoManager.getLastCardOwner()){
                                int quickResult=unoManager.quickDecide(unoManager.oppositePlayer());
                                if(quickResult!=-1){
                                    unoManager.quickPlayCard(unoManager.oppositePlayer(),unoManager.quickDecide(unoManager.oppositePlayer()));
                                    reset();
                                }
                            }
                        }
                    }
                }
            }



        }

        //render last card
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.5F,2.5F,2.5F);
        this.minecraft.getTextureManager().bind(cardTextureLoc);
        Point p= unoManager.getCardPosition(unoManager.lastCard);
        this.blit(p_230430_1_, 72,24, p.x,p.y,23,31);
        RenderSystem.popMatrix();

        //dialog
        RenderSystem.pushMatrix();
        RenderSystem.scalef(1.5F,1.5F,1.5F);
        drawString(p_230430_1_,font,unoManager.curDialog,36, 147, 16777215);
        RenderSystem.popMatrix();


        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }





}
