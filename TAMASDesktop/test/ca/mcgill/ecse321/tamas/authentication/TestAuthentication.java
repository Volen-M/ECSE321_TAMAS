package ca.mcgill.ecse321.tamas.authentication;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAuthentication {

    Authentication authentication;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        authentication = new Authentication();
    }

    @After
    public void tearDown() throws Exception {
        authentication.delete();
    }

    @Test
    public void testAuthenticationAddUser() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if (!authentication.hasUsers()) {
            fail("Failed to add a test user to our Authentication instance.");
        }
    }

    @Test
    public void testAuthenticationAddUserAt() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        authentication.addUserAt(new CourseWorker("test", "test", "test"), 1);
        if (!(authentication.getUser(1) instanceof CourseWorker)) {
            fail("Failed to add a test course worker to our desired index.");
        }
    }

    @Test
    public void testAuthenticationAddOrMoveUserAt() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        authentication.addOrMoveUserAt(authentication.getUser(0), authentication.numberOfUsers() - 1);
        if (!(authentication.getUsers().get(authentication.numberOfUsers() - 1) instanceof DepartmentAdministrator)) {
            fail("Failed to add or move a test department administrator to our desired index.");
        }
    }

    @Test
    public void testAuthenticationGetUser() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if (!(authentication.getUser(0) instanceof DepartmentAdministrator)) {
            fail("Failed to get the user at the desired index within our authentication instance.");
        }
    }
    
    @Test
    public void testAuthenticationUserToString() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if(authentication.getUser(0).toString().length() <= 0){
            fail("Failed to get the a serialized string of the user at the first index within our authentication instance.");
        }
    }

    @Test
    public void testAuthenticationGetUsers() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if (authentication.getUsers().size() <= 0) {
            fail("Failed to get the users within our authentication instance.");
        }
    }

    @Test
    public void testAuthenticationSetCurrentUser() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if (!authentication.setCurrentUser(authentication.getUser(0))) {
            fail("Failed to set the current user within our authentication instance.");
        }
    }

    @Test
    public void testAuthenticationRemoveUser() {
        authentication.addUser(new DepartmentAdministrator("test", "test", "test"));
        if (!authentication.removeUser(authentication.getUser(0))) {
            fail("Failed to remove a user within our authentication instance.");
        }
    }

}
