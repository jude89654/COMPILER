package com.ust;


public class Parser {

    // yung susunod na token
    Token currentToken = null;

    // yung parseTree na gagawin
    TreeNode tree = new TreeNode("PROGRAM");
    //yung AST na gagawin
    AST punonapagcompute = null;

    // Initialize Lexer
    Lexer lexer = null;

    //for computations
    String infix = "";


    //For outputStmt
    String outputstmt = "";

    //for booleanexpressions
    String bool="";


    // Constructor
    public Parser(String filename) {
        // initialize na yung Lexical Anal
        lexer = new Lexer(filename);
    }


    //yung symboltable
    SymbolTable mgasimbolo = new SymbolTable();

    public void error(String message, Token token, TreeNode parent) {
        System.out.println("ERROR!!! " + message + "EXPECTED AT LINE " + token.getLineNumber());
        System.exit(1);
        while (currentToken.getTokenClass() != Token.DELIMITER
                & currentToken.getTokenClass() != Token.CLOSEPARENTHESIS
                & currentToken.getTokenClass() != Token.CLOSECURLYBRACKET
                & currentToken.getTokenClass() != Token.OPENPARENTHESIS
                & currentToken.getTokenClass() != Token.OPENCURLYBRACKET
                & currentToken.getTokenClass() != Token.EOF) {
            currentToken = lexer.nextToken();
        }
        parent.addChild(currentToken);
        currentToken = lexer.nextToken();
        stmt(parent);
    }


    //gusto ko lang magkaerror :D
    public void startParse() throws ParseError {
        System.out.println("PARSING STARTED");

        // Create the main createTree node called Program
        // programNode = new DefaultMutableTreeNode("Program");
        // Start of analysis
        currentToken = lexer.nextToken();


        if (currentToken.getLexeme().equals("LEGGO")) {
            // System.out.print("FOUND LEGGO");
            tree.addChild(currentToken);

            //programNode.add(new DefaultMutableTreeNode("LEGGO"));

            currentToken = lexer.nextToken();

            statements(tree);

            if (currentToken.getTokenClass() == Token.STOP_KEYWORD) {

                //programNode.add(new DefaultMutableTreeNode("Stop"));
                tree.addChild(currentToken);
                //currentToken = lexer.nextToken();

                if (currentToken != null && currentToken.getTokenClass() == Token.NA_KEYWORD) {
                    tree.addChild(currentToken);
                }

                tree.toString();

            } else {
                // throw new ParseError();
            }
            System.out.println("PARSE COMPLETE");
            //programNode.add(new DefaultMutableTreeNode("LEGGO"));
            mgasimbolo.showTable();
            tree.toString();

        } else {
            System.out.println("ERROR ON LINE " + currentToken.getLineNumber() + "\n LEGGO EXPECTED");
        }
    }

    public void statements(TreeNode parent) {
        System.out.println("PUMASOK STATEMENTS");

        //programBodyNode = new DefaultMutableTreeNode("Program Body");
        //parent.add(programBodyNode);

        //create a node
        TreeNode progBodyNode = new TreeNode("<STATEMENTS>");
        //addnode sa createTree
        parent.addChild(progBodyNode);



        stmt(progBodyNode);
        while (currentToken.getTokenClass() != Token.STOP_KEYWORD) {

            if (currentToken.getTokenClass() == Token.DELIMITER) {

                progBodyNode.addChild(currentToken);

                currentToken = lexer.nextToken();

            }

            if (currentToken.getTokenClass() == Token.STOP_KEYWORD|currentToken.getTokenClass()==Token.CLOSECURLYBRACKET) break;

            stmt(progBodyNode);

        }
        System.out.println("UMALIS PROGBODY");
    }

