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


        inFile = getFile();


        parse(inFile);

        scanner(inFile);




    }

    public static void parse(String filename ){
        Syntaxer bungol = new Syntaxer(filename);
        bungol.program();

    }

    public static void scanner(String filename){

        String outputfile=filename.replace(".conyo",".PeaceOut");
        Lexer lexer = new Lexer(filename);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile));

            Token t;

            while ((t = lexer.nextToken()) != null) {
                writer.write(t.toString());
                writer.newLine();
            }

            writer.close();

            System.out.println("Done tokenizing file: " + filename);
            System.out.println("Output written in file: " +outputfile );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFile(){
        JFileChooser chooser = new JFileChooser();

        String inFile="";

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ALL CONYO FILES","conyo");
        chooser.setFileFilter(filter);
        chooser.changeToParentDirectory();

        JFrame jude = new JFrame();
        int returnVal = chooser.showOpenDialog(jude);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            inFile = chooser.getSelectedFile().getAbsolutePath();
        }
        return inFile;
    }
}
