<?php
namespace ca\mcgill\ecse321\tamas\web\persistence;

require_once __DIR__.'/../authentication/Authentication.php';
require_once __DIR__.'/../model/Tamas.php';

use \ca\mcgill\ecse321\tamas\web\authentication\Authentication;
use \ca\mcgill\ecse321\tamas\web\model\Tamas;

class Persistence {

    public static function saveModelToXML(Tamas &$tamas, $filename) {
        $str = serialize($tamas);
        // May need to change folder permissions on server: chmod 777 persistence
        // And set the right owner on this folder recursively: sudo chown -R www:www TAMASWeb
        file_put_contents($filename, $str);
    }

    public static function loadModelFromXML($filename) {
        if (file_exists($filename)) {
            $str = file_get_contents($filename);
            $tamas = unserialize($str);
            return $tamas;
        } else {
            $tamas = new Tamas();
            $str = serialize($tamas);
            file_put_contents($filename, $str);
            return $tamas;
        }
    }

    public static function saveAuthenticationToXML(Authentication &$authentication, $filename) {
        $str = serialize($authentication);
        // May need to change folder permissions on server: chmod 777 persistence
        // And set the right owner on this folder recursively: sudo chown -R www:www TAMASWeb
        file_put_contents($filename, $str);
    }

    public static function loadAuthenticationFromXML($filename) {
        if (file_exists($filename)) {
            $str = file_get_contents($filename);
            $authentication = unserialize($str);
            return $authentication;
        } else {
            $authentication = new Authentication();
            $str = serialize($authentication);
            file_put_contents($filename, $str);
            return $authentication;
        }
    }

}
?>