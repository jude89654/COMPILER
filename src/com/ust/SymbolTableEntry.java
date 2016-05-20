package com.ust;

/**
 * Created by Jude on 5/14/2016.
 */
public class SymbolTableEntry {

    //para malaman kung gano kalalim sa scope
    public int level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //mga attributes ng entry
    public String id;
    public String type;
    public boolean isUsed = false;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object value;


    //pag wala pang data type na nailalagay
    public SymbolTableEntry(String id){
        this.id=id;
    }

    public SymbolTableEntry(String id, String type){
        this.id = id;
        this.type=type;
    }
    public SymbolTableEntry(String id, String type, Object value){
        this.id = id;
        this.type=type;
        this.value = value;
    }

    public String toString(){
        return "id: "+id+"\t\ttype:"+type+"\t\tvalue:"+value;
    }



}
