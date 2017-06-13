package jsjf;

import jsjf.exceptions.*;
import jsjf.*;

/**
 * SimulLinkedBinarySearchTree implements the BinarySearchTreeADT interface 
 * with links.
 * 
 * @author Java Foundations
 * @version 4.0
 */
public class SimulLinkedBinarySearchTree<T> extends SimulLinkedBinaryTree<T>
                                        implements BinarySearchTreeADT<T>
{

    /**
     * Creates an empty binary search tree.
     */
    public SimulLinkedBinarySearchTree() 
    {
        super();
    }
    
    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new binary
     *        search tree
     */
    public SimulLinkedBinarySearchTree(T element) 
    {
        super(element);
        
        if (!(element instanceof Comparable))
            throw new NonComparableElementException("SimulLinkedBinarySearchTree");
    }


    /**
     * Add an element to the array that holds
     * the tree elements.
     */
    private Integer addToArray(T element)
    {
        Integer index = totalElements;

        // Check the array for a free spot
        Integer i = 0;
        while(i<totalElements&&index==totalElements)
        {
           if(simulLinkArr[i]==null)
              index = i;
           i++;
        }
        
        // Expand array capacity if it is full
        if (index == simulLinkArr.length) 
            expandCapacity();

        // Assign the element to array
        simulLinkArr[index] = element;
        if(index==totalElements)
           totalElements++;

        return index;
    }
    /**
     * Increases the capacity of the array that holds
     * the tree elements.
     */
    private void expandCapacity()
    {
        T[] tempArr = (T[])(new Object[simulLinkArr.length*2]);

        for (int i=0; i < simulLinkArr.length; i++)
            tempArr[i] = simulLinkArr[i];

        simulLinkArr = tempArr;
    }
    /**
     * Remove an element from the array that holds
     * the tree elements.
     */
    private void removeFromArray(Integer index)
    {
System.out.print("Removing " + simulLinkArr[index].toString() + " from position ");System.out.println(index);
        simulLinkArr[index] = null;
    }

    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its natural order.  Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    public void addElement(T element) 
    {
        if (!(element instanceof Comparable))
            throw new NonComparableElementException("SimulLinkedBinarySearchTree");

        Comparable<T> comparableElement = (Comparable<T>)element;

        if (isEmpty())
        {
            root = new SimulLinkBinaryTreeNode(addToArray(element));
        }
        else 
        {
            if (comparableElement.compareTo(simulLinkArr[root.getIndex()]) < 0)
            {
                if (root.getLeft() == null)
                {
                    this.getRootNode().setLeft(new SimulLinkBinaryTreeNode(addToArray(element)));
                }
                else
                    addElement(element, root.getLeft());
            }
            else
            {
                if (root.getRight() == null)
                {
                    this.getRootNode().setRight(new SimulLinkBinaryTreeNode(addToArray(element)));
                }
                else
                    addElement(element, root.getRight());
            }
        }
        modCount++;
    }
    
    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its natural order.  Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    private void addElement(T element, SimulLinkBinaryTreeNode node) 
    {
        Comparable<T> comparableElement = (Comparable<T>)element;
        
        if (comparableElement.compareTo(simulLinkArr[node.getIndex()]) < 0)
        {
            if (node.getLeft() == null)
            {
                node.setLeft(new SimulLinkBinaryTreeNode(addToArray(element)));
            }
            else
                addElement(element, node.getLeft());
        }
        else
        {
            if (node.getRight() == null)
            {
                node.setRight(new SimulLinkBinaryTreeNode(addToArray(element)));
            }
            else
                addElement(element, node.getRight());
        }
    }
        
        
    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it.  Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if the target element is not found
     */
    public T removeElement(T targetElement)
                                  throws ElementNotFoundException 
    {
        T result = null;

        if (isEmpty())
            throw new ElementNotFoundException("SimulLinkedBinarySearchTree");
        else
        {
            SimulLinkBinaryTreeNode parent = null;
            if (((Comparable<T>)targetElement).equals(simulLinkArr[root.arrIndex]))
            {
                result = simulLinkArr[root.arrIndex];
                removeFromArray(root.arrIndex);
                SimulLinkBinaryTreeNode temp = replacement(root);
                if (temp == null)
                    root = null;
                else 
                {
                    root.setIndex(temp.arrIndex);
                    root.setRight(temp.right);
                    root.setLeft(temp.left);
                }

                modCount--;
            }
            else 
            {                
                parent = root;
                if (((Comparable)targetElement).compareTo(simulLinkArr[root.arrIndex]) < 0)
                    result = removeElement(targetElement, root.getLeft(), parent);
                else
                    result = removeElement(targetElement, root.getRight(), parent);
            }
        }
        
        return result;
    }
                
    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it.  Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @param node the node from which to search
     * @param parent the parent of the node from which to search
     * @throws ElementNotFoundException if the target element is not found
     */
    private T removeElement(T targetElement, SimulLinkBinaryTreeNode node, SimulLinkBinaryTreeNode parent)
    throws ElementNotFoundException 
    {
        T result = null;
        
        if (node == null)
            throw new ElementNotFoundException("SimulLinkedBinarySearchTree");
        else
        {
            if (((Comparable<T>)targetElement).equals(simulLinkArr[node.arrIndex])) 
            {
                result = simulLinkArr[node.arrIndex];
                removeFromArray(node.arrIndex);
                SimulLinkBinaryTreeNode temp = replacement(node);
                if (parent.right == node)
                    parent.right = temp;
                else 
                    parent.left = temp;

                modCount--;
            }
            else 
            {                
                parent = node;
                if (((Comparable)targetElement).compareTo(simulLinkArr[node.arrIndex]) < 0)
                    result = removeElement(targetElement, node.getLeft(), parent);
                else
                    result = removeElement(targetElement, node.getRight(), parent);
            }
        }
        
        return result;
    }
    
    /**
     * Returns a reference to a node that will replace the one
     * specified for removal.  In the case where the removed node has 
     * two children, the inorder successor is used as its replacement.
     *
     * @param node the node to be removed
     * @return a reference to the replacing node
     */
    private SimulLinkBinaryTreeNode replacement(SimulLinkBinaryTreeNode node) 
    {
        SimulLinkBinaryTreeNode result = null;
        
        if ((node.left == null) && (node.right == null))
            result = null;
        
        else if ((node.left != null) && (node.right == null))
            result = node.left;
        
        else if ((node.left == null) && (node.right != null))
            result = node.right;
        
        else
        {
            SimulLinkBinaryTreeNode current = node.right;
            SimulLinkBinaryTreeNode parent = node;
            
            while (current.left != null)
            {
                parent = current;
                current = current.left;
            }
            
            current.left = node.left;
            if (node.right != current)
            {
                parent.left = current.right;
                current.right = node.right;
            }
            
            result = current;
        }
        
        return result;
    }
    
    /**
     * Removes elements that match the specified target element from 
     * the binary search tree. Throws a ElementNotFoundException if 
     * the sepcified target element is not found in this tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if the target element is not found
     */
    public void removeAllOccurrences(T targetElement)
                   throws ElementNotFoundException 
    {
        removeElement(targetElement);
        
        try
        {
            while (contains((T)targetElement))
                removeElement(targetElement);
        }
        
        catch (Exception ElementNotFoundException)
        {
        }
    }

    /**
     * Removes the node with the least value from the binary search
     * tree and returns a reference to its element.  Throws an
     * EmptyCollectionException if this tree is empty. 
     *
     * @return a reference to the node with the least value
     * @throws EmptyCollectionException if the tree is empty
     */
    public T removeMin() throws EmptyCollectionException 
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
        else 
        {
            if (root.left == null) 
            {
                result = simulLinkArr[root.arrIndex];
                removeFromArray(root.arrIndex);
                root = root.right;
            }
            else 
            {
                SimulLinkBinaryTreeNode parent = root;
                SimulLinkBinaryTreeNode current = root.left;
                while (current.left != null) 
                {
                    parent = current;
                    current = current.left;
                }
                result = simulLinkArr[current.arrIndex];
                removeFromArray(current.arrIndex);
                parent.left = current.right;
            }

            modCount--;
        }
 
        return result;
    }

    /**
     * Removes the node with the highest value from the binary
     * search tree and returns a reference to its element.  Throws an
     * EmptyCollectionException if this tree is empty. 
     *
     * @return a reference to the node with the highest value
     * @throws EmptyCollectionException if the tree is empty
     */
    public T removeMax() throws EmptyCollectionException 
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
        else 
        {
            if (root.right == null) 
            {
                result = simulLinkArr[root.arrIndex];
                removeFromArray(root.arrIndex);
                root = root.left;
            }
            else 
            {
                SimulLinkBinaryTreeNode parent = root;
                SimulLinkBinaryTreeNode current = root.right;
                while (current.right != null) 
                {
                    parent = current;
                    current = current.right;
                }
                result = simulLinkArr[current.arrIndex];
                removeFromArray(current.arrIndex);
                parent.right = current.left;
            }

            modCount--;
        }
 
        return result;
    }

    /**
     * Returns the element with the least value in the binary search
     * tree. It does not remove the node from the binary search tree. 
     * Throws an EmptyCollectionException if this tree is empty.
     *
     * @return the element with the least value
     * @throws EmptyCollectionException if the tree is empty
     */
    public T findMin() throws EmptyCollectionException 
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
        else 
        {
            if (root.left == null) 
            {
                result = simulLinkArr[root.arrIndex];
            }
            else 
            {
                SimulLinkBinaryTreeNode parent = root;
                SimulLinkBinaryTreeNode current = root.left;
                while (current.left != null) 
                {
                    parent = current;
                    current = current.left;
                }
                result = simulLinkArr[current.arrIndex];
            }

        }
 
        return result;
    }

    /**
     * Returns the element with the highest value in the binary
     * search tree.  It does not remove the node from the binary
     * search tree.  Throws an EmptyCollectionException if this 
     * tree is empty.
     *
     * @return the element with the highest value
     * @throws EmptyCollectionException if the tree is empty
     */
    public T findMax() throws EmptyCollectionException 
    {
        T result = null;

        if (isEmpty())
            throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
        else 
        {
            if (root.right == null) 
            {
                result = simulLinkArr[root.arrIndex];
            }
            else 
            {
                SimulLinkBinaryTreeNode parent = root;
                SimulLinkBinaryTreeNode current = root.right;
                while (current.right != null) 
                {
                    parent = current;
                    current = current.right;
                }
                result = simulLinkArr[current.arrIndex];
            }

        }
 
        return result;
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
     * found in the binary tree.  Throws a NoSuchElementException if
     * the specified target element is not found in this tree.
     *
     * @param targetElement the element being sough in the binary tree
     * @throws ElementNotFoundException if the target element is not found
     */
    public T find(T targetElement) throws ElementNotFoundException 
    {
      SimulLinkBinaryTreeNode current = findNode(targetElement, root);
	  
      if (current == null)
         throw new ElementNotFoundException("SimulLinkedBinarySearchTree");

      return simulLinkArr[current.getIndex()];
    }
    /**
     * Returns a reference to the specified target element if it is
     * found in this tree.  
     *
     * @param targetElement the element being sought in the tree
     * @param next the tree node to begin searching on
     */
      private SimulLinkBinaryTreeNode findNode(T targetElement, SimulLinkBinaryTreeNode next) 
      {
        if (next == null)
            return null;

        //if (simulLinkArr[next.getIndex()].equals(targetElement))
        //    return next;
        
        // Overriding the equals method in the Procedure class
        // didn't seem to affect the use of the equals method here, so I replaced it
        // with a compareTo method because it is working perfectly
            Comparable<T> comparableElement = (Comparable<T>)targetElement;
            if(comparableElement.compareTo(simulLinkArr[next.getIndex()]) == 0)
                return next;
        
        SimulLinkBinaryTreeNode temp = findNode(targetElement, next.getLeft());
        
        if (temp == null)
            temp = findNode(targetElement, next.getRight());
        
        return temp;
      }
    
    /**
     * Returns the left subtree of the root of this tree.
     *
     * @return a link to the left subtree fo the tree
     */
    public SimulLinkedBinarySearchTree<T> getLeft()
    {
        if (root == null)
           throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
            
        SimulLinkedBinarySearchTree<T> result = new SimulLinkedBinarySearchTree<T>();
        result.root = root.getLeft();

        return result;
    }
    
    /**
     * Returns the right subtree of the root of this tree.
     *
     * @return a link to the right subtree of the tree
     */
    public SimulLinkedBinarySearchTree<T> getRight()
    {
        if (root == null)
           throw new EmptyCollectionException("SimulLinkedBinarySearchTree");
            
        SimulLinkedBinarySearchTree<T> result = new SimulLinkedBinarySearchTree<T>();
        result.root = root.getRight();

        return result;
    }
    
}
