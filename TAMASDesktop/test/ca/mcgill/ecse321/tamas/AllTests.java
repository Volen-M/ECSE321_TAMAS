package ca.mcgill.ecse321.tamas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.tamas.authentication.TestAuthentication;
import ca.mcgill.ecse321.tamas.controller.TestController;
import ca.mcgill.ecse321.tamas.model.TestModel;
import ca.mcgill.ecse321.tamas.persistence.TestPersistence;

@RunWith(Suite.class)
@SuiteClasses({ TestAuthentication.class, TestController.class, TestModel.class, TestPersistence.class })
public class AllTests {

}
