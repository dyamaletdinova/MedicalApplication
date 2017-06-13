import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * BackPainAnaylyzer demonstrates the use of a binary decision tree to 
 * diagnose back pain.
 */
public class BackPainAnalyzer {

   private JPanel patientInfoPanel = null;
   private JTabbedPane jtp = null;

    /**
    * constructor that has no arguments
    */
   public BackPainAnalyzer(){
   }
   
   /**
    * constructor that has one argument
    * @param jtp the JTabbedPane from the main program
    */ 
    
   public BackPainAnalyzer( JTabbedPane jtp ){
      this.jtp = jtp;
   }
   
   /**
    *  Asks questions of the user to diagnose a medical problem.
    */
   public String getDiagnosis(JFrame parentWindow, String bpaInputFileName) {
   
      patientInfoPanel = new JPanel();   //creates JPanel
      patientInfoPanel.add(new JLabel("PLEASE ANSWER FOLLOWING QUESTIONS."));//adds label
   
      int selection = JOptionPane.showConfirmDialog(parentWindow, patientInfoPanel, null, JOptionPane.PLAIN_MESSAGE);  
      String result = "";

      //white OK_OPTION - do the following
      while ( (selection == JOptionPane.OK_OPTION) ){ 
      
         try { // try-catch statement to catch wrong input
            DecisionTree expert = new DecisionTree(bpaInputFileName);
            result = expert.evaluate(parentWindow);
            break;
         }
         
         catch ( Exception exception ) {
            // inform that the input was incorrect and prompt for the input again
            JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
            continue;
         }
      }
      return result;
      
   } 
}
