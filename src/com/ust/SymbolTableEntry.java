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
    public int type;
    public boolean isUsed = false;

    public Object value;


    //pag wala pang data type na nailalagay
    public SymbolTableEntry(String id){
        this.id=id;
    }

    public SymbolTableEntry(String id, int type){
        this.id = id;
        this.type=type;
    }
    public SymbolTableEntry(String id, int type, Object value){
        this.id = id;
        this.type=type;
        this.value = value;
    }

    public String toString(){
        return "id: "+id+"\t\t value:"+value;
    }

}
