<?php
require_once 'controller/Controller.php';
use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

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
	
	$courseWorkerUsername = "test";
	$evaluatorUsername = "test2";
	$job = new \ca\mcgill\ecse321\tamas\web\model\Job();

	Controller::addCourseWorkerEvaluation($course, $job, $evaluatorUsername, $courseWorkerUsername, $description);
} catch (Exception $e) {
	$errors = explode("@", $e->getMessage());
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/TAMASWeb/evaluationpage.php" />
	</head>
</html>