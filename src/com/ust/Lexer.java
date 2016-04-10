package com.ust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Lexer {

    private BufferedReader reader; // Reader
    private char currentCharacter; // The current character being scanned
    public static int line = 1;
    private static final char EOF = (char) (-1);

    // End of file character

    public Lexer(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Read the first character
        currentCharacter = read();
    }

    private char read() {
        try {
            char x = (char) (reader.read());
            return x;
        } catch (IOException e) {
            e.printStackTrace();
            return EOF;
        }
    }

    // Checks if a character is a digit
    private boolean isNumeric(char c) {
        if (c >= '0' && c <= '9')
            return true;

        return false;
    }

    public boolean isAlpha(char c) {
        if (c >= 'a' && c <= 'z')
            return true;
        if (c >= 'A' && c <= 'Z')
            return true;

        return false;

    }

    public boolean isSymbol(char x) {
        String symbol = "#@.";
        return symbol.contains("" + x);
    }

    public Token nextToken() {

        String state = "Qstart"; // Initial state
        int mgaNabasangNumberNaSunodSunod = 0; // A buffer for number literals
        String mgaNabasangLetra = "";
        int decBuffer = 0;
        boolean skipped = false;

        while (true) {

            System.out.println(currentCharacter + " " + state + " " + line);
            if (currentCharacter == EOF && !skipped) {
                skipped = true;
            } else if (skipped) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            switch (state) {//controller
                case "Qstart":
                    switch (currentCharacter) {
                        case '\n':
                            line++;
                        case ' ': // Whitespaces

                        case '\r':
                        case '\t':
                            currentCharacter = read();
                            continue;
                        case '|':
                            currentCharacter = read();
                            return new Token("DELIMITER", "|", line, Token.DELIMITER);
                        case ',':
                            currentCharacter = read();
                            return new Token("ParamSep", ",", line, Token.PARAM_SEP);
                        case '(':
                            currentCharacter = read();
                            return new Token("LParen", "(", line,Token.OPENPARENTHESIS);
                        case ')':
                            currentCharacter = read();
                            return new Token("RParen", ")", line, nextToken().CLOSEPARENTHESIS);
                        case '{':
                            currentCharacter = read();
                            return new Token("LBracket", "{", line, Token.OPENCURLYBRACKET);
                        case '}':
                            currentCharacter = read();
                            return new Token("RBracket", "}", line, Token.CLOSECURLYBRACKET);
                        case '%':
                            currentCharacter = read();
                            return new Token("Modulo", "%", line, Token.MODULOOP);
                        case '=':
                            currentCharacter = read();
                            state = "Q=";
                            continue;
                        case '!':
                            currentCharacter = read();
                            state = "Q!";
                            continue;
                        case '>':
                            currentCharacter = read();
                            state = "Q>";
                            continue;
                        case '<':
                            currentCharacter = read();
                            state = "Q<";
                            continue;
                        case '#':
                            currentCharacter = read();
                            state = "Q#";
                            if (currentCharacter == '\n' | currentCharacter == '\r') line++;
                            continue;
                            //return new Token("Comment", "#");
                        case '@':
                            currentCharacter = read();
                            state = "Q@";
                            continue;
                            //return new Token("Comment","@");
                        case '.':
                            currentCharacter = read();
                            return new Token("CONCATOP", ".", line, Token.CONCATOP);
                        case '"':
                            currentCharacter = read();
                            state = "Q\"";
                            mgaNabasangLetra = "";
                            continue;
                        default:
                            state = "QnumStart"; // Check the next possibility
                            continue;
                    }
                    // Integer - Start
                case "QnumStart":
                    if (isNumeric(currentCharacter)) {
                        mgaNabasangNumberNaSunodSunod = 0; // Reset the buffer.
                        mgaNabasangNumberNaSunodSunod += (currentCharacter - '0');

                        state = "QnumBody";

                        currentCharacter = read();

                    } else {
                        state = "QidStart"; //doesnot start with number or symbol go to id
                    }
                    continue;

                    // Integer - Body
                case "QnumBody":
                    if (isNumeric(currentCharacter)) {
                        mgaNabasangNumberNaSunodSunod *= 10;
                        mgaNabasangNumberNaSunodSunod += (currentCharacter - '0');

                        currentCharacter = read();

                    } else if (currentCharacter == '.') {

                        currentCharacter = read();

                        state = "Qfloat"; //has decimal point go to case 4

                    } else if(isAlpha(currentCharacter)){

                    }else{
                        return new Token("NUMINT", "" + mgaNabasangNumberNaSunodSunod, line, Token.NUMINT);
                    }

                    continue;

                    //decimal-start
                case "Qfloat":
                    if (isNumeric(currentCharacter)) {
                        decBuffer = 0;
                        decBuffer += (currentCharacter - '0');
                        state = "QFloatBody";
                        currentCharacter = read();

                    } else {
                        return new Token("ERROR", "Invalid input: " + mgaNabasangNumberNaSunodSunod + ".", line,Token.T_UNKNOWN);
                    }
                    continue;
                    //decimal body
                case "QFloatBody":
                    if (isNumeric(currentCharacter)) {
                        decBuffer *= 10;
                        decBuffer += (currentCharacter - '0');

                        currentCharacter = read();
                    } else {
                        return new Token("NUMDEC", "" + mgaNabasangNumberNaSunodSunod + "." + decBuffer, line, Token.NUMDEC);
                    }
                    continue;

                    //identifier or keyword -start
                case "QidStart":
                    if (currentCharacter == 's') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qs";
                        currentCharacter = read();
                    } else if (currentCharacter == 'Q') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qr";
                        currentCharacter = read();
                    } else if (currentCharacter == 'L') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QL";
                        currentCharacter = read();
                    } else if (currentCharacter == 'O') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QO";
                        currentCharacter = read();
                    } else if (currentCharacter == 'A') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QA";
                        currentCharacter = read();
                    } else if (currentCharacter == 'm') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qm";
                        currentCharacter = read();
                    } else if (currentCharacter == 'l') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Ql";
                        currentCharacter = read();
                    } else if (currentCharacter == 'o') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qo";
                        currentCharacter = read();
                    } else if (currentCharacter == 'N') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QN";
                        currentCharacter = read();
                    } else if (currentCharacter == 'Y') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QY";
                        currentCharacter = read();
                    } else if (currentCharacter == 'i') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qi";
                        currentCharacter = read();
                    } else if (currentCharacter == 'g') {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "Qg";
                        currentCharacter = read();
                    } else if (isAlpha(currentCharacter)) {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        state = "QidBody";
                        currentCharacter = read();
                    } else {
                        mgaNabasangLetra = "";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                        return new Token("ERROR", "Invalid input:" + mgaNabasangLetra, line, Token.T_UNKNOWN);
                    }
                    continue;
                    //identifier - Body
                case "QidBody":
                    if ((isAlpha(currentCharacter) || isNumeric(currentCharacter) || isSymbol(currentCharacter))) {

                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();


                    } else {


                        return new Token("VARIABLE", "" + mgaNabasangLetra, line, Token.VARIABLE);
                    }
                    continue;
                    //Check Kung stopNa
                case "Qs":
                    if (currentCharacter == 't') {
                        state = "Qst";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qst":
                    if (currentCharacter == 'o') {
                        state = "Qsto";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qsto":
                    if (currentCharacter == 'p') {
                        state = "Qstop";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qstop":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.STOP_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;

                    //Check kung returnNa
                case "Qr":
                    if (currentCharacter == 'e') {
                        state = "Qre";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qre":
                    if (currentCharacter == 't') {
                        state = "Qret";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qret":
                    if (currentCharacter == 'u') {
                        state = "Qretu";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qretu":
                    if (currentCharacter == 'r') {
                        state = "Qretur";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qretur":
                    if (currentCharacter == 'n') {
                        state = "Qreturn";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qreturn":
                    if (currentCharacter == 'N') {
                        state = "QreturnN";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QreturnN":
                    if (currentCharacter == 'a') {
                        state = "QreturnNa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QreturnNa":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.RETURNNA_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //Check kung LEGGO
                case "QL":
                    if (currentCharacter == 'E') {
                        state = "QLE";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QLE":
                    if (currentCharacter == 'G') {
                        state = "QLEG";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QLEG":
                    if (currentCharacter == 'G') {
                        state = "QLEGG";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QLEGG":
                    if (currentCharacter == 'O') {
                        state = "QLEGGO";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QLEGGO":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.LEGGO_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //Check kung Or
                case "QO":
                    if (currentCharacter == 'r') {
                        state = "QOr";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QOr":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("LOGICALOP", mgaNabasangLetra, line, Token.OR_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //Check KUNG And
                case "QA":
                    if (currentCharacter == 'n') {
                        state = "QAn";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QAn":
                    if (currentCharacter == 'd') {
                        state = "QAnd";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QAnd":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("LOGICALOP", mgaNabasangLetra, line,Token.AND_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG MAKELIMBAG OR MAKELAGAY
                case "Qm":
                    if (currentCharacter == 'a') {
                        state = "Qma";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qma":
                    if (currentCharacter == 'k') {
                        state = "Qmak";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qmak":
                    if (currentCharacter == 'e') {
                        state = "Qmake";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qmake":
                    if (currentCharacter == 'L') {
                        state = "QmakeL";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeL":
                    if (currentCharacter == 'a') {
                        state = "QmakeLa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'i') {
                        state = "QmakeLi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLa":
                    if (currentCharacter == 'g') {
                        state = "QmakeLag";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLag":
                    if (currentCharacter == 'a') {
                        state = "QmakeLaga";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLaga":
                    if (currentCharacter == 'y') {
                        state = "QmakeLagay";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLagay":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line,Token.MAKELAGAY_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLi":
                    if (currentCharacter == 'm') {
                        state = "QmakeLim";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLim":
                    if (currentCharacter == 'b') {
                        state = "QmakeLimb";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLimb":
                    if (currentCharacter == 'a') {
                        state = "QmakeLimba";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLimba":
                    if (currentCharacter == 'g') {
                        state = "QmakeLimbag";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QmakeLimbag":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line,Token.MAKELIMBAG_KEYWORD );
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //Check kung likewhile or likeFor
                case "Ql":
                    if (currentCharacter == 'i') {
                        state = "Qli";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qli":
                    if (currentCharacter == 'k') {
                        state = "Qlik";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qlik":
                    if (currentCharacter == 'e') {
                        state = "Qlike";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qlike":
                    if (currentCharacter == 'F') {
                        state = "QlikeF";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'W') {
                        state = "QlikeW";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeF":
                    if (currentCharacter == 'o') {
                        state = "QlikeFo";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeFo":
                    if (currentCharacter == 'r') {
                        state = "QlikeFor";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeFor":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.LIKEFOR_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeW":
                    if (currentCharacter == 'h') {
                        state = "QlikeWh";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeWh":
                    if (currentCharacter == 'i') {
                        state = "QlikeWhi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeWhi":
                    if (currentCharacter == 'l') {
                        state = "QlikeWhil";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeWhil":
                    if (currentCharacter == 'e') {
                        state = "QlikeWhile";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QlikeWhile":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.LIKEWHILE_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG orKaya o orKung
                case "Qo":
                    if (currentCharacter == 'r') {
                        state = "Qor";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qor":
                    if (currentCharacter == 'K') {
                        state = "QorK";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QorK":
                    if (currentCharacter == 'u') {
                        state = "QorKu";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'a') {
                        state = "QorKa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QorKa":
                    if (currentCharacter == 'y') {
                        state = "QorKay";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QorKay":
                    if (currentCharacter == 'a') {
                        state = "QorKaya";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QorKaya":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line,Token.ORKAYA_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QorKu":
                    if (currentCharacter == 'n') {
                        state = "QorKun";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                case "QorKun":
                    if (currentCharacter == 'g') {
                        state = "QorKung";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                case "QorKung":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line,Token.ORKUNG_KEYWORD );
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG Nah or Na
                case "QN":
                    if (currentCharacter == 'o') {
                        state = "QNo";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'a') {
                        state = "QNa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QNo":
                    if (currentCharacter == 't') {
                        state = "QNot";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QNot":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("LOGICALOP", mgaNabasangLetra, line, Token.NOT_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QNa":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("NOISEWORD", mgaNabasangLetra, line,Token.NA_KEYWORD);
                    } else if (currentCharacter == 'h') {
                        state = "QNah";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QNah":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("BOOLEAN", mgaNabasangLetra, line,Token.NAH_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK Kung Yah
                case "QY":
                    if (currentCharacter == 'a') {
                        state = "QYa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QYa":
                    if (currentCharacter == 'h') {
                        state = "QYah";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QYah":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("BOOLEAN", mgaNabasangLetra, line, Token.YAH_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;

                case "Qi"://iAdd,ifKung,is,iSub,iTimes,iDiv,iPow
                    if (currentCharacter == 'A') {
                        state = "QiA";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'f') {
                        state = "Qif";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 's') {
                        state = "Qis";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'S') {
                        state = "QiS";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'M') {
                        state = "QiM";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'T') {
                        state = "QiT";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'D') {
                        state = "QiD";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'P') {
                        state = "QiP";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //case hanggang gawin
                case "Qg":
                    if (currentCharacter == 'a') {
                        state = "Qga";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qga":
                    if (currentCharacter == 'w') {
                        state = "Qgaw";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qgaw":
                    if (currentCharacter == 'i') {
                        state = "Qgawi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qgawi":
                    if (currentCharacter == 'n') {
                        state = "Qgawin";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Qgawin":
                    if (currentCharacter == 'T') {
                        state = "QgawinT";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else if (currentCharacter == 'L') {
                        state = "QgawinL";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //gawinThis and gawinLater
                case "QgawinT":
                    if (currentCharacter == 'h') {
                        state = "QgawinTh";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinTh":
                    if (currentCharacter == 'i') {
                        state = "QgawinThi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinThi":
                    if (currentCharacter == 's') {
                        state = "QgawinThis";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinThis":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line,Token.GAWINTHIS_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;

                case "QgawinL":
                    if (currentCharacter == 'a') {
                        state = "QgawinLa";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinLa":
                    if (currentCharacter == 't') {
                        state = "QgawinLat";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinLat":
                    if (currentCharacter == 'e') {
                        state = "QgawinLate";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinLate":
                    if (currentCharacter == 'r') {
                        state = "QgawinLater";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QgawinLater":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.GAWINLATER_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;

                    //CHECK KUNG ifKung
                case "Qif":
                    if (currentCharacter == 'K') {
                        state = "QifK";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QifK":
                    if (currentCharacter == 'u') {
                        state = "QifKu";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QifKu":
                    if (currentCharacter == 'n') {
                        state = "QifKun";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QifKun":
                    if (currentCharacter == 'g') {
                        state = "QifKung";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QifKung":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("KEYWORD", mgaNabasangLetra, line, Token.IFKUNG_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG iMod
                case "QiM":
                    if (currentCharacter == 'o') {
                        state = "QiMo";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiMo":
                    if (currentCharacter == 'd') {
                        state = "QiMod";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiMod":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("ModOp", mgaNabasangLetra, line, Token.MODULOOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG iPow
                case "QiP":
                    if (currentCharacter == 'o') {
                        state = "QiPo";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiPo":
                    if (currentCharacter == 'w') {
                        state = "QiPow";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiPow":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("POWOP", mgaNabasangLetra, line,Token.POWOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;

                    //CHECK KUNG iDiv
                case "QiD":
                    if (currentCharacter == 'i') {
                        state = "QiDi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiDi":
                    if (currentCharacter == 'v') {
                        state = "QiDiv";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiDiv":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("DivOp", mgaNabasangLetra, line, Token.DIVOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG iTimes
                case "QiT":
                    if (currentCharacter == 'i') {
                        state = "QiTi";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiTi":
                    if (currentCharacter == 'm') {
                        state = "QiTim";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";

                    }
                    continue;
                case "QiTim":
                    if (currentCharacter == 'e') {
                        state = "QiTime";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiTime":
                    if (currentCharacter == 's') {
                        state = "QiTimes";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiTimes":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("MultiOp", mgaNabasangLetra, line,Token.MULTOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG is
                case "Qis":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("ASSIGNOP", mgaNabasangLetra, line,Token.IS_KEYWORD);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG iADD
                case "QiA":
                    if (currentCharacter == 'd') {
                        state = "QiAd";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";

                    }
                    continue;
                case "QiAd":
                    if (currentCharacter == 'd') {
                        state = "QiAdd";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiAdd":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("addOp", mgaNabasangLetra, line,Token.ADDOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                    //CHECK KUNG iSub
                case "QiS":
                    if (currentCharacter == 'u') {
                        state = "QiSu";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";

                    }
                    continue;
                case "QiSu":
                    if (currentCharacter == 'b') {
                        state = "QiSub";
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "QiSub":
                    if (!isSymbol(currentCharacter) | !isNumeric(currentCharacter) | !isAlpha(currentCharacter)) {
                        return new Token("SUBOP", mgaNabasangLetra, line,Token.SUBOP);
                    } else {
                        state = "QidBody";
                    }
                    continue;
                case "Q=":
                    if (currentCharacter == '=') {
                        currentCharacter = read();
                        return new Token("Equal", "==", line,Token.ISEQUALOP);
                    } else {

                        return new Token("ERROR", "Invalid input: =", line, Token.T_UNKNOWN);
                    }
                    //if !=
                case "Q!":
                    if (currentCharacter == '=') {
                        currentCharacter = read();
                        return new Token("NotEqual", "!=", line, Token.NOTEQUALOP);
                    } else {
                        return new Token("ERROR", "Invalid input: !", line, Token.T_UNKNOWN);
                    }
                case "Q\"": // for String
                    if (currentCharacter == '"') {//pag nakita na ang quotation

                        currentCharacter = read();

                        return new Token("STRING", "\"" + mgaNabasangLetra + "\"", line, Token.STRING);
                    } else if (currentCharacter == '\n' || currentCharacter == EOF) {//pag end of file
                        currentCharacter = read();
                        return new Token("ERROR", "Invalid string literal", line,Token.T_UNKNOWN);
                    } else {//pag hindi pa quotations nakikita
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    }
                    continue;
                case "Q>": //for > or >=
                    if (currentCharacter == '=') {
                        return new Token("BooleanOp", ">=", line, Token.GREATERTHANOREQUAL);
                    } else {

                        return new Token("BooleanOp", ">", line,Token.GREATERTHAN);
                    }

                case "Q<": //for < or <=
                    if (currentCharacter == '=') {
                        return new Token("BooleanOp", "<=", line,Token.LESSTHANOREQUAL);
                    } else {
                        return new Token("BooleanOp", "<", line,Token.LESSTHAN);
                    }
                case "Q#":
                    if (currentCharacter == '\n') {
                        line++;//pag nakita na ang #
                        currentCharacter = read();
                        return new Token("Comment", "#" + mgaNabasangLetra, line - 1,Token.COMMENT);
                    } else {//pag hindi pa /n nakikita
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();
                    }
                    continue;
                case "Q@": // for Comments
                    if (currentCharacter == '@') {//pag nakita na ang quotation
                        currentCharacter = read();
                        return new Token("Comment", "@" + mgaNabasangLetra + "@",line, Token.COMMENT);
                    } else if (currentCharacter == EOF) {//pag end of file
                        currentCharacter = read();
                        return new Token("ERROR", "Invalid string literal", line,Token.T_UNKNOWN);
                    } else {//pag hindi pa quotations nakikita
                        if (currentCharacter == '\n') line++;
                        mgaNabasangLetra += currentCharacter;
                        currentCharacter = read();

                    }
                    continue;

            }
        }
    }
}
