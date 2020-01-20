<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <title>Teaching Assistant Management System</title>
        <style>
            .error {color: #FF0000;}
            .success {color: #409F40;}

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

            <h2 align="center">TAMAS</h2>
            <h3 align="center">Teaching Assistant Management System</h3>

            <form action="login.php" method="post">
                <span class="error">
                <?php
                if(isset($_SESSION['errorLogin']) && !empty($_SESSION['errorLogin'])) {
                    echo $_SESSION["errorLogin"];
                }
                ?>
                </span>
                <p>Username: <span><input type="text" name="loginUsername"></span></p>
                <p>Password: <span><input type="password" name="loginPassword"></span></p>
                <p><input type="submit" value="Login"></p>
            </form>

            <form action="register.php" method="post">
                <span class="error">
                <?php
                if(isset($_SESSION['errorRegister']) && !empty($_SESSION['errorRegister'])) {
                    echo $_SESSION["errorRegister"];
                }
                ?>
                </span>
                <span class="success">
                <?php
                if(isset($_SESSION['successRegister']) && !empty($_SESSION['successRegister'])) {
                	echo $_SESSION["successRegister"];
                }
                ?>
                </span>
                <p>New Username: <span><input type="text" name="registerUsername"></span></p>
                <p>New Password: <span><input type="password" name="registerPassword"></span></p>
                <p>Confirm Password: <span><input type="password" name="registerRepeatPassword"></span></p>
                <p>McGill ID: <span><input style="width: 100%;" type="number" name="registerMcGillID"></span></p>
                <p>Full Name: <span><input type="text" name="registerFullName"></span></p>
                <p><input type="submit" value="Register"/></p>
            </form>

        </div>
    </body>
</html>