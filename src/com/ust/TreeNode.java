package com.ust;/*
 * JUDE ARDYN C. BISMONTE
 * 2CSC-4
 * LAB EXERCISE 8
 */

import java.util.ArrayList;

public class TreeNode extends Object {

    protected String key;
    protected Token token;
    protected TreeNode left, right;
    protected ArrayList<TreeNode> children =  new ArrayList<>();
    protected int level;



    public TreeNode(Token token) {
        this.token = token;
    }

    public TreeNode(String key){
        this.key = key;
    }

    public TreeNode(Token token, Token[] tokens) {
        this.token = token;
        for(Token tempToken: tokens){
            children.add(new TreeNode(tempToken));
        }
    }

    public void addChild(TreeNode treeNode){
        children.add(treeNode);
    }
    public void addChild(String string){
        children.add(new TreeNode(string));
    }
    public void addChild(Token token){
        children.add(new TreeNode(token));
    }

    public void addChildren(TreeNode[] treeNodes){
        for(TreeNode treeNode: treeNodes){
            children.add(treeNode);
        }
    }



    public Token getToken() {
        return token;
    }

    public String toString() {
        String details="[K=";
        if(token!=null)
            details+=token.getLexeme();
        else
            details+=key;

        if(!children.isEmpty()){
            for (TreeNode treeNode : children) {
                //System.out.println(token.getLexeme());
                details += treeNode.toString();
            }
        } else {
            //details += "]";
        }
        details += "]";
        return details;
    }

    public static void main(String args[]){

        TreeNode a= new TreeNode("A");
        TreeNode b= new TreeNode(new Token("LOL","B",1,1));
        TreeNode c= new TreeNode(new Token("LOL","C",1,1));
        TreeNode d= new TreeNode(new Token("LOL","D",1,1));
        TreeNode x= new TreeNode("LOL");
        TreeNode jude = new TreeNode(new Token("LOL","LOL",1,1));
        jude.addChildren(new TreeNode[]{a,b});
        a.addChild(c);
        b.addChild(d);
        b.addChild(x);

        System.out.println(jude.toString());
    }
    
    /*

    public boolean delete(String pinapabura, TreeNode parent)
    {
            if (pinapabura.compareTo(token)<0) {
                  if (left != null)
                        return left.delete(pinapabura, this);
                  else
                        return false;
            } 
            else if (pinapabura.compareTo(token)>0)
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
                        token = left.maxValue();
                        left.delete(token, this);
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
                  return token;
            else
                  return right.maxValue();
      }
    */
}