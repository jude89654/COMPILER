package com.ust;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jude on 5/13/2016.
 */

public class SymbolTable {
    Identifier ide = new Identifier();
    static int counter = 0;
    private static LinkedHashMap<String, SymbolTableEntry> linkedHashMap = new LinkedHashMap<>();


    public static boolean checkIdentifier(Token token) {
        return linkedHashMap.containsKey(token.getLexeme());
    }


    public static boolean checkIdentifier(String name) {

        return linkedHashMap.containsKey(name);

    }

    public static SymbolTableEntry getValue(String name) {

        SymbolTableEntry entry = linkedHashMap.get(name);
        entry.isUsed=true;
        addToTable(entry);
        return entry;
    }

    public static SymbolTableEntry getValue(Token token) {
        return getValue(token.getLexeme());
    }

    public static void addToTable(SymbolTableEntry symbolTableEntry) {
        linkedHashMap.put(symbolTableEntry.getId(), symbolTableEntry);
    }

    public static void addToTable(String name, SymbolTableEntry symbol) {
        counter++;
        //id.setIndex(counter);
        linkedHashMap.put(name, symbol);
    }

    public static void addToTable(Token token) {
        counter++;
        //TODO linkedHashMap.put(token.getLexeme(),token.getTokenClass());

    }

    public static void showTable() {

        System.out.println("----------------SYMBOL TABLE----------------");
        Iterator<Map.Entry<String, SymbolTableEntry>> itr = linkedHashMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, SymbolTableEntry> entry = itr.next();

            SymbolTableEntry list = entry.getValue();
            System.out.println(list.toString());
        }
    }


}