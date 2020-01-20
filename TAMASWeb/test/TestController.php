<?php
namespace ca\mcgill\ecse321\tamas\web\controller;

require_once 'PHPUnit/Autoload.php';
require_once __DIR__.'/../model/Course.php';
require_once __DIR__.'/../model/CourseWorker.php';
require_once __DIR__.'/../model/DepartmentAdministrator.php';
require_once __DIR__.'/../model/Evaluation.php';
require_once __DIR__.'/../model/Instructor.php';
require_once __DIR__.'/../model/Job.php';
require_once __DIR__.'/../model/JobApplication.php';
require_once __DIR__.'/../model/Tamas.php';
require_once __DIR__.'/../model/User.php';

use \ca\mcgill\ecse321\tamas\web\controller\Controller;
use \ca\mcgill\ecse321\tamas\web\model\Course;
use \ca\mcgill\ecse321\tamas\web\model\Evaluation;
use \ca\mcgill\ecse321\tamas\web\model\Job;
use \ca\mcgill\ecse321\tamas\web\model\Tamas;
use PHPUnit\Framework\TestCase;

class TestController extends \PHPUnit_Framework_TestCase {
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
	public function setUp () 
	{
		Controller::loadAuthentication();
		Controller::loadModel();
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
	public function testLoadAuthentication () 
	{
		if (!Controller::loadAuthentication())
		{
			$this->fail("Failed to load Authentication from users.xml");
		}
	}
	public function testSaveAuthentication () 
	{
		Controller::saveAuthentication();
	}
	public function testLoadModel () 
	{
		if (!Controller::loadModel())
		{
			$this->fail("Failed to load Model from data.xml");
		}
	}
	public function testSaveModel () 
	{
		Controller::saveModel();
	}
	public function testGetAuthenticationUsers () 
	{
		$this->testRegisterNewUser();
		$users = Controller::getAuthenticationUsers();
		if (count($users) <= 0)
		{
			$this->fail("Failed to get any users from users.xml.");
		}
	}
	public function testGetModelUsers () 
	{
		$this->testRegisterNewUser();
		$users = Controller::getModelUsers();
		if (count($users) <= 0)
		{
			$this->fail("Failed to get any users from users.xml.");
		}
	}
	public function testGetCourseList () 
	{
		$this->testAddNewCourse();
		$courses = Controller::getCourseList();
		if (count($courses) <= 0)
		{
			$this->fail("Failed to get any courses from data.xml");
		}
	}
	public function testGetCourseJobPostings () 
	{
		$this->testAddCourseJob();
		$jobs = Controller::getCourseJobPostings(Controller::getCourseList()[0]);
		if (count($jobs) <= 0)
		{
			$this->fail("Failed to get any jobs from data.xml");
		}
	}
	public function testVerifyAuthenticationCredentials () 
	{
		$this->testRegisterNewUser();
		if (is_null(Controller::verifyAuthenticationCredentials("test", "test")))
		{
			$this->fail("The verified user object returned is null.");
		}
	}
	public function testRegisterNewUser () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		if (!(Controller::getAuthenticationUsers()[0]->getUsername() === "test") && !(Controller::getModelUsers()[0]->getUsername() === "test"))
		{
			$this->fail("Failed to register a new user.");
		}
	}
	public function testChangeUserPassword () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::changeUserPassword("test", "test1", "test1");
		if (!(Controller::getAuthenticationUsers()[0]->getPassword() === "test1"))
		{
			$this->fail("Failed to change the password for a test user.");
		}
	}
	public function testAddNewCourse () 
	{
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		if (!(Controller::getCourseList()[0]->getName() === "test"))
		{
			$this->fail("Failed to add a test course to the Model.");
		}
	}
	public function testRemoveCourse () 
	{
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		$courses = Controller::getCourseList();
		foreach ($courses as $course) {
			Controller::removeCourse($course);
		}
		if (count(Controller::getCourseList()) > 0)
		{
			$this->fail("Failed to remove all courses from the Model.");
		}
	}
	public function testAddCourseJob () 
	{
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		Controller::addCourseJob("test", "test", Controller::getCourseList()[0], 1, 1, new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime());
		if (count(Controller::getCourseList()[0]->getJobPostings()) < 0)
		{
			$this->fail("Failed to add a test course job posting.");
		}
	}
	public function testRemoveCourseJob () 
	{
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		Controller::addCourseJob("test", "test", Controller::getCourseList()[0], 1, 2, new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime());
		Controller::removeCourseJob(Controller::getCourseList()[0], Controller::getCourseList()[0]->getJobPostings()[0]);
		Controller::getCourseList()[0]->getJobPostings()[0]->delete();
		if (count(Controller::getCourseList()[0]->getJobPostings()) > 0)
		{
			$this->fail("Failed to remove a test course job posting.");
		}
	}
	public function testAllocateWorkerToJob () 
	{
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		Controller::addCourseJob("test", "test", Controller::getCourseList()[0], 1, 1, new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime());
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::allocateWorkerToJob(Controller::getCourseJobPostings(Controller::getCourseList()[0])[0], Controller::getModelUsers()[1]->getUsername());
		if (!((Controller::getCourseJobPostings(Controller::getCourseList()[0])[0]->getAllocatedWorker() === Controller::getModelUsers()[1])))
		{
			$this->fail("Failed to allocate a test user to a test course's test job.");
		}
	}
	public function testChangeUserType () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::changeUserType("test", new \ca\mcgill\ecse321\tamas\web\model\CourseWorker(NULL, NULL, 0, 0, NULL));
		if (!(Controller::getModelUsers()[0] instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker))
		{
			$this->fail("Failed to change the type on an existing test user.");
		}
		Controller::changeUserType("test", new \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator(NULL, NULL));
		if (!(Controller::getModelUsers()[0] instanceof \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator))
		{
			$this->fail("Failed to change the type on an existing test user.");
		}
	}
	public function testAddCourseWorkerEvaluation () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::changeUserType("test2", new \ca\mcgill\ecse321\tamas\web\model\Instructor("", ""));
		Controller::registerNewUser("test3", "test", "test", "test", 0);
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		Controller::addCourseJob("test", "test", Controller::getCourseList()[0], 1, 1, new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime());
		Controller::addCourseWorkerEvaluation(Controller::getCourseList()[0], Controller::getCourseList()[0]->getJobPostings()[0], Controller::getModelUsers()[1]->getUsername(), Controller::getModelUsers()[2]->getUsername(), "Great worker. 10/10");
		if (count(Controller::getModelUsers()[2]->getEvals()) <= 0)
		{
			$this->fail("Failed to add an evaluation to a test course worker.");
		}
	}
	public function testChangeCourseWorkerAcademicStatus () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::changeCourseWorkerAcademicStatus("test2", 1);
		/*if ((Controller::getModelUsers()[1] instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker && !(Controller::getModelUsers()[1]->getAcademicStatus() === 1)))
		{
			$this->fail("Failed to change the academic status on a test course worker.");
		}*/
	}
	public function testChangeCourseWorkerMcGillID () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::changeCourseWorkerMcGillID("test2", 2);
		if ((Controller::getModelUsers()[1] instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker && !(Controller::getModelUsers()[1]->getMcgillID() === 2)))
		{
			$this->fail("Failed to change the McGill ID for a test course worker.");
		}
	}
	public function testChangeCourseWorkerPastExp () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::changeCourseWorkerPastExp("test2", "NEW EXPERIENCE");
		if ((Controller::getModelUsers()[1] instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker && !(Controller::getModelUsers()[1]->getPastWorkerExperience() === "NEW EXPERIENCE")))
		{
			$this->fail("Failed to change the past experience for a test course worker.");
		}
	}
	public function testApplyForJob () 
	{
		Controller::registerNewUser("test", "test", "test", "test", 0);
		Controller::registerNewUser("test2", "test", "test", "test", 0);
		Controller::addNewCourse("test", 1, 1, 1, 1, 1, 1);
		Controller::addCourseJob("test", "test", Controller::getCourseList()[0], 1, 1, new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime());
		Controller::applyForJob(Controller::getCourseJobPostings(Controller::getCourseList()[0])[0], new \DateTime(), new \DateTime(), 1, Controller::getModelUsers()[1]->getUsername());
		if (!(Controller::getCourseList()[0]->getJobPostings()[0]->getJobApplications()[0]->getWorker() === Controller::getModelUsers()[1]))
		{
			$this->fail("Failed to have a test course worker apply for a job posting.");
		}
	}
}
?>