    public void stmt(TreeNode parent) {
        System.out.println("ENTERED STATEMENT");

        //stmtNode = new DefaultMutableTreeNode("Statement");
        //parent.add(stmtNode);


        TreeNode stmtNode = new TreeNode("STATEMENT");
        parent.addChild(stmtNode);
        switch (currentToken.getTokenClass()) {
            case Token.VARIABLE:
                Token tempToken = currentToken;
                currentToken = lexer.nextToken();
                assignstmt(stmtNode, tempToken);
                break;
            case Token.NUMINT:
            case Token.NUMDEC:
                currentToken = lexer.nextToken();
                exprStmt(stmtNode);
                break;
            case Token.STRING:
                currentToken = lexer.nextToken();
                string_stmt(stmtNode, "");
                break;
            case Token.MAKELAGAY_KEYWORD:// IO

                Token IOToken = currentToken;
                currentToken = lexer.nextToken();
                inputStmt(stmtNode, IOToken);
                break;

            case Token.MAKELIMBAG_KEYWORD:
                Token OToken = currentToken;
                currentToken = lexer.nextToken();
                outputStmt(stmtNode, OToken);
                break;
            case Token.IFKUNG_KEYWORD:
                currentToken = lexer.nextToken();
                ifKungStmt(stmtNode);
                break;
            case Token.LIKEWHILE_KEYWORD:
                currentToken = lexer.nextToken();
                while_stmt(stmtNode);
                break;

            case Token.GAWINTHIS_KEYWORD:
                currentToken = lexer.nextToken();
                gawinThis_stmt(stmtNode);
                break;///dsdasdasdasd
            case Token.OPENCURLYBRACKET:
                currentToken = lexer.nextToken();
                statements(stmtNode);
                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                    currentToken = lexer.nextToken();
                } else {
                    error("}", currentToken, parent);
                }

            case Token.CLOSECURLYBRACKET:
            case Token.STOP_KEYWORD:
            case Token.DELIMITER:
                parent.addChild(currentToken);
                currentToken = lexer.nextToken();
                break;
            default:
                System.out.println("INVALID TOKEN");
                error("INVALID TOKEN ", currentToken, stmtNode);
                break;


        }
        System.out.println("EXITED STATEMENT");
    }

    public void gawinThis_stmt(TreeNode parent) {

        System.out.println("ENTERED GAWIN THIS");

        TreeNode gawinThisNode = new TreeNode("GAWINTHISSTMT");
        parent.addChild(gawinThisNode);
        gawinThisNode.addChild("gawinThis");

        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
            gawinThisNode.addChild(currentToken);
            currentToken = lexer.nextToken();

            stmt(gawinThisNode);

            while (currentToken.getTokenClass() == Token.DELIMITER) {
                gawinThisNode.addChild(currentToken);
                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET)
                    break;

                stmt(gawinThisNode);

            }

            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                gawinThisNode.addChild(currentToken);
                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.LIKEWHILE_KEYWORD) {
                    gawinThisNode.addChild(currentToken);
                    currentToken = lexer.nextToken();

                    if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
                        gawinThisNode.addChild(currentToken);
                        currentToken = lexer.nextToken();

                        booleanstmt(gawinThisNode);

                        if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                            gawinThisNode.addChild(currentToken);
                            currentToken = lexer.nextToken();

                        } else {
                            // System.out.println("CLOSE PARENTHESIS EXPECTED at line " + currentToken.getLineNumber());
                            error(") EXPECTED ", currentToken, gawinThisNode);
                        }

                    } else {
                        //System.out.println("( EXPECTED at LINE " + currentToken.getLineNumber());
                        error("( ", currentToken, gawinThisNode);
                    }

                } else {
                    error("LIKEWHILE ", currentToken, gawinThisNode);
                    //System.out.println("EXPECTED WHILE AT LINE" + currentToken.getLineNumber());
                }

            } else {
                error("} ", currentToken, gawinThisNode);
                //System.out.println("} EXPECTED AT LINE " + currentToken.getLineNumber());
            }

        } else {
            error("{ ", currentToken, gawinThisNode);
            //System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
        }

        System.out.println("EXITED GAWIN THIS");
    }

    public void while_stmt(TreeNode parent) {

        System.out.println("ENTERED WHILESTMT");

        TreeNode whileNode = new TreeNode("LIKEWHILE");
        parent.addChild(whileNode);


        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            whileNode.addChild(currentToken);
            currentToken = lexer.nextToken();

            booleanstmt(whileNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                whileNode.addChild(currentToken);
                currentToken = lexer.nextToken();

                if(currentToken.getTokenClass()==Token.OPENCURLYBRACKET) {
                    whileNode.addChild(currentToken);
                    currentToken = lexer.nextToken();

                    statements(whileNode);

                    if(currentToken.getTokenClass()==Token.CLOSECURLYBRACKET){
                        whileNode.addChild(currentToken);
                        currentToken = lexer.nextToken();

                    }else{
                        error("}", currentToken, whileNode);
                    }
                }else{
                    error("{", currentToken,whileNode);
                }
            } else {
                System.out.println(") EXPECTED at line " + currentToken.getLineNumber());
            }

        } else {
            System.out.println("( EXPECTED at line " + currentToken.getLineNumber());
        }

        System.out.println("EXITED WHILESTMT");
    }

    public void string_stmt(TreeNode parent, String strings) {
        System.out.println("ENTERED STRING STMT");
        TreeNode stringNode = new TreeNode("STRINGEXPR");
        parent.addChild(stringNode);
       /* outputstmt = "";
        if (!strings.equals("")) {
            outputstmt += StackExpr.postfixEvaluation(StackExpr.infixToPostfix(strings));
        }*/


       if (currentToken.getTokenClass() == Token.VARIABLE |
                currentToken.getTokenClass() == Token.NUMDEC |
                currentToken.getTokenClass() == Token.STRING |
                currentToken.getTokenClass() == Token.NUMINT) {
            /* switch (currentToken.getTokenClass()) {
                case Token.VARIABLE:
                    if (mgasimbolo.checkIdentifier(currentToken)) {
                        //add yung value ng variable sa outputstmt
                        outputstmt += mgasimbolo.getValue(currentToken.getLexeme()).value;
                    } else {
                        System.out.println("ERROR! - VARIABLE" + currentToken.getLexeme() + "NOT INITIALIZED");
                        System.exit(0);
                    }
                    break;
                case Token.NUMDEC:
                case Token.NUMINT:
                case Token.STRING:
                    outputstmt += currentToken.getLexeme();
                    break;
            }
*/
            stringNode.addChild(currentToken);
            currentToken = lexer.nextToken();

            while (currentToken.getTokenClass() == Token.CONCATOP) {
                stringNode.addChild(currentToken);
                currentToken = lexer.nextToken();
                if (currentToken.getTokenClass() == Token.VARIABLE |
                        currentToken.getTokenClass() == Token.NUMDEC |
                        currentToken.getTokenClass() == Token.STRING |
                        currentToken.getTokenClass() == Token.NUMINT) {


                    stringNode.addChild(currentToken);
                    currentToken = lexer.nextToken();
                }
            }

        } else {
            error("NUMBER OR STRING", currentToken, stringNode);
        }


        System.out.println("EXITED STRING STMT");

    }

    public void assignstmt(TreeNode parent, Token token) {
        System.out.println("Entered Assignment");

        TreeNode assignmentNode = new TreeNode("ASSIGNMENT");
        parent.addChild(assignmentNode);
        TreeNode variable = new TreeNode("VARIABLE");
        assignmentNode.addChild(variable);
        variable.addChild(token);


        //Temporary storage ng identifier
        String id = token.getLexeme();

        //STRING NA ICOCOMPUTE
        infix = "";


        if (currentToken.getTokenClass() == Token.IS_KEYWORD) {

            assignmentNode.addChild(currentToken);

            currentToken = lexer.nextToken();
            exprStmt(assignmentNode);
/*
            int valueint;
            double value;
            System.out.println(infix);
            SymbolTableEntry symbolTableEntry;
            try {
                String postfix = StackExpr.infixToPostfix(infix.trim());
                
                System.out.println(postfix);
                try {
                    valueint = Integer.parseInt(StackExpr.postfixEvaluation(postfix));
                 symbolTableEntry = new SymbolTableEntry(id, 0, valueint);
                    mgasimbolo.addToTable(symbolTableEntry);
                    return;
                } catch (NumberFormatException nfe) {
                    value = Double.parseDouble(StackExpr.postfixEvaluation(postfix));
                    symbolTableEntry = new SymbolTableEntry(id, 0, value);
                    mgasimbolo.addToTable(symbolTableEntry);
                    return;
                }
            } catch (Exception e) {
                SymbolTableEntry symbolTableEntrys = new SymbolTableEntry(id, 0, outputstmt);
                mgasimbolo.addToTable(symbolTableEntrys);
                return;
            }*/

            //lagay sa symbol table
            



        }


        System.out.println("Exited Assignment");
    }

    public void inputStmt(TreeNode parent, Token token) {
        System.out.println("ENTERED inputStmt");

        TreeNode IONode = new TreeNode("inputStmt");
        parent.addChild(IONode);
        IONode.addChild(token);


        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            // infix+=" (";
            IONode.addChild(currentToken);
            currentToken = lexer.nextToken();
            if(currentToken.getTokenClass()==Token.VARIABLE) {

                currentToken=lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                    IONode.addChild(currentToken);
                    currentToken = lexer.nextToken();
                }
            }
        } else {
            error("STRING EXPR", currentToken, IONode);
        }

        System.out.println("EXITED IO");

    }

    public void outputStmt(TreeNode parent, Token token) {
        System.out.println("ENTERED outputStmt");


        TreeNode IONode = new TreeNode("outputStmt");

        parent.addChild(IONode);
        IONode.addChild(token);


        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            // infix+=" (";
            IONode.addChild(currentToken);
            currentToken = lexer.nextToken();
            string_stmt(IONode, "");
            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {

                IONode.addChild(currentToken);
                currentToken = lexer.nextToken();
            }
            //System.out.println("outputStmt--------" + outputstmt);
        } else {
            error("STRING EXPR", currentToken, IONode);
        }


        System.out.println("EXITED IO");

    }

    public void ifKungStmt(TreeNode parent) {
        System.out.println("ENTERED IF KUNG");

        TreeNode ifKungNode = new TreeNode("IFKUNGSTMT");

        parent.addChild(ifKungNode);
        ifKungNode.addChild(new Token("", "ifKung", Token.IFKUNG_KEYWORD, Token.IFKUNG_KEYWORD));

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            ifKungNode.addChild(currentToken);
            currentToken = lexer.nextToken();

            booleanstmt(ifKungNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                ifKungNode.addChild(currentToken);
                currentToken = lexer.nextToken();

               if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
                    ifKungNode.addChild(currentToken);

                   currentToken = lexer.nextToken();

                    statements(ifKungNode);


                    //statements(ifKungNode);

                    if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                        ifKungNode.addChild(currentToken);
                        currentToken = lexer.nextToken();

                        while (currentToken.getTokenClass() == Token.ORKUNG_KEYWORD) {

                            currentToken = lexer.nextToken();
                            orkung_stmt(ifKungNode);
                        }

                        if (currentToken.getTokenClass() == Token.ORKAYA_KEYWORD) {
                            //ifKungNode.addChild(currentToken);
                            currentToken = lexer.nextToken();
                            orkaya_stmt(ifKungNode);
                        }


                    } else {
                        error("}", currentToken, ifKungNode);
                        System.out.println("} EXPECTED AFTER STATEMENTS A");
                    }

                } else {
                    error("{", currentToken, ifKungNode);
                    System.out.print("{ EXPECTED AT LINE " + currentToken.getLineNumber());
                }

            } else {
                error(")", currentToken, ifKungNode);
                System.out.println(") EXPECTED AT LINE " + currentToken.getLineNumber());
            }

        } else {
            error("(", currentToken, ifKungNode);
            System.out.println("( EXPECTED AT LINE " + currentToken.getLineNumber());
        }
        System.out.println("EXITED IF KUNG");
    }

    public void orkaya_stmt(TreeNode parent) {//else if
        System.out.println("ENTERED OR KAYA");
        TreeNode orKayaNode = new TreeNode("ORKAYA");
        parent.addChild(orKayaNode);
        orKayaNode.addChild("orKaya");
        //orKayaNode = new DefaultMutableTreeNode("OR KAYA");
        // parent.add(orKayaNode);

        if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
            orKayaNode.addChild(currentToken);
            //orKayaNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
            currentToken = lexer.nextToken();

            stmt(orKayaNode);

            while (currentToken.getTokenClass() == Token.DELIMITER) {
                orKayaNode.addChild(currentToken);
                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                    // System.out.println("UNEXPECTED END OF FILE");
                    break;
                }
                stmt(orKayaNode);
            }

            if (currentToken.getTokenClass() == Token.CLOSECURLYBRACKET) {
                orKayaNode.addChild(currentToken);
                //orKayaNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
                currentToken = lexer.nextToken();
            } else {
                error("}", currentToken, orKayaNode);
                System.out.println("} EXPECTED AT LINE" + currentToken.getLineNumber());
            }
        } else {
            error("{", currentToken, orKayaNode);
            System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
        }
        System.out.println("ORKAYA EXITED");

    }

    public void orkung_stmt(TreeNode parent) {
        System.out.println("ENTERED OR KUNG");

        TreeNode orKungNode = new TreeNode("ORKUNGSTMT");
        parent.addChild(orKungNode);
        orKungNode.addChild("orKung");
        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            orKungNode.addChild(currentToken);
            currentToken = lexer.nextToken();

            booleanstmt(orKungNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                orKungNode.addChild(currentToken);


                currentToken = lexer.nextToken();

                if (currentToken.getTokenClass() == Token.OPENCURLYBRACKET) {
                    orKungNode.addChild(currentToken);

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
                        orKungNode.addChild(currentToken);
                        currentToken = lexer.nextToken();


                    } else {
                        error("}", currentToken, orKungNode);
                        System.out.println("} EXPECTED AT LINE" + currentToken.getLineNumber());
                    }

                } else {

                    error("{", currentToken, orKungNode);
                    System.out.println("{ EXPECTED AT LINE" + currentToken.getLineNumber());
                }

            } else {

                error(")", currentToken, orKungNode);
                System.out.println(") EXPECTED AT LINE" + currentToken.getLineNumber());
            }
        } else {

            error("(", currentToken, orKungNode);
            System.out.println("( EXPECTED AT LINE" + currentToken.getLineNumber());
        }


        System.out.println("EXITED OR KUNG");
    }

    //FOR THE LOGICAL AND RELATIONAL AMBIGUITY
    public void booleanstmt(TreeNode parent) {
        System.out.println("ENTERED BOOLEAN STMT");
        TreeNode booleanStmtNode = new TreeNode("BOOLEANSTMT");
        parent.addChild(booleanStmtNode);
        if (currentToken.getTokenClass() == Token.NOT_KEYWORD) {

            //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));

            booleanStmtNode.addChild(currentToken);

            currentToken = lexer.nextToken();
        }

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            booleanStmtNode.addChild(currentToken);
            currentToken = lexer.nextToken();
            //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
            booleanstmt(booleanStmtNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
                booleanStmtNode.addChild(currentToken);
                currentToken = lexer.nextToken();
            } else {

                error(")", currentToken, booleanStmtNode);
                System.out.println(") EXPECTED AT LINE " + currentToken.getLineNumber());
            }
        }


        relationalstmt(booleanStmtNode);
        while (currentToken.getTokenClass() == Token.AND_KEYWORD | currentToken.getTokenClass() == Token.OR_KEYWORD) {
            //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
            booleanStmtNode.addChild(currentToken);
            currentToken = lexer.nextToken();
            relationalstmt(booleanStmtNode);

        }

        System.out.println("EXITED BOOLEAN STMT");
    }

    public void relationalstmt(TreeNode parent) {
        System.out.println("ENTERED RELATIONAL STMT");
        TreeNode relationalStmtNode = new TreeNode("RELATIONALSTMT");
        parent.addChild(relationalStmtNode);

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            relationalStmtNode.addChild(currentToken);
            currentToken = lexer.nextToken();
            //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
            relationalstmt(relationalStmtNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                //booleanStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));
                relationalStmtNode.addChild(currentToken);
                currentToken = lexer.nextToken();
            } else {

                error(")", currentToken, relationalStmtNode);
                System.out.println(") EXPECTED AT LINE " + currentToken.getLineNumber());
            }
        }

        if (currentToken.getTokenClass() == Token.YAH_KEYWORD
                | currentToken.getTokenClass() == Token.NAH_KEYWORD
                | currentToken.getTokenClass()==Token.VARIABLE ) {
            relationalStmtNode.addChild(currentToken);
            currentToken = lexer.nextToken();


            if (currentToken.getTokenClass() == Token.GREATERTHANOREQUAL
                    | currentToken.getTokenClass() == Token.GREATERTHAN
                    | currentToken.getTokenClass() == Token.LESSTHANOREQUAL
                    | currentToken.getTokenClass() == Token.LESSTHAN
                    | currentToken.getTokenClass() == Token.ISEQUALOP
                    | currentToken.getTokenClass() == Token.NOTEQUALOP) {
                relationalStmtNode.addChild(currentToken);
                currentToken = lexer.nextToken();
                exprStmt(relationalStmtNode);

            }




        }// else {
        //}
        System.out.println("EXITED RELATIONAL STMT");
    }

    public void exprStmt(TreeNode parent) {
        System.out.println("ENTERED EXPRESSION STATEMENT");

        TreeNode exprStmtNode = new TreeNode("EXPRSTMT");

        parent.addChild(exprStmtNode);

        if (currentToken.getTokenClass() == Token.OPENPARENTHESIS) {
            infix += " (";
            exprStmtNode.addChild(currentToken);
            //exprStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));

            currentToken = lexer.nextToken();

            exprStmt(exprStmtNode);

            if (currentToken.getTokenClass() == Token.CLOSEPARENTHESIS) {
                infix += " )";

                exprStmtNode.addChild(currentToken);

                //exprStmtNode.add(new DefaultMutableTreeNode(currentToken.getLexeme()));

                currentToken = lexer.nextToken();

            } else {

                error("(", currentToken, exprStmtNode);

            }
        }

        operand(exprStmtNode);
        while (currentToken.getTokenClass() == Token.ADDOP | currentToken.getTokenClass() == Token.SUBOP) {
            if (currentToken.getTokenClass() == Token.ADDOP) infix += " +";
            else infix += " -";

            exprStmtNode.addChild(currentToken);
            currentToken = lexer.nextToken();
            operand(exprStmtNode);
        }

        //infix="";
        System.out.println("EXITED EXPRESSION STATEMENT");

    }

    public void operand(TreeNode parent) {
        System.out.println("ENTERED OPERAND STATEMENT");
        TreeNode operandNode = new TreeNode("OPERAND");
        parent.addChild(operandNode);
        term(operandNode);
        while (currentToken.getTokenClass() == Token.POWOP) {
            infix += " ^";
            parent.addChild(currentToken);
            currentToken = lexer.nextToken();
            term(operandNode);
        }

        
        System.out.println("EXITED OPERAND STATEMENT");
    }

    public void term(TreeNode parent) {
        System.out.println("ENTERED TERM");

        TreeNode termNode = new TreeNode("TERM");
        parent.addChild(termNode);

        factor(termNode);
        while (currentToken.getTokenClass() == Token.MULTOP
                | currentToken.getTokenClass() == Token.DIVOP
                | currentToken.getTokenClass() == Token.MODULOOP
                | currentToken.getTokenClass() == Token.CONCATOP) {

            switch (currentToken.getTokenClass()) {
                case Token.MULTOP:
                    infix += " *";
                    break;

                case Token.DIVOP:
                    infix += " *";
                    break;

                case Token.MODULOOP:
                    infix += " *";
                    break;
                	
            }


            termNode.addChild(currentToken);
            currentToken = lexer.nextToken();
            factor(termNode);
        }

        System.out.println("EXITED TERM");

    }

    public void factor(TreeNode parent) {

        System.out.println("ENTERED FACTOR");

        TreeNode factorNode = new TreeNode("factor");
        parent.addChild(factorNode);

        if (currentToken.getTokenClass() == Token.NUMINT | currentToken.getTokenClass() == Token.NUMDEC
                | currentToken.getTokenClass() == Token.VARIABLE) {
            id(factorNode);
            //currentToken = lexer.nextToken(); //redundant kasi

        } else if (currentToken.getTokenClass() == Token.STRING) {
            string_stmt(factorNode, infix);
            //currentToken = lexer.nextToken();
        } else {
            error("IDENTIFIER", currentToken, parent);
            System.out.println("NUMBER OR IDENTIFIER EXPECTED AT LINE:" + currentToken.getLineNumber());
        }

        System.out.println("EXITED FACTOR");

    }


    public void id(TreeNode parent) {
        System.out.println("ENTERED ID");
        TreeNode idNode = new TreeNode("ID");
        parent.addChild(idNode);
        int tokenClass = currentToken.getTokenClass();

        if (tokenClass == Token.NUMDEC | tokenClass == Token.NUMINT) {
            //infix += " " + currentToken.getLexeme();
            //System.out.println("INFIX:"+infix);

            idNode.addChild(currentToken);
            currentToken = lexer.nextToken();
        } else if (tokenClass == Token.VARIABLE) {

            //checheck niya kung nasa symbol table yung id
           // if (mgasimbolo.checkIdentifier(currentToken)) {

                //kukunin yung value ng id
                //infix += " " + mgasimbolo.getValue(currentToken.getLexeme()).value;
                currentToken = lexer.nextToken();
                //kung string ba;
                if(currentToken.getTokenClass()==Token.CONCATOP){
                	currentToken = lexer.nextToken();
                	string_stmt(parent,infix);
                }
           // } else {
         //       System.out.println("ERROR! - VARIABLE" + currentToken.getLexeme() + "NOT INITIALIZED");
           //     System.exit(0);
          //  }

        } else {
            error("ID", currentToken, idNode);
            System.out.println("INVALID ID");
        }
        System.out.println("EXITED ID");
    }

}

class ParseError extends Exception {
    public ParseError(String message) {
        super(message);
    }

    public ParseError() {
        super();
    }
}
