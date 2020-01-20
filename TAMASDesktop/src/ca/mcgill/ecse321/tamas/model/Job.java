/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

// line 19 "../../../../../Model.ump"
public class Job
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes
  private Time startTime;
  private Time endTime;
  private Date deadlineDate;
  private Time deadlineTime;
  private String title;
  private String description;

  //Job State Machines
  public enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
  private Day day;
  public enum Type { TUTORIAL, LAB, GRADER }
  private Type type;

  //Job Associations
  private List<JobApplication> jobApplications;
  private CourseWorker allocatedWorker;
  private Course course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Job(Time aStartTime, Time aEndTime, Date aDeadlineDate, Time aDeadlineTime, String aTitle, String aDescription, Course aCourse, Day aDay, Type aType)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    deadlineDate = aDeadlineDate;
    deadlineTime = aDeadlineTime;
    title = aTitle;
    description = aDescription;
    day = aDay;
    type = aType;
    jobApplications = new ArrayList<JobApplication>();
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create jobPosting due to course");
    }
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeadlineDate(Date aDeadlineDate)
  {
    boolean wasSet = false;
    deadlineDate = aDeadlineDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeadlineTime(Time aDeadlineTime)
  {
    boolean wasSet = false;
    deadlineTime = aDeadlineTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  /**
   * This enumeration decides how we expose the number of hours.
   */
  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDeadlineDate()
  {
    return deadlineDate;
  }

  public Time getDeadlineTime()
  {
    return deadlineTime;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription()
  {
    return description;
  }

  public String getDayFullName()
  {
    String answer = day.toString();
    return answer;
  }

  public String getTypeFullName()
  {
    String answer = type.toString();
    return answer;
  }

  public Day getDay()
  {
    return day;
  }

  public Type getType()
  {
    return type;
  }

  public boolean setDay(Day aDay)
  {
    day = aDay;
    return true;
  }

  public boolean setType(Type aType)
  {
    type = aType;
    return true;
  }

  public JobApplication getJobApplication(int index)
  {
    JobApplication aJobApplication = jobApplications.get(index);
    return aJobApplication;
  }

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

  public CourseWorker getAllocatedWorker()
  {
    return allocatedWorker;
  }

  public boolean hasAllocatedWorker()
  {
    boolean has = allocatedWorker != null;
    return has;
  }

  public Course getCourse()
  {
    return course;
  }

  public static int minimumNumberOfJobApplications()
  {
    return 0;
  }

  public JobApplication addJobApplication(Date aApplicationDate, Time aApplicationTime, int aRank, CourseWorker aWorker)
  {
    return new JobApplication(aApplicationDate, aApplicationTime, aRank, aWorker, this);
  }

  public boolean addJobApplication(JobApplication aJobApplication)
  {
    boolean wasAdded = false;
    if (jobApplications.contains(aJobApplication)) { return false; }
    Job existingJob = aJobApplication.getJob();
    boolean isNewJob = existingJob != null && !this.equals(existingJob);
    if (isNewJob)
    {
      aJobApplication.setJob(this);
    }
    else
    {
      jobApplications.add(aJobApplication);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJobApplication(JobApplication aJobApplication)
  {
    boolean wasRemoved = false;
    //Unable to remove aJobApplication, as it must always have a job
    if (!this.equals(aJobApplication.getJob()))
    {
      jobApplications.remove(aJobApplication);
      wasRemoved = true;
    }
    return wasRemoved;
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

  public boolean setAllocatedWorker(CourseWorker aNewAllocatedWorker)
  {
    boolean wasSet = false;
    if (aNewAllocatedWorker == null)
    {
      CourseWorker existingAllocatedWorker = allocatedWorker;
      allocatedWorker = null;
      
      if (existingAllocatedWorker != null && existingAllocatedWorker.getJob() != null)
      {
        existingAllocatedWorker.setJob(null);
      }
      wasSet = true;
      return wasSet;
    }

    CourseWorker currentAllocatedWorker = getAllocatedWorker();
    if (currentAllocatedWorker != null && !currentAllocatedWorker.equals(aNewAllocatedWorker))
    {
      currentAllocatedWorker.setJob(null);
    }

    allocatedWorker = aNewAllocatedWorker;
    Job existingJob = aNewAllocatedWorker.getJob();

    if (!equals(existingJob))
    {
      aNewAllocatedWorker.setJob(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setCourse(Course aCourse)
  {
    boolean wasSet = false;
    if (aCourse == null)
    {
      return wasSet;
    }

    Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      existingCourse.removeJobPosting(this);
    }
    course.addJobPosting(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (jobApplications.size() > 0)
    {
      JobApplication aJobApplication = jobApplications.get(jobApplications.size() - 1);
      aJobApplication.delete();
      jobApplications.remove(aJobApplication);
    }
    
    CourseWorker existingAllocatedWorker = allocatedWorker;
    allocatedWorker = null;
    if (existingAllocatedWorker != null)
    {
      existingAllocatedWorker.delete();
      existingAllocatedWorker.setJob(null);
    }
    Course placeholderCourse = course;
    this.course = null;
    placeholderCourse.removeJobPosting(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "title" + ":" + getTitle()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "deadlineDate" + "=" + (getDeadlineDate() != null ? !getDeadlineDate().equals(this)  ? getDeadlineDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "deadlineTime" + "=" + (getDeadlineTime() != null ? !getDeadlineTime().equals(this)  ? getDeadlineTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "allocatedWorker = "+(getAllocatedWorker()!=null?Integer.toHexString(System.identityHashCode(getAllocatedWorker())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null")
     + outputString;
  }
}