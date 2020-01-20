<?php
require_once 'controller/Controller.php';
use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

$c = new Controller();
try {
	$deadlineDate = NULL;
	if(isset($_POST['Date'])) {
		$deadlineDate = $_POST['Date'];
	}
	
	$deadlineTime = NULL;
	if(isset($_POST['Time'])) {
		$deadlineTime = $_POST['Time'];
	}
	
	$rank = NULL;
	if(isset($_POST['rank'])) {
		$rank = $_POST['rank'];
	}
	
	$job = NULL;
	if(isset($_POST['Job'])) {
		$job = $_POST['Job'];
	}
	
	Controller::registerNewApplication($deadlineDate, $deadlineTime, $rank, $user, $job);
	$_SESSION["success"] = "Successfully applied to ".$job.".";
	
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
