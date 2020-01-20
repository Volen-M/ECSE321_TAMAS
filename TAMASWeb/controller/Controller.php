<?php
namespace ca\mcgill\ecse321\tamas\web\controller;

require_once __DIR__.'/InputValidator.php';
require_once __DIR__.'/../authentication/Authentication.php';
require_once __DIR__.'/../authentication/CourseWorker.php';
require_once __DIR__.'/../authentication/DepartmentAdministrator.php';
require_once __DIR__.'/../authentication/Instructor.php';
require_once __DIR__.'/../authentication/User.php';
require_once __DIR__.'/../model/Course.php';
require_once __DIR__.'/../model/CourseWorker.php';
require_once __DIR__.'/../model/DepartmentAdministrator.php';
require_once __DIR__.'/../model/Evaluation.php';
require_once __DIR__.'/../model/Instructor.php';
require_once __DIR__.'/../model/Job.php';
require_once __DIR__.'/../model/JobApplication.php';
require_once __DIR__.'/../model/Tamas.php';
require_once __DIR__.'/../model/User.php';
require_once __DIR__.'/../persistence/Persistence.php';

use \ca\mcgill\ecse321\tamas\web\authentication\Authentication;
use \ca\mcgill\ecse321\tamas\web\model\Course;
use \ca\mcgill\ecse321\tamas\web\model\Evaluation;
use \ca\mcgill\ecse321\tamas\web\model\Job;
use \ca\mcgill\ecse321\tamas\web\model\Tamas;
use \ca\mcgill\ecse321\tamas\web\persistence\Persistence;

class Controller {
    private static $tamas;
    private static $authentication;

    public static function loadAuthentication() {
        Controller::$authentication = Persistence::loadAuthenticationFromXML("users.xml");
        if(is_null(Controller::$authentication)) {
            return false;
        } else {
            return true;
        }
    }

    public static function saveAuthentication() {
        Persistence::saveAuthenticationToXML(Controller::$authentication, "users.xml");
    }

    public static function loadModel() {
        Controller::$tamas = Persistence::loadModelFromXML("data.xml");
        if(is_null(Controller::$tamas)) {
            return false;
        } else {
            return true;
        }
    }

    public static function saveModel() {
        Persistence::saveModelToXML(Controller::$tamas, "data.xml");
    }

    public static function setCurrentUser($user) {
    	if(!is_null(Controller::$authentication)){
    		Controller::$authentication->setCurrentUser($user);
    	}
    }

    public static function getCurrentUser() {
        if(!is_null(Controller::$authentication)){
            return Controller::$authentication->getCurrentUser();
        } else {
        	return null;
        }
    }

    public static function getAuthenticationUsers() {
        return Controller::$authentication->getUsers();
    }

    public static function getModelUsers() {
        return Controller::$tamas->getUsers();
    }

    public static function getCourseList() {
        return Controller::$tamas->getCourses();
    }

    public static function getCourseJobPostings(Course &$course) {
        return $course->getJobPostings();
    }

    public static function getCourseWorkerJobApplications($username) {
        $courseworker = null;
        $modelUsers = Controller::getModelUsers();
        foreach ($modelUsers as $user) {
            if ($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $worker = $user;
            }
        }
        if($courseworker === null) {
        	throw new \Exception("Failed to get courseworker for username.");
        }
        return $courseworker->getJobApplications();
    }

    public static function getCourseWorkerEvaluations($username) {
        $courseworker = null;
        $modelUsers = Controller::getModelUsers();
        foreach ($modelUsers as $user) {
            if ($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $worker = $user;
            }
        }
        if($courseworker === null) {
            throw new \Exception("Failed to get courseworker for username.");
        }
        return $courseworker->getEvals();
    }

    public static function verifyAuthenticationCredentials($username, $password) {
        $authUsers = Controller::getAuthenticationUsers();
        foreach ($authUsers as $user) {
            if($user->getUsername() === $username && $user->getPassword() === $password) {
                Controller::$authentication->setCurrentUser($user);
                return $user;
            }
        }
        return null;
    }

