/**
 * Procedure class
 * Defines the relationship between a diagnosis and a medical procedure
 * implements the Comparable interface 
 * 
 * @author Michael Berck
 * @date 7/15/15
 */

public class Procedure implements Comparable<Procedure>
{
   private String diagnosis, procedureName, procedureID;
   private int procedurePriority, procedureDuration, arrivalNumber;


   /**
    * Creates a new Procedure object without a procedureID
    * To be used to recommend a procedure based on a diagnosis.
    *
    * @param diagnosisIN the diagnosis that this procedure is recommended for.
    * @param procedureNameIN the name of the procedure.
    * @param procedurePriorityIN the scheduling priority for the procedure.
    * @param procedureDurationIN the duration of the procedure in minutes.
    */
   public Procedure(String diagnosisIN, String procedureNameIN,
                    int procedurePriorityIN, int procedureDurationIN)
   {
      diagnosis = diagnosisIN;
      procedureName = procedureNameIN;
      procedureID = "";
      procedurePriority = procedurePriorityIN;
      procedureDuration = procedureDurationIN;
      arrivalNumber = 0;
   }

   /**
    * Creates a new Procedure object without a diagnosis or procedureName.
    * Used to schedule procedures.
    *
    * @param procedureIDIN the ID of the procedure.
    * @param procedurePriorityIN the scheduling priority for the procedure.
    * @param procedureDurationIN the duration of the procedure in minutes.
    * @param arrivalNumberIN the original order of procedure arrival.
    */
   public Procedure(String procedureIDIN, int procedurePriorityIN,
                    int procedureDurationIN, int arrivalNumberIN)
   {
      diagnosis = "";
      procedureName = "";
      procedureID = procedureIDIN;
      procedurePriority = procedurePriorityIN;
      procedureDuration = procedureDurationIN;
      arrivalNumber = arrivalNumberIN;
   }

   /**
    * Creates a new Procedure object with only a diagnosis value.
    * Useful as a search criteria for recommending a procedure based on diagnosis.
    *
    * @param diagnosisIN the diagnosis that this procedure is recommended for.
    */
   public Procedure(String diagnosisIN)
   {
      diagnosis = diagnosisIN;
      procedureName = null;
      procedureID = null;
      procedurePriority = 0;
      procedureDuration = 0;
      arrivalNumber = 0;
   }

   /**
    * Return the diagnosis value of a procedure
    */
   public String getDiagnosis()
   {
      return diagnosis;
   }

   /**
    * Return the procedureName value of a procedure
    */
   public String getProcedureName()
   {
      return procedureName;
   }

   /**
    * Return the procedureID value of a procedure
    */
   public String getProcedureID()
   {
      return procedureID;
   }

   /**
    * Return the procedurePriority value of a procedure
    */
   public int getProcedurePriority()
   {
      return procedurePriority;
   }

   /**
    * Return the procedureDuration value of a procedure
    */
   public int getProcedureDuration()
   {
      return procedureDuration;
   }

   /**
    * Return the arrivalNumber value of a procedure
    */
   public int getArrivalNumber()
   {
      return arrivalNumber;
   }

   /**
    * compareTo method ensures that Procedures are compared to
    * each other based on their diagnosis variable.
    */
   public int compareTo(Procedure compareProc)
   {
      return this.diagnosis.compareTo(compareProc.getDiagnosis());
   }

   /**
    * @overrides equals method to only check if the diagnosis values of two 
    * Procedure objects are equal.
    *
    * DOESN'T SEEM TO WORK FROM WITHIN THE TREE WHEN REFERRING TO CLASS AS GENERIC
    */
   public boolean equals(Procedure compareProc)
   {
      System.out.println("Testing!");
      return this.diagnosis.equals(compareProc.getDiagnosis());
   }
}