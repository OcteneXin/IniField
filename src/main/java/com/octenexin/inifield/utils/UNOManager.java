package com.octenexin.inifield.utils;

import com.octenexin.inifield.IniField;

import java.awt.*;
import java.util.ArrayList;


public class UNOManager {

    int playerNumber;
    public Card lastCard;
    boolean shouldCareNum;
    Cycle currentCycle;
    CardStatus currentStatus;
    public int currentPlayer;
    int index;
    int addCounter;
    boolean hasUno;
    public boolean isEnd;
    int curColor;
    public boolean isQuick;
    public String curDialog;


    ArrayList<Card> cardStack;
    ArrayList<Card> discardStack;

    public ArrayList<ArrayList<Card>> handCards;

    public UNOManager() {
        this.playerNumber = 4;
        this.shouldCareNum =true;
        this.index=0;
        this.currentPlayer=0;
        this.addCounter=0;
        this.hasUno=false;
        this.isEnd=false;
        this.curColor=0;
        this.isQuick=false;
        this.curDialog="";

        cardStack=new ArrayList<>(256);
        discardStack=new ArrayList<>(256);
        handCards=new ArrayList<>(playerNumber);

        //colored card, 100
        for (int i=0; i<4; i++){
            for (int j = 0; j < 13; j++) {
                if(j!=0){
                    cardStack.add(new Card(i,j));//0 only 1 time
                }
                cardStack.add(new Card(i,j));
            }
        }
        //black card, 8
        for (int i = 0; i < 4; i++) {
            cardStack.add(new Card(4,13));
            cardStack.add(new Card(4,14));
        }


        //shuffle
        ModGeneralUtils.shuffle(cardStack);

        for (Card c:cardStack) {
            IniField.LOGGER.debug(c);
        }

        //touch
        for (int i = 0; i < playerNumber; i++) {
            ArrayList<Card> temp=new ArrayList<>(20);
            for (int j = 0; j < 7; j++) {
                temp.add(cardStack.get(index++).setOwner(i));
            }
            handCards.add(temp);
        }

        //first card
        do {
            lastCard = cardStack.get(index++);
            discardStack.add(lastCard);
        } while (lastCard.color == 4);


        //handle effects
        if(lastCard.num==10){
            currentStatus=CardStatus.BANNED;
            currentCycle=Cycle.CLOCKWISE;
        }else if(lastCard.num==11){
            currentStatus=CardStatus.NORMAL;
            currentCycle=Cycle.ANTICLOCKWISE;
        }else if(lastCard.num==12){
            currentStatus=CardStatus.PLUS2;
            currentCycle=Cycle.CLOCKWISE;
        }else{
            currentStatus=CardStatus.NORMAL;
            currentCycle=Cycle.CLOCKWISE;
        }

    }

    String transColor(int a) {
        switch (a) {
            case 0:
                return "yellow";
            case 1:
                return "red";
            case 2:
                return "green";
            case 3:
                return "blue";
            case 4:
                return "black";
            default:
                return "";

        }
    }

    String transNum(int a) {
        if (a < 10)
        {
            return a+"";
        }
        else if (a == 10)
            return "ban";
        else if (a == 11)
            return "trans";
        else if (a == 12)
            return "plus 2";
        else if (a == 13)
            return "trans color";
        else
            return "plus 4";
    }

    String transPlayer(int a){
        switch (a){
            case 0:return "You";
            case 1:return "Jeb";
            case 2:return "Dinnerbone";
            case 3:return "Searge";
            default:return "";
        }
    }

    public Point getCardPosition(int player, int idx){

        Card c=handCards.get(player).get(idx);

        return getCardPosition(c);
    }

    public Point getCardPosition(Card c){

        int x;
        int y;


        if(c.color!=4){
            if(0<=c.num&&c.num<=10){
                x=c.num*23;
                y=c.color*62;
            }else{
                x=(c.num-11)*23;
                y=c.color*62+31;
            }
        }else{
            if(c.num==13){
                x=2*23;
                y=7*31;
            }else{
                x=3*23;
                y=7*31;
            }
        }

        return new Point(x,y);
    }

    public void next(int player){
        if(currentCycle==Cycle.CLOCKWISE){
            currentPlayer=(player+1)%4;
        }else {
            currentPlayer=(player+3)%4;
        }
    }

    public int nextPlayer(){
        if(currentCycle==Cycle.CLOCKWISE){
            return (currentPlayer+1)%4;
        }else {
            return (currentPlayer+3)%4;
        }
    }

    public int oppositePlayer(){
        return (currentPlayer+2)%4;
    }

