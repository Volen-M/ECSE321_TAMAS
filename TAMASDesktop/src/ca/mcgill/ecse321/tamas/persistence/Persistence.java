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

public abstract class Persistence {

    private static XStream xstream = new XStream();

    public static boolean saveModelToXMLwithXStream(Tamas tamas, String filename) {
        xstream.setMode(XStream.ID_REFERENCES);
        setAlias("course", Course.class);
        setAlias("evaluation", Evaluation.class);
        setAlias("job", Job.class);
        setAlias("jobapplication", JobApplication.class);
        setAlias("tamas", Tamas.class);
        String xml = xstream.toXML(tamas);

        File fileD = new File(filename);
        if (!fileD.exists()) {
            try {
                fileD.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Tamas loadModelFromXMLwithXStream(String filename) throws FileNotFoundException {
        xstream.setMode(XStream.ID_REFERENCES);
        setAlias("course", Course.class);
        setAlias("evaluation", Evaluation.class);
        setAlias("job", Job.class);
        setAlias("jobapplication", JobApplication.class);
        setAlias("tamas", Tamas.class);

        File fileModel = new File(filename);
        if (!fileModel.exists()) {
            try {
                fileModel.createNewFile();
                Tamas tamas = new Tamas();
                saveModelToXMLwithXStream(tamas, filename);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        FileReader fileReader = new FileReader(filename);
        return (Tamas) xstream.fromXML(fileReader);
    }

    public static void saveAuthenticationToXMLwithXStream(Authentication authentication, String filename) {
        xstream.setMode(XStream.ID_REFERENCES);
        setAlias("authentication", ca.mcgill.ecse321.tamas.authentication.Authentication.class);
        setAlias("user", ca.mcgill.ecse321.tamas.authentication.User.class);
        setAlias("courseworker", ca.mcgill.ecse321.tamas.authentication.CourseWorker.class);
        setAlias("instructor", ca.mcgill.ecse321.tamas.authentication.Instructor.class);
        setAlias("departmentadministrator", ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator.class);
        String xml = xstream.toXML(authentication);

        File fileAuthentication = new File(filename);
        if (!fileAuthentication.exists()) {
            try {
                fileAuthentication.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ca.mcgill.ecse321.tamas.authentication.Authentication loadAuthenticationFromXMLwithXStream(
            String filename) throws FileNotFoundException {
        xstream.setMode(XStream.ID_REFERENCES);
        setAlias("authentication", ca.mcgill.ecse321.tamas.authentication.Authentication.class);
        setAlias("user", ca.mcgill.ecse321.tamas.authentication.User.class);
        setAlias("courseworker", ca.mcgill.ecse321.tamas.authentication.CourseWorker.class);
        setAlias("instructor", ca.mcgill.ecse321.tamas.authentication.Instructor.class);
        setAlias("departmentadministrator", ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator.class);

        File fileD = new File(filename);
        if (!fileD.exists()) {
            try {
                fileD.createNewFile();
                Authentication authentication = new Authentication();
                saveAuthenticationToXMLwithXStream(authentication, filename);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        FileReader fileReader = new FileReader(filename);
        return (ca.mcgill.ecse321.tamas.authentication.Authentication) xstream.fromXML(fileReader);
    }

    public static void setAlias(String xmlTagName, Class<?> className) {
        xstream.alias(xmlTagName, className);
    }

}