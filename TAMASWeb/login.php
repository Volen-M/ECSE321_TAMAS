<?php
require_once 'controller/Controller.php';

use \ca\mcgill\ecse321\tamas\web\controller\Controller;

session_start();

$_SESSION["errorLogin"] = "";
try {
    $username = NULL;
    if(isset($_POST['loginUsername'])) {
        $username = $_POST['loginUsername'];
    } else {
        $_SESSION["errorLogin"] = "Error - bad username or password.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    $password = NULL;
    if(isset($_POST['loginPassword'])) {
        $password = $_POST['loginPassword'];
    } else {
    	$_SESSION["errorLogin"] = "Error - bad username or password.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    }
    Controller::loadAuthentication();
    Controller::loadModel();
    if(is_null(Controller::verifyAuthenticationCredentials($username, $password))){
        $_SESSION["errorLogin"] = "Error - bad username or password.";
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
                  </head>
              </html>";
    } else {
        echo "<!DOCTYPE html>
              <html>
                  <head>
                      <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/mainpage.php?username=".$username."\" />
                  </head>
              </html>";
    }
} catch (Exception $e) {
    $_SESSION["errorLogin"] = $e->getMessage();
    echo "<!DOCTYPE html>
          <html>
              <head>
                  <meta http-equiv=\"refresh\" content=\"0; url=/TAMASWeb/index.php\" />
              </head>
          </html>";
}
?>