    public void discardCard(int player, int idx){
        if(isQuick){
            curDialog=(transPlayer(player)+" : Also "+transColor(handCards.get(player).get(idx).color)+" "+transNum(handCards.get(player).get(idx).num)+" !");
            isQuick=false;
        }else {
            curDialog=(transPlayer(player)+" : "+transColor(handCards.get(player).get(idx).color)+" "+transNum(handCards.get(player).get(idx).num)+" !");
        }
        if(handCards.get(player).size()==2){
            curDialog=(transPlayer(player)+" : Uno!");
        }

        if(handCards.get(player).get(idx).num==14){
            shouldCareNum=false;
            currentStatus=CardStatus.PLUS4;
            curColor=(int)(Math.random()*4);
            curDialog=(transPlayer(player)+" : "+"Change to "+transColor(curColor)+" now!");

            addCounter+=4;
        }else if(handCards.get(player).get(idx).num==12){
            shouldCareNum=true;
            currentStatus=CardStatus.PLUS2;
            addCounter+=2;
        }else if(handCards.get(player).get(idx).num==13){
            shouldCareNum=false;
            curColor=(int)(Math.random()*4);
            if(Math.random()<=0.5D){
                curDialog=(transPlayer(player)+" : "+"Change to "+transColor(curColor)+" now!");
            }else{
                curDialog=(transPlayer(player)+" : "+transColor(curColor)+"! Anyone has "+transColor(curColor)+"?");
            }
        }else if(handCards.get(player).get(idx).num==10){
            shouldCareNum=true;
            currentStatus=CardStatus.BANNED;
        }else if(handCards.get(player).get(idx).num==11){
            shouldCareNum=true;
            if(currentCycle==Cycle.CLOCKWISE){
                currentCycle=Cycle.ANTICLOCKWISE;
            }else{
                currentCycle=Cycle.CLOCKWISE;
            }
        }else {
            shouldCareNum=true;
        }

        lastCard=handCards.get(player).remove(idx);
        discardStack.add(lastCard);

        if(handCards.get(player).isEmpty()){
            isEnd=true;
        }
    }

    public void touchCard(int player,int cnt){
        if(cardStack.size()<cnt){
            ModGeneralUtils.shuffle(discardStack);
            cardStack.addAll(discardStack);
            discardStack.clear();
        }

        for (int i = 0; i < cnt; i++) {
            handCards.get(player).add(cardStack.get(index++).setOwner(player));
        }
    }

    public void checkUno(){
        for (ArrayList<Card> a: handCards) {
            if(a.size()==1){
                hasUno=true;
                return;
            }
        }

        hasUno=false;
    }

