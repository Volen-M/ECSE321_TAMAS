<?php
namespace ca\mcgill\ecse321\tamas\web\authentication;
require_once __DIR__.'/User.php';
use \ca\mcgill\ecse321\tamas\web\authentication\User;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class DepartmentAdministrator extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aPassword, $aName)
  {
    parent::__construct($aUsername, $aPassword, $aName);
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