    public static function registerNewUser($username, $password, $repeatedPassword, $name, $mcgillID) {
        if($password === $repeatedPassword) {
            $authUsers = Controller::getAuthenticationUsers();
            foreach ($authUsers as $user) {
                if($user->getUsername() === $username) {
                    throw new \Exception("Username already exists");
                }
            }
            $modelUsers = Controller::getModelUsers();
            foreach ($modelUsers as $user) {
                if($user->getUsername() === $username) {
                    throw new \Exception("Username already exists");
                }
            }
            if (count($authUsers) !== count($modelUsers)) {
                throw new \Exception("Either data.xml or users.xml is corrupt.");
            } else {
                if(Controller::$authentication->hasUsers() && Controller::$tamas->hasUsers()) {
                    Controller::$authentication->addUser(new \ca\mcgill\ecse321\tamas\web\authentication\CourseWorker($username, $password, $name));
                    Controller::$tamas->addUser(new \ca\mcgill\ecse321\tamas\web\model\CourseWorker($username, $name, 0, $mcgillID, ""));
                } else {
                    // First user registered must be a department administrator.
                    Controller::$authentication->addUser(new \ca\mcgill\ecse321\tamas\web\authentication\DepartmentAdministrator($username, $password, $name));
                    Controller::$tamas->addUser(new \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator($username, $name));
                }
                Controller::saveAuthentication();
                Controller::saveModel();
            }
        } else {
            throw new \Exception("The password is not equivalent to the repeated password string.");
        }
    }

    public static function changeUserPassword($username, $newPassword, $newPasswordRepeated) {
        if ($newPassword === $newPasswordRepeated) {
            $authUsers = Controller::getAuthenticationUsers();
            foreach ($authUsers as $user) {
                if ($user->getUsername() === $username) {
                    $user->setPassword($newPassword);
                    Controller::saveAuthentication();
                    return;
                }
            }
        } else {
            throw new \Exception("The password is not equivalent to the repeated password string.");
        }
    }

    public static function addNewCourse($name, $budget, $studentsEnrolled, $credits, $hours, $tutorialCount, $labCount) {
        if (strlen($name) > 0 && $budget > 0.0 && $studentsEnrolled > 0 && $credits > 0 && $hours > 0 && $tutorialCount > 0 && $labCount > 0) {
            Controller::$tamas->addCourseVia($name, $budget, $studentsEnrolled, $credits, $hours, $tutorialCount, $labCount);
            Controller::saveModel();
        } else {
            throw new \Exception("A parameter has a bad value.");
        }
    }

    public static function removeCourse(Course &$course) {
        $courses = Controller::getCourseList();
        foreach ($courses as $cour) {
            if ($cour === $course) {
                $cour->delete();
                Controller::saveModel();
                return;
            } else if (($cour === $courses[count($courses) - 1]) && !($cour === $course)) {
                throw new \Exception("Could not remove course.");
            }
        }
    }

    public static function addCourseJob($title, $description, Course &$course, $day, $type, \DateTime &$startTime, \DateTime &$endTime, \DateTime &$deadlineDate, \DateTime &$deadlineTime) {
        if (strlen($title) > 0 && strlen($description) > 0 && !is_null($course) && $day > 0 && $day < 8 && $type > 0 && $type < 4 && !is_null($startTime) && !is_null($endTime) && !is_null($deadlineDate) && !is_null($deadlineTime)) {
            $courses = Controller::getCourseList();
            foreach ($courses as $cour) {
                if ($cour === $course) {
                    $cour->addJobPostingVia($startTime, $endTime, $deadlineDate, $deadlineTime, $title, $description);
                    Controller::saveModel();
                    return;
                }
            }
        } else {
            throw new \Exception("A parameter has a bad value.");
        }
    }

    public static function removeCourseJob(Course &$course, Job &$job) {
        $jobs = Controller::getCourseJobPostings($course);
        foreach ($jobs as $jo) {
            if ($jo === $job) {
                $course->removeJobPosting($jo);
                Controller::saveModel();
                return;
            } else if (($jo === $jobs[count($jobs) - 1]) && !($jo === $job)) {
                throw new \Exception("Could not remove course job.");
            }
        }
    }

