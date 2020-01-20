<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Job
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes

  /**
   * This enumeration decides how we expose the number of hours.
   */
  private $startTime;
  private $endTime;
  private $deadlineDate;
  private $deadlineTime;
  private $title;
  private $description;

  //Job State Machines
  private static $DayMONDAY = 1;
  private static $DayTUESDAY = 2;
  private static $DayWEDNESDAY = 3;
  private static $DayTHURSDAY = 4;
  private static $DayFRIDAY = 5;
  private static $DaySATURDAY = 6;
  private static $DaySUNDAY = 7;
  private $day;

  private static $TypeTUTORIAL = 1;
  private static $TypeLAB = 2;
  private static $TypeGRADER = 3;
  private $type;

  //Job Associations
  private $jobApplications;
  private $allocatedWorker;
  private $course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aStartTime, $aEndTime, $aDeadlineDate, $aDeadlineTime, $aTitle, $aDescription, $aCourse)
  {
    $this->startTime = $aStartTime;
    $this->endTime = $aEndTime;
    $this->deadlineDate = $aDeadlineDate;
    $this->deadlineTime = $aDeadlineTime;
    $this->title = $aTitle;
    $this->description = $aDescription;
    $this->jobApplications = array();
    $didAddCourse = $this->setCourse($aCourse);
    if (!$didAddCourse)
    {
      throw new Exception("Unable to create jobPosting due to course");
    }
    $this->setDay(self::$DayMONDAY);
    $this->setType(self::$TypeTUTORIAL);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setStartTime($aStartTime)
  {
    $wasSet = false;
    $this->startTime = $aStartTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setEndTime($aEndTime)
  {
    $wasSet = false;
    $this->endTime = $aEndTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setDeadlineDate($aDeadlineDate)
  {
    $wasSet = false;
    $this->deadlineDate = $aDeadlineDate;
    $wasSet = true;
    return $wasSet;
  }

  public function setDeadlineTime($aDeadlineTime)
  {
    $wasSet = false;
    $this->deadlineTime = $aDeadlineTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setTitle($aTitle)
  {
    $wasSet = false;
    $this->title = $aTitle;
    $wasSet = true;
    return $wasSet;
  }

  public function setDescription($aDescription)
  {
    $wasSet = false;
    $this->description = $aDescription;
    $wasSet = true;
    return $wasSet;
  }

  public function getStartTime()
  {
    return $this->startTime;
  }

  public function getEndTime()
  {
    return $this->endTime;
  }

  public function getDeadlineDate()
  {
    return $this->deadlineDate;
  }

  public function getDeadlineTime()
  {
    return $this->deadlineTime;
  }

  public function getTitle()
  {
    return $this->title;
  }

  public function getDescription()
  {
    return $this->description;
  }

  public function getDayFullName()
  {
    $answer = $this->getDay();
    return $answer;
  }

  public function getTypeFullName()
  {
    $answer = $this->getType();
    return $answer;
  }

  public function getDay()
  {
    if ($this->day == self::$DayMONDAY) { return "DayMONDAY"; }
    elseif ($this->day == self::$DayTUESDAY) { return "DayTUESDAY"; }
    elseif ($this->day == self::$DayWEDNESDAY) { return "DayWEDNESDAY"; }
    elseif ($this->day == self::$DayTHURSDAY) { return "DayTHURSDAY"; }
    elseif ($this->day == self::$DayFRIDAY) { return "DayFRIDAY"; }
    elseif ($this->day == self::$DaySATURDAY) { return "DaySATURDAY"; }
    elseif ($this->day == self::$DaySUNDAY) { return "DaySUNDAY"; }
    return null;
  }

  public function getType()
  {
    if ($this->type == self::$TypeTUTORIAL) { return "TypeTUTORIAL"; }
    elseif ($this->type == self::$TypeLAB) { return "TypeLAB"; }
    elseif ($this->type == self::$TypeGRADER) { return "TypeGRADER"; }
    return null;
  }

  public function setDay($aDay)
  {
    if ($aDay == "DayMONDAY" || $aDay == self::$DayMONDAY)
    {
      $this->day = self::$DayMONDAY;
      return true;
    }
    elseif ($aDay == "DayTUESDAY" || $aDay == self::$DayTUESDAY)
    {
      $this->day = self::$DayTUESDAY;
      return true;
    }
    elseif ($aDay == "DayWEDNESDAY" || $aDay == self::$DayWEDNESDAY)
    {
      $this->day = self::$DayWEDNESDAY;
      return true;
    }
    elseif ($aDay == "DayTHURSDAY" || $aDay == self::$DayTHURSDAY)
    {
      $this->day = self::$DayTHURSDAY;
      return true;
    }
    elseif ($aDay == "DayFRIDAY" || $aDay == self::$DayFRIDAY)
    {
      $this->day = self::$DayFRIDAY;
      return true;
    }
    elseif ($aDay == "DaySATURDAY" || $aDay == self::$DaySATURDAY)
    {
      $this->day = self::$DaySATURDAY;
      return true;
    }
    elseif ($aDay == "DaySUNDAY" || $aDay == self::$DaySUNDAY)
    {
      $this->day = self::$DaySUNDAY;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function setType($aType)
  {
    if ($aType == "TypeTUTORIAL" || $aType == self::$TypeTUTORIAL)
    {
      $this->type = self::$TypeTUTORIAL;
      return true;
    }
    elseif ($aType == "TypeLAB" || $aType == self::$TypeLAB)
    {
      $this->type = self::$TypeLAB;
      return true;
    }
    elseif ($aType == "TypeGRADER" || $aType == self::$TypeGRADER)
    {
      $this->type = self::$TypeGRADER;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getJobApplication_index($index)
  {
    $aJobApplication = $this->jobApplications[$index];
    return $aJobApplication;
  }

  public function getJobApplications()
  {
    $newJobApplications = $this->jobApplications;
    return $newJobApplications;
  }

  public function numberOfJobApplications()
  {
    $number = count($this->jobApplications);
    return $number;
  }

  public function hasJobApplications()
  {
    $has = $this->numberOfJobApplications() > 0;
    return $has;
  }

  public function indexOfJobApplication($aJobApplication)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobApplications as $jobApplication)
    {
      if ($jobApplication->equals($aJobApplication))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getAllocatedWorker()
  {
    return $this->allocatedWorker;
  }

  public function hasAllocatedWorker()
  {
    $has = $this->allocatedWorker != null;
    return $has;
  }

  public function getCourse()
  {
    return $this->course;
  }

  public static function minimumNumberOfJobApplications()
  {
    return 0;
  }

  public function addJobApplicationVia($aApplicationDate, $aApplicationTime, $aRank, $aWorker)
  {
    return new JobApplication($aApplicationDate, $aApplicationTime, $aRank, $aWorker, $this);
  }

  public function addJobApplication($aJobApplication)
  {
    $wasAdded = false;
    if ($this->indexOfJobApplication($aJobApplication) !== -1) { return false; }
    $existingJob = $aJobApplication->getJob();
    $isNewJob = $existingJob != null && $this !== $existingJob;
    if ($isNewJob)
    {
      $aJobApplication->setJob($this);
    }
    else
    {
      $this->jobApplications[] = $aJobApplication;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJobApplication($aJobApplication)
  {
    $wasRemoved = false;
    //Unable to remove aJobApplication, as it must always have a job
    if ($this !== $aJobApplication->getJob())
    {
      unset($this->jobApplications[$this->indexOfJobApplication($aJobApplication)]);
      $this->jobApplications = array_values($this->jobApplications);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobApplicationAt($aJobApplication, $index)
  {  
    $wasAdded = false;
    if($this->addJobApplication($aJobApplication))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobApplications()) { $index = $this->numberOfJobApplications() - 1; }
      array_splice($this->jobApplications, $this->indexOfJobApplication($aJobApplication), 1);
      array_splice($this->jobApplications, $index, 0, array($aJobApplication));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobApplicationAt($aJobApplication, $index)
  {
    $wasAdded = false;
    if($this->indexOfJobApplication($aJobApplication) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobApplications()) { $index = $this->numberOfJobApplications() - 1; }
      array_splice($this->jobApplications, $this->indexOfJobApplication($aJobApplication), 1);
      array_splice($this->jobApplications, $index, 0, array($aJobApplication));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobApplicationAt($aJobApplication, $index);
    }
    return $wasAdded;
  }

  public function setAllocatedWorker($aNewAllocatedWorker)
  {
    $wasSet = false;
    if ($aNewAllocatedWorker == null)
    {
      $existingAllocatedWorker = $this->allocatedWorker;
      $this->allocatedWorker = null;
      
      if ($existingAllocatedWorker != null && $existingAllocatedWorker->getJob() != null)
      {
        $existingAllocatedWorker->setJob(null);
      }
      $wasSet = true;
      return $wasSet;
    }
    
    $currentAllocatedWorker = $this->getAllocatedWorker();
    if ($currentAllocatedWorker != null && $currentAllocatedWorker != $aNewAllocatedWorker)
    {
      $currentAllocatedWorker->setJob(null);
    }
    
    $this->allocatedWorker = $aNewAllocatedWorker;
    $existingJob = $aNewAllocatedWorker->getJob();
    
    if ($this != $existingJob)
    {
      $aNewAllocatedWorker->setJob($this);
    }
    $wasSet = true;
    return $wasSet;
  }

  public function setCourse($aCourse)
  {
    $wasSet = false;
    if ($aCourse == null)
    {
      return $wasSet;
    }
    
    $existingCourse = $this->course;
    $this->course = $aCourse;
    if ($existingCourse != null && $existingCourse != $aCourse)
    {
      $existingCourse->removeJobPosting($this);
    }
    $this->course->addJobPosting($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->jobApplications) > 0)
    {
      $aJobApplication = $this->jobApplications[count($this->jobApplications) - 1];
      $aJobApplication->delete();
      unset($this->jobApplications[$this->indexOfJobApplication($aJobApplication)]);
      $this->jobApplications = array_values($this->jobApplications);
    }
    
    
    if ($this->allocatedWorker != null)
    {
        $this->allocatedWorker->delete();
        $this->allocatedWorker = null;
    }
    
    $placeholderCourse = $this->course;
    $this->course = null;
    $placeholderCourse->removeJobPosting($this);
  }

}
?>