package jsjf;

/**
 * SimulLinkBinaryTreeNode represents a node in a simulated link
 * binary tree with a left and right child.
 * 
 * @author Java Foundations
 * @version 4.0
 */
public class SimulLinkBinaryTreeNode
{
    protected Integer arrIndex;
    protected SimulLinkBinaryTreeNode left, right;

    /**
     * Creates a new tree node with the specified data.
     *
     * @param index the array index of the element that will become a part of the new tree node
    */
    public SimulLinkBinaryTreeNode(Integer index) 
    {
        arrIndex = index;
        left = null;
        right = null;
    }

    /**
     * Creates a new tree node with the specified data.
     *
     * @param index the array index of the element that will become a part of the new tree node
     * @param left the tree that will be the left subtree of this node
     * @param right the tree that will be the right subtree of this node
     */
    public <T> SimulLinkBinaryTreeNode(Integer index, SimulLinkedBinaryTree<T> left, SimulLinkedBinaryTree<T> right) 
    {
        arrIndex = index;
        if (left == null)
            this.left = null;
        else
            this.left = left.getRootNode();
        
         if (right == null)
            this.right = null;
        else
            this.right = right.getRootNode();
    }
    
    /**
     * Returns the number of non-null children of this node.
     *
     * @return the integer number of non-null children of this node 
     */
    public int numChildren() 
    {
        int children = 0;

        if (left != null)
            children = 1 + left.numChildren();

        if (right != null)
            children = children + 1 + right.numChildren();

        return children;
    }
    
    /**
     * Return the index of the element at this node.
     *
     * @return the index of the element stored at this node
     */
    public Integer getIndex() 
    {
        return arrIndex;
    }

    /**
     * Sets the array index of the element of this node
     *
     * @param node the array index of the element stored at this node
     */
    public void setIndex(Integer index) 
    {
        arrIndex = index;
    }

    /**
     * Return the right child of this node.
     *
     * @return the right child of this node
     */
    public SimulLinkBinaryTreeNode getRight() 
    {
        return right;
    }
    
    /**
     * Sets the right child of this node.
     *
     * @param node the right child of this node
     */
    public void setRight(SimulLinkBinaryTreeNode node) 
    {
        right = node;
    }
    
    /**
     * Return the left child of this node.
     *
     * @return the left child of the node
     */
    public SimulLinkBinaryTreeNode getLeft() 
    {
        return left;
    }
    
    /**
     * Sets the left child of this node.
     *
     * @param node the left child of this node
     */
    public void setLeft(SimulLinkBinaryTreeNode node) 
    {
        left = node;
    }
}
