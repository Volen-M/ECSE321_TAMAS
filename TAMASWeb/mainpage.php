<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<link rel='shortcut icon' href='favicon.ico' type='image/x-icon'>
<title>Teaching Assistant Management System Main Page</title>
<style>
.error {color: #FF0000;}

input[type=text], select {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=password], select {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

textarea {
	resize: none;
	width: 98%;
}

input[type=submit] {
	width: 100%;
	background-color: #4C8BAF;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=submit]:hover {
	background-color: #4CA0AF;
}

button[type=button] {
	width: 100%;
	background-color: #4C8BAF;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button[type=button]:hover {
	background-color: #4CA0AF;
}

div {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
</head>
<body style='font-family: Arial;'>
<div style='margin: auto;
                    display: block;
                    width: 300px;'>
                    <?php
                    require_once 'authentication/Authentication.php';
                    require_once 'controller/Controller.php';
                    require_once 'model/JobApplication.php';
                

                    use \ca\mcgill\ecse321\tamas\web\controller\Controller;
                    use \ca\mcgill\ecse321\tamas\web\authentication\Authentication;
				

                    session_start();

                    Controller::loadAuthentication();
                    Controller::loadModel();


                    $users = Controller::getAuthenticationUsers();
                    foreach ($users as $user) {
                    	if($user->getUsername() === $_GET["username"]) {
                    		Controller::setCurrentUser($user);
                    	}
                    }
                    

                    if(Controller::getCurrentUser() instanceof \ca\mcgill\ecse321\tamas\web\authentication\CourseWorker){?>
			
			<h2 align="center">Course Worker</h2>
            <form action='addjobapplication.php' method=post id='postjobform'>
  					
  					<p> <strong>For CourseWorker</strong><p>
                	<p><strong> Course List</strong><p>
                   	<select>
						<?php
						$courses = Controller::getCourseList();
						foreach ($courses as $course) {
							echo "<option value=\"".$course."\">".$course."</option>";	
							}
						
						?>
				    </select>
                    <p> <button type='button'>Load Job List</button></p>
                  	<p> <strong>Job List</strong></p>
                    <select>
  						<?php
  						$jobs = Controller:: getCourseJobPostings();
  						foreach ($jobs as $job) {
  							echo "<option value=\"".$job."\">".$job."</option>";
  						}
						?>
				    </select>
                    <p> <button type='button' name ='Job'>Load Job</button></p>        
               	    <p>Job Title: <span><input type='text' name='mainPageJobTitle'></span></p>
                    <label for='jobpostingdescription'>Job Description:</label>
                    <textarea rows='4' cols='50' name='jobpostingdescription' placeholder='Enter job description here...' form='postjobform'></textarea>
                    <p> <strong>Application Deadline Information:</strong> <p> 
                    <p>Application Deadline Date: <input type='date' name='Date'></p>
               	    <p> Application Deadline Time: <input type='time' name='Time'> </p> 

           		<p> Working Days </p> 
                  <input type='checkbox' > Monday 
  				  <input type='checkbox'> Tuesday 
                  <input type='checkbox' > Wednesday 
  				  <input type='checkbox'> Thursday  
				  <input type='checkbox' > Friday 

				<p> Ranking </p>
		          <input type='radio' name='rank' >1 
  				  <input type='radio' name='rank'> 2
                  <input type='radio' name='rank'> 3  
				
                <p> <strong>Profile Description</strong> <textarea rows='4' cols='50' name='profiledescription' placeholder='Enter profile description here...' form='postjobform'></textarea></p> 
                
                <p> Education level <p> 
                  <input type='radio' name='educ' > Graduate 
  				  <input type='radio' name='educ'> Undergraduate
                  
                <p> <button type='button' name= 'apply'>Apply to Job</button></p>
                <p> <button type='button'>Accept Job offer</button></p>
                <p> <button type='button'>Decline Job offer</button></p>

            	</form>
				
<?php    		
} else if (Controller::getCurrentUser() instanceof \ca\mcgill\ecse321\tamas\web\authentication\Instructor) {
 ?>
			<h2 align="center">Instructor</h2>
            <form action='addjobposting.php' method='post' id='postjobform'>
            
          
                <p>Course Title: <span><input type=text name='mainPageCourseTitle'></span></p>                
                <p>Amount of Course hours: <span><input type=text name='mainPageCourseHours'></span></p>               
                <p>Amount of Lab Sessions: <span><input type=text name='mainPageLabSessions'></span></p>
                <p>Amount of Tutorial Sessions: <span><input type=text name='mainPageTutorialSessions'></span></p>
                <p>Amount of Enrolled Students: <span><input type=text name='mainPageEnrolledStudents'></span></p>            
                <p>Job Title: <span><input type='text' name='mainPageJobTitle'></span></p>
                <label for='jobpostingdescription'>Job Description:</label>
                <textarea rows='4' cols='50' name='jobpostingdescription' placeholder='Enter job description here...' form='postjobform'></textarea>
                <p> <strong>Application Deadline Information:</strong> <p> 
                <p>Application Deadline Date: <input type='date' name='Date'></p>
               	<p> Application Deadline Time: <input type='time' name='Time'> </p>
                <p> Job information </p> 
                <p> Work Start Time <input type='time' name='Time'> 
                <p> Work End Time<input type='time' name='Time'></p> 
           		<p> Working Days </p> 
                  <input type='checkbox' > Monday 
  				  <input type='checkbox'> Tuesday 
                  <input type='checkbox' > Wednesday 
  				  <input type='checkbox'> Thursday  
				  <input type='checkbox' > Friday 
                <p>Total Worked Hours per week: <span><input type='text' name='mainPageWorkedHours'></span></p>
                  <input type='radio' name = 'position' > Grader 
  				  <input type='radio' name = 'position'> Teaching Assistant
                  <input type='radio' name = 'position' > Lab Technician 
                <p> <button type='button'>Add Job</button></p>
                <p> <button type='button'>Delete Job</button></p>
                
                <p> <strong>Applicant List</strong><p>
                   <select>
						<?php
  						$cws = Controller:: getCourseWorkerJobApplications($username);
  						foreach ($cws as $cw) {
  							echo "<option value=\"".$cw."\">".$cw."</option>";
  						}

						?>
				    </select>
                    <p> <button type='button'>View Applicant Information</button></p>
 
            </form>
<?php
} else if (Controller::getCurrentUser() instanceof \ca\mcgill\ecse321\tamas\web\authentication\DepartmentAdministrator) {

	?>

   
            <form action='addcourse.php' method='post' id='postjobform'>
            	
               <p> <strong>For Administrator</strong><p>
                <p> <strong>Course List</strong><p>
                    <select>
						<?php 
  						$courses = Controller::getCourseList();
						foreach ($courses as $course) {
							echo "<option value=\"".$course."\">".$course."</option>";
							}
							?>
				    </select>
                <p> <button type='button'>Load Job List</button></p>
                <p>Course Title: <span><input type='text' name='mainPageCourseTitle'></span></p>
                <p>Amount of Course hours: <span><input type='text' name='mainPageCourseHours'></span></p>
                <p>Amount of Lab Sessions: <span><input type='text' name='mainPageLabSessions'></span></p>
                <p>Amount of Tutorial Sessions: <span><input type='text' name='mainPageTutorialSessions'></span></p>
                <p>Amount of Enrolled Students: <span><input type='text' name='mainPageEnrolledStudents'></span></p>
                <p>Number of Credits: <span><input type='text' name='mainPageCredits'></span></p>
                <p>Budget Amount (CAD): <span><input type='text' name='mainPageBudget'></span></p>
       
                <p> <input type='submit' value='Add Course'></p>
                <p> <button type='button'>Delete Course</button></p>
                <p> <button type='button' >Open Admin Page</button></p>

                
           	</form>
           	
			<form action='addjobposting.php' method='post' id='postjobform'>  
                 <p> <strong>Job List</strong><p>
                   <select>
						<?php 
  						$courses = Controller::getCourseList();
						foreach ($courses as $course) {
							echo "<option value=\"".$course."\">".$course."</option>";
							}
							?>
				   </select>
                 <p> <button type='button'>Load Job</button></p>        
                 <p>Job Title: <span><input type='text' name='mainPageJobTitle'></span></p>
                <label for='jobpostingdescription'>Job Description:</label>
                <textarea rows='4' cols='50' name='jobpostingdescription' placeholder='Enter job description here...' form='postjobform'></textarea>

                 <p> <strong>Application Deadline Information:</strong> <p> 
               
               
                 <p>Application Deadline Date: <input type='date' name='Date'></p>
               	 <p> Application Deadline Time: <input type='time' name='Time'></p> 
   				
                 <p> Job information </p> 
                
                 <p> Work Start Time <input type='time' name='Time'> </p> 
                
                 <p> Work End Time<input type='time' name='Time'></p> 
                
               
           		 <p> Working Days </p> 
                  <input type='checkbox' > Monday 
  				  <input type='checkbox'> Tuesday 
                  <input type='checkbox' > Wednesday 
  				  <input type='checkbox'> Thursday  
				  <input type='checkbox' > Friday 
              
                 
                 <p>Total Worked Hours per week: <span><input type='text' name='mainPageWorkedHours'></span></p>
 
                  <input type='radio' name = 'position' > Grader 
  				  <input type='radio' name = 'position'> Teaching Assistant
                  <input type='radio' name = 'position'> Lab Technician 
                 <p> <button type='button'>Post Job</button></p>
                 <p> <strong>Applicant List</strong><p>
                   <select>
						<?php 
  						$courses = Controller::getCourseList();
						foreach ($courses as $course) {
							echo "<option value=\"".$course."\">".$course."</option>";
							}
							?>
				    </select>
				  
                    <p> <button type='button'>View Applicant Information</button></p>
                 <p> Profile Description <textarea rows='4' cols='50' name='jobpostingdescription' placeholder='Enter job description here...' form='postjobform'></textarea></p> 
                
                 <p> Education level <p> 
                  <input type='radio' name = 'educ' > Graduate 
  				  <input type='radio' name = 'educ'> Undergraduate
  				   
                 <p> <button type='button'>Accept Employment</button></p>
                 <p> <button type='button'>Reject Employment</button></p>
                 
                  </form>
                     
<?php
} else {
    throw new Exception("Invalid current user.");
}
?>
        </div>
    </body>
</html>