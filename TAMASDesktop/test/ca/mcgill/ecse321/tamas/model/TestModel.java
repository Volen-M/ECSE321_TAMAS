package ca.mcgill.ecse321.tamas.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestModel {

    Tamas tamas;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        tamas = new Tamas();
    }

    @After
    public void tearDown() throws Exception {
        tamas.delete();
    }

    @Test
    public void testTamasAddCourse() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        if(tamas.getCourses().size() <= 0){
            fail("Failed to add a course to our Tamas Model instance.");
        }
    }
    
    @Test
    public void testTamasAddUser() {
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        if(tamas.getUser(0) == null){
            fail("Failed to add a course worker user to our Tamas Model instance.");
        }
    }
    
    @Test
    public void testTamasAddOrMoveCourseAt() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.addCourse(new Course("test2", 1, 1, 1, 1, 1, 1, tamas));
        tamas.addOrMoveCourseAt(tamas.getCourse(1), 0);
        if(!tamas.getCourse(0).getName().equals("test2")){
            fail("Failed to add or move a course in our Tamas Model instance.");
        }
    }
    
    @Test
    public void testTamasAddOrMoveUserAt() {
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        tamas.addUser(new CourseWorker("test2", "test", 0, 0, "test"));
        tamas.addOrMoveUserAt(tamas.getUser(1), 0);
        if(!tamas.getUser(0).getUsername().equals("test2")){
            fail("Failed to add or move a user in our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseSetName() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true , FirstCourse.setName("ECSE321")); 
        // originally false.
        assertEquals(true, FirstCourse.setName(""));
        assertEquals(true, FirstCourse.setName("1231-7"));
    }
    
    @Test
    public void testCourseSetStudentsEnrolled() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setStudentsEnrolled(100) );
        assertEquals(true, FirstCourse.setStudentsEnrolled(0) );
        // originally false.
        assertEquals(true, FirstCourse.setStudentsEnrolled(-100) );
    }
    
    @Test
    public void testCourseSetBudget() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setBudget(1000));
        assertEquals(true, FirstCourse.setBudget(0));
        // originally false.
        assertEquals(true, FirstCourse.setBudget(-1000));
    }
    
    @Test
    public void testCourseSetCredits() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setCredits(3));
        assertEquals(true, FirstCourse.setCredits(0));
        // originally false.
        assertEquals(true, FirstCourse.setCredits(-3));
    }
    
    @Test
    public void testCourseSetHours() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setHours(3));
        // originally false.
        assertEquals(true, FirstCourse.setHours(0));
        // originally false.
        assertEquals(true, FirstCourse.setHours(-1));
    }
    
    @Test
    public void testCourseSetTutorialCount() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setTutorialCount(2));
        assertEquals(true, FirstCourse.setTutorialCount(0));
        // originally false.
        assertEquals(true, FirstCourse.setTutorialCount(-1));
    }
    
    @Test
    public void testCourseSetLabCount() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setLabCount(2));
        assertEquals(true, FirstCourse.setLabCount(0));
        // originally false.
        assertEquals(true, FirstCourse.setLabCount(-1));
    }
    
    @Test
    public void testCourseSetTamas() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(true, FirstCourse.setTamas(tamas));
    }
    
    @Test
    public void testCourseGetName() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals("ECSE321", FirstCourse.getName());
    }
    
    @Test
    public void testCourseGetBudget() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(1000, FirstCourse.getBudget(), 1);
    }
    
    @Test
    public void testCourseGetCredits() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(3, FirstCourse.getCredits());
    }
    
    @Test
    public void testCourseGetStudentsEnrolled() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(100, FirstCourse.getStudentsEnrolled());
    }
    
    @Test
    public void testCourseGetTamas() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(tamas, FirstCourse.getTamas());
    }
    
    @Test
    public void testCourseGetTutorialCount() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(2, FirstCourse.getTutorialCount());
    }
    
    @Test
    public void testCourseGetLabCount() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        assertEquals(2, FirstCourse.getLabCount());
    }
    
    @Test
    public void testCourseAddOrMoveJobPostingAt() throws IndexOutOfBoundsException {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        Calendar cal = Calendar.getInstance();
        CourseWorker Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
        Job TaJ = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        Job TaJ1 = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        Job TaJ2 = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        Job TaJ3 = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        FirstCourse.addJobPosting(TaJ);
        FirstCourse.addJobPosting(TaJ1);
        FirstCourse.addJobPosting(TaJ2);
        FirstCourse.addJobPosting(TaJ3);
        assertEquals(true,FirstCourse.addOrMoveJobPostingAt(TaJ,3));
        assertEquals(true, FirstCourse.addOrMoveJobPostingAt(TaJ,2));
        // Why was this false originally?
        assertEquals(true,FirstCourse.addOrMoveJobPostingAt(TaJ,1));
        // Throws an exception! Be careful!
        assertEquals(true,FirstCourse.addOrMoveJobPostingAt(TaJ,-1));
    }
    
    @Test
    public void testCourseHasJobPostings() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        Calendar cal = Calendar.getInstance();
        CourseWorker Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
        Job TaJ = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        FirstCourse.addJobPosting(TaJ);
        assertEquals(true, FirstCourse.hasJobPostings());
    }
    
    @Test
    public void testCourseIndexOfJobPosting() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        Calendar cal = Calendar.getInstance();
        CourseWorker Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
        Job TaJ = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        FirstCourse.addJobPosting(TaJ);
        // 0-based indexing!!!
        assertEquals(0, FirstCourse.indexOfJobPosting(TaJ));
    }
    
    @Test
    public void testCourseNumberOfJobPostings() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        Calendar cal = Calendar.getInstance();
        CourseWorker Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
        Job TaJ = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        FirstCourse.addJobPosting(TaJ);
        assertEquals(1, FirstCourse.numberOfJobPostings());
    }
    
    @Test
    public void testCourseRemoveJobPosting() {
        Course FirstCourse = new Course("ECSE321", 1000, 100, 3, 3, 2, 2, tamas);
        Calendar cal = Calendar.getInstance();
        CourseWorker Cs = new CourseWorker("John", "guest01", 45, 111111111, "noexperience");
        Job TaJ = new Job(new Time(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis()), "TAforECSE321", "Give tutorials for ECSE321", FirstCourse, ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL);
        FirstCourse.addJobPosting(TaJ);
        // removeJobPosting is called by the destructor on the Job instance. Please try to understand the model better.
        TaJ.delete();
        assertEquals(false, FirstCourse.hasJobPostings());
    }
    
    @Test
    public void testCourseToString() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        if(tamas.getCourse(0).toString().length() <= 0){
            fail("Failed to get the a serialized string of the course at the first index within our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseWorkerToString() {
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        if(tamas.getUser(0).toString().length() <= 0){
            fail("Failed to get the a serialized string of the user at the first index within our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseWorkerAddOrMoveEvalAt() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        tamas.addUser(new Instructor("test", "test"));
        ((CourseWorker)tamas.getUser(0)).addEval(new Evaluation("test", tamas.getCourse(0), tamas.getCourse(0).getJobPosting(0), (Instructor)tamas.getUser(1), (CourseWorker)tamas.getUser(0)));
        ((CourseWorker)tamas.getUser(0)).addEval(new Evaluation("test2", tamas.getCourse(0), tamas.getCourse(0).getJobPosting(0), (Instructor)tamas.getUser(1), (CourseWorker)tamas.getUser(0)));
        ((CourseWorker)tamas.getUser(0)).addOrMoveEvalAt(((CourseWorker)tamas.getUser(0)).getEval(1), 0);
        if(!((CourseWorker)tamas.getUser(0)).getEval(0).getDescription().equals("test2")){
            fail("Failed to add or move a course evaluation for a test course worker.");
        }
    }
    
    @Test
    public void testCourseWorkerSetJobApplications() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        ((CourseWorker)tamas.getUser(0)).setJobApplications(new JobApplication(new Date(1), new Time(2), 0, (CourseWorker)tamas.getUser(0), tamas.getCourse(0).getJobPosting(0)));
        if(((CourseWorker)tamas.getUser(0)).getJobApplications().size() <= 0){
            fail("Failed to set the job applications on a test course worker in our Tamas Model instance.");
        }
    }
    
    @Test
    public void testEvaluationToString() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        tamas.addUser(new Instructor("test", "test"));
        ((CourseWorker)tamas.getUser(0)).addEval(new Evaluation("test", tamas.getCourse(0), tamas.getCourse(0).getJobPosting(0), (Instructor)tamas.getUser(1), (CourseWorker)tamas.getUser(0)));
        if(((CourseWorker)tamas.getUser(0)).getEval(0).toString().length() <= 0){
            fail("Failed to add and get the serialized string representation of an evaluation object.");
        }
    }
    
    @Test
    public void testJobApplicationToString() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        ((CourseWorker)tamas.getUser(0)).setJobApplications(new JobApplication(new Date(1), new Time(2), 0, (CourseWorker)tamas.getUser(0), tamas.getCourse(0).getJobPosting(0)), new JobApplication(new Date(1), new Time(2), 0, (CourseWorker)tamas.getUser(0), tamas.getCourse(0).getJobPosting(0)));
        if(((CourseWorker)tamas.getUser(0)).getJobApplication(0).toString().length() <= 0){
            fail("Failed to get the a serialized string of the job application at the first index within our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseWorkerAddOrMoveJobApplicationAt() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        tamas.addUser(new CourseWorker("test", "test", 0, 0, "test"));
        ((CourseWorker)tamas.getUser(0)).setJobApplications(new JobApplication(new Date(1), new Time(2), 0, (CourseWorker)tamas.getUser(0), tamas.getCourse(0).getJobPosting(0)), new JobApplication(new Date(1), new Time(2), 0, (CourseWorker)tamas.getUser(0), tamas.getCourse(0).getJobPosting(0)));
        if(!((CourseWorker)tamas.getUser(0)).addOrMoveJobApplicationAt(((CourseWorker)tamas.getUser(0)).getJobApplication(0), 1)){
            fail("Failed to add or move a job application at the first index in our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseAddJob() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        if(tamas.getCourse(0).getJobPosting(0) == null){
            fail("Failed to add a job posting to a test course in our Tamas Model instance.");
        }
    }
    
    @Test
    public void testCourseJobToString() {
        tamas.addCourse(new Course("test", 1, 1, 1, 1, 1, 1, tamas));
        tamas.getCourse(0).addJobPosting(new Job(new Time(1), new Time(2), new Date(1), new Time(3), "test", "test", tamas.getCourse(0), ca.mcgill.ecse321.tamas.model.Job.Day.MONDAY, ca.mcgill.ecse321.tamas.model.Job.Type.TUTORIAL));
        if(tamas.getCourse(0).getJobPosting(0).toString().length() <= 0){
            fail("Failed to get the a serialized string of the job at the first index within our Tamas Model instance.");
        }
    }

}