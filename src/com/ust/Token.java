package com.ust;/*
 *  Class Token
 *  
 *  Each token is represented by its type and value. 
 */

public class Token {

    static final int VARIABLE = 100;                // Variable
    static final int T_UNKNOWN = 101;               // Unknown
    static final int ADDOP = 103;                   // '+'
    static final int SUBOP = 104;                 // '-'
    static final int DIVOP = 105;                    // '/'
    static final int MULTOP = 106;                    // '*'
    static final int DELIMITER = 107;                // ';'
    static final int IS_KEYWORD = 108;                // 'is'
    //static final int NUMINT = 109;					// 'int' keyword
    //static final int NUMDEC = 110;					// 'float' keyword
    static final int EOF = 112;                    // End of File
    static final int NUMDEC = 113;              // Float number
    static final int NUMINT = 114;                  // Integer number
    static final int IFKUNG_KEYWORD = 115;            // 'if' keyword
    static final int OPENPARENTHESIS = 116;        // '('
    static final int CLOSEPARENTHESIS = 117;        // ')'
    static final int OPENCURLYBRACKET = 118;        // '{'
    static final int CLOSECURLYBRACKET = 119;        // '}'
    static final int GREATERTHAN = 120;            // '>'
    static final int LESSTHAN = 121;                // '<'
    static final int WHILE_KEYWORD = 122;
    static final int FOR_KEYWORD = 123;
    static final int LEGGO_KEYWORD = 124;
    static final int STOP_KEYWORD= 125;
    static final int NA_KEYWORD = 126;
    static final int PARAM_SEP = 127;
    static final int MODULOOP= 128;
    static final int CONCATOP= 129;
    static final int RETURNNA_KEYWORD=131;
    static final int OR_KEYWORD=132;
    static final int AND_KEYWORD=133;
    static final int MAKELAGAY_KEYWORD=134;
    static final int MAKELIMBAG_KEYWORD =135;
    static final int LIKEFOR_KEYWORD = 136;
    static final int LIKEWHILE_KEYWORD = 137;
    static final int ORKAYA_KEYWORD= 138;
    static final int ORKUNG_KEYWORD=139;
    static final int NAH_KEYWORD = 140;
    static final int NOT_KEYWORD = 141;
    static final int YAH_KEYWORD = 142;
    static final int GAWINTHIS_KEYWORD = 143;
    static final int GAWINLATER_KEYWORD = 144;
    static final int POWOP=145;
    static final int ISEQUALOP=146;
    static final int NOTEQUALOP=147;
    static final int STRING = 148;
    static final int COMMENT=149;

    static final int GREATERTHANOREQUAL=150;
    static final int LESSTHANOREQUAL=151;
    static final int SEMICOLON = 152;

    private String token; // Type of of token
    private String lexeme; // The lexeme
    private int lineNumber;
    public int tokenClass;

    private static int counter = 0;

    public Token(String token, String lexeme, int lineNumber, int tokenClass) {
        this.token = token;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.tokenClass = tokenClass;
        System.out.println("FOUND \""+lexeme.toUpperCase()+"\"\t AT LINE "+lineNumber  );
    }

    // Returns the type of the token
    public String getTokenType() {
        return token;
    }

    // Returns the lexeme of the token
    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getTokenClass(){
        return tokenClass;
    }

    // Returns a string representation of the token
    public String toString() {

        return
                getTokenType() + "\t\t\t"
                        + getLexeme()
                        + "\tlineNumber:" + getLineNumber();
    }
}