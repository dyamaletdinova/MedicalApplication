import jsjf.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * The DecisionTree class uses the LinkedBinaryTree class to implement 
 * a binary decision tree. Tree elements are read from a given file and  
 * then the decision tree can be evaluated based on user input using the
 * evaluate method. 
 * 
 * @author Java Foundations
 * @version 4.0
 */
public class DecisionTree
{
   private LinkedBinaryTree<String> tree;
    
    /**
     * Builds the decision tree based on the contents of the given file
     *
     * @param filename the name of the input file
     * @throws FileNotFoundException if the input file is not found
     */
   public DecisionTree(String filename) throws FileNotFoundException
   {
      File inputFile = new File(filename);
      Scanner scan = new Scanner(inputFile);
      int numberNodes = scan.nextInt();
      scan.nextLine();
      int root = 0, left, right;
        
      List<LinkedBinaryTree<String>> nodes = new java.util.ArrayList<LinkedBinaryTree<String>>();
      for (int i = 0; i < numberNodes; i++)
         nodes.add(i,new LinkedBinaryTree<String>(scan.nextLine()));
        
      while (scan.hasNext())
      {
         root = scan.nextInt();
         left = scan.nextInt();
         right = scan.nextInt();
         scan.nextLine();
            
         nodes.set(root, new LinkedBinaryTree<String>((nodes.get(root)).getRootElement(), 
                                                       nodes.get(left), nodes.get(right)));
      }
      tree = nodes.get(root);
   }

    /**
     *  Follows the decision tree based on user responses.
     */
   public String evaluate(JFrame parentWindow){
      LinkedBinaryTree<String> current = tree;
   
      while (current.size() > 1) {
         boolean inputAcceptedFl = false;
         while(!(inputAcceptedFl))  {
           int result = JOptionPane.showConfirmDialog(parentWindow, current.getRootElement(), null, JOptionPane.YES_OPTION);

            if (result == JOptionPane.NO_OPTION){
                   // Negative input, choose left branch
               current = current.getLeft();
               inputAcceptedFl = true;
            }
            else if (result == JOptionPane.YES_OPTION) {
               // Affirmative input, choose right branch
               current = current.getRight();
               inputAcceptedFl = true;
            }
            else {
                   // Invalid input
               JOptionPane.showMessageDialog(parentWindow, "I'm sorry, I did not understand your response.");
            }
         }
      }
        // Output diagnosis
      String diagnosis = current.getRootElement();
      JOptionPane.showMessageDialog(parentWindow, diagnosis);
      return diagnosis;
   }
}