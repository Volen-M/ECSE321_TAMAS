package ca.mcgill.ecse321.tamas.persistence;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.model.Tamas;

public class TestPersistence {

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
    public void testSaveModelToXMLwithXStream() {
        Persistence.saveModelToXMLwithXStream(new Tamas(), "data.xml");
        File fileModel = new File("data.xml");
        if (!fileModel.exists()) {
            fail("Failed to save a Model instance to data.xml.");
        }
    }

    @Test
    public void testLoadModelFromXMLwithXStream() throws FileNotFoundException {
        Persistence.saveModelToXMLwithXStream(new Tamas(), "data.xml");
        File fileModel = new File("data.xml");
        if (!fileModel.exists()) {
            fail("Failed to save a Model instance to data.xml.");
        }
        Tamas tamas = Persistence.loadModelFromXMLwithXStream("data.xml");
        if (tamas == null) {
            fail("Failed to load a Model instance from data.xml");
        }
    }

    @Test
    public void testSaveAuthenticationToXMLwithXStream() {
        Persistence.saveAuthenticationToXMLwithXStream(new Authentication(), "users.xml");
        File fileAuth = new File("users.xml");
        if (!fileAuth.exists()) {
            fail("Failed to save an Authentication instance to users.xml.");
        }
    }

    @Test
    public void testLoadAuthenticationFromXMLwithXStream() throws FileNotFoundException {
        Persistence.saveAuthenticationToXMLwithXStream(new Authentication(), "users.xml");
        File fileAuth = new File("users.xml");
        if (!fileAuth.exists()) {
            fail("Failed to save a Model instance to data.xml.");
        }
        Authentication authentication = Persistence.loadAuthenticationFromXMLwithXStream("users.xml");
        if (authentication == null) {
            fail("Failed to load a Model instance from data.xml");
        }
    }

}
