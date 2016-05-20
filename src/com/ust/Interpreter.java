package com.ust;

import java.util.ArrayList;
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

        //System.out.println(treeNode.printTerminals());

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
            //case "<OUTPUTSTMT>":

            case "|":
                break;
            case "<ASSIGNSTMT>":
                //assignmentInterpret(node);
                break;
            case "<GAWINTHISSTMT>":
                //TODO gawinThisInterpret();
                break;
            case "<OUTPUTSTMT>":
                outputStatementInterpret(node);
                break;
            case "<IFSTMT>":
                //ifStmtInterpret(node);
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
        //for the scanning of the input
        Scanner sc = new Scanner(System.in);
        String identifier =node.getChildren().get(2).getChildren().get(0).getKey();
        //prompt
        System.out.println("input value for \""+identifier+"\": ");
        String laman = sc.nextLine().trim();

        SymbolTableEntry symbolTableEntry = new SymbolTableEntry(identifier,"","");
        //check kung anong data type yung ininput
        int value;
        double value1;
        //String type;
        try{
            value = Integer.parseInt(laman);
            symbolTableEntry.value=value;
            symbolTableEntry.type="INTEGER";
        }catch(NumberFormatException n){
            try {
                value1 = Double.parseDouble(laman);
                symbolTableEntry.value=value1;
                symbolTableEntry.type="DECIMAL";
            }catch(Exception e){
                symbolTableEntry.value=laman;
                symbolTableEntry.type="STRING";
            }
        }

        //lagay na sa symbol table
        symbolTable.addToTable(symbolTableEntry);
    }

    void outputStatementInterpret(TreeNode node){

        System.out.println("PRINT------"+stringExpressionInterpret(node.getChildren().get(2)));

    }

    String stringExpressionInterpret(TreeNode node){
        String output="";
        for(TreeNode children: node.getChildren()){
            if(children.getToken().getTokenClass()==Token.NUMINT
                    |children.getToken().getTokenClass()==Token.NUMDEC
                    |children.getToken().getTokenClass()==Token.STRING){
                output+=children.getToken().getLexeme();
                continue;
            }else if(children.getToken().getTokenClass()==Token.VARIABLE){
                if(symbolTable.checkIdentifier(children.getToken())){
                    output+=symbolTable.getValue(children.getToken()).value;
                }

            }
        }
        return output;
    }

    void booleanExpressionInterpret(TreeNode node){

        //boolean bulyan =

        //node.printTerminals();
        for(TreeNode child : node.getChildren()){



        }
    }

    void ExpressionInterpret(TreeNode node){
        String expression = "";
       // System.out.println(node.printTerminals());



        //return expression;
    }

    void error(String string){
        System.out.println("ERROR------"+string);
        System.exit(1);
    }





}
