package com.ust;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String inFile = "IfEnhanced.conyo";
        String outFile = "Sample.peaceOut";

        //try {
        //    inFile = getFile();


            parse(inFile);

            //scanner(inFile);

/*
        } catch (FileNotFoundException fnfe) {
            System.out.println("FILE NOT FOUND");
        } catch (InvalidFileException ife) {
            System.out.println("WRONG FILE EXTENSION");
        }
*/

    }

    public static void parse(String filename) {
        Syntaxer bungol = new Syntaxer(filename);
        bungol.program();

    }

    public static void scanner(String filename) {

        String outputfile = filename.replace(".conyo", ".PeaceOut");
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
            System.out.println("Output written in file: " + outputfile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFile() throws FileNotFoundException, InvalidFileException {
        JFileChooser chooser = new JFileChooser();

        String inFile = "";

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ALL CONYO FILES", "conyo");
        chooser.setFileFilter(filter);
        //.

        JFrame jude = new JFrame();
        int returnVal = chooser.showOpenDialog(jude);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            inFile = chooser.getSelectedFile().getAbsolutePath();
        }
        if (!inFile.endsWith(".conyo")) throw new InvalidFileException();

        return inFile;
    }
}


class InvalidFileException extends Exception {

    InvalidFileException(String message) {
        super(message);
    }

    InvalidFileException() {
        super();
    }

}
