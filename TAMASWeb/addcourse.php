<?php
require_once 'controller/Controller.php';
use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

try {
	$coursename = NULL;
	if(isset($_POST['mainPageCourse'])) {
		$coursename = $_POST['mainPageCourse'];
	}
	
	$coursehours = NULL;
	if(isset($_POST['mainPageCourseHours'])) {
		$coursehours = $_POST['mainPageCourseHours'];
	}
	
	$labsessions = NULL;
	if(isset($_POST['mainPageLabSessions'])) {
		$labsessions = $_POST['mainPageLabSessions'];
	}
	
	$tutsessions = NULL;
	if(isset($_POST['mainPageTutorialSessions'])) {
		$tutsessions = $_POST['mainPageTutorialSessions'];
	}
	
	$studentnum = NULL;
	if(isset($_POST['mainPageEnrolledStudents'])) {
		$studentnum = $_POST['mainPageEnrolledStudents'];
	}
	
	$budget = NULL;
	if(isset($_POST['mainPageBudget'])) {
		$budget = $_POST['mainPageBudget'];
	}
	
	$credits = NULL;
	if(isset($_POST['mainPageCredits'])) {
		$credits = $_POST['mainPageCredits'];
	}

	Controller::addNewCourse($coursename, $budget, $studentnum, 3, $coursehours, $tutsessions, $labsessions);
} catch (Exception $e) {
	$errors = explode("@", $e->getMessage());
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/TAMASWeb/mainpage.php" />
	</head>
</html>