<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class JobApplication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //JobApplication Attributes
  private $applicationDate;
  private $applicationTime;

  /**
   * This is the preference number given by a TA/Grader for this job.
   */
  private $rank;

  //JobApplication State Machines
  private static $ApplicationStatusAPPLIED = 1;
  private static $ApplicationStatusAPPROVED = 2;
  private static $ApplicationStatusOFFER = 3;
  private static $ApplicationStatusACCEPTED = 4;
  private static $ApplicationStatusREJECTED = 5;
  private $applicationStatus;

  //JobApplication Associations
  private $worker;
  private $job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aApplicationDate, $aApplicationTime, $aRank, $aWorker, $aJob)
  {
    $this->applicationDate = $aApplicationDate;
    $this->applicationTime = $aApplicationTime;
    $this->rank = $aRank;
    if (!$this->setWorker($aWorker))
    {
      throw new Exception("Unable to create JobApplication due to aWorker");
    }
    $didAddJob = $this->setJob($aJob);
    if (!$didAddJob)
    {
      throw new Exception("Unable to create jobApplication due to job");
    }
    $this->setApplicationStatus(self::$ApplicationStatusAPPLIED);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setApplicationDate($aApplicationDate)
  {
    $wasSet = false;
    $this->applicationDate = $aApplicationDate;
    $wasSet = true;
    return $wasSet;
  }

  public function setApplicationTime($aApplicationTime)
  {
    $wasSet = false;
    $this->applicationTime = $aApplicationTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setRank($aRank)
  {
    $wasSet = false;
    $this->rank = $aRank;
    $wasSet = true;
    return $wasSet;
  }

  public function getApplicationDate()
  {
    return $this->applicationDate;
  }

  public function getApplicationTime()
  {
    return $this->applicationTime;
  }

  public function getRank()
  {
    return $this->rank;
  }

  public function getApplicationStatusFullName()
  {
    $answer = $this->getApplicationStatus();
    return $answer;
  }

  public function getApplicationStatus()
  {
    if ($this->applicationStatus == self::$ApplicationStatusAPPLIED) { return "ApplicationStatusAPPLIED"; }
    elseif ($this->applicationStatus == self::$ApplicationStatusAPPROVED) { return "ApplicationStatusAPPROVED"; }
    elseif ($this->applicationStatus == self::$ApplicationStatusOFFER) { return "ApplicationStatusOFFER"; }
    elseif ($this->applicationStatus == self::$ApplicationStatusACCEPTED) { return "ApplicationStatusACCEPTED"; }
    elseif ($this->applicationStatus == self::$ApplicationStatusREJECTED) { return "ApplicationStatusREJECTED"; }
    return null;
  }

  public function setApplicationStatus($aApplicationStatus)
  {
    if ($aApplicationStatus == "ApplicationStatusAPPLIED" || $aApplicationStatus == self::$ApplicationStatusAPPLIED)
    {
      $this->applicationStatus = self::$ApplicationStatusAPPLIED;
      return true;
    }
    elseif ($aApplicationStatus == "ApplicationStatusAPPROVED" || $aApplicationStatus == self::$ApplicationStatusAPPROVED)
    {
      $this->applicationStatus = self::$ApplicationStatusAPPROVED;
      return true;
    }
    elseif ($aApplicationStatus == "ApplicationStatusOFFER" || $aApplicationStatus == self::$ApplicationStatusOFFER)
    {
      $this->applicationStatus = self::$ApplicationStatusOFFER;
      return true;
    }
    elseif ($aApplicationStatus == "ApplicationStatusACCEPTED" || $aApplicationStatus == self::$ApplicationStatusACCEPTED)
    {
      $this->applicationStatus = self::$ApplicationStatusACCEPTED;
      return true;
    }
    elseif ($aApplicationStatus == "ApplicationStatusREJECTED" || $aApplicationStatus == self::$ApplicationStatusREJECTED)
    {
      $this->applicationStatus = self::$ApplicationStatusREJECTED;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getWorker()
  {
    return $this->worker;
  }

  public function getJob()
  {
    return $this->job;
  }

  public function setWorker($aNewWorker)
  {
    $wasSet = false;
    if ($aNewWorker != null)
    {
      $this->worker = $aNewWorker;
      $wasSet = true;
    }
    return $wasSet;
  }

  public function setJob($aJob)
  {
    $wasSet = false;
    if ($aJob == null)
    {
      return $wasSet;
    }
    
    $existingJob = $this->job;
    $this->job = $aJob;
    if ($existingJob != null && $existingJob != $aJob)
    {
      $existingJob->removeJobApplication($this);
    }
    $this->job->addJobApplication($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->worker = null;
    $placeholderJob = $this->job;
    $this->job = null;
    $placeholderJob->removeJobApplication($this);
  }

}
?>