    public static function allocateWorkerToJob(Job &$job, $username) {
        $worker = null;
        $users = Controller::getModelUsers();
        foreach ($users as $user) {
            if ($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $worker = $user;
            }
        }
        if ($worker === null) {
            throw new \Exception("Failed to get courseworker for username.");
        }
        if (!is_null($job) && !is_null($worker)) {
            $courses = Controller::getCourseList();
            foreach ($courses as $course) {
                $jobs = Controller::getCourseJobPostings($course);
                foreach ($jobs as $jo) {
                    if ($jo === $job) {
                        $jo->setAllocatedWorker($worker);
                        Controller::saveModel();
                        return;
                    }
                }
            }
        } else {
            throw new \Exception("A parameter has a bad value.");
        }
    }

    public static function changeUserType($username, \ca\mcgill\ecse321\tamas\web\model\User &$newUserType) {
        $authUsers = Controller::getAuthenticationUsers();
        $modelUsers = Controller::getModelUsers();
        if ($newUserType instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
            $auth = null;
            $mode = null;

            foreach ($authUsers as $authUser) {
                if ($authUser->getUsername() === $username) {
                    $auth = $authUser;
                    break;
                }
            }

            foreach ($modelUsers as $modelUser) {
                if($modelUser->getUsername() === $username) {
                    $mode = $modelUser;
                    break;
                }
            }

            if (($auth === null) || ($mode === null)) {
                throw new \Exception("Could not find user for username.");
            }

            $replacementAuthUser = new \ca\mcgill\ecse321\tamas\web\authentication\CourseWorker($username, $auth->getPassword(), $auth->getName());
            $replacementModelUser = new \ca\mcgill\ecse321\tamas\web\model\CourseWorker($username, $mode->getName(), 0, 0, "");

            $auth->delete();
            $mode->delete();

            Controller::$authentication->addUser($replacementAuthUser);
            Controller::$tamas->addUser($replacementModelUser);

            Controller::saveAuthentication();
            Controller::saveModel();
        } else if ($newUserType instanceof \ca\mcgill\ecse321\tamas\web\model\Instructor) {
            $auth = null;
            $mode = null;

            foreach ($authUsers as $authUser) {
                if ($authUser->getUsername() === $username) {
                    $auth = $authUser;
                    break;
                }
            }

            foreach ($modelUsers as $modelUser) {
                if($modelUser->getUsername() === $username) {
                    $mode = $modelUser;
                    break;
                }
            }

            if (($auth === null) || ($mode === null)) {
                throw new \Exception("Could not find user for username.");
            }

            $replacementAuthUser = new \ca\mcgill\ecse321\tamas\web\authentication\Instructor($username, $auth->getPassword(), $auth->getName());
            $replacementModelUser = new \ca\mcgill\ecse321\tamas\web\model\Instructor($username, $mode->getName());

            $auth->delete();
            $mode->delete();

            Controller::$authentication->addUser($replacementAuthUser);
            Controller::$tamas->addUser($replacementModelUser);

            Controller::saveAuthentication();
            Controller::saveModel();
        } else if ($newUserType instanceof \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator) {
            $auth = null;
            $mode = null;

            foreach ($authUsers as $authUser) {
                if ($authUser->getUsername() === $username) {
                    $auth = $authUser;
                    break;
                }
            }

            foreach ($modelUsers as $modelUser) {
                if($modelUser->getUsername() === $username) {
                    $mode = $modelUser;
                    break;
                }
            }

            if (($auth === null) || ($mode === null)) {
                throw new \Exception("Could not find user for username.");
            }

            $replacementAuthUser = new \ca\mcgill\ecse321\tamas\web\authentication\DepartmentAdministrator($username, $authUser->getPassword(), $authUser->getName());
            $replacementModelUser = new \ca\mcgill\ecse321\tamas\web\model\DepartmentAdministrator($username, $mode->getName());

            $auth->delete();
            $mode->delete();

            Controller::$authentication->addUser($replacementAuthUser);
            Controller::$tamas->addUser($replacementModelUser);

            Controller::saveAuthentication();
            Controller::saveModel();
        } else {
            throw new \Exception("Invalid object newUserType.");
        }
    }

