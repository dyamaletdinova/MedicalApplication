import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.border.*;

/**
  * The MedApp program that offers to get the diagnosis of the back pain, based on the seriel of questions from input.txt file,
  * provides with recomendations based on the diagnosis and shows the avaliable time for the appointment witht he medical personel.
  * The program also promts for the ID of the patient and records it along with the diagnosis result to a file BackPainAnalyzer.dat.
  * Then the data from the file analysed and the app provides recommendations for the treatment. 
  * 
  * @author Diana Yamaletdinova
  * @date 7/15/2015 
  */

public class MedApp  extends JFrame {
   // input files
   private final String bpaInputFileName = "input.txt";
   private final String diagnosisFileName = "BackPainAnalyzer.dat";
   private final String procRecommendationFileName = "ProcedureRecommendations.txt";
   private final String procInfoFileName = "ProcedureInfo.txt";

   private JFrame frame;
   private JButton cancelButtonl; //cancelButtonl object
   //creates a ButtonHandler object
   private ButtonHandler phandler = new ButtonHandler();
   //creates JTabbedPane object
   private JTabbedPane jtp = new JTabbedPane();
   // creates patientTabPanel and managerTabPanel
   private JPanel patientTabPanel = new JPanel(new GridBagLayout());
   // creates  text area on the patientTabPanel
   private JTextArea patientTextArea;


   /**
	 * method that builds the display of the main program 
	 */
   public MedApp() {
   
      frame = new JFrame("MedApp");//creats a frame 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set default close operation, exit when close frame
      frame.setSize(920, 600); // set size
      
      // creates tabs on the frame
      getContentPane().add(jtp);
      jtp.addTab("Patient", patientTabPanel); // adds Customer tab
   
      // method to create patient options view
      createPatientOptionsView(); 
      frame.add(jtp, BorderLayout.CENTER); // adds tabs to the frame
      frame.validate();
      frame.setLocationRelativeTo(null); //center form
      //frame.setResizable(false);//freeze the resizing of the frame
      frame.setVisible(true);
   }  
   
   
   /**
	* Method that creates the createPatientOptionsView
   * using the GridBagLayout
	*/
   public void createPatientOptionsView() {
   
      GridBagConstraints c = new GridBagConstraints(); // creates new gridBagConstraints object
      
      // position of area
      c.gridx = 0; 
      c.gridy = 0;
      
      // size of area in rows and columns count
      c.gridwidth = 1;
      c.gridheight = 1;
      
      // specify how much space compare to the other columns and rows this area should take
      c.weightx = 0.0;
      c.weighty = 0.5;
      
      c.fill = GridBagConstraints.BOTH;
      c.insets = new Insets (10, 10, 15, 15);
              
      JPanel patientButtonPanel = new JPanel(new GridLayout(4, 1, 20, 20));//creates the patientButtonPanel
      patientButtonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         
      //adds managerButtonPanel to the manager tab
      patientTabPanel.add(patientButtonPanel, c); 
      patientTabPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
   
      // adds the buttons to the patientButtonPanel
      JButton btnOptionOne = new JButton("Back Pain Analyzer");  
      btnOptionOne.addActionListener(phandler); //adds actionListener to Back Pain Analyzer   button      
      patientButtonPanel.add(btnOptionOne); // adds Back Pain Analyzer to the panel  
      JButton btnOptionTwo = new JButton("Medical Recommendations");
      btnOptionTwo.addActionListener(phandler);
      patientButtonPanel.add(btnOptionTwo);
      JButton btnOptionThree = new JButton("Appointment Schedule Report");
      btnOptionThree.addActionListener(phandler);
      patientButtonPanel.add(btnOptionThree);
      JButton btnExit = new JButton("Exit");
      btnExit.addActionListener(phandler);
      patientButtonPanel.add(btnExit);
     
      // specifies constraints for components that are laid out using the GridBagLayout class
      c = new GridBagConstraints();
      
      // Position of area
      c.gridx = 1; 
      c.gridy = 0;
      
      // Size of area in rows and columns count
      c.gridwidth = 4;
      c.gridheight = 1;
      
      // specify how much space compare to the other columns and rows this area should take
      c.weightx = 0.1;
      c.weighty = 0.5;
      
      c.fill = GridBagConstraints.BOTH;
   
      // creates a Text area to display the results of the patient panel options
      patientTextArea = 
         new JTextArea(){
         
            final ImageIcon imageIcon = new ImageIcon("dnaIcon.jpg");
             
             //Get image that we use as JTextArea background 
            Image image = imageIcon.getImage();
            //Image grayImage = GrayFilter.createDisabledImage(image);
            {
               //Make JTextArea transparent  
               setOpaque(false);  
               
               //Make JTextArea line wrap  
               setLineWrap(true);  
            
               //Make JTextArea word wrap  
               setWrapStyleWord(true); 
            }
                   
         /** 
         * Class that adds a DNA icon to the TextArea
         * @param g Graphics
         */
            public void paint(Graphics g) {
            
            //scale the image
               int w = image.getWidth(null);
               int h = image.getHeight(null);
               int scale = 2;
               
               //Draw JTextArea background image 
               g.drawImage(image, 75, 110, w*scale, h*scale, this);
               super.paint(g);
            }
         };
      
      patientTabPanel.add(patientTextArea, c);//add textArea to the panel
      
      // creates a scroll object to add it to the text area
      JScrollPane scroll = new JScrollPane(patientTextArea);
      patientTabPanel.add(scroll,c);
      patientTextArea.setText("\n                               Welcome to MedApp.\n" +
                  "                Please Choose One of the Options on Your Left."); //welcome msg
      patientTextArea.setFont(new Font("Serif", Font.ITALIC, 25)); // set the text font for the welcoming msg
   }
      
