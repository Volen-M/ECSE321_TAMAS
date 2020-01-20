<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Course
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private $name;
  private $budget;
  private $studentsEnrolled;
  private $credits;
  private $hours;
  private $tutorialCount;
  private $labCount;

  //Course Associations
  private $jobPostings;
  private $tamas;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aBudget, $aStudentsEnrolled, $aCredits, $aHours, $aTutorialCount, $aLabCount, $aTamas)
  {
    $this->name = $aName;
    $this->budget = $aBudget;
    $this->studentsEnrolled = $aStudentsEnrolled;
    $this->credits = $aCredits;
    $this->hours = $aHours;
    $this->tutorialCount = $aTutorialCount;
    $this->labCount = $aLabCount;
    $this->jobPostings = array();
    $didAddTamas = $this->setTamas($aTamas);
    if (!$didAddTamas)
    {
      throw new Exception("Unable to create course due to tamas");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function setBudget($aBudget)
  {
    $wasSet = false;
    $this->budget = $aBudget;
    $wasSet = true;
    return $wasSet;
  }

  public function setStudentsEnrolled($aStudentsEnrolled)
  {
    $wasSet = false;
    $this->studentsEnrolled = $aStudentsEnrolled;
    $wasSet = true;
    return $wasSet;
  }

  public function setCredits($aCredits)
  {
    $wasSet = false;
    $this->credits = $aCredits;
    $wasSet = true;
    return $wasSet;
  }

  public function setHours($aHours)
  {
    $wasSet = false;
    $this->hours = $aHours;
    $wasSet = true;
    return $wasSet;
  }

  public function setTutorialCount($aTutorialCount)
  {
    $wasSet = false;
    $this->tutorialCount = $aTutorialCount;
    $wasSet = true;
    return $wasSet;
  }

  public function setLabCount($aLabCount)
  {
    $wasSet = false;
    $this->labCount = $aLabCount;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getBudget()
  {
    return $this->budget;
  }

  public function getStudentsEnrolled()
  {
    return $this->studentsEnrolled;
  }

  public function getCredits()
  {
    return $this->credits;
  }

  public function getHours()
  {
    return $this->hours;
  }

  public function getTutorialCount()
  {
    return $this->tutorialCount;
  }

  public function getLabCount()
  {
    return $this->labCount;
  }

  public function getJobPosting_index($index)
  {
    $aJobPosting = $this->jobPostings[$index];
    return $aJobPosting;
  }

  public function getJobPostings()
  {
    $newJobPostings = $this->jobPostings;
    return $newJobPostings;
  }

  public function numberOfJobPostings()
  {
    $number = count($this->jobPostings);
    return $number;
  }

  public function hasJobPostings()
  {
    $has = $this->numberOfJobPostings() > 0;
    return $has;
  }

  public function indexOfJobPosting($aJobPosting)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobPostings as $jobPosting)
    {
      if ($jobPosting->equals($aJobPosting))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getTamas()
  {
    return $this->tamas;
  }

  public static function minimumNumberOfJobPostings()
  {
    return 0;
  }

  public function addJobPostingVia($aStartTime, $aEndTime, $aDeadlineDate, $aDeadlineTime, $aTitle, $aDescription)
  {
    return new Job($aStartTime, $aEndTime, $aDeadlineDate, $aDeadlineTime, $aTitle, $aDescription, $this);
  }

  public function addJobPosting($aJobPosting)
  {
    $wasAdded = false;
    if ($this->indexOfJobPosting($aJobPosting) !== -1) { return false; }
    $existingCourse = $aJobPosting->getCourse();
    $isNewCourse = $existingCourse != null && $this !== $existingCourse;
    if ($isNewCourse)
    {
      $aJobPosting->setCourse($this);
    }
    else
    {
      $this->jobPostings[] = $aJobPosting;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJobPosting($aJobPosting)
  {
    $wasRemoved = false;
    //Unable to remove aJobPosting, as it must always have a course
    if ($this !== $aJobPosting->getCourse())
    {
      unset($this->jobPostings[$this->indexOfJobPosting($aJobPosting)]);
      $this->jobPostings = array_values($this->jobPostings);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobPostingAt($aJobPosting, $index)
  {  
    $wasAdded = false;
    if($this->addJobPosting($aJobPosting))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobPostings()) { $index = $this->numberOfJobPostings() - 1; }
      array_splice($this->jobPostings, $this->indexOfJobPosting($aJobPosting), 1);
      array_splice($this->jobPostings, $index, 0, array($aJobPosting));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobPostingAt($aJobPosting, $index)
  {
    $wasAdded = false;
    if($this->indexOfJobPosting($aJobPosting) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobPostings()) { $index = $this->numberOfJobPostings() - 1; }
      array_splice($this->jobPostings, $this->indexOfJobPosting($aJobPosting), 1);
      array_splice($this->jobPostings, $index, 0, array($aJobPosting));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobPostingAt($aJobPosting, $index);
    }
    return $wasAdded;
  }

  public function setTamas($aTamas)
  {
    $wasSet = false;
    if ($aTamas == null)
    {
      return $wasSet;
    }
    
    $existingTamas = $this->tamas;
    $this->tamas = $aTamas;
    if ($existingTamas != null && $existingTamas != $aTamas)
    {
      $existingTamas->removeCourse($this);
    }
    $this->tamas->addCourse($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->jobPostings) > 0)
    {
      $aJobPosting = $this->jobPostings[count($this->jobPostings) - 1];
      $aJobPosting->delete();
      unset($this->jobPostings[$this->indexOfJobPosting($aJobPosting)]);
      $this->jobPostings = array_values($this->jobPostings);
    }
    
    $placeholderTamas = $this->tamas;
    $this->tamas = null;
    $placeholderTamas->removeCourse($this);
  }

}
?>