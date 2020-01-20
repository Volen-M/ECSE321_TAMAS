<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Evaluation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Evaluation Attributes
  private $description;

  //Evaluation Associations
  private $course;
  private $job;
  private $evaluator;
  private $worker;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aDescription, $aCourse, $aJob, $aEvaluator, $aWorker)
  {
    $this->description = $aDescription;
    if (!$this->setCourse($aCourse))
    {
      throw new Exception("Unable to create Evaluation due to aCourse");
    }
    if (!$this->setJob($aJob))
    {
      throw new Exception("Unable to create Evaluation due to aJob");
    }
    if (!$this->setEvaluator($aEvaluator))
    {
      throw new Exception("Unable to create Evaluation due to aEvaluator");
    }
    if (!$this->setWorker($aWorker))
    {
      throw new Exception("Unable to create Evaluation due to aWorker");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setDescription($aDescription)
  {
    $wasSet = false;
    $this->description = $aDescription;
    $wasSet = true;
    return $wasSet;
  }

  public function getDescription()
  {
    return $this->description;
  }

  public function getCourse()
  {
    return $this->course;
  }

  public function getJob()
  {
    return $this->job;
  }

  public function getEvaluator()
  {
    return $this->evaluator;
  }

  public function getWorker()
  {
    return $this->worker;
  }

  public function setCourse($aNewCourse)
  {
    $wasSet = false;
    if ($aNewCourse != null)
    {
      $this->course = $aNewCourse;
      $wasSet = true;
    }
    return $wasSet;
  }

  public function setJob($aNewJob)
  {
    $wasSet = false;
    if ($aNewJob != null)
    {
      $this->job = $aNewJob;
      $wasSet = true;
    }
    return $wasSet;
  }

  public function setEvaluator($aNewEvaluator)
  {
    $wasSet = false;
    if ($aNewEvaluator != null)
    {
      $this->evaluator = $aNewEvaluator;
      $wasSet = true;
    }
    return $wasSet;
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

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->course = null;
    $this->job = null;
    $this->evaluator = null;
    $this->worker = null;
  }

}
?>