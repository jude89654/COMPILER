package com.ust;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Syntaxer {

    // yung susunod na token
    Token currentToken = null;

    // yung parse tree na gagawin
    JTree parseTree = null;

    // mga components ng parseTree
    DefaultMutableTreeNode programNode = null;
    DefaultMutableTreeNode programBodyNode=null;
    DefaultMutableTreeNode stmtNode=null;

    DefaultMutableTreeNode gawinThisNode=null;
    DefaultMutableTreeNode likeForNode=null;
    DefaultMutableTreeNode whileNode=null;
    DefaultMutableTreeNode stringNode=null;
    DefaultMutableTreeNode assignmentNode=null;
    DefaultMutableTreeNode ioNode=null;
    DefaultMutableTreeNode ifKungNode=null;
    DefaultMutableTreeNode orKayaNode=null;
    DefaultMutableTreeNode orKungNode=null;
    DefaultMutableTreeNode booleanStmtNode=null;
    DefaultMutableTreeNode relationalStmtNode=null;
    DefaultMutableTreeNode exprStmtNode=null;
    DefaultMutableTreeNode operandNode=null;
    DefaultMutableTreeNode termNode=null;
    DefaultMutableTreeNode factorNode=null;
    DefaultMutableTreeNode idNode=null;



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
        parseTree = new JTree(programNode);
        return this.parseTree;
    }

    public void program() {
        System.out.println("PARSING STARTED");

        // Create the main tree node called Program
        programNode = new DefaultMutableTreeNode("Program");

        // Start of analysis
        currentToken = lexer.nextToken();


        if (currentToken.getLexeme().equals("LEGGO")) {
            // System.out.print("FOUND LEGGO");

            currentToken = lexer.nextToken();
            progbody(programNode);

            System.out.println("PARSE COMPLETE");

        } else {
            System.out.println("ERROR ON LINE " + currentToken.getLineNumber() + "\n LEGGO EXPECTED");
        }
    }

    public void progbody(DefaultMutableTreeNode parent){
        System.out.println("ENTERED PROGBODY");

        programBodyNode= new DefaultMutableTreeNode("Program Body");
        parent.add(programBodyNode);

        stmt(programBodyNode);
        while (currentToken.getTokenClass() == Token.DELIMITER & currentToken.getTokenClass() != Token.STOP_KEYWORD) {

            currentToken = lexer.nextToken();

            if (currentToken.getTokenClass() == Token.STOP_KEYWORD) break;

            stmt(programBodyNode);

        }
        System.out.println("EXITED PROGBODY");
    }

    public void stmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED STATEMENT");

        stmtNode = new DefaultMutableTreeNode("Statement");
        parent.add(stmtNode);

        switch (currentToken.getTokenClass()) {
            case Token.VARIABLE:
                currentToken = lexer.nextToken();
                assignstmt(stmtNode);
                break;
            case Token.NUMINT:
            case Token.NUMDEC:
                currentToken = lexer.nextToken();
                exprStmt(stmtNode);
                break;
            case Token.STRING:
                currentToken = lexer.nextToken();
                string_stmt(stmtNode);
                break;
            case Token.MAKELAGAY_KEYWORD:// IO
            case Token.MAKELIMBAG_KEYWORD:
                currentToken = lexer.nextToken();
                IO(stmtNode);
                break;
            case Token.IFKUNG_KEYWORD:
                currentToken = lexer.nextToken();
                ifKungStmt(stmtNode);
                break;
            case Token.LIKEWHILE_KEYWORD:
                currentToken = lexer.nextToken();
                while_stmt(stmtNode);
                break;
            case Token.LIKEFOR_KEYWORD:
                currentToken = lexer.nextToken();
                likefor(stmtNode);
                break;
            case Token.GAWINTHIS_KEYWORD:
                currentToken = lexer.nextToken();
                gawinThis_stmt(stmtNode);
                break;///dsdasdasdasd
            case Token.CLOSECURLYBRACKET:
            default:
                System.out.println("INVALID TOKEN");

        }
        System.out.println("EXITED STATEMENT");
    }

    public void gawinThis_stmt(DefaultMutableTreeNode parent) {

        System.out.println("ENTERED GAWIN THIS");

        gawinThisNode = new DefaultMutableTreeNode("GAWINTHISSTMT");
        parent.add(gawinThisNode);

        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

            currentToken = lexer.nextToken();

            stmt(gawinThisNode);

            while (currentToken.getTokenClass() == Token.DELIMITER) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET)
                    break;

                stmt(gawinThisNode);

            }

            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.LIKEWHILE_KEYWORD) {

                    currentToken = lexer.nextToken();

                    if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

                        currentToken = lexer.nextToken();

                        booleanstmt(gawinThisNode);

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

    public void likefor(DefaultMutableTreeNode parent) {
        System.out.print("ENTERED FOR");
        likeForNode= new DefaultMutableTreeNode("LIKE FOR");
        parent.add(likeForNode);

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            stmt(likeForNode);

            if (currentToken.getTokenClass() == Token.SEMICOLON) {
                currentToken = lexer.nextToken();

                booleanstmt(likeForNode);

                if (currentToken.getTokenClass() == Token.SEMICOLON) {
                    currentToken = lexer.nextToken();

                    stmt(likeForNode);

                    if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
                            currentToken = lexer.nextToken();
                            currentToken = lexer.nextToken();

                            stmt(likeForNode);

                            while (currentToken.getTokenClass() == Token.DELIMITER) {

                                currentToken = lexer.nextToken();
                                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) break;

                                stmt(likeForNode);

                            }
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

    public void while_stmt(DefaultMutableTreeNode parent) {

        System.out.println("ENTERED WHILESTMT");

        whileNode = new DefaultMutableTreeNode("WHILE");
        parent.add(whileNode);


        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            booleanstmt(whileNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt(whileNode);

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET)
                            break;

                        stmt(whileNode);

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

    public void string_stmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED STRING STMT");
        stringNode = new DefaultMutableTreeNode("String EXPR");
        parent.add(parent);

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

    public void assignstmt(DefaultMutableTreeNode parent) {
        System.out.println("Entered Assignment");

        assignmentNode= new DefaultMutableTreeNode("ASSIGNMENT");
        parent.add(assignmentNode);
        if (currentToken.getTokenClass() == Token.IS_KEYWORD) {
            currentToken = lexer.nextToken();
            exprStmt(assignmentNode);
        }

        System.out.println("Exited Assignment");
    }

    public void IO(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED IO");

        ioNode= new DefaultMutableTreeNode("IO");
        parent.add(ioNode);


        string_stmt(ioNode);

        System.out.println("EXITED IO");

    }

    public void ifKungStmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED IF KUNG");
        ifKungNode = new DefaultMutableTreeNode("IF KUNG");
        parent.add(ifKungNode);

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

            currentToken = lexer.nextToken();

            booleanstmt(ifKungNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt(ifKungNode);

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();
                        if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) break;

                        stmt(ifKungNode);

                    }

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                        currentToken = lexer.nextToken();

                        while (currentToken.getTokenClass() == Token.ORKUNG_KEYWORD) {
                            currentToken = lexer.nextToken();
                            orkung_stmt(ifKungNode);
                        }

                        if (currentToken.getTokenClass() == Token.ORKAYA_KEYWORD) {
                            currentToken = lexer.nextToken();
                            orkaya_stmt(ifKungNode);
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

    public void orkaya_stmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED OR KAYA");
        orKayaNode = new DefaultMutableTreeNode("OR KAYA");
        parent.add(orKayaNode);

        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

            currentToken = lexer.nextToken();

            stmt(orKayaNode);

            while (currentToken.getTokenClass() == Token.DELIMITER) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                    // System.out.println("UNEXPECTED END OF FILE");
                    break;
                }
                stmt(orKayaNode);

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

    public void orkung_stmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED OR KUNG");

        orKungNode= new DefaultMutableTreeNode("orKung");
        parent.add(orKungNode);

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            currentToken = lexer.nextToken();

            booleanstmt(orKungNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {

                    currentToken = lexer.nextToken();

                    stmt(orKungNode);

                    while (currentToken.getTokenClass() == Token.DELIMITER) {

                        currentToken = lexer.nextToken();

                        if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                            //System.out.println("UNEXPECTED END OF FILE");
                            break;
                        }
                        stmt(orKungNode);
                    }

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                        currentToken = lexer.nextToken();
                        if (currentToken.getTokenClass() == Token.ORKAYA_KEYWORD) {
                            orkaya_stmt(orKungNode);
                        } else {
                            stmt(orKungNode);
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
    public void booleanstmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED BOOLEAN STMT");
        booleanStmtNode= new DefaultMutableTreeNode("BOOLEAN STMT");
        parent.add(booleanStmtNode);
        if(currentToken.getTokenClass()==Token.OPENPARENTHESIS){
            currentToken=lexer.nextToken();
            booleanstmt(booleanStmtNode);
            if(currentToken.getTokenClass()==Token.CLOSEPARENTHESIS){
                currentToken=lexer.nextToken();
            }else{
                System.out.println(") EXPECTED AT LINE "+currentToken.getLineNumber());
            }
        }

        if (currentToken.getTokenClass() == Token.NOT_KEYWORD) {
            currentToken = lexer.nextToken();
        }

        relationalstmt(booleanStmtNode);
        while (currentToken.getTokenClass() == Token.AND_KEYWORD | currentToken.getTokenClass() == Token.OR_KEYWORD) {
            currentToken = lexer.nextToken();
            relationalstmt(booleanStmtNode);

        }

        System.out.println("EXITED BOOLEAN STMT");
    }

    public void relationalstmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED RELATIONAL STMT");
        relationalStmtNode= new DefaultMutableTreeNode("Relationalstmt");
        parent.add(relationalStmtNode);

        if(currentToken.getTokenClass()==Token.YAH_KEYWORD
                |currentToken.getTokenClass()==Token.NAH_KEYWORD){
            currentToken=lexer.nextToken();
        }else {
            exprStmt(relationalStmtNode);
            if (currentToken.getTokenClass() == Token.GREATERTHANOREQUAL
                    | currentToken.getTokenClass() == Token.GREATERTHAN
                    | currentToken.getTokenClass() == Token.LESSTHANOREQUAL
                    | currentToken.getTokenClass() == Token.LESSTHAN
                    | currentToken.getTokenClass() == Token.ISEQUALOP
                    |currentToken.getTokenClass()==Token.NOTEQUALOP) {
                currentToken = lexer.nextToken();
                exprStmt(relationalStmtNode);

            }else{
                System.out.println("RELATIONAL OPERATOR EXPECTED");
            }
        }
        System.out.println("EXITED RELATIONAL STMT");
    }

    public void exprStmt(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED EXPRESSION STATEMENT");

        exprStmtNode= new DefaultMutableTreeNode("EXPR STMT");
        parent.add(exprStmtNode);
        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {

            currentToken = lexer.nextToken();

            exprStmt(exprStmtNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                currentToken = lexer.nextToken();
            }
        }

        operand(exprStmtNode);
        while (currentToken.getTokenClass() == Token.ADDOP | currentToken.getTokenClass() == Token.SUBOP) {
            currentToken = lexer.nextToken();
            operand(exprStmtNode);
        }

        System.out.println("EXITED EXPRESSION STATEMENT");

    }

    public void operand(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED OPERAND STATEMENT");
        operandNode = new DefaultMutableTreeNode("operand");
        parent.add(operandNode);
        term(operandNode);
        while (currentToken.getTokenClass() == Token.POWOP) {
            currentToken = lexer.nextToken();
            term(operandNode);
        }
        System.out.println("EXITED OPERAND STATEMENT");
    }

    public void term(DefaultMutableTreeNode parent) {
        System.out.println("ENTERED TERM");

        termNode = new DefaultMutableTreeNode("TERM");
        parent.add(termNode);

        factor(termNode);
        while (currentToken.getTokenClass() == Token.MULTOP
                | currentToken.getTokenClass() == Token.DIVOP
                | currentToken.getTokenClass() == Token.MODULOOP) {

            currentToken = lexer.nextToken();
            factor(termNode);
        }

        System.out.println("EXITED TERM");

    }

    public void factor(DefaultMutableTreeNode parent) {

        System.out.println("ENTERED FACTOR");

        factorNode = new DefaultMutableTreeNode("factor");
        parent.add(factorNode);

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
