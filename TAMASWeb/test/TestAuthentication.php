<?php
namespace ca\mcgill\ecse321\tamas\web\authentication;

require_once 'PHPUnit/Autoload.php';
require_once __DIR__.'/../authentication/Authentication.php';
require_once __DIR__.'/../authentication/CourseWorker.php';
require_once __DIR__.'/../authentication/DepartmentAdministrator.php';
require_once __DIR__.'/../authentication/Instructor.php';
require_once __DIR__.'/../authentication/User.php';

use \ca\mcgill\ecse321\tamas\web\authentication\Authentication;
use \ca\mcgill\ecse321\tamas\web\authentication\CourseWorker;
use \ca\mcgill\ecse321\tamas\web\authentication\Instructor;
use \ca\mcgill\ecse321\tamas\web\authentication\DepartmentAdministrator;
use PHPUnit\Framework\TestCase;

class TestAuthentication extends \PHPUnit_Framework_TestCase {
	protected $authentication;	// Authentication

	public function setUp () 
	{
		$this->authentication = new Authentication();
	}
	public function tearDown () 
	{
		$this->authentication->delete();
	}
	public function testAuthenticationAddUser () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (!$this->authentication->hasUsers())
		{
			$this->fail("Failed to add a test user to our Authentication instance.");
		}
	}
	public function testAuthenticationAddUserAt () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		$this->authentication->addUserAt(new CourseWorker("test", "test", "test"), 1);
		if (!($this->authentication->getUsers()[1] instanceof CourseWorker))
		{
			$this->fail("Failed to add a test course worker to our desired index.");
		}
	}
	public function testAuthenticationAddOrMoveUserAt () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		$this->authentication->addUser(new DepartmentAdministrator("test2", "test2", "test"));
		$this->authentication->addOrMoveUserAt($this->authentication->getUser_index(0), 0);
		if (!($this->authentication->getUsers()[1] instanceof DepartmentAdministrator))
		{
			$this->fail("Failed to add or move a test department administrator to our desired index.");
		}
	}
	public function testAuthenticationGetUser () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (!($this->authentication->getUsers()[0] instanceof DepartmentAdministrator))
		{
			$this->fail("Failed to get the user at the desired index within our authentication instance.");
		}
	}
	public function testAuthenticationUserToString () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (count($this->authentication->getUsers()) <= 0)
		{
			$this->fail("Failed to get the a serialized string of the user at the first index within our authentication instance.");
		}
	}
	public function testAuthenticationGetUsers () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (count($this->authentication->getUsers()) <= 0)
		{
			$this->fail("Failed to get the users within our authentication instance.");
		}
	}
	public function testAuthenticationSetCurrentUser () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (!$this->authentication->setCurrentUser($this->authentication->getUsers()[0]))
		{
			$this->fail("Failed to set the current user within our authentication instance.");
		}
	}
	public function testAuthenticationRemoveUser () 
	{
		$this->authentication->addUser(new DepartmentAdministrator("test", "test", "test"));
		if (!$this->authentication->removeUser($this->authentication->getUsers()[0]))
		{
			$this->fail("Failed to remove a user within our authentication instance.");
		}
	}
}
?>
