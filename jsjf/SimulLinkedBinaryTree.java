package jsjf;

import java.util.*;
import jsjf.exceptions.*;

/**
 * SimulLinkedBinaryTree implements the BinaryTreeADT interface
 * 
 * @author Java Foundations
 * @version 4.0
 */
public class SimulLinkedBinaryTree<T> implements BinaryTreeADT<T>, Iterable<T>
{
    protected SimulLinkBinaryTreeNode root; 
    protected int modCount;

    private static final int DEFAULT_CAPACITY = 100;
    T[] simulLinkArr;
    Integer totalElements;

    /**
     * Creates an empty binary tree.
     */
    public SimulLinkedBinaryTree() 
    {
        simulLinkArr = (T[])(new Object[DEFAULT_CAPACITY]);
        totalElements = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the binary tree
     */
    public SimulLinkedBinaryTree(T element) 
    {
        simulLinkArr = (T[])(new Object[DEFAULT_CAPACITY]);
        totalElements = 0;
        root = new SimulLinkBinaryTreeNode(totalElements);
        simulLinkArr[totalElements] = element;
        totalElements++;
    }
    
    /**
     * Creates a binary tree with the specified element as its root and the 
     * given trees as its left child and right child
     *
     * @param element the element that will become the root of the binary tree
     * @param left the left subtree of this tree
     * @param right the right subtree of this tree
     */
    public SimulLinkedBinaryTree(T element, SimulLinkedBinaryTree<T> left, 
                            SimulLinkedBinaryTree<T> right) 
    {
        root = new SimulLinkBinaryTreeNode(totalElements);
        simulLinkArr[totalElements] = element;
        totalElements++;
        root.setLeft(left.root);
        root.setRight(right.root);
    }
    
    /**
     * Returns a reference to the element at the root
     *
     * @return a reference to the specified target
     * @throws EmptyCollectionException if the tree is empty
     */
    public T getRootElement() throws EmptyCollectionException
    {
        return simulLinkArr[root.getIndex()];
    }
    
    /**
     * Returns a reference to the node at the root
     *
     * @return a reference to the specified node
     * @throws EmptyCollectionException if the tree is empty
     */
    protected SimulLinkBinaryTreeNode getRootNode() throws EmptyCollectionException
    {
        return root;
    }
    
    /**
     * Returns the left subtree of the root of this tree.
     *
     * @return a link to the left subtree fo the tree
     */
    public SimulLinkedBinaryTree<T> getLeft()
    {
        SimulLinkedBinaryTree<T> result = new SimulLinkedBinaryTree<T>();
        result.root = root.getLeft();

        return result;
    }
    
    /**
     * Returns the right subtree of the root of this tree.
     *
     * @return a link to the right subtree of the tree
     */
    public SimulLinkedBinaryTree<T> getRight()
    {
        SimulLinkedBinaryTree<T> result = new SimulLinkedBinaryTree<T>();
        result.root = root.getRight();

        return result;
    }
    
    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty, false otherwise
     */
    public boolean isEmpty() 
    {
        return (root == null);
    }

    /**
     * Returns the integer size of this tree.
     *
     * @return the integer size of the tree
     */
    public int size() 
    {
        if (root == null)
           return 0;
        else
           return 1 + root.numChildren();
    }
    
    /**
     * Returns the height of this tree.
     *
     * @return the height of the tree
     */
     public int getHeight()
    {
		  if(root == null)
			  return 0;
  		  else
		  	  return height(root);
    }
    
    /**
     * Returns the height of the specified node.
     *
     * @param node the node from which to calculate the height
     * @return the height of the tree
     */
    private int height(SimulLinkBinaryTreeNode node) 
    {
       if(node == null)
          return -1;
       else
          return 1 + Math.max(height(node.getLeft()),
                              height(node.getRight()));
    }
    
    /**
     * Returns true if this tree contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in this tree
     * @return true if the element in is this tree, false otherwise
     */
    public boolean contains(T targetElement) 
    {
		 try
		 {
			 find(targetElement);
		 }
		 catch(ElementNotFoundException Enfe)
		 {
			 return false;
		 }
		
		 return true;
    }
    
    /**    
     * Returns a reference to the specified target element if it is
     * found in this binary tree.  Throws a ElementNotFoundException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if the element is not in the tree
     */
    public T find(T targetElement) throws ElementNotFoundException
    {
        SimulLinkBinaryTreeNode current = findNode(targetElement, root);
        
        if (current == null)
            throw new ElementNotFoundException("SimulLinkedBinaryTree");
        
        return (simulLinkArr[current.getIndex()]);
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private SimulLinkBinaryTreeNode findNode(T targetElement, 
                                        SimulLinkBinaryTreeNode next)
    {
        if (next == null)
            return null;
        
        if (simulLinkArr[next.getIndex()].equals(targetElement))
            return next;
        
        SimulLinkBinaryTreeNode temp = findNode(targetElement, next.getLeft());
        
        if (temp == null)
            temp = findNode(targetElement, next.getRight());
        
        return temp;
    }
    
    /**
     * Returns a string representation of this binary tree showing
     * the nodes in an inorder fashion.
     *
     * @return a string representation of this binary tree
     */
    public String toString() 
    {
		 String result = "";
       Iterator<T> iter = iterator();
       while(iter.hasNext())
          result += iter.next() + "\n";
       return result;
    }

    /**
     * Returns an iterator over the elements in this tree using the 
     * iteratorInOrder method
     *
     * @return an in order iterator over this binary tree
     */
    public Iterator<T> iterator()
    {
        return iteratorInOrder();
    }
    
    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with
     * the root.
     *
     * @return an in order iterator over this binary tree
     */
    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(root, tempList);
        
        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inOrder(SimulLinkBinaryTreeNode node, 
                           ArrayUnorderedList<T> tempList) 
    {
        if (node != null)
        {
            inOrder(node.getLeft(), tempList);
            tempList.addToRear(simulLinkArr[node.getIndex()]);
            inOrder(node.getRight(), tempList);
        }
    }

    /**
     * Performs an preorder traversal on this binary tree by calling 
     * an overloaded, recursive preorder method that starts with
     * the root.
     *
     * @return a pre order iterator over this tree
     */
    public Iterator<T> iteratorPreOrder() 
    {
       ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
       preOrder(root, tempList);
       return tempList.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void preOrder(SimulLinkBinaryTreeNode node, 
                            ArrayUnorderedList<T> tempList) 
    {
       if (node != null) 
   	 {
          tempList.addToRear(simulLinkArr[node.getIndex()]);
          preOrder(node.getLeft(), tempList);
          preOrder(node.getRight(), tempList);
       }
    }

    /**
     * Performs an postorder traversal on this binary tree by calling
     * an overloaded, recursive postorder method that starts
     * with the root.
     *
     * @return a post order iterator over this tree
     */
    public Iterator<T> iteratorPostOrder() 
    {
       ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
       postOrder(root, tempList);
       return tempList.iterator();
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postOrder(SimulLinkBinaryTreeNode node, 
                             ArrayUnorderedList<T> tempList) 
    {
       if (node != null) 
	    {
          postOrder(node.getLeft(), tempList);
          postOrder(node.getRight(), tempList);
          tempList.addToRear(simulLinkArr[node.getIndex()]);
       }
    }

    /**
     * Performs a levelorder traversal on this binary tree, using a
     * templist.
     *
     * @return a levelorder iterator over this binary tree
     */
    public Iterator<T> iteratorLevelOrder() 
    {
        ArrayUnorderedList<SimulLinkBinaryTreeNode> nodes = 
                              new ArrayUnorderedList<SimulLinkBinaryTreeNode>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        SimulLinkBinaryTreeNode current;

        nodes.addToRear(root);
        
        while (!nodes.isEmpty()) 
        {
            current = nodes.removeFirst();
            
            if (current != null)
            {
                tempList.addToRear(simulLinkArr[current.getIndex()]);
                if (current.getLeft() != null)
                    nodes.addToRear(current.getLeft());
                if (current.getRight() != null)
                    nodes.addToRear(current.getRight());
            }
            else
                tempList.addToRear(null);
        }
        
        return new TreeIterator(tempList.iterator());
    }
    
    /**
     * Inner class to represent an iterator over the elements of this tree
     */
    private class TreeIterator implements Iterator<T>
    {
        private int expectedModCount;
        private Iterator<T> iter;
        
        /**
         * Sets up this iterator using the specified iterator.
         *
         * @param iter the list iterator created by a tree traversal
         */
        public TreeIterator(Iterator<T> iter)
        {
            this.iter = iter;
            expectedModCount = modCount;
        }
        
        /**
         * Returns true if this iterator has at least one more element
         * to deliver in the iteration.
         *
         * @return  true if this iterator has at least one more element to deliver
         *          in the iteration
         * @throws  ConcurrentModificationException if the collection has changed
         *          while the iterator is in use
         */
        public boolean hasNext() throws ConcurrentModificationException
        {
            if (!(modCount == expectedModCount))
                throw new ConcurrentModificationException();
            
            return (iter.hasNext());
        }
        
        /**
         * Returns the next element in the iteration. If there are no
         * more elements in this iteration, a NoSuchElementException is
         * thrown.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iterator is empty
         */
        public T next() throws NoSuchElementException
        {
            if (hasNext())
                return (iter.next());
            else 
                throw new NoSuchElementException();
        }
        
        /**
         * The remove operation is not supported.
         * 
         * @throws UnsupportedOperationException if the remove operation is called
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
