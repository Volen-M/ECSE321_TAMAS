<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <title>Teaching Assistant Management System - Evaluation Page</title>
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

            <b>Evaluate Employee</b>

            <form action="submitevaluation.php" method="post" id="postevaluation">
                <textarea rows="10" cols="50" name="jobpostingdescription" placeholder="Enter employee evaluation here..." form="postjobform"></textarea>
                <p><input type="submit" value="Post Job Posting"></p>
                <p><button type="button">Return to Main Page</p>
            </form>

        </div>
    </body>
</html>