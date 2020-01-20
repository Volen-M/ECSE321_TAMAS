/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.sql.Date;
import java.sql.Time;

// line 32 "../../../../../Model.ump"
public class JobApplication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //JobApplication Attributes
  private Date applicationDate;
  private Time applicationTime;
  private int rank;

  //JobApplication State Machines
  public enum ApplicationStatus { APPLIED, APPROVED, OFFER, ACCEPTED, REJECTED }
  private ApplicationStatus applicationStatus;

  //JobApplication Associations
  private CourseWorker worker;
  private Job job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JobApplication(Date aApplicationDate, Time aApplicationTime, int aRank, CourseWorker aWorker, Job aJob)
  {
    applicationDate = aApplicationDate;
    applicationTime = aApplicationTime;
    rank = aRank;
    
    if (!setWorker(aWorker))
    {
      throw new RuntimeException("Unable to create JobApplication due to aWorker");
    }
    boolean didAddJob = setJob(aJob);
    if (!didAddJob)
    {
      throw new RuntimeException("Unable to create jobApplication due to job");
    }
    setApplicationStatus(ApplicationStatus.APPLIED);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setApplicationDate(Date aApplicationDate)
  {
    boolean wasSet = false;
    applicationDate = aApplicationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setApplicationTime(Time aApplicationTime)
  {
    boolean wasSet = false;
    applicationTime = aApplicationTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setRank(int aRank)
  {
    boolean wasSet = false;
    rank = aRank;
    wasSet = true;
    return wasSet;
  }

  public Date getApplicationDate()
  {
    return applicationDate;
  }

  public Time getApplicationTime()
  {
    return applicationTime;
  }

  /**
   * This is the preference number given by a TA/Grader for this job.
   */
  public int getRank()
  {
    return rank;
  }

  public String getApplicationStatusFullName()
  {
    String answer = applicationStatus.toString();
    return answer;
  }

  public ApplicationStatus getApplicationStatus()
  {
    return applicationStatus;
  }

  public boolean setApplicationStatus(ApplicationStatus aApplicationStatus)
  {
    applicationStatus = aApplicationStatus;
    return true;
  }

  public CourseWorker getWorker()
  {
    return worker;
  }

  public Job getJob()
  {
    return job;
  }

  public boolean setWorker(CourseWorker aNewWorker)
  {
    boolean wasSet = false;
    if (aNewWorker != null)
    {
      worker = aNewWorker;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setJob(Job aJob)
  {
    boolean wasSet = false;
    if (aJob == null)
    {
      return wasSet;
    }

    Job existingJob = job;
    job = aJob;
    if (existingJob != null && !existingJob.equals(aJob))
    {
      existingJob.removeJobApplication(this);
    }
    job.addJobApplication(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    worker = null;
    Job placeholderJob = job;
    this.job = null;
    placeholderJob.removeJobApplication(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "rank" + ":" + getRank()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "applicationDate" + "=" + (getApplicationDate() != null ? !getApplicationDate().equals(this)  ? getApplicationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "applicationTime" + "=" + (getApplicationTime() != null ? !getApplicationTime().equals(this)  ? getApplicationTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "worker = "+(getWorker()!=null?Integer.toHexString(System.identityHashCode(getWorker())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "job = "+(getJob()!=null?Integer.toHexString(System.identityHashCode(getJob())):"null")
     + outputString;
  }
}