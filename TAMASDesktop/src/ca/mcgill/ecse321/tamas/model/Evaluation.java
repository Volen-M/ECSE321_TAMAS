/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;

// line 40 "../../../../../Model.ump"
public class Evaluation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Evaluation Attributes
  private String description;

  //Evaluation Associations
  private Course course;
  private Job job;
  private Instructor evaluator;
  private CourseWorker worker;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evaluation(String aDescription, Course aCourse, Job aJob, Instructor aEvaluator, CourseWorker aWorker)
  {
    description = aDescription;
    if (!setCourse(aCourse))
    {
      throw new RuntimeException("Unable to create Evaluation due to aCourse");
    }
    if (!setJob(aJob))
    {
      throw new RuntimeException("Unable to create Evaluation due to aJob");
    }
    if (!setEvaluator(aEvaluator))
    {
      throw new RuntimeException("Unable to create Evaluation due to aEvaluator");
    }
    if (!setWorker(aWorker))
    {
      throw new RuntimeException("Unable to create Evaluation due to aWorker");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public Course getCourse()
  {
    return course;
  }

  public Job getJob()
  {
    return job;
  }

  public Instructor getEvaluator()
  {
    return evaluator;
  }

  public CourseWorker getWorker()
  {
    return worker;
  }

  public boolean setCourse(Course aNewCourse)
  {
    boolean wasSet = false;
    if (aNewCourse != null)
    {
      course = aNewCourse;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setJob(Job aNewJob)
  {
    boolean wasSet = false;
    if (aNewJob != null)
    {
      job = aNewJob;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setEvaluator(Instructor aNewEvaluator)
  {
    boolean wasSet = false;
    if (aNewEvaluator != null)
    {
      evaluator = aNewEvaluator;
      wasSet = true;
    }
    return wasSet;
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

  public void delete()
  {
    course = null;
    job = null;
    evaluator = null;
    worker = null;
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "job = "+(getJob()!=null?Integer.toHexString(System.identityHashCode(getJob())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "evaluator = "+(getEvaluator()!=null?Integer.toHexString(System.identityHashCode(getEvaluator())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "worker = "+(getWorker()!=null?Integer.toHexString(System.identityHashCode(getWorker())):"null")
     + outputString;
  }
}