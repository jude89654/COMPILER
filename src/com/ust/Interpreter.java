package com.ust;

import sun.reflect.generics.tree.Tree;

import java.util.Scanner;

/**
 * Created by Jude on 5/14/2016.
 */
public class Interpreter {

    private SymbolTable symbolTable = new SymbolTable();
    private TreeNode treeNode;

    public Interpreter(TreeNode treeNode){
        this.treeNode = treeNode;
    }

    public void  startsemantics(){
       program(treeNode);
    }

    public void program(TreeNode treeNode){
        statementsInterpret(treeNode.getChildren().get(1));

        symbolTable.showTable();
    }

    void statementsInterpret(TreeNode node){

        //interpret every child ng kada node.
        for(TreeNode children:node.getChildren()){

            //pag delimiter hayaan lang
            if(children.getKey().equals("|"))continue;

            System.out.println(children.getKey());
            statement(children.getChildren().get(0));
        }

    }

    void statement(TreeNode node){

        System.out.println("--"+node.getKey());
        switch(node.getKey()){
            case "|":
                break;
            case "<ASSIGNSTMT>":
                //assignmentInterpret(node);
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
                inputStatementInterpret(node);
                break;
            case "<WHILESTMT>":
                //TODO whileStmtInterpret();
                break;
            case "<STATEMENTS>":
                statementsInterpret(node);
        }
    }
    void inputStatementInterpret(TreeNode node){



        Scanner sc = new Scanner(System.in);
        String identifier =node.getChildren().get(2).getKey();


        System.out.println("input value for \""+identifier+"\": ");

        String laman = sc.nextLine().trim();



        SymbolTableEntry symbolTableEntry = new SymbolTableEntry(identifier,"","");


        //check kung anong data type yung ininput
        int value;
        double value1;
        try{
            value = Integer.parseInt(laman);

            symbolTableEntry.value=value;
        }catch(NumberFormatException n){
            try {
                value1 = Double.parseDouble(laman);
                symbolTableEntry.value=value1;
            }catch(Exception e){
                symbolTableEntry.value=laman;
            }
        }

        //lagay na sa symbol table
        symbolTable.addToTable(symbolTableEntry);


    }

    String ExpressionInterpret(TreeNode node){
        String expression = "";

        for(TreeNode child : node.getChildren()){

            switch(child.getKey()){
                case "<OPERAND>":
                    operandInterpret(child);
                    break;

                default:
                    expression+="";
                    break;
            }

        }


        return expression;
    }

    String operandInterpret(TreeNode node){
        String expression ="";

        for(TreeNode child : node.getChildren()){
            switch(child.getKey()){
                case "<TERM>":
                    //expression+=termInterpret(child);
                    break;

            }
        }
        return null;
    }

    void assignmentInterpret(TreeNode node){
        String identifier = node.getChildren().get(0).getKey();
        String Expression = ExpressionInterpret(node.getChildren().get(3));

    }

    void Expression(TreeNode node){

    }



}
