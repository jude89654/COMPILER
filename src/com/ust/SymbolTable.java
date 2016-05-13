package com.ust;

import java.util.LinkedHashMap;

/**
 * Created by Jude on 5/13/2016.
 */

public class SymbolTable {
    Identifier ide = new Identifier();
    static int counter = 0;
    private static LinkedHashMap<String, Identifier> hashTable = new LinkedHashMap<>();


    public static boolean checkIdentifier(String name, Identifier id){

        return hashTable.containsKey(name);

    }
    public static Identifier getValue(String name){
        return hashTable.get(name);
    }

    public static void addToTable(String name, Identifier id){
        counter++;
        id.setIndex(counter);
        hashTable.put(name,  id);

    }



}