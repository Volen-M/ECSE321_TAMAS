package ca.mcgill.ecse321.tamas.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobApplication;
import ca.mcgill.ecse321.tamas.model.Tamas;

public abstract class PersistenceMobile {

    private static XStream xstream = new XStream();
    private static String datafile = "data.xml";
    private static String userfile = "user.xml";
    
    public static Tamas initializeModelManager(String datafile3){
    	Tamas tamas;
    	setDatafile(datafile3);
        setAlias("course", Course.class);
        setAlias("evaluation", Evaluation.class);
        setAlias("job", Job.class);
        setAlias("jobapplication", JobApplication.class);
        setAlias("tamas", Tamas.class);
    	
        File file = new File(datafile);
        if (file.exists()) {
            tamas = (Tamas) loadModelFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            tamas = new Tamas();
            saveModelToXMLwithXStream(tamas);
        }
        return tamas;
    }

    public static Authentication initializeAuthenticationManager(String userfile3){
    	Authentication authentication;
    	setUserfile(userfile3);
        setAlias("authentication", ca.mcgill.ecse321.tamas.authentication.Authentication.class);
        setAlias("user", ca.mcgill.ecse321.tamas.authentication.User.class);
        setAlias("courseworker", ca.mcgill.ecse321.tamas.authentication.CourseWorker.class);
        setAlias("instructor", ca.mcgill.ecse321.tamas.authentication.Instructor.class);
        setAlias("departmentadministrator", ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator.class);;
    	
        File file = new File(userfile);
        if (file.exists()) {
            authentication = (Authentication) loadAuthenticationFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            authentication = new Authentication();
            saveModelToXMLwithXStream(authentication);
        }
        return authentication;
    	
    }
    
	public static boolean saveModelToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(datafile);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object loadModelFromXMLwithXStream(){
        xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(datafile); // load our xml file
            return xstream.fromXML(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveAuthenticationToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(userfile);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object loadAuthenticationFromXMLwithXStream(){
            xstream.setMode(XStream.ID_REFERENCES);
            try {
                FileReader fileReader = new FileReader(userfile); // load our xml file
                return xstream.fromXML(fileReader);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }

    public static void setAlias(String xmlTagName, Class<?> className) {
        xstream.alias(xmlTagName, className);
    }
    
    private static void setDatafile(String datafile2) {
		datafile = datafile2;
	}
    
    private static void setUserfile(String userfile2) {
		userfile = userfile2;
		
	}

}