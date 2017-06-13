import java.util.*;
import java.io.*;
import javax.swing.*;
import jsjf.*;

/*
  This implements a priority heap,
  loads it with procedures from a file,
  and prints a report when finished
  
  @verison 0.0.4 
  @author Trever Cluney
  @date 7/14/2015
*/

public class AppointmentScheduling{

   
   private String procID;
   private int time;
   private int priority;
   private PriorityQueue<Procedure> heap;
   private int startTime = 0;
   private int endTime = 0;
   
   /**
    * constructor that has no arguments
    */
   public AppointmentScheduling(String filename){
   }
   
   public String appointmentScheduling(String filename)
   {
      File inputfile = new File(filename);
      Scanner scan = null;
      int arrivalNumber = 0;
      heap = new PriorityQueue();

      try {
         scan = new Scanner(inputfile);
      } catch(IOException io) {
         System.out.println("Error: cannot find " + filename);
         return "No procedures to be scheduled";
      }
      
      //Scan through each line of the file
      while(scan.hasNextLine())
      {
         String lineIn = scan.nextLine();
         if(lineIn.contains("|"))
         {
            // Split the line into separate variables
            String[] parts = lineIn.split("\\|");
               procID = parts[0];
               time = Integer.parseInt(parts[1].trim());
               priority = Integer.parseInt(parts[2].trim());
            
            // Track the order of arrival based on the order
            // of the procedures in the file.
            arrivalNumber++;

            // Create a Procedure object based on the data in the file
            // and add it to the heap.
            Procedure proc = new Procedure(procID,  priority, time, arrivalNumber);
            heap.addElement(proc, time, priority);
         }   
      }
      scan.close();


      // Create an array to store the lines of the report in order of
      // arrival number order based on the requirements of the assignment
      String[] report = new String[arrivalNumber];
         
      // Remove all elements from the top of the heap,
      // creating a report line for each one
      boolean quitFl = false;     
      while(!(quitFl))
      {
         Procedure proc = null;
         try{
            proc = heap.removeNext(); // Pop the next procedure from the heap
         } catch( Exception exception ) {
            quitFl = true;            // Heap is empty so quit loop
         }

         if(!(quitFl))
         {
            // create a new line for the report
            String reportLine = "";
            
            // Add the procedure ID
            reportLine = reportLine +
                  String.format("%1$8s", proc.getProcedureID());
           
            // Add the estimated procedure duration
            reportLine = reportLine +
                  String.format("%1$19s", (int)(proc.getProcedureDuration()/60) + " h and " + (int)(proc.getProcedureDuration()%60)+ " min");
            
            // Add the procedure's scheduling priority
            reportLine = reportLine +
                  String.format("%1$9s", proc.getProcedurePriority());
            
            // Add the appointment start time
            // based on the next available appointment time
            reportLine = reportLine +
                  String.format("%1$15s", (int)(startTime/60)+ ":" + (int)(startTime%60));

            // Calculate and add appointment end time
            // based on estimated procedure time
            endTime = startTime + proc.getProcedureDuration();
            reportLine = reportLine +
                  String.format("%1$12s", (int)(endTime/60) + ":" + (int)(endTime%60));

            // Set the next available appointment time
            // based on the current appointment's end time
            startTime = endTime;
            
            // Add the line to the report array
            // based on order of arrival
            report[proc.getArrivalNumber() - 1]  = reportLine;
         }

      }
      
      // creates a StringBuilder object
      StringBuilder sb = new StringBuilder();
      
      // Add report headers to stringbuilder
      sb.append(" ________________________________________________________________\n");
      sb.append("  Procedure      Procedure      Schedule       Start       End \n");
      sb.append("     ID          Duration       Priority       Time        Time\n");
      sb.append(" ________________________________________________________________\n");

      // Loop through report array and add each report line
      // ito stringbuilder n order of arrival number
         for(int i=0;i<arrivalNumber;i++)
               sb.append(report[i] + "\n");
               
      return sb.toString();
   }
        
}