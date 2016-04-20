/*
 * JUDE ARDYN C. BISMONTE 
 * 2CSC-4
 * LAB EXERCISE 8
 */

import com.ust.TreeNode;

public class Tree extends Object {

    protected TreeNode root = null;
    private int height;
 

    public Tree() {}

    public void setRoot(TreeNode n)
    {
        root = n;
    }


/*
    public void computeLevels() {
        TreeNode node = root;
        Queue<BTNode> kyu = new Queue();
        if (node != null) {
            kyu.enqueue(node);
            node.level=1;
            while (!kyu.isEmpty()) 
            {
                node = kyu.dequeue();
                System.out.println("K="+node.getToken()+" L="+node.level);

                if (node.left != null) 
                {
                    kyu.enqueue(node.left);
                    node.left.level = node.level+1;
                    
                }
                if (node.right != null) 
                {
                    kyu.enqueue(node.right);
                    node.right.level = node.level+1;
                    
                }
            }
            height= node.level;
        }
    }*/
    /*
    public String toString() {
        BTNode node = root;
        return "HT=" + height +" ["+node.toString()+"]";
        
    }*/
    //trip ko lang tong nasa baba
    /*
    public String bottomLeavesCount() {
        BTNode node = root;
        Queue<BTNode> kyu = new Queue();
        int bottomCount=0;
        int count=0;

        if (node != null) 
        {
            kyu.enqueue(node);
            while (!kyu.isEmpty()) 
            {
                 node = kyu.dequeue();
                 count++;
     
                if(height==node.level)
                {
                        bottomCount++;
                }
                
                if (node.left != null) 
                {
                    kyu.enqueue(node.left);
                }
                if (node.right != null)
                {
                    kyu.enqueue(node.right);    
                }
            }
        }
         return "count "+ count+" BOTTOM LEAVES "+bottomCount;
    }
    
    

   
    
    public void preorderIter() {
        BTNode n = root;
        Stack<BTNode> stk = new Stack<BTNode>();
        if (n != null) {
            stk.push(n);
            while (!stk.isEmpty()) {
                n = (BTNode) stk.pop();
                System.out.print(n.getToken() + " ");

                if (n.right != null) {
                    stk.push(n.right);
                }

                if (n.left != null) {
                    stk.push(n.left);
                }
            }
        }
    }
    /*
    public void inorderIter() 
    {
    TreeNode n = root;
    Stack<TreeNode> stk = new Stack<TreeNode>();
    while (n != null) 
    {
        while (n != null) 
        {
            if (n.right != null) stk.push(n.right); //stack right child
            stk.push(n); //then stack itself
            n = n.left; 
        } //traverse left subtree
        n = stk.pop(); //no left child
        while (!stk.isEmpty() && (n.right == null)) 
        {   
            System.out.print(n.getToken() + " "); //visit it and all
            n = stk.pop(); 
        } //nodes w/o right child
        visit(n); //1st node w/ right child or last node
if (!stk.isEmpty()) n = stk.pop(); //traverse right subtree
else n = null; }}
    
    
    public void inorderRecursive(BTNode root)
    {
       
        if(root == null) return;
        inorderRecursive(root.left);
        System.out.print(root.getToken()+" ");
        inorderRecursive( root.right );
        
    }
    public void inorderIter()
    {
        // Inorder traversal:
        // Keep the nodes in the path that are waiting to be visited
        Stack s = new Stack(); 
        // The first node to be visited is the leftmost
        BTNode node = root;
        while (node != null)
        {
            s.push(node);
            node = node.left;
        }
        // Traverse the tree
        while (!s.isEmpty())    
        {
            // Visit the top node
            node = (BTNode)s.pop();
            System.out.print(node.getToken()+" ");
            // Find the next node
            
            if (node.right != null)
            {
                node = node.right;
                // The next node to be visited is the leftmost
                while (node != null)
                {
                    s.push(node);
                    node = node.left;
                }   
            }
        }
    
    }
    public void postorderIter() {
        BTNode n = root, t = root;
        Stack<BTNode> stk = new Stack<BTNode>();
        while (n != null) 
        {
            for (; n.left != null; n = n.left) 
            {
                stk.push(n); //stack left nodes except last
            }
            while (n != null && (n.right == null) || n.right == t) 
            {
                System.out.print(n.getToken() + " "); //visit if no right or has visited right
                t = n; //prepare to move up
                if (stk.isEmpty()) 
                {
                    return; //traversal completed
                }
                n = stk.pop();
            } //move up the tree
            stk.push(n); //remember n
            n = n.right;
        } //proceed tor right subtree
    }
*/
    
}
