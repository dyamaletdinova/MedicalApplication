import jsjf.*;
import java.util.*;
import java.io.*;

/**
  * Use a simulated link binary search tree to search for a
  * diagnosis match in order to recommend a medical procedure
  *
  * @author Michael Berck
  * @date 7/15/15
  *
  */
  
public class ProcedureRecommendation
{
    
   public static String execute(String diagnosisFileName,
                              String procRecommendationFileName,
                              String procInfoFileName)
   {
      File scanFile;
      Scanner scan;
   
      SimulLinkedBinarySearchTree<Procedure> tree;
      tree = new SimulLinkedBinarySearchTree<Procedure>();
      Procedure test;
   
       // creates a StringBuilder object
      StringBuilder sb = new StringBuilder();
      
      // Load each procedure in the procedure recommendations
      // file into the search tree
      scanFile = new File(procRecommendationFileName);
      try {         
         scan = new Scanner(scanFile);
         
         
         while(scan.hasNextLine()){
            String lineIn = scan.nextLine();
            if(lineIn.contains("|")) {
            
               // Split the line into separate variables
               String[] parts = lineIn.split("\\|");
               String diagnosis = parts[0];
               String procedureName = parts[1];
               int priority = Integer.parseInt(parts[2].trim());
               int duration = Integer.parseInt(parts[3].trim());
            
               // Create a Procedure object based on the data in the file
               // and add it to the tree.
               Procedure proc = new Procedure(diagnosis, procedureName, priority, duration);
               tree.addElement(proc);
            }
         }
      }
      catch(IOException io) {
         sb.append("Error: cannot find " + procRecommendationFileName);
      }
      
      // Pull diagnosis from BackPainAnalyzer file
      scanFile = new File(diagnosisFileName);
      try {      
         scan = new Scanner(scanFile);

         // Delete existing procedure information file
         File delFile = new File(procInfoFileName);
         delFile.delete();
                  
         while(scan.hasNextLine()) {
            String lineIn = scan.nextLine();
            if(lineIn.contains("|")) {
               String[] parts = lineIn.split("\\|");
               String patientID = parts[0];
               String diagnosis = parts[1];
            
               // Create a test Procedure object based on the
               // diagnosis in the last line of data in the file
               test = new Procedure(diagnosis);
            
               // Find the recommended procedure from the search tree
               // based on the diagnosis retrieved from the file
               Procedure recommendation = tree.find(test);
            
               sb.append ("\n\n");
               sb.append("     Patient " + patientID + ": \n");
               sb.append("     ------------------------ \n");
               sb.append("     Our recommended procedure based on your symptoms is: \n");
               sb.append("     " + recommendation.getProcedureName());
               sb.append ("         ");
               sb.append ("\n\n");
            
               writeProcedureInfoToFile(recommendation, procInfoFileName);
            }
         }
      
      }
      catch(IOException io) {
         //System.out.println("Error: cannot find " + diagnosisFileName);
         sb.append("No diagnoses to recommend procedures for.");
      }
      return sb.toString();
   }


   /**
    * Add a line of procedure information to a file
    */
   private static void writeProcedureInfoToFile(Procedure recommendation, String procInfoFileName)
   {
      // Create a new entry for the procedure info file
      String fLine = createNewProcID(recommendation, procInfoFileName);
      fLine = fLine + "|" + recommendation.getProcedureDuration();
      fLine = fLine + "|" + recommendation.getProcedurePriority();
      fLine = fLine + "\n";
      
      // Write the procedure recommendation to a ProcedureInfo file 
      PrintWriter out = null;
      try {         
         // Print to file
         out = new PrintWriter(new FileWriter(procInfoFileName, true));
         out.print(fLine);
      } 
      catch(IOException io) {
         System.out.println("Error: cannot find a file");
      }
      out.close();
   }

   /**
    * Create a new unique procedure ID based on the first three
    * characters of the procedure name and a three-digit
    * auto-incrementing integer.
    */
   private static String createNewProcID(Procedure recommendation, String procInfoFileName)
   {
      String procIDPrefix = recommendation.getProcedureName().substring(0,3);
      int maxProcID = 0;
   
      // Scan the ProcedureInfo file to determine the highest
      // existing procedureID for this type of procedure
      File scanFile = new File(procInfoFileName);
      try
      {
         Scanner scan = new Scanner(scanFile);
         while(scan.hasNextLine())
         {
            String lineIn = scan.nextLine();
            if(lineIn.contains("|"))
            {
               String[] parts = lineIn.split("\\|");
               String procID = parts[0];
               if(procIDPrefix.equals(procID.substring(0,3)))
               {
                  int procIDSuffix = Integer.parseInt(procID.substring(3));
                  maxProcID = Math.max(maxProcID, procIDSuffix);
               }
            }
         }
         scan.close();
      }
      catch(IOException io) {
      }
   
      maxProcID++;
      String procIDSuffix = String.format("%03d", maxProcID);
      return procIDPrefix + procIDSuffix;
   }

}




