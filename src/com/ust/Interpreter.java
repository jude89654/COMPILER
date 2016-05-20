package com.ust;

import sun.reflect.generics.tree.Tree;

/**
 * Created by Jude on 5/14/2016.
 */
public class Interpreter {

    private SymbolTable symbolTable = new SymbolTable();
    private TreeNode treeNode;

    public Interpreter(TreeNode treeNode){
        this.treeNode = treeNode.children.get(1);
    }

    public void  startsemantics(){
        //statementsInterpret(tre);
    }

    void statementsInterpret(TreeNode node){

        //interpret every child ng kada node.
        for(TreeNode children:node.getChildren()){
            statement(children);
        }

    }

    void statement(TreeNode node){

        switch(node.getKey()){
            case "|":
                break;
            case "<ASSIGNSTMT>":
                assignmentInterpret(node);
                break;
            case "<GAWINTHISSTMT>":
                //TODO gawinThisInterpret();
                break;
            case "<OUTPUTSTMT>":
                //TODO outputInterpret();
                break;
            case "<IFSTMT>":
                //TODO ifStmtInterpret();
                break;
            case "<INPUTSTMT>":
                //TODO inputStmtInterpret();
                break;
            case "<WHILESTMT>":
                //TODO whileStmtInterpret();
                break;
            case "<STATEMENTS>":
                statementsInterpret(node);
        }
    }

    String ExpressionInterpret(TreeNode node){
        String expression = "";

        for(TreeNode children : node.getChildren()){
            //oExpression
        }
        return null;
    }

    void assignmentInterpret(TreeNode node){
        String identifier = node.getChildren().get(0).getKey();
        String Expression = ExpressionInterpret(node.getChildren().get(3));


    }



}
