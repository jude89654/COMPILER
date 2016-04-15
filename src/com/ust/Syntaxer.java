package com.ust;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Syntaxer {

    // yung susunod na token
    Token currentToken = null;

    // yung parse tree na gagawin
    JTree parseTree = null;

    // mga components ng parseTree
    DefaultMutableTreeNode nodeProgram = null;


    // Initialize Lexer
    Lexer lexer = null;

    // Constructor
    public Syntaxer(String filename) {
        // initialize na yung Lexical Anal
        lexer = new Lexer(filename);
    }

	/*
     * Function getParseTree para ibabalik na ang punong nabuo
	 */

    public JTree getParseTree() {
        parseTree = new JTree(nodeProgram);
        return this.parseTree;
    }

    public void program() {
        System.out.println("PARSING STARTED");

        // Create the main tree node called Program
        nodeProgram = new DefaultMutableTreeNode("Program");

        // Start of analysis
        currentToken = lexer.nextToken();


        if (currentToken.getLexeme().equals("LEGGO")) {
            // System.out.print("FOUND LEGGO");

            currentToken = lexer.nextToken();
            stmt();
            while (currentToken.getTokenClass() == Token.DELIMITER & currentToken.getTokenClass() != Token.STOP_KEYWORD) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.STOP_KEYWORD) break;

                stmt();

            }


            System.out.println("PARSE COMPLETE");

        } else {
            System.out.println("ERROR ON LINE " + currentToken.getLineNumber() + "\n LEGGO EXPECTED");
        }
    }


    // <STMT> → <IO> | <CONDSTMT> | <ASSIGNSTMT> | <ITERSTMT>
    // |<LOGICALOP>|<EXPR_STMT>|<DECSTMT>
    public void stmt() {
        System.out.println("ENTERED STATEMENT");


        switch (currentToken.getTokenClass()) {
            case Token.VARIABLE:
                currentToken = lexer.nextToken();
                assignstmt();
                break;
            case Token.NUMINT:
            case Token.NUMDEC:
                currentToken = lexer.nextToken();
                expr_stmt();
                break;
            case Token.STRING:
                currentToken = lexer.nextToken();
                string_stmt();
                break;
            case Token.MAKELAGAY_KEYWORD:// IO
            case Token.MAKELIMBAG_KEYWORD:
                currentToken = lexer.nextToken();
                IO();
                break;
            case Token.IFKUNG_KEYWORD:
                currentToken = lexer.nextToken();
                ifKungStmt();
                break;
            case Token.LIKEWHILE_KEYWORD:
                currentToken = lexer.nextToken();
                while_stmt();
                break;
            case Token.LIKEFOR_KEYWORD:
                currentToken = lexer.nextToken();
                likefor();
                break;
            case Token.GAWINTHIS_KEYWORD:
                currentToken = lexer.nextToken();
                gawinThis_stmt();
                break;///dsdasdasdasd
            default:
                System.out.println("INVALID TOKEN");

        }
        System.out.println("EXITED STATEMENT");
    }

    public void gawinThis_stmt() {

        System.out.println("ENTERED GAWIN THIS");


        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

            currentToken = lexer.nextToken();

            stmt();

            while (currentToken.getTokenClass() == Token.DELIMITER) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET)
                    break;

                stmt();

            }

            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.LIKEWHILE_KEYWORD) {

                    currentToken = lexer.nextToken();

                    if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

                        currentToken = lexer.nextToken();

                        booleanstmt();

                        if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                            currentToken = lexer.nextToken();

                        } else {
                            System.out.println("CLOSE PARENTHESIS EXPECTED at line " + currentToken.getLineNumber());
                        }

                    } else {
                        System.out.println("( EXPECTED at LINE " + currentToken.getLineNumber());
                    }

                } else {
                    System.out.println("EXPECTED WHILE AT LINE" + currentToken.getLineNumber());
                }

            } else {
                System.out.println("} EXPECTED AT LINE " + currentToken.getLineNumber());
            }

        } else {
            System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
        }

        System.out.println("EXITED GAWIN THIS");
    }

    public void likefor() {
        System.out.print("ENTERED FOR");

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            stmt();

            if (currentToken.getTokenClass() == Token.SEMICOLON) {
                currentToken = lexer.nextToken();

                booleanstmt();

                if (currentToken.getTokenClass() == Token.SEMICOLON) {
                    currentToken = lexer.nextToken();

                    stmt();

                    if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
                            currentToken = lexer.nextToken();
                            stmt();
                            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                                currentToken = lexer.nextToken();
                            }
                        }

                    }

                } else {
                    System.out.println("; EXPECTED at line " + currentToken.getLineNumber());
                }


            } else {
                System.out.println("; EXPECTED at line " + currentToken.getLineNumber());
            }

        } else {
            System.out.println("( EXPECTED at line " + currentToken.getLineNumber());
        }
        System.out.println("EXITED FOR");

    }

    public void while_stmt() {

        System.out.println("ENTERED WHILESTMT");
        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            booleanstmt();

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt();

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.EOF)
                            break;

                        stmt();

                    }

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {

                        currentToken = lexer.nextToken();

                    } else {

                        System.out.println("} EXPECTED at line " + currentToken.getLineNumber());
                    }

                } else {
                    System.out.println("{ EXPECTED at line " + currentToken.getLineNumber());
                }

            } else {
                System.out.println(") EXPECTED at line " + currentToken.getLineNumber());
            }

        } else {
            System.out.println("( EXPECTED at line " + currentToken.getLineNumber());
        }

        System.out.println("EXITED WHILESTMT");
    }

    public void string_stmt() {
        System.out.println("ENTERED STRING STMT");
        if (currentToken.getTokenClass() == Token.VARIABLE |
                currentToken.getTokenClass() == Token.NUMDEC |
                currentToken.getTokenClass() == Token.STRING |
                currentToken.getTokenClass() == Token.NUMINT) {
            currentToken = lexer.nextToken();


            while (currentToken.getTokenClass() != Token.DELIMITER) {
                currentToken = lexer.nextToken();
                if (currentToken.getTokenClass() == Token.DELIMITER) {
                    break;
                } else if (currentToken.getTokenClass() == Token.CONCATOP) {
                    currentToken = lexer.nextToken();
                    if (currentToken.getTokenClass() == Token.VARIABLE |
                            currentToken.getTokenClass() == Token.NUMDEC |
                            currentToken.getTokenClass() == Token.STRING |
                            currentToken.getTokenClass() == Token.NUMINT) {
                        currentToken = lexer.nextToken();
                    }
                } else {
                    System.out.println("CONCAT OP EXPECTED");
                    break;
                }
            }


        }

        System.out.println("EXITED STRING STMT");

    }

    public void assignstmt() {
        System.out.println("Entered Assignment");

        if (currentToken.getTokenClass() == Token.IS_KEYWORD) {
            currentToken = lexer.nextToken();
            expr_stmt();
        }

        System.out.println("Exited Assignment");
    }

    public void IO() {
        System.out.println("ENTERED IO");

        string_stmt();

        System.out.println("EXITED IO");

    }

    public void ifKungStmt() {
        System.out.println("ENTERED IF KUNG");
        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

            currentToken = lexer.nextToken();

            booleanstmt();

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt();

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();
                        if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) break;

                        stmt();

                    }

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                        currentToken = lexer.nextToken();

                        while (currentToken.getTokenClass() == Token.ORKUNG_KEYWORD) {
                            currentToken = lexer.nextToken();
                            orkung_stmt();
                        }

                        if (currentToken.getTokenClass() == Token.ORKAYA_KEYWORD) {
                            currentToken = lexer.nextToken();
                            orkaya_stmt();
                        }


                    } else {
                        System.out.println("} EXPECTED AFTER STATEMENTS A");
                    }

                } else {
                    System.out.print("{ EXPECTED AT LINE " + currentToken.getLineNumber());
                }

            } else {
                System.out.println(") EXPECTED AT LINE " + currentToken.getLineNumber());
            }

        } else {
            System.out.println("( EXPECTED AT LINE " + currentToken.getLineNumber());
        }
        System.out.println("EXITED IF KUNG");
    }

    public void orkaya_stmt() {
        System.out.println("ENTERED OR KAYA");

        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

            currentToken = lexer.nextToken();

            stmt();

            while (currentToken.getTokenClass() == Token.DELIMITER) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                    // System.out.println("UNEXPECTED END OF FILE");
                    break;
                }

            }

            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                //currentToken = lexer.nextToken();
            } else {
                System.out.println("} EXPECTED AT LINE" + currentToken.getLineNumber());
            }
        } else {
            System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
        }
        System.out.println("ORKAYA EXITED");

    }

    public void orkung_stmt() {
        System.out.println("ENTERED OR KUNG");

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            booleanstmt();

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt();

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                            //System.out.println("UNEXPECTED END OF FILE");
                            break;
                        }
                        stmt();
                    }

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                        currentToken = lexer.nextToken();
                        if (currentToken.getTokenClass() == Token.ORKAYA_KEYWORD) {
                            orkaya_stmt();
                        } else {
                            stmt();
                        }

                    } else {
                        System.out.println("} EXPECTED AT LINE" + currentToken.getLineNumber());
                    }

                } else {
                    System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
                }

            } else {
                System.out.println(") EXPECTED AT LINE" + currentToken.getLineNumber());
            }
        } else {
            System.out.println("( EXPECTED AT LINE" + currentToken.getLineNumber());
        }


        System.out.println("EXITED OR KUNG");
    }

    //FOR THE LOGICAL AND RELATIONAL AMBIGUITY
    public void booleanstmt() {
        System.out.println("ENTERED BOOLEAN STMT");

        if(currentToken.getTokenClass()==Token.OPENPARENTHESIS){
            currentToken=lexer.nextToken();
            booleanstmt();
            if(currentToken.getTokenClass()==Token.CLOSEPARENTHESIS){
                currentToken=lexer.nextToken();
            }else{
                System.out.println(") EXPECTED AT LINE "+currentToken.getLineNumber());
            }
        }

        if (currentToken.getTokenClass() == Token.NOT_KEYWORD) {
            currentToken = lexer.nextToken();
        }

        relationalstmt();
        while (currentToken.getTokenClass() == Token.AND_KEYWORD | currentToken.getTokenClass() == Token.OR_KEYWORD) {
            currentToken = lexer.nextToken();
            relationalstmt();

        }

        System.out.println("EXITED BOOLEAN STMT");
    }

    public void relationalstmt() {
        System.out.println("ENTERED RELATIONAL STMT");

        if(currentToken.getTokenClass()==Token.YAH_KEYWORD
                |currentToken.getTokenClass()==Token.NAH_KEYWORD){
            currentToken=lexer.nextToken();
        }else {
            expr_stmt();
            if (currentToken.getTokenClass() == Token.GREATERTHANOREQUAL
                    | currentToken.getTokenClass() == Token.GREATERTHAN
                    | currentToken.getTokenClass() == Token.LESSTHANOREQUAL
                    | currentToken.getTokenClass() == Token.LESSTHAN
                    | currentToken.getTokenClass() == Token.ISEQUALOP) {
                currentToken = lexer.nextToken();
                expr_stmt();

            }else{
                System.out.println("RELATIONAL OPERATOR EXPECTED");
            }
        }
        System.out.println("EXITED RELATIONAL STMT");
    }

    public void expr_stmt() {
        System.out.println("ENTERED EXPRESSION STATEMENT");

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

            currentToken = lexer.nextToken();

            expr_stmt();

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                currentToken = lexer.nextToken();
            }
        }

        operand();
        while (currentToken.getTokenClass() == Token.ADDOP | currentToken.getTokenClass() == Token.SUBOP) {
            currentToken = lexer.nextToken();
            operand();
        }

        System.out.println("EXITED EXPRESSION STATEMENT");

    }

    public void operand() {
        System.out.println("ENTERED OPERAND STATEMENT");
        term();
        while (currentToken.getTokenClass() == Token.POWOP) {
            currentToken = lexer.nextToken();
            term();
        }
        System.out.println("EXITED OPERAND STATEMENT");
    }

    public void term() {
        System.out.println("ENTERED TERM");

        factor();
        while (currentToken.getTokenClass() == Token.MULTOP
                | currentToken.getTokenClass() == Token.DIVOP
                | currentToken.getTokenClass() == Token.MODULOOP) {

            currentToken = lexer.nextToken();
            factor();
        }

        System.out.println("EXITED TERM");

    }

    public void factor() {

        System.out.println("ENTERED FACTOR");
        if (currentToken.getTokenClass() == Token.NUMINT | currentToken.getTokenClass() == Token.NUMDEC
                | currentToken.getTokenClass() == Token.VARIABLE) {

            currentToken = lexer.nextToken();

        } else {
            System.out.println("NUMBER OR IDENTIFIER EXPECTED AT LINE:" + currentToken.getLineNumber());
        }

        System.out.println("EXITED FACTOR");

    }


    public void id() {
        System.out.println("ENTERED ID");
        int tokenClass = currentToken.getTokenClass();

        if (tokenClass == Token.VARIABLE | tokenClass == Token.NUMDEC | tokenClass == Token.NUMINT) {
            currentToken = lexer.nextToken();
        } else {
            System.out.println("INVALID ID");
        }
        System.out.println("EXITED ID");
    }

}