   /** 
     * Class that handles the button-click event
     * for Exit,Back Pain Analyzer,Medical Recommendations and Appointment Schedule Report 
     * buttons on the Patient Tab
	  */
   class ButtonHandler implements ActionListener {
   
      public void actionPerformed(ActionEvent e) {
         patientTextArea.setText("");
         patientTextArea.setOpaque(true); //remove the image and set the text area clean
         patientTextArea.setFont(new Font("Courier New", Font.PLAIN, 16)); // set the new font
         
         // if "Exit" button was chosen
         if (e.getActionCommand().equals("Exit") ) {
            System.exit(0);
         }
       
         // if "Back Pain Analyzer" button was chosen
         if ( e.getActionCommand().equals("Back Pain Analyzer") ) {
            CustomerInfo ci = new CustomerInfo();
            BackPainAnalyzer bpa = new BackPainAnalyzer();
         
             // asks for the ID and save the data to a file
            boolean gotID = ci.getIDnumberFromUser(frame);
            
            if (gotID){
               ci.saveDiagnosisToFile(bpa.getDiagnosis(frame, bpaInputFileName), diagnosisFileName);
            }
            return;
         }
      
         // if "Medical Recommendations" button was chosen
         if ( e.getActionCommand().equals("Medical Recommendations") ){
            String msg = ProcedureRecommendation.execute(diagnosisFileName, 
                                            procRecommendationFileName,
                                            procInfoFileName); // call ProcedureRecommendation to get the data
            patientTextArea.setText(msg);// set the results in the text area
            return;
         }
      
         // if "Appointment Schedule Report" button was chosen
         if ( e.getActionCommand().equals("Appointment Schedule Report") ){
            AppointmentScheduling as = new AppointmentScheduling(procInfoFileName);
            String msg = as.appointmentScheduling(procInfoFileName); // call AppointmentScheduling class to print a report
            patientTextArea.setText(msg);// set the results in the text area
            return;
         }
      }
   }

   /**
    * the main method to display the Computer store app;
    */  
   public static void main(String args[]) throws FileNotFoundException {
      new MedApp();
   }
}

