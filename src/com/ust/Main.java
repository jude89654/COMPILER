package com.ust;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
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
        //String filename;

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ALL CONYO FILES","conyo");
        chooser.setFileFilter(filter);
        chooser.changeToParentDirectory();


        JFrame jude = new JFrame();
        int returnVal = chooser.showOpenDialog(jude);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            inFile = chooser.getSelectedFile().getAbsolutePath();
        }



            parse("Jude.conyo");

           // scanner(inFile,"Jude.peaceOut");




    }

    public static void parse(String filename ){
        Syntaxer bungol = new Syntaxer(filename);
        bungol.program();

    }

    public static void scanner(String filename, String outputFile){

        Lexer lexer = new Lexer(filename);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            Token t;

            while ((t = lexer.nextToken()) != null) {
                writer.write(t.toString());
                writer.newLine();
            }

            writer.close();

            System.out.println("Done tokenizing file: " + filename);
            System.out.println("Output written in file: " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