    public int decide(int player){



        //banned
        if(currentStatus==CardStatus.BANNED){
            return -1;
        }


        ArrayList<Card> ownCard=handCards.get(player);
        int size=ownCard.size();



        //+4
        if(currentStatus==CardStatus.PLUS4){
            for (int i = 0; i < size; i++) {
                if(ownCard.get(i).num==14) return i;
            }
            return -1;
        }

        //+2
        if(currentStatus==CardStatus.PLUS2){
            for (int i = 0; i < size; i++) {
                if(ownCard.get(i).num==14||ownCard.get(i).num==12) return i;
            }
            return -1;
        }

        //normal
        boolean available=false;
        int correctNum=0;
        int correctColor=0;

        for (int i = 0; i < size; i++) {

            if(shouldCareNum){
                if(ownCard.get(i).color==lastCard.color||ownCard.get(i).num==lastCard.num||ownCard.get(i).color==4){
                    if(ownCard.get(i).color==lastCard.color){
                        ++correctColor;
                    }else if(ownCard.get(i).num==lastCard.num){
                        ++correctNum;
                    }
                    available=true;
                }
            }else {
                if(ownCard.get(i).color==curColor||ownCard.get(i).color==4){
                    if(ownCard.get(i).color==curColor){
                        ++correctColor;
                    }
                    available=true;
                }

            }

        }

        if(!available) return -1;


        if(hasUno){
            if(shouldCareNum){
                for (int i = 0; i < size; i++) {
                    if(ownCard.get(i).color==4) return i;
                }
                for (int i = 0; i < size; i++) {
                    if(ownCard.get(i).num==lastCard.num) return i;
                }
                for (int i = 0; i < size; i++) {
                    if(ownCard.get(i).color==lastCard.color) return i;
                }

            }else {
                for (int i = 0; i < size; i++) {
                    if(ownCard.get(i).color==4) return i;
                }
                for (int i = 0; i < size; i++) {
                    if(ownCard.get(i).color==curColor) return i;
                }
            }
        }else {
            if(correctColor<=correctNum){
                if(shouldCareNum){
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).num==lastCard.num) return i;
                    }
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==lastCard.color) return i;
                    }
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==4) return i;
                    }
                }else{
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==curColor) return i;
                    }
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==4) return i;
                    }
                }

            }else{
                if(shouldCareNum){
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).num==lastCard.num||ownCard.get(i).color==lastCard.color) return i;
                    }
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==4) return i;
                    }
                }else{
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==curColor) return i;
                    }
                    for (int i = 0; i < size; i++) {
                        if(ownCard.get(i).color==4) return i;
                    }
                }

            }

        }
        return -1;


    }

    public ArrayList<Integer> checkSet(int player){

        ArrayList<Integer> await=new ArrayList<>(20);

        //banned
        if(currentStatus==CardStatus.BANNED){
            return await;
        }


        ArrayList<Card> ownCard=handCards.get(player);
        int size=ownCard.size();


        //+4
        if(currentStatus==CardStatus.PLUS4){
            for (int i = 0; i < size; i++) {
                if(ownCard.get(i).num==14) await.add(i);
            }
            return await;
        }

        //+2
        if(currentStatus==CardStatus.PLUS2){
            for (int i = 0; i < size; i++) {
                if(ownCard.get(i).num==14||ownCard.get(i).num==12) await.add(i);
            }
            return await;
        }

        //normal


        for (int i = 0; i < size; i++) {

            if(shouldCareNum){
                if(ownCard.get(i).color==lastCard.color||ownCard.get(i).num==lastCard.num||ownCard.get(i).color==4){
                    await.add(i);
                }
            }else {
                if(ownCard.get(i).color==curColor||ownCard.get(i).color==4){
                    await.add(i);
                }
            }
        }

        return await;



    }

    public int quickDecide(int player){
        for (int i = 0; i < handCards.get(player).size(); i++) {
            if(handCards.get(player).get(i).num==lastCard.num&&handCards.get(player).get(i).color==lastCard.color&&handCards.get(player).get(i).color!=4){
                return i;
            }

        }
        return -1;
    }

    public ArrayList<Integer> quickCheckSet(int player){

        ArrayList<Integer> await=new ArrayList<>(20);

        for (int i = 0; i < handCards.get(player).size(); i++) {
            if(handCards.get(player).get(i).num==lastCard.num&&handCards.get(player).get(i).color==lastCard.color&&handCards.get(player).get(i).color!=4)
               await.add(i);
        }
        return await;

    }

    public void postProcess(int player){
        double d=Math.random();

        if (currentStatus==CardStatus.PLUS2||currentStatus==CardStatus.PLUS4){
            currentStatus=CardStatus.NORMAL;
            touchCard(player,addCounter);
            if(addCounter<=2){
                if(d<0.33D){
                    curDialog=(transPlayer(player)+" : Emm...");
                }else if(d<=0.66D){
                    curDialog=(transPlayer(player)+" : So bad.");
                }else{
                    curDialog=(transPlayer(player)+" : Uuh.");
                }

            }else if(addCounter<=4){
                if(d<0.33D){
                    curDialog=(transPlayer(player)+" : Well...");
                }else if(d<=0.66D){
                    curDialog=(transPlayer(player)+" : Yooooooo~");
                }else{
                    curDialog=(transPlayer(player)+" : Four?");
                }
            }else if(addCounter<=10){
                if(d<0.33D){
                    curDialog=(transPlayer(player)+" : Huh...");
                }else if(d<=0.66D){
                    curDialog=(transPlayer(player)+" : My god...");
                }else{
                    curDialog=(transPlayer(player)+" : How you got so many?");
                }
            }else if(addCounter<=14){
                if(d<0.33D){
                    curDialog=(transPlayer(player)+" : At least I have all cards now.");
                }else if(d<=0.66D){
                    curDialog=(transPlayer(player)+" : All right... Help me to count!");
                }else{
                    curDialog=(transPlayer(player)+" : (uneasy noise)");
                }
            }else{
                curDialog=(transPlayer(player)+" : I can't hold them all... wait! It fell!");
            }

            addCounter=0;
        }else if(currentStatus==CardStatus.BANNED){
            currentStatus=CardStatus.NORMAL;

            if(d<=0.33D){
                curDialog=(transPlayer(player)+" : (cough)");
            }else if(d<=0.66D){
                curDialog=(transPlayer(player)+" : Next?");
            }else{
                curDialog=(transPlayer(player)+" : Who banned me?");
            }
        }else{
            touchCard(player,1);


            int newIdx=decide(player);
            if(newIdx!=-1){
                discardCard(player,newIdx);
                checkUno();
            }else{
                if(d<0.33D){
                    curDialog=(transPlayer(player)+" : Touch a card...");
                }else if(d<=0.66D){
                    curDialog=(transPlayer(player)+" : Didn't got.");
                }else{
                    curDialog=(transPlayer(player)+" : (reach card pile)");
                }
            }

        }


    }

    public void playCard(int player,int idx){

        if(idx!=-1){
            discardCard(player,idx);

            checkUno();
        }else{
            postProcess(player);
        }

        next(player);

    }

    public void quickPlayCard(int player,int idx){
        if(idx!=-1){
            isQuick=true;
            discardCard(player,idx);
            checkUno();
            next(player);
        }
    }

    public int getLastCardOwner(){
        return lastCard.getOwner();
    }

}

class Card{
    /*
     * color:
     *   0 red
     *   1 yellow
     *   2 blue
     *   3 green
     *   4 function
     * num:
     *   0~9: num
     *   10: ban
     *   11: reverse
     *   12: +2
     *   13: trans color
     *   14: +4
     *
     * */
    int color;
    int num;
    int owner;//because someone shouldn't grab his own card

    public Card(int color, int num) {
        this.color = color;
        this.num = num;
    }

    public int getOwner() {
        return owner;
    }

    public Card setOwner(int owner) {
        this.owner = owner;
        return this;
    }
}


enum CardStatus{
    NORMAL,
    BANNED,
    PLUS2,
    PLUS4
}

enum Cycle{
    CLOCKWISE,
    ANTICLOCKWISE
}