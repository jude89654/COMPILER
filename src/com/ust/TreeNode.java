package com.ust;/*
 * JUDE ARDYN C. BISMONTE
 * 2CSC-4
 * LAB EXERCISE 8
 */

import java.util.ArrayList;

public class TreeNode extends Object {

    protected Token key;
    protected TreeNode left, right;
    protected ArrayList<TreeNode> children =  new ArrayList<>();
    protected int level;



    public TreeNode(Token token) {
        key = token;
    }

    public TreeNode(Token token, Token[] tokens) {
        key = token;
        for(Token tempToken: tokens){
            children.add(new TreeNode(tempToken));
        }
    }

    public void addChild(TreeNode treeNode){
        children.add(treeNode);
    }

    public void addChildren(TreeNode[] treeNodes){
        for(TreeNode treeNode: treeNodes){
            children.add(treeNode);
        }
    }



    public Token getKey() {
        return key;
    }

    public String toString() {
        String details="[K="+key.getLexeme();
        if(!children.isEmpty()){
            for (TreeNode treeNode : children) {
                System.out.println(key.getLexeme());
                details += treeNode.toString();
            }
        } else {
            //details += "]";
        }
        details += "]";
        return details;
    }

    public static void main(String args[]){

        TreeNode a= new TreeNode(new Token("LOL","A",1,1));
        TreeNode b= new TreeNode(new Token("LOL","B",1,1));
        TreeNode c= new TreeNode(new Token("LOL","C",1,1));
        TreeNode d= new TreeNode(new Token("LOL","D",1,1));
        TreeNode jude = new TreeNode(new Token("LOL","LOL",1,1));
        jude.addChildren(new TreeNode[]{a,b});
        a.addChild(c);
        c.addChild(d);
        System.out.println(jude.toString());
    }
    
    /*

    public boolean delete(String pinapabura, TreeNode parent)
    {
            if (pinapabura.compareTo(key)<0) {
                  if (left != null)
                        return left.delete(pinapabura, this);
                  else
                        return false;
            } 
            else if (pinapabura.compareTo(key)>0) 
            {
                  if (right != null)
                        return right.delete(pinapabura, this);
                  else
                        return false;
            } 
            else 
            {
                  if (left != null && right != null) 
                  {
                        key = left.maxValue();
                        left.delete(key, this);
                  } 
                  else if (parent.left == this) 
                  {
                        parent.left = (left != null) ? left : right;
                  } else if (parent.right == this) {
                        parent.right = (left != null) ? left : right;
                  }
                  return true;
            }
      }
     public String maxValue() {
            if (right == null)
                  return key;
            else
                  return right.maxValue();
      }
    */
}