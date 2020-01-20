/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 8 "../../../../../Model.ump"
public class Course
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private String name;
  private double budget;
  private int studentsEnrolled;
  private int credits;
  private int hours;
  private int tutorialCount;
  private int labCount;

  //Course Associations
  private List<Job> jobPostings;
  private Tamas tamas;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Course(String aName, double aBudget, int aStudentsEnrolled, int aCredits, int aHours, int aTutorialCount, int aLabCount, Tamas aTamas)
  {
    name = aName;
    budget = aBudget;
    studentsEnrolled = aStudentsEnrolled;
    credits = aCredits;
    hours = aHours;
    tutorialCount = aTutorialCount;
    labCount = aLabCount;
    jobPostings = new ArrayList<Job>();
    boolean didAddTamas = setTamas(aTamas);
    if (!didAddTamas)
    {
      throw new RuntimeException("Unable to create course due to tamas");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setBudget(double aBudget)
  {
    boolean wasSet = false;
    budget = aBudget;
    wasSet = true;
    return wasSet;
  }

  public boolean setStudentsEnrolled(int aStudentsEnrolled)
  {
    boolean wasSet = false;
    studentsEnrolled = aStudentsEnrolled;
    wasSet = true;
    return wasSet;
  }

  public boolean setCredits(int aCredits)
  {
    boolean wasSet = false;
    credits = aCredits;
    wasSet = true;
    return wasSet;
  }

  public boolean setHours(int aHours)
  {
    boolean wasSet = false;
    hours = aHours;
    wasSet = true;
    return wasSet;
  }

  public boolean setTutorialCount(int aTutorialCount)
  {
    boolean wasSet = false;
    tutorialCount = aTutorialCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setLabCount(int aLabCount)
  {
    boolean wasSet = false;
    labCount = aLabCount;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getBudget()
  {
    return budget;
  }

  public int getStudentsEnrolled()
  {
    return studentsEnrolled;
  }

  public int getCredits()
  {
    return credits;
  }

  public int getHours()
  {
    return hours;
  }

  public int getTutorialCount()
  {
    return tutorialCount;
  }

  public int getLabCount()
  {
    return labCount;
  }

  public Job getJobPosting(int index)
  {
    Job aJobPosting = jobPostings.get(index);
    return aJobPosting;
  }

  public List<Job> getJobPostings()
  {
    List<Job> newJobPostings = Collections.unmodifiableList(jobPostings);
    return newJobPostings;
  }

  public int numberOfJobPostings()
  {
    int number = jobPostings.size();
    return number;
  }

  public boolean hasJobPostings()
  {
    boolean has = jobPostings.size() > 0;
    return has;
  }

  public int indexOfJobPosting(Job aJobPosting)
  {
    int index = jobPostings.indexOf(aJobPosting);
    return index;
  }

  public Tamas getTamas()
  {
    return tamas;
  }

  public static int minimumNumberOfJobPostings()
  {
    return 0;
  }

  public Job addJobPosting(Time aStartTime, Time aEndTime, Date aDeadlineDate, Time aDeadlineTime, String aTitle, String aDescription, Job.Day aDay, Job.Type aType)
  {
    return new Job(aStartTime, aEndTime, aDeadlineDate, aDeadlineTime, aTitle, aDescription, this, aDay, aType);
  }

  public boolean addJobPosting(Job aJobPosting)
  {
    boolean wasAdded = false;
    if (jobPostings.contains(aJobPosting)) { return false; }
    Course existingCourse = aJobPosting.getCourse();
    boolean isNewCourse = existingCourse != null && !this.equals(existingCourse);
    if (isNewCourse)
    {
      aJobPosting.setCourse(this);
    }
    else
    {
      jobPostings.add(aJobPosting);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJobPosting(Job aJobPosting)
  {
    boolean wasRemoved = false;
    //Unable to remove aJobPosting, as it must always have a course
    if (!this.equals(aJobPosting.getCourse()))
    {
      jobPostings.remove(aJobPosting);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addJobPostingAt(Job aJobPosting, int index)
  {  
    boolean wasAdded = false;
    if(addJobPosting(aJobPosting))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobPostings()) { index = numberOfJobPostings() - 1; }
      jobPostings.remove(aJobPosting);
      jobPostings.add(index, aJobPosting);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobPostingAt(Job aJobPosting, int index)
  {
    boolean wasAdded = false;
    if(jobPostings.contains(aJobPosting))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobPostings()) { index = numberOfJobPostings() - 1; }
      jobPostings.remove(aJobPosting);
      jobPostings.add(index, aJobPosting);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobPostingAt(aJobPosting, index);
    }
    return wasAdded;
  }

  public boolean setTamas(Tamas aTamas)
  {
    boolean wasSet = false;
    if (aTamas == null)
    {
      return wasSet;
    }

    Tamas existingTamas = tamas;
    tamas = aTamas;
    if (existingTamas != null && !existingTamas.equals(aTamas))
    {
      existingTamas.removeCourse(this);
    }
    tamas.addCourse(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (jobPostings.size() > 0)
    {
      Job aJobPosting = jobPostings.get(jobPostings.size() - 1);
      aJobPosting.delete();
      jobPostings.remove(aJobPosting);
    }
    
    Tamas placeholderTamas = tamas;
    this.tamas = null;
    placeholderTamas.removeCourse(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "budget" + ":" + getBudget()+ "," +
            "studentsEnrolled" + ":" + getStudentsEnrolled()+ "," +
            "credits" + ":" + getCredits()+ "," +
            "hours" + ":" + getHours()+ "," +
            "tutorialCount" + ":" + getTutorialCount()+ "," +
            "labCount" + ":" + getLabCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tamas = "+(getTamas()!=null?Integer.toHexString(System.identityHashCode(getTamas())):"null")
     + outputString;
  }
}