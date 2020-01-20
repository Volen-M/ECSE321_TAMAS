<?php
require_once 'controller/Controller.php';

use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

$_SESSION["errorRegister"] = "";
$_SESSION["successRegister"] = "";
try {
    $username = NULL;
    if(isset($_POST['registerUsername'])) {
        $username = $_POST['registerUsername'];
    } else {
        $_SESSION["errorRegister"] = "Please enther a username to register.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    $password = NULL;
    if(isset($_POST['registerPassword'])) {
        $password = $_POST['registerPassword'];
    } else {
        $_SESSION["errorRegister"] = "Please enther a password to register.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    $repeatpassword = NULL;
    if(isset($_POST['registerRepeatPassword'])) {
        $repeatpassword = $_POST['registerRepeatPassword'];
    } else {
        $_SESSION["errorRegister"] = "Please enther the same password twice to register.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    $mcgillID = NULL;
    if(isset($_POST['registerMcGillID'])) {
        $name = $_POST['registerMcGillID'];
    } else {
        $_SESSION["errorRegister"] = "Please enther a McGill ID for the new user to register.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    $name = NULL;
    if(isset($_POST['registerFullName'])) {
        $name = $_POST['registerFullName'];
    } else {
        $_SESSION["errorRegister"] = "Please enther a name for the new user to register.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    Controller::loadAuthentication();
    Controller::loadModel();
    try{
        Controller::registerNewUser($username, $password, $repeatpassword, $name, $mcgillID);
        $_SESSION["successRegister"] = "Successfully registered user ".$username.".";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    } catch (\Exception $e) {
        $_SESSION["errorRegister"] = $e->getMessage();
        echo "<!DOCTYPE html>
          <html>
              <head>
                  <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
              </head>
          </html>";
    }
} catch (\Exception $e) {
    $_SESSION["errorRegister"] = $e->getMessage();
    echo "<!DOCTYPE html>
          <html>
              <head>
                  <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
              </head>
          </html>";
}
?>