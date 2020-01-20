<?php
namespace ca\mcgill\ecse321\tamas\web\authentication;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes

  /**
   * We may plan to store hashed passwords locally in a file for each username in the system.
   */
  private $username;
  private $password;
  private $name;

  //User Associations
  private $authentication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aPassword, $aName)
  {
    $this->username = $aUsername;
    $this->password = $aPassword;
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

  public function setPassword($aPassword)
  {
    $wasSet = false;
    $this->password = $aPassword;
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

  public function getPassword()
  {
    return $this->password;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getAuthentication()
  {
    return $this->authentication;
  }

  public function hasAuthentication()
  {
    $has = $this->authentication != null;
    return $has;
  }

  public function setAuthentication($aAuthentication)
  {
    $wasSet = false;
    $existingAuthentication = $this->authentication;
    $this->authentication = $aAuthentication;
    if ($existingAuthentication != null && $existingAuthentication !== $aAuthentication)
    {
      $existingAuthentication->removeUser($this);
    }
    if ($aAuthentication != null && $aAuthentication !== $existingAuthentication)
    {
      $aAuthentication->addUser($this);
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
    if ($this->authentication != null)
    {
      $this->authentication->removeUser($this);
    }
  }

}
?>