    public static function changeJobApplicationStatus(JobApplication $jobapplication, $newStatus) {
        if(!is_null($jobapplication) && $newStatus > 0 && $newStatus < 5) {
            $jobapplication->setApplicationStatus($newStatus);
        } else {
            throw new \Exception("A parameter has a bad value.");
        }
    }

    public static function addCourseWorkerEvaluation(Course &$course, Job &$job, $evaluatorUsername, $courseWorkerUsername, $description) {
        $worker = null;
        $evaluator = null;
        $users = Controller::getModelUsers();
        foreach ($users as $user) {
            if($user->getUsername() === $courseWorkerUsername && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $worker = $user;
            } else if ($user->getUsername() === $evaluatorUsername && $user instanceof \ca\mcgill\ecse321\tamas\web\model\Instructor) {
                $evaluator = $user;
            }
        }
        if(is_null($worker) || is_null($evaluator)){
            throw new \Exception("Failed to get courseworker/instructor for username.");
        }
        if (!is_null($course) && !is_null($job) && !is_null($evaluator) && !is_null($worker)) {
            $worker->addEval(new Evaluation($description, $course, $job, $evaluator, $worker));
            Controller::saveModel();
        } else {
            throw new \Exception("A parameter has a bad value.");
        }
    }

    public static function changeCourseWorkerAcademicStatus($username, $newAcademicStatus) {
        $users = Controller::getModelUsers();
        foreach ($users as $user) {
            if($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                if($newAcademicStatus > 0 && $newAcademicStatus < 3){
                    $user->setAcademicStatus($newAcademicStatus);
                    Controller::saveModel();
                    return;
                } else {
                    throw new \Exception("Invalid academic status passed to this method.");
                }
            }
        }
    }

    public static function changeCourseWorkerMcGillID($username, $newIDNumber) {
        $users = Controller::getModelUsers();
        foreach ($users as $user) {
            if($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $user->setMcgillID($newIDNumber);
                Controller::saveModel();
                return;
            }
        }
        throw new \Exception("Could not change course worker McGill ID.");
    }

    public static function changeCourseWorkerPastExp($username, $newExp) {
        $users = Controller::getModelUsers();
        foreach ($users as $user) {
            if($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                $user->setPastWorkerExperience($newExp);
                Controller::saveModel();
                return;
            }
        }
        throw new \Exception("Could not change course worker past experience.");
    }

    public static function applyForJob(Job &$job, \DateTime &$applicationDate, \DateTime &$applicationTime, $rank, $username) {
        $users = Controller::getModelUsers();
        if (!is_null($job) && !is_null($applicationDate) && !is_null($applicationTime) && $rank > -1	&& !is_null($username) && strlen($username) > 0) {
            $worker = null;
            $users = Controller::getModelUsers();
            foreach ($users as $user) {
                if ($user->getUsername() === $username && $user instanceof \ca\mcgill\ecse321\tamas\web\model\CourseWorker) {
                    $worker = $user;
                }
            }
            if (is_null($worker)) {
                throw new \Exception("Failed to get courseworker for username.");
            }
            $courses = Controller::getCourseList();
            foreach ($courses as $course) {
                $jobs = $course->getJobPostings();
                    foreach ($jobs as $jo) {
                        if ($jo === $job) {
                            $jo->addJobApplicationVia($applicationDate, $applicationTime, $rank, $worker);
                            Controller::saveModel();
                            return;
                        }
                    }
                }
        } else {
            throw new \Exception("A job application parameter has a bad value.");
        }
    }

    public static function getCourse($index) {
        if(!($index < 0)){
            Controller::$tamas->getCourse($index);
        } else {
            throw new \Exception("The entered index is bad.");
        }
    }

}
?>