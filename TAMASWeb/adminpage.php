<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <title>Teaching Assistant Management System - Admin Page</title>
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
    <body style="font-family: Arial;">
        <?php 
            session_start();
        ?>
        <div style="margin: auto;
                    display: block;
                    width: 300px;">
			
			<h2 align="center">Instructor</h2>
            <b>Manage Users</b>

            <select>
						<option value='opt1'>opt1</option>
				    </select>
            <p><button type='button'>Load User</button></p>
            <p>User Type</p>
            <input type='radio' name='user'>Course Worker<br>
            <input type='radio' name='user'>Instructor<br>
            <input type='radio' name='user'>Administrator

            <p>Username:<span><input type='text'></span></p>
            <p>Full Name:<span><input type='text'></span></p>
            <p>McGill ID:<span><input type='text'></span></p>
            <p><button type='button'>Save User</button></p>

            <p><b>Manage Jobs</b></p>

            <form action="addjobposting.php" method="post" id="postjobform">
                <p>Course: <span><input type="text" name="mainPageCourse"></span>
                <span class="error">
                     <?php
						if (isset($_SESSION['errormainPageCourse']) && !empty($_SESSION['errormainPageCourse'])) {
						 echo " * " . $_SESSION["errormainPageCourse"];
					}
					?>
				</span></p>
                <p>Job Title: <span><input type="text" name="mainPageJobTitle"></span>
                
                <span class="error">
                     <?php
						if (isset($_SESSION['errormainPageJobTitle']) && !empty($_SESSION['errormainPageJobTitle'])) {
						 echo " * " . $_SESSION["errormainPageJobTitle"];
					}
					?>
					
				</span></p>
                <label for="jobpostingdescription">Job Description:</label>
                <textarea rows="4" cols="50" name="jobpostingdescription" placeholder="Enter job description here..." form="postjobform"></textarea>
                <p><input type="submit" value="Post Job Posting"></p>
            </form>

        </div>
    </body>
</html>