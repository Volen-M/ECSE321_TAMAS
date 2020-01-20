<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Tamas
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tamas Associations
  private $courses;

  /**
   * We will want to ensure in our controller that we have atleast 1 user - if not make a default admin user.
   */
  private $users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->courses = array();
    $this->users = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getCourse_index($index)
  {
    $aCourse = $this->courses[$index];
    return $aCourse;
  }

  public function getCourses()
  {
    $newCourses = $this->courses;
    return $newCourses;
  }

  public function numberOfCourses()
  {
    $number = count($this->courses);
    return $number;
  }

  public function hasCourses()
  {
    $has = $this->numberOfCourses() > 0;
    return $has;
  }

  public function indexOfCourse($aCourse)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->courses as $course)
    {
      if ($course->equals($aCourse))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

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

  public static function minimumNumberOfCourses()
  {
    return 0;
  }

  public function addCourseVia($aName, $aBudget, $aStudentsEnrolled, $aCredits, $aHours, $aTutorialCount, $aLabCount)
  {
    return new Course($aName, $aBudget, $aStudentsEnrolled, $aCredits, $aHours, $aTutorialCount, $aLabCount, $this);
  }

  public function addCourse($aCourse)
  {
    $wasAdded = false;
    if ($this->indexOfCourse($aCourse) !== -1) { return false; }
    $existingTamas = $aCourse->getTamas();
    $isNewTamas = $existingTamas != null && $this !== $existingTamas;
    if ($isNewTamas)
    {
      $aCourse->setTamas($this);
    }
    else
    {
      $this->courses[] = $aCourse;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeCourse($aCourse)
  {
    $wasRemoved = false;
    //Unable to remove aCourse, as it must always have a tamas
    if ($this !== $aCourse->getTamas())
    {
      unset($this->courses[$this->indexOfCourse($aCourse)]);
      $this->courses = array_values($this->courses);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addCourseAt($aCourse, $index)
  {  
    $wasAdded = false;
    if($this->addCourse($aCourse))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfCourses()) { $index = $this->numberOfCourses() - 1; }
      array_splice($this->courses, $this->indexOfCourse($aCourse), 1);
      array_splice($this->courses, $index, 0, array($aCourse));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveCourseAt($aCourse, $index)
  {
    $wasAdded = false;
    if($this->indexOfCourse($aCourse) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfCourses()) { $index = $this->numberOfCourses() - 1; }
      array_splice($this->courses, $this->indexOfCourse($aCourse), 1);
      array_splice($this->courses, $index, 0, array($aCourse));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addCourseAt($aCourse, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfUsers()
  {
    return 0;
  }

  public function addUser($aUser)
  {
    $wasAdded = false;
    if ($this->indexOfUser($aUser) !== -1) { return false; }
    $existingTamas = $aUser->getTamas();
    if ($existingTamas == null)
    {
      $aUser->setTamas($this);
    }
    elseif ($this !== $existingTamas)
    {
      $existingTamas->removeUser($aUser);
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
      if ($this === $aUser->getTamas())
      {
        $aUser->setTamas(null);
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

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->courses) > 0)
    {
      $aCourse = $this->courses[count($this->courses) - 1];
      $aCourse->delete();
      unset($this->courses[$this->indexOfCourse($aCourse)]);
      $this->courses = array_values($this->courses);
    }
    
    while (count($this->users) > 0)
    {
      $aUser = $this->users[count($this->users) - 1];
      $aUser->delete();
      unset($this->users[$this->indexOfUser($aUser)]);
      $this->users = array_values($this->users);
    }
    
  }

}
?>