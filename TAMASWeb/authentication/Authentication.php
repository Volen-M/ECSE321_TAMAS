<?php
namespace ca\mcgill\ecse321\tamas\web\authentication;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Authentication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Authentication Associations

  /**
   * We will want to ensure in our controller that we have atleast 1 user - if not make a default admin user.
   */
  private $users;
  private $currentUser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->users = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getUser_index($index)
  {
    $aUser = $this->users[$index];
    return $aUser;
  }

  public function getUsers()
  {
    $newUsers = $this->users;
    return $newUsers;
  }

  public function numberOfUsers()
  {
    $number = count($this->users);
    return $number;
  }

  public function hasUsers()
  {
    $has = $this->numberOfUsers() > 0;
    return $has;
  }

  public function indexOfUser($aUser)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->users as $user)
    {
      if ($user->equals($aUser))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getCurrentUser()
  {
    return $this->currentUser;
  }

  public function hasCurrentUser()
  {
    $has = $this->currentUser != null;
    return $has;
  }

  public static function minimumNumberOfUsers()
  {
    return 0;
  }

  public function addUser($aUser)
  {
    $wasAdded = false;
    if ($this->indexOfUser($aUser) !== -1) { return false; }
    $existingAuthentication = $aUser->getAuthentication();
    if ($existingAuthentication == null)
    {
      $aUser->setAuthentication($this);
    }
    elseif ($this !== $existingAuthentication)
    {
      $existingAuthentication->removeUser($aUser);
      $this->addUser($aUser);
    }
    else
    {
      $this->users[] = $aUser;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeUser($aUser)
  {
    $wasRemoved = false;
    if ($this->indexOfUser($aUser) != -1)
    {
      unset($this->users[$this->indexOfUser($aUser)]);
      $this->users = array_values($this->users);
      if ($this === $aUser->getAuthentication())
      {
        $aUser->setAuthentication(null);
      }
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addUserAt($aUser, $index)
  {  
    $wasAdded = false;
    if($this->addUser($aUser))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfUsers()) { $index = $this->numberOfUsers() - 1; }
      array_splice($this->users, $this->indexOfUser($aUser), 1);
      array_splice($this->users, $index, 0, array($aUser));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveUserAt($aUser, $index)
  {
    $wasAdded = false;
    if($this->indexOfUser($aUser) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfUsers()) { $index = $this->numberOfUsers() - 1; }
      array_splice($this->users, $this->indexOfUser($aUser), 1);
      array_splice($this->users, $index, 0, array($aUser));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addUserAt($aUser, $index);
    }
    return $wasAdded;
  }

  public function setCurrentUser($aNewCurrentUser)
  {
    $wasSet = false;
    $this->currentUser = $aNewCurrentUser;
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->users) > 0)
    {
      $aUser = $this->users[count($this->users) - 1];
      $aUser->delete();
      unset($this->users[$this->indexOfUser($aUser)]);
      $this->users = array_values($this->users);
    }
    
    $this->currentUser = null;
  }

}
?>