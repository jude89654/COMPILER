package com.ust;

/**
 * Created by Jude on 5/13/2016.
 */
public class BTNode {

    protected String key;
    protected String value;
    protected int level;
    protected BTNode left, right;
    //protected int Da

    public BTNode(){
        this.key = null;
        this.value = null;
        this.left = this.right = null;
    }



    public BTNode(String i){
        this(i,null,null);
    }

    public BTNode(String i, BTNode l, BTNode r){
        key = i;
        left = l;
        right = r;
    }
    public void setValue(String val){

        value = val;
    }
    public String getValue(){
        return value;
    }

    public String getKey(){
        return key;
    }

    public BTNode getNextSibling(){
        return right;
    }

    public BTNode getFirstChild(){
        return left;
    }

    public void setNextSibling(BTNode r){
        right = r;
    }

    public void setFirstChild(BTNode l){
        left = l;
    }

    public String toString(){
        String output="";
        if(key == null && left == null && right == null){ //if there is no node
            output = "";
        }
        else if(key != null && left == null && right == null){ //if node is a leaf
            output = "{K=" + key + "}";
        }
        else if(key != null && left != null && right == null){ //if node has only left child
            output = "{K=" + key + " C=" + left + "}";
        }
        else if(key != null && left == null && right != null){ //if node has only right child
            output = "{K=" + key + " S=" + right + "}";
        }
        else if(key != null && left != null && right != null){ //if node has both left and right children
            output = "{K=" + key + " C=" + left + " S=" + right + "}";
        }
        else if(key == null && left != null && right != null){ //if node has null key but has children (shouldn't occur)
            output = "{K=" + key + " C=" + left + " S=" + right + "}";
        }
        return output;
    }
}
