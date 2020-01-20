<?php
require_once 'controller/Controller.php';
use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

$c = new Controller();
try {
	$course = NULL;
	if(isset($_POST['mainPageCourse'])) {
		$course = $_POST['mainPageCourse'];
	}
	
	$jobtitle = NULL;
	if(isset($_POST['mainPageJobTitle'])) {
		$jobtitle = $_POST['mainPageJobTitle'];
	}
	
	$description = NULL;
	if(isset($_POST['jobpostingdescription'])) {
		$description = $_POST['jobpostingdescription'];
	}
	
	$day = 1;
	$type = 1;
	$startTime = new \DateTime();
	$endTime = new \DateTime();
	$deadlineDate = new \DateTime();
	$deadlineTime = new \DateTime();

	Controller::addCourseJob($jobtitle, $description, $course, $day, $type, $startTime, $endTime, $deadlineDate, $deadlineTime);
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