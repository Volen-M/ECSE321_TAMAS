<?php
namespace ca\mcgill\ecse321\tamas\web\controller;

class InputValidator
{
  public static function validate_input($data)
  {
    $data = trim($data);
	$data = stripslashes($data);
	$data = htmlspecialchars($data);
	return $data;
  }
}
?>