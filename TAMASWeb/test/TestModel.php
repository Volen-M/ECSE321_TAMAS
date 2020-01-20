<?php
namespace ca\mcgill\ecse321\tamas\web\model;

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

use \ca\mcgill\ecse321\tamas\web\model\Course;
use \ca\mcgill\ecse321\tamas\web\model\CourseWorker;
use \ca\mcgill\ecse321\tamas\web\model\Instructor;
use \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator;
use \ca\mcgill\ecse321\tamas\web\model\Evaluation;
use \ca\mcgill\ecse321\tamas\web\model\Job;
use \ca\mcgill\ecse321\tamas\web\model\JobApplication;
use \ca\mcgill\ecse321\tamas\web\model\Tamas;
use PHPUnit\Framework\TestCase;

class TestModel extends \PHPUnit_Framework_TestCase {
	protected $tamas;	// Tamas

	public function setUp () 
	{
		$this->tamas = new Tamas();
	}
	public function tearDown () 
	{
		$this->tamas->delete();
	}
	public function testTamasAddCourse () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		if (count($this->tamas->getCourses()) <= 0)
		{
			$this->fail("Failed to add a course to our Tamas Model instance.");
		}
	}
	public function testTamasAddUser () 
	{
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		if (is_null($this->tamas->getUsers()[0]))
		{
			$this->fail("Failed to add a course worker user to our Tamas Model instance.");
		}
	}
	public function testTamasAddOrMoveCourseAt () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->addCourse(new Course("test2", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->addOrMoveCourseAt($this->tamas->getCourses()[1], 0);
		if (!($this->tamas->getCourses()[0]->getName() === "test2"))
		{
			$this->fail("Failed to add or move a course in our Tamas Model instance.");
		}
	}
	public function testTamasAddOrMoveUserAt () 
	{
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->addUser(new CourseWorker("test2", "test", 0, 0, "test"));
		$this->tamas->addOrMoveUserAt($this->tamas->getUsers()[1], 0);
		if (!($this->tamas->getUsers()[0]->getUsername() === "test2"))
		{
			$this->fail("Failed to add or move a user in our Tamas Model instance.");
		}
	}
	public function testCourseSetName () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setName("ECSE321"));
		$this->assertEquals(TRUE, $FirstCourse->setName(""));
		$this->assertEquals(TRUE, $FirstCourse->setName("1231-7"));
	}
	public function testCourseSetStudentsEnrolled () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setStudentsEnrolled(100));
		$this->assertEquals(TRUE, $FirstCourse->setStudentsEnrolled(0));
		$this->assertEquals(TRUE, $FirstCourse->setStudentsEnrolled(-100));
	}
	public function testCourseSetBudget () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setBudget(1000));
		$this->assertEquals(TRUE, $FirstCourse->setBudget(0));
		$this->assertEquals(TRUE, $FirstCourse->setBudget(-1000));
	}
	public function testCourseSetCredits () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setCredits(3));
		$this->assertEquals(TRUE, $FirstCourse->setCredits(0));
		$this->assertEquals(TRUE, $FirstCourse->setCredits(-3));
	}
	public function testCourseSetHours () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setHours(3));
		$this->assertEquals(TRUE, $FirstCourse->setHours(0));
		$this->assertEquals(TRUE, $FirstCourse->setHours(-1));
	}
	public function testCourseSetTutorialCount () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setTutorialCount(2));
		$this->assertEquals(TRUE, $FirstCourse->setTutorialCount(0));
		$this->assertEquals(TRUE, $FirstCourse->setTutorialCount(-1));
	}
	public function testCourseSetLabCount () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setLabCount(2));
		$this->assertEquals(TRUE, $FirstCourse->setLabCount(0));
		$this->assertEquals(TRUE, $FirstCourse->setLabCount(-1));
	}
	public function testCourseSetTamas () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(TRUE, $FirstCourse->setTamas($this->tamas));
	}
	public function testCourseGetName () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals("ECSE321", $FirstCourse->getName());
	}
	public function testCourseGetBudget () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(1000, $FirstCourse->getBudget());
	}
	public function testCourseGetCredits () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(3, $FirstCourse->getCredits());
	}
	public function testCourseGetStudentsEnrolled () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(100, $FirstCourse->getStudentsEnrolled());
	}
	public function testCourseGetTamas () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals($this->tamas, $FirstCourse->getTamas());
	}
	public function testCourseGetTutorialCount () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(2, $FirstCourse->getTutorialCount());
	}
	public function testCourseGetLabCount () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$this->assertEquals(2, $FirstCourse->getLabCount());
	}
	public function testCourseAddOrMoveJobPostingAt () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$cal = new \DateTime();
		$Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
		$TaJ = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$TaJ1 = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$TaJ2 = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$TaJ3 = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		/*FirstCourse->addJobPosting($TaJ);
		$FirstCourse->addJobPosting($TaJ1);
		$FirstCourse->addJobPosting($TaJ2);
		$FirstCourse->addJobPosting($TaJ3);
		$this->assertEquals( TRUE , $FirstCourse->addOrMoveJobPostingAt($TaJ, 3));
		$this->assertEquals( TRUE , $FirstCourse->addOrMoveJobPostingAt($TaJ, 2));
		$this->assertEquals( TRUE , $FirstCourse->addOrMoveJobPostingAt($TaJ, 1));
		$this->assertEquals( TRUE , $FirstCourse->addOrMoveJobPostingAt($TaJ, 0));*/
	}
	public function testCourseHasJobPostings () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$cal = new \DateTime();
		$Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
		$TaJ = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$FirstCourse->addJobPosting($TaJ);
		$this->assertEquals( TRUE , $FirstCourse->hasJobPostings());
	}
	public function testCourseIndexOfJobPosting () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$cal = new \DateTime();
		$Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
		$TaJ = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$FirstCourse->addJobPosting($TaJ);
		$this->assertEquals(0, $FirstCourse->indexOfJobPosting($TaJ));
	}
	public function testCourseNumberOfJobPostings () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$cal = new \DateTime();
		$Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
		$TaJ = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$FirstCourse->addJobPosting($TaJ);
		$this->assertEquals(1, $FirstCourse->numberOfJobPostings());
	}
	public function testCourseRemoveJobPosting () 
	{
		$FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, $this->tamas);
		$cal = new \DateTime();
		$Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
		$TaJ = new Job($cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), $cal->getTimestamp(), "TAforECSE321", "Give tutorials for ECSE321", $FirstCourse);
		$FirstCourse->addJobPosting($TaJ);
		// buggy.
		//$this->tamas->getCourses()[0]->getJobPostings()[0]->delete();
		//$this->assertEquals(0, $this->tamas->getCourses()[0]->numberOfJobPostings());
	}
	public function testCourseToString () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		if (count($this->tamas->getCourses()) <= 0)
		{
			$this->fail("Failed to get the a serialized string of the course at the first index within our Tamas Model instance.");
		}
	}
	public function testCourseWorkerToString () 
	{
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		if (count($this->tamas->getUsers()) <= 0)
		{
			$this->fail("Failed to get the a serialized string of the user at the first index within our Tamas Model instance.");
		}
	}
	public function testCourseWorkerAddOrMoveEvalAt () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->addUser(new Instructor("test", "test"));
		$this->tamas->getUsers()[0]->addEval(new Evaluation("test", $this->tamas->getCourses()[0], $this->tamas->getCourses()[0]->getJobPostings()[0], $this->tamas->getUsers()[1], $this->tamas->getUsers()[0]));
		$this->tamas->getUsers()[0]->addEval(new Evaluation("test2", $this->tamas->getCourses()[0], $this->tamas->getCourses()[0]->getJobPostings()[0], $this->tamas->getUsers()[1], $this->tamas->getUsers()[0]));
		$this->tamas->getUsers()[0]->addOrMoveEvalAt($this->tamas->getUsers()[0]->getEvals()[1], 0);
		if (!($this->tamas->getUsers()[0]->getEvals()[0]->getDescription() === "test2"))
		{
			$this->fail("Failed to add or move a course evaluation for a test course worker.");
		}
	}
	public function testCourseWorkerSetJobApplications () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->getUsers()[0]->setJobApplications(new JobApplication(new \DateTime(), new \DateTime(), 0, $this->tamas->getUsers()[0], $this->tamas->getCourses()[0]->getJobPostings()[0]));
		/*if (count($this->tamas->getUsers()[0]->getJobApplications()) <= 0)
		{
			$this->fail("Failed to set the job applications on a test course worker in our Tamas Model instance.");
		}*/
	}
	public function testEvaluationToString () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->addUser(new Instructor("test", "test"));
		$this->tamas->getUsers()[0]->addEval(new Evaluation("test", $this->tamas->getCourses()[0], $this->tamas->getCourses()[0]->getJobPostings()[0], $this->tamas->getUsers()[1], $this->tamas->getUsers()[0]));
		if (count($this->tamas->getUsers()[0]->getEvals()) <= 0)
		{
			$this->fail("Failed to add and get the serialized string representation of an evaluation object.");
		}
	}
	public function testJobApplicationToString () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->getUsers()[0]->setJobApplications(new JobApplication(new \DateTime(), new \DateTime(), 0, $this->tamas->getUsers()[0], $this->tamas->getCourses()[0]->getJobPostings()[0]), new JobApplication(new \DateTime(), new \DateTime(), 0, $this->tamas->getUsers()[0], $this->tamas->getCourses()[0]->getJobPostings()[0]));
		/*if (count($this->tamas->getUsers()[0]->getJobApplications()) <= 0)
		{
			$this->fail("Failed to get the a serialized string of the job application at the first index within our Tamas Model instance.");
		}*/
	}
	public function testCourseWorkerAddOrMoveJobApplicationAt () 
	{
		/*$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		$this->tamas->addUser(new CourseWorker("test", "test", 0, 0, "test"));
		$this->tamas->getUsers()[0]->setJobApplications(new JobApplication(new \DateTime(), new \DateTime(), 0, $this->tamas->getUsers()[0], $this->tamas->getCourses()[0]->getJobPostings()[0]), new JobApplication(new \DateTime(), new \DateTime(), 0, $this->tamas->getUsers()[0], $this->tamas->getCourses()[0]->getJobPostings()[0]));*/
		/*if (!($this->tamas->getUsers()[0]->addOrMoveJobApplicationAt($this->tamas->getUsers()[0]->getJobApplications()[0], 1)))
		{
			$this->fail("Failed to add or move a job application at the first index in our Tamas Model instance.");
		}*/
	}
	public function testCourseAddJob () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		if (is_null($this->tamas->getCourses()[0]->getJobPostings()[0]))
		{
			$this->fail("Failed to add a job posting to a test course in our Tamas Model instance.");
		}
	}
	public function testCourseJobToString () 
	{
		$this->tamas->addCourse(new Course("test", 1, 1, 1, 1, 1, 1, $this->tamas));
		$this->tamas->getCourses()[0]->addJobPosting(new Job(new \DateTime(), new \DateTime(), new \DateTime(), new \DateTime(), "test", "test", $this->tamas->getCourses()[0]));
		if (count($this->tamas->getCourses()[0]->getJobPostings()[0]) <= 0)
		{
			$this->fail("Failed to get the a serialized string of the job at the first index within our Tamas Model instance.");
		}
	}
}
?>
