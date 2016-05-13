package com.ust;

/**
 * Created by Jude on 4/24/2016.
 */
public class SyntaxerVer2 {
    protected Token currentToken;
    protected int currentTokenClass;
    Lexer lexer;

    public SyntaxerVer2(String filename) {
        // initialize na yung Lexical Anal
        lexer = new Lexer(filename);
        currentToken=lexer.nextToken();
    }

    void getLookAhead(){
        currentToken = lexer.nextToken();
        currentTokenClass= currentToken.getTokenClass();
    }

    void match(int tokenClass,String token){
        if(currentToken.getTokenClass() != tokenClass){
            error(token,currentToken);
        }else {
            if (currentTokenClass == Token.STOP_KEYWORD) {
                System.out.print("STOP NA TALAGA BEH");
            } else {
                getLookAhead();
            }
        }
    }

    void program(){
        match(Token.LEGGO_KEYWORD,"LEGGO");
        programBody();
        match(Token.STOP_KEYWORD,"Stop");
    }

    void programBody(){
        stmt();
        while(currentToken.getTokenClass()==Token.DELIMITER){
            getLookAhead();
            stmt();
        }
    }

     void stmt(){
        switch (currentToken.getTokenClass()){
            case Token.VARIABLE:
                assignment();
                break;
            case Token.IFKUNG_KEYWORD:
                ifKung();
        }
    }

    void ifKung(){
        match(Token.IFKUNG_KEYWORD,"ifKung");
        match(Token.OPENPARENTHESIS,"(");
        expression();
        match(Token.CLOSEPARENTHESIS,")");
        match(Token.OPENCURLYBRACKET,"{");
        programBody();
        match(Token.CLOSECURLYBRACKET,"}");
        }

    void assignment(){
        match(Token.VARIABLE,"VARIABLE");
        switch (currentTokenClass) {
            case Token.DELIMITER:
                decstmt();
            case Token.IS_KEYWORD:
                expression();
                break;
        }
    }

    void decstmt(){
        match(Token.DELIMITER,"|");
    }

    void booleanexprstmt(){
        if (currentTokenClass == Token.NOT_KEYWORD) {
            getLookAhead();
        }
        relationalexprstmt();

        switch (currentTokenClass){
            case Token.OPENPARENTHESIS:
                getLookAhead();
                booleanexprstmt();
                match(Token.CLOSEPARENTHESIS,")");
                break;
            case Token.VARIABLE:
                relationalexprstmt();
        }



    }

    void relationalexprstmt(){
        while(currentTokenClass==Token.GREATERTHAN
                |currentTokenClass==Token.LESSTHAN
                |currentTokenClass==Token.GREATERTHANOREQUAL
                |currentTokenClass==Token.LESSTHANOREQUAL
                |currentTokenClass==Token.ISEQUALOP
                |currentTokenClass==Token.NOTEQUALOP){
            getLookAhead();
            relationalexprstmt();
        }
    }

    void expression(){
        operand();
        while(currentTokenClass==Token.ADDOP|currentTokenClass==Token.SUBOP){
            operand();
        }
    }

    void operand(){
        term();
        while(currentTokenClass==Token.POWOP){
            term();
        }
    }
    void term(){
        factor();
        while(currentTokenClass==Token.MULTOP|currentTokenClass==Token.DIVOP){
            factor();
        }
    }
    void factor(){
        id();
    }
    void id(){
        switch (currentTokenClass){
            case Token.VARIABLE:
                match(Token.VARIABLE,"VARIABLE");
                break;
            case Token.NUMDEC:
                match(Token.NUMDEC,"NUMDEC");
                break;
            case Token.NUMINT:
                match(Token.NUMINT,"NUMINT");
                break;
            case Token.OPENPARENTHESIS:
                match(Token.OPENPARENTHESIS,"(");
                expression();
                match(Token.CLOSEPARENTHESIS,")");
                break;
            default:
                error("ID",currentToken);

        }

    }



    void error(String tokenNeeded, Token currentToken){
        System.out.println(tokenNeeded+"EXPECTED AT LINE "+currentToken.getLineNumber());

    }


}
