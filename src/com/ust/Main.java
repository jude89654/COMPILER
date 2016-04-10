package com.ust;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inFile = "Sample.conyo";
        String outFile = "Sample.peaceOut";

        if (args.length > 1) {
            inFile = args[0];
            outFile = args[1];
        }


        if (inFile.endsWith(".conyo")) {

            Lexer lexer = new Lexer(inFile);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

                Token t;

                while ((t = lexer.nextToken()) != null) {
                    writer.write(t.toString());
                    writer.newLine();
                }

                writer.close();

                System.out.println("Done tokenizing file: " + inFile);
                System.out.println("Output written in file: " + outFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("INVALID FILENAME");
        }
    }

    public void parse(){

    }
}
