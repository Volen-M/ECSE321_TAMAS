package ca.mcgill.ecse321.tamas.controller;

import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.Job.Day;
import ca.mcgill.ecse321.tamas.model.Job.Type;

public class TestController {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Clear any old data.xml and users.xml files before any tests are run.
        File fileModel = new File("data.xml");
        if (fileModel.exists()) {
            fileModel.delete();
        }
        File fileAuthentication = new File("users.xml");
        if (fileAuthentication.exists()) {
            fileAuthentication.delete();
        }
    }

    @Before
    public void setUp() throws Exception {
        // Create empty data.xml and users.xml files for each test.
        // Also store a new instance of Tamas and Authentication in the
        // Controller for testing purposes.
        // These two methods must not fail and most likely will not in and of
        // themselves.
        Controller.loadAuthentication();
        Controller.loadModel();
    }

    @After
    public void tearDown() throws Exception {
        // Cleanup the data.xml and users.xml files after each test.
        File fileModel = new File("data.xml");
        if (fileModel.exists()) {
            fileModel.delete();
        }
        File fileAuthentication = new File("users.xml");
        if (fileAuthentication.exists()) {
            fileAuthentication.delete();
        }
    }

    @Test
    public void testLoadAuthentication() {
        // Try to reload our Controller's empty authentication instance.
        if (!Controller.loadAuthentication()) {
            fail("Failed to load Authentication from users.xml");
        }
    }

    @Test
    public void testSaveAuthentication() {
        // Try to save our Controller's empty authentication instance.
        Controller.saveAuthentication();
    }

    @Test
    public void testLoadModel() {
        // Try to reload our Controller's empty tamas instance.
        if (!Controller.loadModel()) {
            fail("Failed to load Model from data.xml");
        }
    }

    @Test
    public void testSaveModel() {
        // Try to save our Controller's empty tamas instance.
        Controller.saveModel();
    }

    @Test
    public void testGetAuthenticationUsers() throws Exception {
        testRegisterNewUser();
        List<ca.mcgill.ecse321.tamas.authentication.User> users = Controller.getAuthenticationUsers();
        if (users.size() <= 0) {
            fail("Failed to get any users from users.xml.");
        }
    }

    @Test
    public void testGetModelUsers() throws Exception {
        testRegisterNewUser();
        List<ca.mcgill.ecse321.tamas.model.User> users = Controller.getModelUsers();
        if (users.size() <= 0) {
            fail("Failed to get any users from users.xml.");
        }
    }

    @Test
    public void testGetCourseList() throws InvalidInputException {
        testAddNewCourse();
        List<Course> courses = Controller.getCourseList();
        if (courses.size() <= 0) {
            fail("Failed to get any courses from data.xml");
        }
    }

    @Test
    public void testGetCourseJobPostings() throws InvalidInputException {
        testAddCourseJob();
        List<Job> jobs = Controller.getCourseJobPostings(Controller.getCourseList().get(0));
        if (jobs.size() <= 0) {
            fail("Failed to get any jobs from data.xml");
        }
    }

    @Test
    public void testVerifyAuthenticationCredentials() throws Exception {
        testRegisterNewUser();
        if (Controller.verifyAuthenticationCredentials("test", "test") == null) {
            fail("The verified user object returned is null.");
        }
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        if (!Controller.getAuthenticationUsers().get(0).getUsername().equals("test")
                && !Controller.getModelUsers().get(0).getUsername().equals("test")) {
            fail("Failed to register a new user.");
        }
    }

    @Test
    public void testChangeUserPassword() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.changeUserPassword("test", "test1", "test1");
        if (!Controller.getAuthenticationUsers().get(0).getPassword().equals("test1")) {
            fail("Failed to change the password for a test user.");
        }
    }

    @Test
    public void testAddNewCourse() throws InvalidInputException {
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        if (!Controller.getCourseList().get(0).getName().equals("test")) {
            fail("Failed to add a test course to the Model.");
        }
    }

    @Test
    public void testRemoveCourse() throws InvalidInputException {
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        List<Course> courses = Controller.getCourseList();
        for (int i = 0; i < courses.size(); i++) {
            Controller.removeCourse(courses.get(i));
        }
        if (Controller.getCourseList().size() > 0) {
            fail("Failed to remove all courses from the Model.");
        }
    }

    @Test
    public void testAddCourseJob() throws InvalidInputException {
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        Controller.addCourseJob("test", "test", Controller.getCourseList().get(0), Day.MONDAY, Type.GRADER, new Time(1),
                new Time(2), new Date(1), new Time(3));
        if (Controller.getCourseList().get(0).getJobPostings().size() < 0) {
            fail("Failed to add a test course job posting.");
        }
    }

    @Test
    public void testRemoveCourseJob() throws InvalidInputException {
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        Controller.addCourseJob("test", "test", Controller.getCourseList().get(0), Day.MONDAY, Type.GRADER, new Time(1),
                new Time(2), new Date(1), new Time(3));
        Controller.removeCourseJob(Controller.getCourseList().get(0),
                Controller.getCourseList().get(0).getJobPostings().get(0));
        if (Controller.getCourseList().get(0).getJobPostings().size() > 0) {
            fail("Failed to remove a test course job posting.");
        }
    }

    @Test
    public void testAllocateWorkerToJob() throws Exception {
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        Controller.addCourseJob("test", "test", Controller.getCourseList().get(0), Day.MONDAY, Type.GRADER, new Time(1),
                new Time(2), new Date(1), new Time(3));
        Controller.registerNewUser("test", "test", "test", "test", 0);
        // Second user will be a CourseWorker for sure.
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.allocateWorkerToJob(Controller.getCourseJobPostings(Controller.getCourseList().get(0)).get(0),
                Controller.getModelUsers().get(1).getUsername());
        if (!(Controller.getCourseJobPostings(Controller.getCourseList().get(0)).get(0)
                .getAllocatedWorker() == Controller.getModelUsers().get(1))) {
            fail("Failed to allocate a test user to a test course's test job.");
        }
    }

    @Test
    public void testChangeUserType() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.changeUserType("test", new ca.mcgill.ecse321.tamas.model.CourseWorker(null, null, 0, 0, null));
        if (!(Controller.getModelUsers().get(0) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker)) {
            fail("Failed to change the type on an existing test user.");
        }
        Controller.changeUserType("test", new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(null, null));
        if (!(Controller.getModelUsers().get(0) instanceof ca.mcgill.ecse321.tamas.model.DepartmentAdministrator)) {
            fail("Failed to change the type on an existing test user.");
        }
    }

    @Test
    public void testAddCourseWorkerEvaluation() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.changeUserType("test2", new ca.mcgill.ecse321.tamas.model.Instructor("", ""));
        Controller.registerNewUser("test3", "test", "test", "test", 0);
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        Controller.addCourseJob("test", "test", Controller.getCourseList().get(0), Day.MONDAY, Type.GRADER, new Time(1),
                new Time(2), new Date(1), new Time(3));
        Controller.addCourseWorkerEvaluation(Controller.getCourseList().get(0),
                Controller.getCourseList().get(0).getJobPostings().get(0),
                Controller.getModelUsers().get(1).getUsername(), Controller.getModelUsers().get(2).getUsername(),
                "Great worker. 10/10");
        if (((ca.mcgill.ecse321.tamas.model.CourseWorker) Controller.getModelUsers().get(2)).getEvals().size() <= 0) {
            fail("Failed to add an evaluation to a test course worker.");
        }
    }

    @Test
    public void testChangeCourseWorkerAcademicStatus() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.changeCourseWorkerAcademicStatus("test2", AcademicStatus.GRADUATE);
        if (((ca.mcgill.ecse321.tamas.model.CourseWorker) Controller.getModelUsers().get(1))
                .getAcademicStatus() != AcademicStatus.GRADUATE) {
            fail("Failed to change the academic status on a test course worker.");
        }
    }

    @Test
    public void testChangeCourseWorkerMcGillID() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.changeCourseWorkerMcGillID("test2", 2);
        if (((ca.mcgill.ecse321.tamas.model.CourseWorker) Controller.getModelUsers().get(1)).getMcgillID() != 2) {
            fail("Failed to change the McGill ID for a test course worker.");
        }
    }

    @Test
    public void testChangeCourseWorkerPastExp() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.changeCourseWorkerPastExp("test2", "NEW EXPERIENCE");
        if (!((ca.mcgill.ecse321.tamas.model.CourseWorker) Controller.getModelUsers().get(1)).getPastWorkerExperience()
                .equals("NEW EXPERIENCE")) {
            fail("Failed to change the past experience for a test course worker.");
        }
    }

    @Test
    public void testApplyForJob() throws Exception {
        Controller.registerNewUser("test", "test", "test", "test", 0);
        Controller.registerNewUser("test2", "test", "test", "test", 0);
        Controller.addNewCourse("test", 1, 1, 1, 1, 1, 1);
        Controller.addCourseJob("test", "test", Controller.getCourseList().get(0), Day.MONDAY, Type.GRADER, new Time(1),
                new Time(2), new Date(1), new Time(3));
        Controller.applyForJob(Controller.getCourseJobPostings(Controller.getCourseList().get(0)).get(0), new Date(1),
                new Time(1), 1, Controller.getModelUsers().get(1).getUsername());
        if (!Controller.getCourseList().get(0).getJobPostings().get(0).getJobApplications().get(0).getWorker()
                .equals(Controller.getModelUsers().get(1))) {
            fail("Failed to have a test course worker apply for a job posting.");
        }
    }
}