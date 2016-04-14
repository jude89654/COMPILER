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
            while (currentToken.getTokenClass() == Token.DELIMITER|currentToken.getTokenClass()!=Token.T_UNKNOWN) {

                currentToken = lexer.nextToken();

                // if (currentToken.getTokenClass() == Token.continure) // break;

                stmt();
            }


            // System.out.println("PARSE COMPLETE");

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
                // TODO
                break;
            case Token.STRING:
                currentToken = lexer.nextToken();
                string_stmt();
                break;
            case Token.MAKELAGAY_KEYWORD:// IO
            case Token.MAKELIMBAG_KEYWORD:
                currentToken = lexer.nextToken();
                string_stmt();
                break;
            case Token.IFKUNG_KEYWORD:
                currentToken = lexer.nextToken();
                ifKungStmt();
                break;
            case Token.WHILE_KEYWORD:
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

                        booleanexprstmt();

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

                booleanexprstmt();

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

            booleanexprstmt();

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
        while (currentToken.getTokenClass() != Token.DELIMITER) {

            if (currentToken.getTokenClass() == Token.CONCATOP) {

                currentToken = lexer.nextToken();

            } else if (currentToken.getTokenClass() == Token.NUMDEC
                    | currentToken.getTokenClass() == Token.NUMINT
                    | currentToken.getTokenClass() == Token.VARIABLE
                    | currentToken.getTokenClass() == Token.STRING) {
                currentToken = lexer.nextToken();
            } else {
                System.out.println(". OR |" + " EXPECTED");

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

            booleanexprstmt();

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

                        if (currentToken.getTokenClass() == Token.ORKUNG_KEYWORD) {
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


    public void orkung_stmt() {
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

    public void orkaya_stmt() {
        System.out.println("ENTERED OR KUNG");

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            booleanexprstmt();

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

    // <BOOLEAN> → Yah|Nah|<RELATIONALEXPR>
    public void booleanexprstmt() {
        System.out.println("ENTERED BOOLEAN EXPRSTMT");

        switch (currentToken.getTokenClass()) {
            case Token.YAH_KEYWORD:
            case Token.NAH_KEYWORD:
                currentToken = lexer.nextToken();
                // logicalexpr();
                break;
            case Token.VARIABLE:
            case Token.NUMDEC:
            case Token.NUMINT:
                currentToken = lexer.nextToken();
                relationalexpr();
                break;
            case Token.GREATERTHAN:
            case Token.LESSTHAN:
            case Token.GREATERTHANOREQUAL:
            case Token.LESSTHANOREQUAL:
            case Token.NOTEQUALOP:
            case Token.ISEQUALOP:
                relationalexpr();
                break;
            default:
                break;
        }
        System.out.println("EXITED BOOLEAN EXPRSTMT");
    }

    public void relationalexpr() {
        System.out.println("ENTERED RELATIONAL EXPR");

        if (currentToken.getTokenClass() == Token.GREATERTHAN
                | currentToken.getTokenClass() == Token.GREATERTHANOREQUAL
                | currentToken.getTokenClass() == Token.LESSTHAN
                | currentToken.getTokenClass() == Token.LESSTHANOREQUAL
                | currentToken.getTokenClass() == Token.ISEQUALOP
                | currentToken.getTokenClass() == Token.NOTEQUALOP) {

            currentToken = lexer.nextToken();

            if (currentToken.getTokenClass() == Token.VARIABLE
                    | currentToken.getTokenClass() == Token.NUMDEC
                    | currentToken.getTokenClass() == Token.NUMINT) {

                currentToken = lexer.nextToken();

            } else {
                System.out.println("IDENTIFIER EXPECTED AT LINE " + currentToken.getLineNumber());
            }

        } else if (currentToken.getTokenClass() == Token.AND_KEYWORD
                | currentToken.getTokenClass() == Token.OR_KEYWORD) {

            currentToken = lexer.nextToken();

            if (currentToken.getTokenClass() == Token.VARIABLE
                    | currentToken.getTokenClass() == Token.NUMDEC
                    | currentToken.getTokenClass() == Token.NUMINT) {

                currentToken = lexer.nextToken();

            } else {

                System.out.println("IDENTIFIER EXPECTED AT LINE " + currentToken.getLineNumber());

            }

        } else {

            System.out.println("LOGICAL OR OPERATIONAL OPERATOR NEEDED AT LINE" + currentToken.getLineNumber());

        }

        System.out.println("EXITED RELATIONAL EXPR");
    }

	/*
     * Function expression_statement Parses string of the langauges generated by
	 * the rule <expr_stmt> -> <term> {(+|-) <term> }
	 */
    // <EXPR_STMT> → <ARITHMETICEXPR> |<STRINGEXPR> |<ID>| <ASSIGNEXPR>

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

    // <ID> → <VARIABLE> | <NUMBER> | <STRINGEXPR>
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
