package com.ust;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jude on 5/14/2016.
 */
public class Interpreter {

    private SymbolTable symbolTable = new SymbolTable();
    private TreeNode treeNode;
    String stringToBeComputed="";
    String stringToBeOutputted="";

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

    void ifStmtInterpret(TreeNode node){

    }
    void statement(TreeNode node){

        System.out.println("--"+node.getKey());
        switch(node.getKey()){
            //case "<OUTPUTSTMT>":

            case "|":
                break;
            case "<ASSIGNSTMT>":
                assignmentStatementInterpret(node);
                break;
            case "<GAWINTHISSTMT>":
                //TODO gawinThisInterpret();
                break;
            case "<OUTPUTSTMT>":
                outputStatementInterpret(node);
                break;
            case "<IFSTMT>":
                ifStmtInterpret(node);
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
            System.out.println(children.getKey());
            if(children.getToken().getTokenClass()==Token.NUMINT
                    |children.getToken().getTokenClass()==Token.NUMDEC
                    |children.getToken().getTokenClass()==Token.STRING){
                output+=children.getToken().getLexeme();

                continue;
            }else if(children.getToken().getTokenClass()==Token.VARIABLE){
                if(symbolTable.checkIdentifier(children.getToken())){
                    output+=symbolTable.getValue(children.getToken()).value;
                }else{
                    error("VARIABLE"+children.getToken().getLexeme()+"NOT INITIALIZED");
                }

            }
        }
        return output;
    }

    void booleanExpressionInterpret(TreeNode node){

        //boolean bulyan =

        //node.printTerminals();

    }

    void termInterpret(TreeNode node){
        for(TreeNode child : node.getChildren()){
            switch(child.getKey()){
                case "<FACTOR>":
                    factorInterpret(child);
                    break;
                case "iTimes":
                    stringToBeComputed+=" *";
                    break;
                case "iDiv":
                    stringToBeComputed+=" /";
                    break;
                case "iMod":
                    stringToBeComputed+=" %";
                    break;
            }
        }
    }
    void factorInterpret(TreeNode node){
        for(TreeNode child : node.getChildren()){
            switch(child.getKey()){
                case "<ID>":
                    iDInterpret(child);
                    break;
                case "iTimes":
                    stringToBeComputed+=" ^";
                    break;
                case "iDiv":
                    stringToBeComputed+=" /";
                    break;
                case "(":
                    stringToBeComputed+=" (";
                    break;
                case "<EXPRSTMT>":
                    expressionInterpret(child);
                    break;
                case ")":
                    stringToBeComputed+=" )";
                    break;
                case "<STRINGEXPR>":
                    stringToBeOutputted+= stringExpressionInterpret(child);
                    break;
            }
        }
    }

    void iDInterpret(TreeNode node){
        for(TreeNode child : node.getChildren()){
            switch(child.getToken().getTokenClass()){
                case Token.VARIABLE:
                    if(symbolTable.checkIdentifier(child.getToken())){
                        stringToBeComputed+=" "+symbolTable.getValue(child.getKey()).value;
                    }else{
                        error(child.token.getLexeme()+" VARIABLE NOT INITIALIZED");
                    }
                    break;
                case Token.NUMDEC:
                case Token.NUMINT:
                    stringToBeComputed+=" "+child.getToken().getLexeme();
                    break;
            }
        }
    }

    void operandInterpret(TreeNode node){
        for(TreeNode child : node.getChildren()){
            switch(child.getKey()){
                case "<TERM>":
                    termInterpret(child);
                    break;
                case "iPow":
                    stringToBeComputed+=" ^";
                    break;
            }
        }
    }

    void expressionInterpret(TreeNode node){

       for(TreeNode child:node.getChildren()){
           System.out.println(child.getKey());
           switch(child.getKey()){
               case "<OPERAND>":
                   operandInterpret(child);
                   break;
               case "iAdd":
                   stringToBeComputed+=" +";
                   break;
               case "iSub":
                   stringToBeComputed+=" -";
                   break;
               case "<STRINGEXPR>":
                stringToBeOutputted+= stringExpressionInterpret(child);
                   break;
           }
       }



    }

    void error(String string){
        System.out.println("ERROR------"+string);
        System.exit(1);
    }

    void assignmentStatementInterpret(TreeNode node){
        String identifier = node.getChildren().get(0).getChildren().get(0).getKey();
        System.out.println("IDENTIFIER"+identifier);

        stringToBeComputed="";
        stringToBeOutputted="";
        expressionInterpret(node.getChildren().get(2));
        System.out.println("-----------------"+stringToBeOutputted);

        SymbolTableEntry symbolTableEntry = new SymbolTableEntry(identifier,"","");
        try
         {
             String laman = StackExpr.infixToPostfix(stringToBeComputed);
             laman = StackExpr.postfixEvaluation(laman);

            int value;
            double value1;

            //String type;
            try {

                value = Integer.parseInt(laman);
                symbolTableEntry.value = value;
                symbolTableEntry.type = "INTEGER";
            } catch (NumberFormatException n) {
                try {

                    value1 = Double.parseDouble(laman);
                    symbolTableEntry.value = value1;
                    symbolTableEntry.type = "DECIMAL";
                } catch (Exception e) {
                    symbolTableEntry.value = stringToBeComputed;
                    symbolTableEntry.type = "STRING";
                }
            }
        }catch(Exception e){
            if (node.children.size()<4-1) {
                stringToBeOutputted = stringExpressionInterpret(node.getChildren().get(4));
                String laman = StackExpr.infixToPostfix(stringToBeComputed);
                laman = stringToBeOutputted;
                symbolTableEntry.value = laman;
                symbolTableEntry.type = "STRING";

            }else{

                //String laman = StackExpr.infixToPostfix(stringToBeComputed);
                String laman =  stringToBeOutputted;
                symbolTableEntry.value = laman;
                symbolTableEntry.type = "STRING";
            }
        }
        stringToBeOutputted="";
        symbolTable.addToTable(symbolTableEntry);
    }


    void booleanstmt(TreeNode node){

    }


}
