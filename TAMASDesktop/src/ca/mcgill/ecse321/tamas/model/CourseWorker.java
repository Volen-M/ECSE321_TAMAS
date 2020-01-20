/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;

// line 54 "../../../../../Model.ump"
public class CourseWorker extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseWorker Attributes
  private int hoursOfWorkAllocated;
  private int mcgillID;
  private String pastWorkerExperience;

  //CourseWorker State Machines
  public enum AcademicStatus { UNDERGRADUATE, GRADUATE }
  private AcademicStatus academicStatus;

  //CourseWorker Associations
  private List<JobApplication> jobApplications;
  private List<Evaluation> evals;
  private Job job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseWorker(String aUsername, String aName, int aHoursOfWorkAllocated, int aMcgillID, String aPastWorkerExperience)
  {
    super(aUsername, aName);
    hoursOfWorkAllocated = aHoursOfWorkAllocated;
    mcgillID = aMcgillID;
    pastWorkerExperience = aPastWorkerExperience;
    jobApplications = new ArrayList<JobApplication>();
    evals = new ArrayList<Evaluation>();
    setAcademicStatus(AcademicStatus.UNDERGRADUATE);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHoursOfWorkAllocated(int aHoursOfWorkAllocated)
  {
    boolean wasSet = false;
    hoursOfWorkAllocated = aHoursOfWorkAllocated;
    wasSet = true;
    return wasSet;
  }

  public boolean setMcgillID(int aMcgillID)
  {
    boolean wasSet = false;
    mcgillID = aMcgillID;
    wasSet = true;
    return wasSet;
  }

  public boolean setPastWorkerExperience(String aPastWorkerExperience)
  {
    boolean wasSet = false;
    pastWorkerExperience = aPastWorkerExperience;
    wasSet = true;
    return wasSet;
  }

  public int getHoursOfWorkAllocated()
  {
    return hoursOfWorkAllocated;
  }

  public int getMcgillID()
  {
    return mcgillID;
  }

  public String getPastWorkerExperience()
  {
    return pastWorkerExperience;
  }

  public String getAcademicStatusFullName()
  {
    String answer = academicStatus.toString();
    return answer;
  }

  public AcademicStatus getAcademicStatus()
  {
    return academicStatus;
  }

  public boolean setAcademicStatus(AcademicStatus aAcademicStatus)
  {
    academicStatus = aAcademicStatus;
    return true;
  }

  public JobApplication getJobApplication(int index)
  {
    JobApplication aJobApplication = jobApplications.get(index);
    return aJobApplication;
  }

  /**
   * Maximum of 3 job applications.
   */
  public List<JobApplication> getJobApplications()
  {
    List<JobApplication> newJobApplications = Collections.unmodifiableList(jobApplications);
    return newJobApplications;
  }

  public int numberOfJobApplications()
  {
    int number = jobApplications.size();
    return number;
  }

  public boolean hasJobApplications()
  {
    boolean has = jobApplications.size() > 0;
    return has;
  }

  public int indexOfJobApplication(JobApplication aJobApplication)
  {
    int index = jobApplications.indexOf(aJobApplication);
    return index;
  }

  public Evaluation getEval(int index)
  {
    Evaluation aEval = evals.get(index);
    return aEval;
  }

  public List<Evaluation> getEvals()
  {
    List<Evaluation> newEvals = Collections.unmodifiableList(evals);
    return newEvals;
  }

  public int numberOfEvals()
  {
    int number = evals.size();
    return number;
  }

  public boolean hasEvals()
  {
    boolean has = evals.size() > 0;
    return has;
  }

  public int indexOfEval(Evaluation aEval)
  {
    int index = evals.indexOf(aEval);
    return index;
  }

  public Job getJob()
  {
    return job;
  }

  public boolean hasJob()
  {
    boolean has = job != null;
    return has;
  }

  public static int minimumNumberOfJobApplications()
  {
    return 0;
  }

  public static int maximumNumberOfJobApplications()
  {
    return 3;
  }

  public boolean addJobApplication(JobApplication aJobApplication)
  {
    boolean wasAdded = false;
    if (jobApplications.contains(aJobApplication)) { return false; }
    if (numberOfJobApplications() < maximumNumberOfJobApplications())
    {
      jobApplications.add(aJobApplication);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeJobApplication(JobApplication aJobApplication)
  {
    boolean wasRemoved = false;
    if (jobApplications.contains(aJobApplication))
    {
      jobApplications.remove(aJobApplication);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean setJobApplications(JobApplication... newJobApplications)
  {
    boolean wasSet = false;
    ArrayList<JobApplication> verifiedJobApplications = new ArrayList<JobApplication>();
    for (JobApplication aJobApplication : newJobApplications)
    {
      if (verifiedJobApplications.contains(aJobApplication))
      {
        continue;
      }
      verifiedJobApplications.add(aJobApplication);
    }

    if (verifiedJobApplications.size() != newJobApplications.length || verifiedJobApplications.size() > maximumNumberOfJobApplications())
    {
      return wasSet;
    }

    jobApplications.clear();
    jobApplications.addAll(verifiedJobApplications);
    wasSet = true;
    return wasSet;
  }

  public boolean addJobApplicationAt(JobApplication aJobApplication, int index)
  {  
    boolean wasAdded = false;
    if(addJobApplication(aJobApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobApplications()) { index = numberOfJobApplications() - 1; }
      jobApplications.remove(aJobApplication);
      jobApplications.add(index, aJobApplication);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobApplicationAt(JobApplication aJobApplication, int index)
  {
    boolean wasAdded = false;
    if(jobApplications.contains(aJobApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobApplications()) { index = numberOfJobApplications() - 1; }
      jobApplications.remove(aJobApplication);
      jobApplications.add(index, aJobApplication);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobApplicationAt(aJobApplication, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfEvals()
  {
    return 0;
  }

  public boolean addEval(Evaluation aEval)
  {
    boolean wasAdded = false;
    if (evals.contains(aEval)) { return false; }
    evals.add(aEval);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEval(Evaluation aEval)
  {
    boolean wasRemoved = false;
    if (evals.contains(aEval))
    {
      evals.remove(aEval);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addEvalAt(Evaluation aEval, int index)
  {  
    boolean wasAdded = false;
    if(addEval(aEval))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEvals()) { index = numberOfEvals() - 1; }
      evals.remove(aEval);
      evals.add(index, aEval);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEvalAt(Evaluation aEval, int index)
  {
    boolean wasAdded = false;
    if(evals.contains(aEval))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEvals()) { index = numberOfEvals() - 1; }
      evals.remove(aEval);
      evals.add(index, aEval);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEvalAt(aEval, index);
    }
    return wasAdded;
  }

  public boolean setJob(Job aNewJob)
  {
    boolean wasSet = false;
    if (aNewJob == null)
    {
      Job existingJob = job;
      job = null;
      
      if (existingJob != null && existingJob.getAllocatedWorker() != null)
      {
        existingJob.setAllocatedWorker(null);
      }
      wasSet = true;
      return wasSet;
    }

    Job currentJob = getJob();
    if (currentJob != null && !currentJob.equals(aNewJob))
    {
      currentJob.setAllocatedWorker(null);
    }

    job = aNewJob;
    CourseWorker existingAllocatedWorker = aNewJob.getAllocatedWorker();

    if (!equals(existingAllocatedWorker))
    {
      aNewJob.setAllocatedWorker(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    jobApplications.clear();
    evals.clear();
    if (job != null)
    {
      job.setAllocatedWorker(null);
    }
    super.delete();
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "hoursOfWorkAllocated" + ":" + getHoursOfWorkAllocated()+ "," +
            "mcgillID" + ":" + getMcgillID()+ "," +
            "pastWorkerExperience" + ":" + getPastWorkerExperience()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "job = "+(getJob()!=null?Integer.toHexString(System.identityHashCode(getJob())):"null")
     + outputString;
  }
}