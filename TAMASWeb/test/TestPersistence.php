<?php
namespace ca\mcgill\ecse321\tamas\web\persistence;

require_once 'PHPUnit/Autoload.php';
require_once __DIR__.'/../authentication/Authentication.php';
require_once __DIR__.'/../model/Tamas.php';
require_once __DIR__.'/../persistence/Persistence.php';

use \ca\mcgill\ecse321\tamas\web\authentication\Authentication;
use \ca\mcgill\ecse321\tamas\web\model\Tamas;
use \ca\mcgill\ecse321\tamas\web\persistence\Persistence;
use PHPUnit\Framework\TestCase;

class TestPersistence extends \PHPUnit_Framework_TestCase {
	public static function setUpBeforeClass () 
	{
		if (file_exists("data.xml"))
		{
			unlink("data.xml");
		}
		if (file_exists("users.xml"))
		{
			unlink("users.xml");
		}
	}
	public function tearDown () 
	{
		if (file_exists("data.xml"))
		{
			unlink("data.xml");
		}
		if (file_exists("users.xml"))
		{
			unlink("users.xml");
		}
	}
	public function testSaveModelToXML() 
	{
		Persistence::saveModelToXML(new Tamas(), "data.xml");
		if (!file_exists("data.xml"))
		{
			$this->fail("Failed to save a Model instance to data.xml.");
		}
	}
	public function testLoadModelFromXML() 
	{
		Persistence::saveModelToXML(new Tamas(), "data.xml");
		if (!file_exists("data.xml"))
		{
			$this->fail("Failed to save a Model instance to data.xml.");
		}
		$tamas = Persistence::loadModelFromXML("data.xml");
		if (is_null($tamas))
		{
			$this->fail("Failed to load a Model instance from data.xml");
		}
	}
	public function testSaveAuthenticationToXML() 
	{
		Persistence::saveAuthenticationToXML(new Authentication(), "users.xml");
		if (!file_exists("users.xml"))
		{
			$this->fail("Failed to save an Authentication instance to users.xml.");
		}
	}
	public function testLoadAuthenticationFromXML() 
	{
		Persistence::saveAuthenticationToXML(new Authentication(), "users.xml");
		if (!file_exists("users.xml"))
		{
			$this->fail("Failed to save a Model instance to data.xml.");
		}
		$authentication = Persistence::loadAuthenticationFromXML("users.xml");
		if (is_null($authentication))
		{
			$this->fail("Failed to load a Model instance from data.xml");
		}
	}
}
?>
