import java.util.*;
import java.io.*;
import javax.swing.*;

/**
  * A class that prompts for ID and stores it in the data file  
  * It also gets the diagnosis info and stores it to the same file next to the ID
  *
  * @author Diana Yamaletdinova
  * @date 6/29/2015 
  *
  */
public class CustomerInfo {   

   private String IDnumber;
   private JPanel patientInfoPanel = null;
   private JTabbedPane jtp = null;

   /**
    * constructor that has no argument
    */ 
   public CustomerInfo(){
   }
   
   /**
    * constructor that has one argument
    * @param jtp the JTabbedPane from the main program
    */ 
   public CustomerInfo( JTabbedPane jtp ){
      this.jtp = jtp;
   }

     /**
     * Gets the ID 
     * @return the IDnumber
     */
   public String getIDnumber(){
      return IDnumber;
   }
   
    /**
     * Sets the IDnumber
     * @param IDnumber
     */
   public void setIDnumber(String IDnumber){
      this.IDnumber = IDnumber;
   }

   /**
   * Method to save the customer info to the file
   * @param parentWindow the parent GUI window
   */
   public boolean getIDnumberFromUser(JFrame parentWindow) { 
      //creates  field for the panel
      JTextField patientIDField = new JTextField(15); 
      patientInfoPanel = new JPanel();   //creates JPanel
      patientInfoPanel.add(new JLabel(" ID : "));//adds label
      patientInfoPanel.add(patientIDField);//adds field
      int selection = JOptionPane.showConfirmDialog(parentWindow, patientInfoPanel, "PLEASE ENTER YOUR ID", JOptionPane.OK_CANCEL_OPTION);  
     
     //if CANLCEL return to the main menu
      if ( (selection == JOptionPane.CANCEL_OPTION) ){ 
         return false;     
      }
      
      else {
         setIDnumber(patientIDField.getText());   // otherwise sets the ID
         return true;
      }
   } 
   
   /**
   * method to save diagnosis and ID to a file
   * @return sb.toString() concatenamed string result
   */
   public String saveDiagnosisToFile(String diagnosis, String diagnosisFileName) {
   
      // creates a StringBuilder object
      StringBuilder sb = new StringBuilder();
      
      // save the ID and diagnosis to a file
      sb.append( getIDnumber() + "|" );
      sb.append( diagnosis + "|\n" );
   
      // write the patient's data to a patientInfo file 
      PrintWriter out = null;
               
      try {         
         out = new PrintWriter(new FileWriter(diagnosisFileName, true));
         out.print(sb.toString()); // records the data to the diagnosis file
      } 
      //catch the exception and notify the user about the error
      catch(IOException io) {
         System.out.println("Error: cannot find a file");
      }
      out.close();
      return sb.toString();
   }
   
}
