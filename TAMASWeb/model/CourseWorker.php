<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/
require_once __DIR__.'/User.php';

class CourseWorker extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseWorker Attributes
  private $hoursOfWorkAllocated;
  private $mcgillID;
  private $pastWorkerExperience;

  //CourseWorker State Machines
  private static $AcademicStatusUNDERGRADUATE = 1;
  private static $AcademicStatusGRADUATE = 2;
  private $academicStatus;

  //CourseWorker Associations

  /**
   * Maximum of 3 job applications.
   */
  private $jobApplications;
  private $evals;
  private $job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aName, $aHoursOfWorkAllocated, $aMcgillID, $aPastWorkerExperience)
  {
    parent::__construct($aUsername, $aName);
    $this->hoursOfWorkAllocated = $aHoursOfWorkAllocated;
    $this->mcgillID = $aMcgillID;
    $this->pastWorkerExperience = $aPastWorkerExperience;
    $this->jobApplications = array();
    $this->evals = array();
    $this->setAcademicStatus(self::$AcademicStatusUNDERGRADUATE);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setHoursOfWorkAllocated($aHoursOfWorkAllocated)
  {
    $wasSet = false;
    $this->hoursOfWorkAllocated = $aHoursOfWorkAllocated;
    $wasSet = true;
    return $wasSet;
  }

  public function setMcgillID($aMcgillID)
  {
    $wasSet = false;
    $this->mcgillID = $aMcgillID;
    $wasSet = true;
    return $wasSet;
  }

  public function setPastWorkerExperience($aPastWorkerExperience)
  {
    $wasSet = false;
    $this->pastWorkerExperience = $aPastWorkerExperience;
    $wasSet = true;
    return $wasSet;
  }

  public function getHoursOfWorkAllocated()
  {
    return $this->hoursOfWorkAllocated;
  }

  public function getMcgillID()
  {
    return $this->mcgillID;
  }

  public function getPastWorkerExperience()
  {
    return $this->pastWorkerExperience;
  }

  public function getAcademicStatusFullName()
  {
    $answer = $this->getAcademicStatus();
    return $answer;
  }

  public function getAcademicStatus()
  {
    if ($this->academicStatus == self::$AcademicStatusUNDERGRADUATE) { return "AcademicStatusUNDERGRADUATE"; }
    elseif ($this->academicStatus == self::$AcademicStatusGRADUATE) { return "AcademicStatusGRADUATE"; }
    return null;
  }

  public function setAcademicStatus($aAcademicStatus)
  {
    if ($aAcademicStatus == "AcademicStatusUNDERGRADUATE" || $aAcademicStatus == self::$AcademicStatusUNDERGRADUATE)
    {
      $this->academicStatus = self::$AcademicStatusUNDERGRADUATE;
      return true;
    }
    elseif ($aAcademicStatus == "AcademicStatusGRADUATE" || $aAcademicStatus == self::$AcademicStatusGRADUATE)
    {
      $this->academicStatus = self::$AcademicStatusGRADUATE;
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

  public function getEval_index($index)
  {
    $aEval = $this->evals[$index];
    return $aEval;
  }

  public function getEvals()
  {
    $newEvals = $this->evals;
    return $newEvals;
  }

  public function numberOfEvals()
  {
    $number = count($this->evals);
    return $number;
  }

  public function hasEvals()
  {
    $has = $this->numberOfEvals() > 0;
    return $has;
  }

  public function indexOfEval($aEval)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->evals as $eval)
    {
      if ($eval->equals($aEval))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getJob()
  {
    return $this->job;
  }

  public function hasJob()
  {
    $has = $this->job != null;
    return $has;
  }

  public static function minimumNumberOfJobApplications()
  {
    return 0;
  }

  public static function maximumNumberOfJobApplications()
  {
    return 3;
  }

  public function addJobApplication($aJobApplication)
  {
    $wasAdded = false;
    if ($this->indexOfJobApplication($aJobApplication) !== -1) { return false; }
    if ($this->numberOfJobApplications() < self::maximumNumberOfJobApplications())
    {
      $this->jobApplications[] = $aJobApplication;
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function removeJobApplication($aJobApplication)
  {
    $wasRemoved = false;
    if ($this->indexOfJobApplication($aJobApplication) != -1)
    {
      unset($this->jobApplications[$this->indexOfJobApplication($aJobApplication)]);
      $this->jobApplications = array_values($this->jobApplications);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function setJobApplications($newJobApplications)
  {
    $wasSet = false;
    $verifiedJobApplications = array();
    foreach ($newJobApplications as $aJobApplication)
    {
      if (array_search($aJobApplication,$verifiedJobApplications) !== false)
      {
        continue;
      }
      $verifiedJobApplications[] = $aJobApplication;
    }

    if (count($verifiedJobApplications) != count($newJobApplications) || count($verifiedJobApplications) > self::maximumNumberOfJobApplications())
    {
      return $wasSet;
    }

    $this->jobApplications = $verifiedJobApplications;
    $wasSet = true;
    return $wasSet;
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

  public static function minimumNumberOfEvals()
  {
    return 0;
  }

  public function addEval($aEval)
  {
    $wasAdded = false;
    if ($this->indexOfEval($aEval) !== -1) { return false; }
    $this->evals[] = $aEval;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeEval($aEval)
  {
    $wasRemoved = false;
    if ($this->indexOfEval($aEval) != -1)
    {
      unset($this->evals[$this->indexOfEval($aEval)]);
      $this->evals = array_values($this->evals);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addEvalAt($aEval, $index)
  {  
    $wasAdded = false;
    if($this->addEval($aEval))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEvals()) { $index = $this->numberOfEvals() - 1; }
      array_splice($this->evals, $this->indexOfEval($aEval), 1);
      array_splice($this->evals, $index, 0, array($aEval));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveEvalAt($aEval, $index)
  {
    $wasAdded = false;
    if($this->indexOfEval($aEval) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEvals()) { $index = $this->numberOfEvals() - 1; }
      array_splice($this->evals, $this->indexOfEval($aEval), 1);
      array_splice($this->evals, $index, 0, array($aEval));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addEvalAt($aEval, $index);
    }
    return $wasAdded;
  }

  public function setJob($aNewJob)
  {
    $wasSet = false;
    if ($aNewJob == null)
    {
      $existingJob = $this->job;
      $this->job = null;
      
      if ($existingJob != null && $existingJob->getAllocatedWorker() != null)
      {
        $existingJob->setAllocatedWorker(null);
      }
      $wasSet = true;
      return $wasSet;
    }
    
    $currentJob = $this->getJob();
    if ($currentJob != null && $currentJob != $aNewJob)
    {
      $currentJob->setAllocatedWorker(null);
    }
    
    $this->job = $aNewJob;
    $existingAllocatedWorker = $aNewJob->getAllocatedWorker();
    
    if ($this != $existingAllocatedWorker)
    {
      $aNewJob->setAllocatedWorker($this);
    }
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->jobApplications = array();
    $this->evals = array();
    if ($this->job != null)
    {
      $this->job->setAllocatedWorker(null);
    }
    parent::delete();
  }

}
?>