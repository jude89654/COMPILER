package com.ust;


/**
 * Created by Jude on 5/13/2016.
 */
public class AST {

    protected Token token;
    protected String lemma;
    protected Object value;
    protected AST left;
    protected AST right;
    protected int type;

    final static int INTEGER = 1, DECIMAL = 2, STRING =3;

    public AST(Token token, AST left, AST right){
        this.token = token;
        this.left = left;
        this.right = right;
        lemma = token.getLexeme();

        //PARA SA DATA TYPE
        try{
            value = Integer.parseInt(lemma);
            type = 1;
        }catch(NumberFormatException nfe){
            try{
                value = Double.parseDouble(lemma);
                type = 2;
            }catch(NumberFormatException ne){
                value = lemma;
                type = 3;
            }catch(Exception e){
                System.out.println("INVALID DATA TYPE");
            }

        }

    }

    public AST(Token token){
        this.token = token;
        left = null;
        right = null;
    }

    public int getTokenClass(){
        return token.getTokenClass();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public AST getLeft() {
        return left;
    }

    public void setLeft(AST left) {
        this.left = left;
    }

    public AST getRight() {
        return right;
    }

    public void setRight(AST right) {
        this.right = right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static int getINTEGER() {
        return INTEGER;
    }

    public static int getDECIMAL() {
        return DECIMAL;
    }

    public static int getSTRING() {
        return STRING;
    }



}
