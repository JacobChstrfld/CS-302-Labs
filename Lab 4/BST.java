import java.io.*;
import java.util.*;

public class BST
{
    /**
     *  Problem: Perform rotations on tree1 to make it equivalent to tree2.
     */
    public static void problem(BST tree1, BST tree2)
    {

        int[] order1 = tree1.getPreOrder();
        int k = order1.length;
        Node r = tree1.find(tree2.getRootKey());
        Node temp;
        ArrayList<Node> completed = new ArrayList<>();

        if(k >= 2) {
            while(tree1.root != r){
                if(r.parent.left == r && r.parent == tree1.root){
                    tree1.rotateR(r.parent);
                    tree1.root = r;
                }
                else if(r.parent.left == r)tree1.rotateR(r.parent);
                else if(r.parent.right == r && r.parent == tree1.root){
                    tree1.rotateL(r.parent);
                    tree1.root = r;
                }
                else if(r.parent.right == r) tree1.rotateL(r.parent);
            }
            if(tree1.root.left != null && tree2.root.left != null){
                tree1.root = tree1.root.left;
                tree2.root = tree2.root.left;
                problem(tree1, tree2);
            }
           if(tree1.root.right != null && tree2.root.right != null){
                tree1.root = tree1.root.right;
                tree2.root = tree2.root.right;
                problem(tree1, tree2);
            }

            temp = tree1.root;
            while (tree1.root.parent != null) tree1.root = tree1.root.parent;
            order1 = tree1.getPreOrder();
            k = order1.length;
            tree1.root = temp;

            if(completed.size() == k - 1) {
                while (tree1.root.parent != null) tree1.root = tree1.root.parent;
                while (tree2.root.parent != null) tree2.root = tree2.root.parent;
            }
        }
        if(tree1.root.parent != null && tree2.root.parent != null) {
            completed.add(tree1.root);
            tree1.root = tree1.root.parent;
            tree2.root = tree2.root.parent;
        }
    }


    // ---------------------------------------------------------------------
    // Do not change any of the code below!

    private class Node
    {
        public Node left = null;
        public Node right = null;
        public Node parent = null;

        public int key;

        public Node(int key)
        {
            this.key = key;
        }
    }

    private Node root = null;

    public int getRootKey()
    {
        return root.key;
    }

    private Node find(int key)
    {
        for (Node cur = root; cur != null;)
        {
            if (key < cur.key)
            {
                cur = cur.left;
            }
            else if (key == cur.key)
            {
                return cur;
            }
            else // key > cur.key
            {
                cur = cur.right;
            }
        }

        return null;
    }

    //     x            y
    //    / \          / \
    //   a   y   =>   x   c
    //      / \      / \
    //     b   c    a   b
    private void rotateL(Node xNode)
    {
        Node xPar = xNode.parent;
        boolean isRoot = xPar == null;
        boolean isLChild = !isRoot && xPar.left == xNode;

        Node yNode = xNode.right;
        Node beta = yNode.left;

        if (isRoot) root = yNode;
        else if (isLChild) xPar.left = yNode;
        else xPar.right = yNode;

        yNode.parent = xPar;
        yNode.left = xNode;

        xNode.parent = yNode;
        xNode.right = beta;

        if (beta != null) beta.parent = xNode;
    }

    //     y        x
    //    / \      / \
    //   x   c => a   y
    //  / \          / \
    // a   b        b   c
    private void rotateR(Node yNode)
    {
        Node yPar = yNode.parent;
        boolean isRoot = yPar == null;
        boolean isLChild = !isRoot && yPar.left == yNode;

        Node xNode = yNode.left;
        Node beta = xNode.right;

        if (isRoot) root = xNode;
        else if (isLChild) yPar.left = xNode;
        else yPar.right = xNode;

        xNode.parent = yPar;
        xNode.right = yNode;

        yNode.parent = xNode;
        yNode.left = beta;

        if (beta != null) beta.parent = yNode;
    }

    public void insert(int key)
    {
        if (root == null)
        {
            root = new Node(key);
            return;
        }

        Node par = null;

        for (Node node = root; node != null;)
        {
            par = node;

            if (key < node.key)
            {
                node = node.left;
            }
            else if (key > node.key)
            {
                node = node.right;
            }
            else // key == node.key
            {
                // Nothing to do, because no value to update.
                return;
            }
        }

        // Create node and set pointers.
        Node newNode = new Node(key);
        newNode.parent = par;

        if (key < par.key) par.left = newNode;
        else par.right = newNode;
    }

    public int[] getInOrder()
    {
        if (root == null) return new int[] { };

        Stack<Node> stack = new Stack<Node>();
        ArrayList<Integer> orderList = new ArrayList<Integer>();

        for (Node node = root;;)
        {
            if (node == null)
            {
                if (stack.empty()) break;

                node = stack.pop();
                orderList.add(node.key);
                node = node.right;
            }
            else
            {
                stack.push(node);
                node = node.left;
            }
        }

        int[] order = new int[orderList.size()];
        for (int i = 0; i < order.length; i++)
        {
            order[i] = orderList.get(i);
        }

        return order;
    }

    public int[] getPreOrder()
    {
        if (root == null) return new int[] { };

        Stack<Node> stack = new Stack<Node>();
        ArrayList<Integer> orderList = new ArrayList<Integer>();

        for (Node node = root;;)
        {
            if (node == null)
            {
                if (stack.empty()) break;

                node = stack.pop();
                node = node.right;
            }
            else
            {
                orderList.add(node.key);
                stack.push(node);
                node = node.left;
            }
        }

        int[] order = new int[orderList.size()];
        for (int i = 0; i < order.length; i++)
        {
            order[i] = orderList.get(i);
        }

        return order;
    }
}
