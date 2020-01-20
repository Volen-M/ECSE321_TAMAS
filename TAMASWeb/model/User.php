<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private $username;
  private $name;

  //User Associations
  private $tamas;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aName)
  {
    $this->username = $aUsername;
    $this->name = $aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setUsername($aUsername)
  {
    $wasSet = false;
    $this->username = $aUsername;
    $wasSet = true;
    return $wasSet;
  }

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function getUsername()
  {
    return $this->username;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getTamas()
  {
    return $this->tamas;
  }

  public function hasTamas()
  {
    $has = $this->tamas != null;
    return $has;
  }

  public function setTamas($aTamas)
  {
    $wasSet = false;
    $existingTamas = $this->tamas;
    $this->tamas = $aTamas;
    if ($existingTamas != null && $existingTamas !== $aTamas)
    {
      $existingTamas->removeUser($this);
    }
    if ($aTamas != null && $aTamas !== $existingTamas)
    {
      $aTamas->addUser($this);
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
    if ($this->tamas != null)
    {
      $this->tamas->removeUser($this);
    }
  }

}
?>