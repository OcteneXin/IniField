package com.octenexin.inifield.utils;

public class DecisionNode {

    private boolean isLine;

    private String mainLine;
    private String leftChoice;
    private String rightChoice;

    //pointers
    private DecisionNode left;
    private DecisionNode right;

    public String getMainLine() {
        return mainLine;
    }

    public String getLeftChoice() {
        return leftChoice;
    }

    public String getRightChoice() {
        return rightChoice;
    }

    public DecisionNode getLeft() {
        return left;
    }

    public void setLeft(DecisionNode left) {
        this.left = left;
    }

    public DecisionNode getRight() {
        return right;
    }

    public void setRight(DecisionNode right) {
        this.right = right;
    }

    public boolean isLine() {
        return isLine;
    }

    public DecisionNode(boolean isLine, String mainLine, String leftChoice, String rightChoice) {
        this.isLine = isLine;
        this.mainLine = mainLine;
        this.leftChoice = leftChoice;
        this.rightChoice = rightChoice;
    }

    public DecisionNode(boolean isLine, String mainLine) {
        this.isLine = isLine;
        this.mainLine = mainLine;
    }

}
