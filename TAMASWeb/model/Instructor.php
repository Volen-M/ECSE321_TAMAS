<?php
namespace ca\mcgill\ecse321\tamas\web\model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/
require_once __DIR__.'/User.php';

class Instructor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aName)
  {
    parent::__construct($aUsername, $aName);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    parent::delete();
  }

}
?>