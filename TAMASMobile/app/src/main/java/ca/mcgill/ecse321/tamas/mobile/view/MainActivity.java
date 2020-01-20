package ca.mcgill.ecse321.tamas.mobile.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse321.tamas.controller.ControllerMobile;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.*;
import ca.mcgill.ecse321.tamas.authentication.*;
import ca.mcgill.ecse321.tamas.model.CourseWorker;
import ca.mcgill.ecse321.tamas.model.User;
import ca.mcgill.ecse321.tamas.persistence.PersistenceMobile;
import tamas.ecse321.mcgill.ca.view.R;

public class MainActivity extends AppCompatActivity {

    private ca.mcgill.ecse321.tamas.model.CourseWorker loadedEmployee;
    private Job loadedJob3;
    private JobApplication loadedApplication;
    private Course loadedCourse2;
    private ControllerMobile controller;
    private Authentication authentication = null;
    private Tamas tamas = null;
    private String userfile;
    private String datafile;

    public enum Type { COURSEWORKER, INSTRUCTOR, ADMIN };
    private Type type;

    //Main Panel 1 Components init
    private Spinner comboBoxEmployList;
    private Button buttonViewEmploy;
    private EditText textAreaPastExperience;
    private  RadioButton radioUndergrad;
    private RadioButton radioGrad;
    private Button buttonApplyJob;
    private Button buttonAccept;
    private Button buttonDecline;

    //Main Panel 2 Component init;
    private Spinner comboBoxCourseList;
    private Button buttonLoadClass;
    private Spinner comboBoxJobList;
    private  Button buttonLoadJob;
    private  EditText textFieldJobTitle;
    private  EditText textAreaJobDescription;
    private  Button buttonAddJob;
    //Button buttonSaveJob = (Button) findViewById(R.id)
    private  Button buttonDeleteJob;


    //Main Panel 3 Component init
    //private JLabel labelUpdate;

    private TextView datePickerAppDeadline;
    private TextView timeSpinnerAppDeadline;
    private TextView timeSpinnerStartTime;
    private TextView timeSpinnerEndTime;
    private  RadioButton checkMonday;
    private RadioButton checkTuesday;
    private RadioButton checkWednesday;
    private RadioButton checkThursday;
    private RadioButton checkFriday;
    private EditText fieldTotalHoursSum;
    private RadioButton radioTA;
    private RadioButton radioLabTech;
    private RadioButton radioGrader;


    //Main Panel 4 Component init

    private EditText textFieldCourseTitle;
    private EditText textFieldHoursAmnt;
    private EditText textFieldLabAmnt;
    private EditText textFieldTutorialAmnt;
    private EditText textFieldStudentAmnt;
    private TextView labelBudgetAmnt;
    private EditText textFieldBudgetAmnt;

    private Button buttonAddCourse;
    private Button buttonDeleteCourse;
    private Button buttonAdminPage;

    //SnackBar
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posting);
        userfile = getFilesDir().getAbsolutePath() + "/users.xml";
        datafile = getFilesDir().getAbsolutePath() + "/data.xml";
        tamas = PersistenceMobile.initializeModelManager(datafile);
        authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
        controller = new ControllerMobile(tamas, authentication);

        //Main Panel 1 Components init
        comboBoxEmployList = (Spinner) findViewById(R.id.employeeSpinner);
        buttonViewEmploy = (Button) findViewById(R.id.loadEmployee);
        textAreaPastExperience = (EditText) findViewById(R.id.experience_heading);
        radioUndergrad = (RadioButton) findViewById(R.id.radioUndergrad);
        radioGrad = (RadioButton) findViewById(R.id.radioGrad);
        buttonApplyJob = (Button) findViewById(R.id.applyJob);
        buttonAccept = (Button) findViewById(R.id.acceptJob_button);
        buttonDecline = (Button) findViewById(R.id.declineJob_button);

        //Main Panel 2 Component init;
        comboBoxCourseList = (Spinner) findViewById(R.id.courseSpinner);
        buttonLoadClass = (Button) findViewById(R.id.load_course_button);
        comboBoxJobList = (Spinner) findViewById(R.id.jobSpinner);
        buttonLoadJob = (Button) findViewById(R.id.loadJob_button);
        textFieldJobTitle = (EditText) findViewById(R.id.jobTitle);
        textAreaJobDescription = (EditText) findViewById(R.id.jobDescription);
        buttonAddJob = (Button) findViewById(R.id.add_job_button);
        //Button buttonSaveJob = (Button) findViewById(R.id)
       buttonDeleteJob = (Button)  findViewById(R.id.delete_button);


        //Main Panel 3 Component init


        datePickerAppDeadline = (TextView)findViewById(R.id.application_date);
        timeSpinnerAppDeadline = (TextView)findViewById(R.id.application_deadline_time);
        timeSpinnerStartTime = (TextView)findViewById(R.id.work_start_time);
        timeSpinnerEndTime = (TextView)findViewById(R.id.work_end_time);
        checkMonday = (RadioButton)findViewById(R.id.androidChkBox_monday);
        checkTuesday = (RadioButton)findViewById(R.id.androidChkBox_tuesday);
        checkWednesday = (RadioButton)findViewById(R.id.androidChkBox_wednesday);
        checkThursday = (RadioButton)findViewById(R.id.androidChkBox_thursday);
        checkFriday = (RadioButton)findViewById(R.id.androidChkBox_friday);
        fieldTotalHoursSum = (EditText)findViewById(R.id.totalhours_week);
        radioTA = (RadioButton)findViewById(R.id.radioTA);
        radioLabTech = (RadioButton)findViewById(R.id.radioTechnician);
        radioGrader = (RadioButton)findViewById(R.id.radioGrader);


        //Main Panel 4 Component init

        textFieldCourseTitle =(EditText)findViewById(R.id.courseTitle);
        textFieldHoursAmnt = (EditText)findViewById(R.id.hours_course);
        textFieldLabAmnt = (EditText)findViewById(R.id.hours_labs);
        textFieldTutorialAmnt = (EditText)findViewById(R.id.nbr_tutorials);
        textFieldStudentAmnt = (EditText)findViewById(R.id.nbr_enrolled_students);
        textFieldBudgetAmnt = (EditText)findViewById(R.id.budget);

        buttonAddCourse = (Button)findViewById(R.id.add_course_button);
        buttonDeleteCourse = (Button)findViewById(R.id.delete_course_button);
        buttonAdminPage = (Button)findViewById(R.id.open_admin_button);

        if (LoginActivity.currentUser instanceof ca.mcgill.ecse321.tamas.authentication.CourseWorkerAuth){
            type = Type.COURSEWORKER;
            List<ca.mcgill.ecse321.tamas.model.User> users = controller.getModelUsers();
            for (ca.mcgill.ecse321.tamas.model.User user: users){
                if (LoginActivity.currentUser.getUsername() == user.getUsername()){
                    loadedEmployee = (ca.mcgill.ecse321.tamas.model.CourseWorker)user;
                }
            }
        }
        if (LoginActivity.currentUser instanceof ca.mcgill.ecse321.tamas.authentication.InstructorAuth){
            type = Type.INSTRUCTOR;
        }
        if (LoginActivity.currentUser instanceof ca.mcgill.ecse321.tamas.authentication.DepartmentAdministratorAuth){
            type = Type.ADMIN;
        }
        initialSetup();
        loadCourses();
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private Time bundleToTime (Bundle bundle){
        int time_hour = bundle.getInt("hour");
        int time_min = bundle.getInt("minute");
        return new Time(time_hour,time_min,0);
    }

    private Date bundleToDate (Bundle bundle){
        int date_day= bundle.getInt("day");
        int date_month=bundle.getInt("month");
        int date_year=bundle.getInt("year");
        return new Date(date_year,date_month,date_day);

    }

    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    public void acceptJob(View view) {
        try{
            if (type == Type.COURSEWORKER){
                controller.allocateWorkerToJob(loadedJob3, loadedEmployee.getUsername());
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.ACCEPTED);
                String e ="<html>You are now employed for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                loadJob(view);
            }
            else if (type == Type.ADMIN){
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.APPROVED);
                String e ="<html>You have approved the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                viewEmploy(view);
            }

            else if (type == Type.INSTRUCTOR){
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.OFFER);
                String e = "<html>You have sent for approval the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                viewEmploy(view);
            }
        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void applyJob(View view) {
        try{
            updateData();
            Calendar c = Calendar.getInstance();
            Date currentDate = new Date(c.getTimeInMillis());
            Time currentTime = new Time(c.getTimeInMillis());
            CourseWorker tempCourseWorker = loadedEmployee;
            if(radioUndergrad.isChecked())	{ loadedEmployee.setAcademicStatus(CourseWorker.AcademicStatus.UNDERGRADUATE); }
            else if (radioGrad.isChecked())	{ loadedEmployee.setAcademicStatus(CourseWorker.AcademicStatus.GRADUATE); }
            else throw new Exception ("Academic status radio button doesn't work");
            tamas.removeUser(tempCourseWorker);
            tamas.addUser(loadedEmployee);
            controller = new ControllerMobile(tamas,authentication);
            controller.saveModel();
            tempCourseWorker = loadedEmployee;
            loadedEmployee.setPastWorkerExperience( textAreaPastExperience.getText().toString());
            tamas.removeUser(tempCourseWorker);
            tamas.addUser(loadedEmployee);
            controller = new ControllerMobile(tamas,authentication);
            controller.saveModel();

            controller.applyForJob(loadedJob3, currentDate, currentTime, 1 , loadedEmployee.getUsername());
            List<Course> courses = controller.getCourseList();
            for(Course course: courses){
                if (course.getName()==loadedCourse2.getName()) {
                    loadedCourse2 = course;
                }
            }
            List<User> users = controller.getModelUsers();
            for(User user: users){
                if (user.getUsername()==loadedEmployee.getUsername()){
                    loadedEmployee=(CourseWorker)user;
                }
            }
            loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
            controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.APPLIED);
            List<Course> courses2 = controller.getCourseList();
            for(Course course: courses2){
                if (course.getName()==loadedCourse2.getName()) {
                    loadedCourse2 = course;
                }
            }
            List<User> users2 = controller.getModelUsers();
            for(User user: users2){
                if (user.getUsername()==loadedEmployee.getUsername()){
                    loadedEmployee=(CourseWorker)user;
                }
            }
            String e = "<html>You have applied for "  + loadedJob3.getTitle();
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
            loadJob(view);
        }
        catch (Exception e)
        {
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }


    public void declineJob(View view) {
        try{
            if (type == Type.COURSEWORKER){
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
                String e ="<html>You have rejected the job offer for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                loadJob(view);
            }
            else if (type == Type.ADMIN){
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
                String e = "<html>You have rejected the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                viewEmploy(view);
            }

            else if (type == Type.INSTRUCTOR){
                loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);
                controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
                String e = "<html>You have rejected the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                viewEmploy(view);
            }
        }
        catch (Exception e){ Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void addJob(View view)   {

        try{
            Date deadlineDate = bundleToDate(getDateFromLabel(datePickerAppDeadline.getText()));
            Time startTime = bundleToTime(getTimeFromLabel(timeSpinnerStartTime.getText()));
            Time endTime = bundleToTime(getTimeFromLabel(timeSpinnerEndTime.getText()));
            Time deadlineTime= bundleToTime(getTimeFromLabel(timeSpinnerAppDeadline.getText()));
            ca.mcgill.ecse321.tamas.model.Job.Day day = Job.Day.MONDAY;
            ca.mcgill.ecse321.tamas.model.Job.Type type = Job.Type.GRADER;

            day = Job.Day.MONDAY;
            if (checkMonday.isChecked()){ day = Job.Day.MONDAY;}
            else if (checkTuesday.isChecked()){ day = Job.Day.TUESDAY;}
            else if (checkWednesday.isChecked()){ day = Job.Day.WEDNESDAY;}
            else if (checkThursday.isChecked()){ day = Job.Day.THURSDAY;}
            else if (checkFriday.isChecked()){ day = Job.Day.FRIDAY;}
            else throw new Exception ("Radio buttons are busted.");

            type = Job.Type.GRADER;
            if (radioGrader.isChecked()){ type = Job.Type.GRADER;}
            else if (radioLabTech.isChecked()){ type = Job.Type.LAB;}
            else if (radioTA.isChecked()){ type = Job.Type.TUTORIAL;}
            else throw new Exception ("Radio buttons are broken.");
            updateHours();
            controller.addCourseJob(textFieldJobTitle.getText().toString(), textAreaJobDescription.getText().toString(), loadedCourse2, day , type, startTime, endTime, deadlineDate, deadlineTime);
            loadCourse(view);

            String e = textFieldJobTitle.getText().toString() + " successfully added";
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        catch(Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void deleteJob(View view)    {
        try{
            controller.removeCourseJob(loadedCourse2, loadedJob3);
            loadCourse(view);
            String e = loadedJob3.getTitle() + " successfully deleted";
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        catch(Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void updateHours() {
        String[] endTime = timeSpinnerEndTime.getText().toString().split(":");
        String[] startTime = timeSpinnerStartTime.getText().toString().split(":");

        int min = Math.abs((Integer.parseInt(endTime[1]) - Integer.parseInt(startTime[1])));
        int hours = Math.abs((Integer.parseInt(endTime[0]) - Integer.parseInt(startTime[0])));
        if (Integer.parseInt(endTime[0]) - Integer.parseInt(startTime[0]) <0 || Integer.parseInt(endTime[0]) - Integer.parseInt(startTime[0]) == 0  && Integer.parseInt(endTime[1]) - Integer.parseInt(startTime[1]) <0){
            hours= 24- hours;
        }
        if (Integer.parseInt(endTime[1]) - Integer.parseInt(startTime[1]) <0){
            min= Math.abs(min-60);
            hours--;
        }
        fieldTotalHoursSum.setText(Integer.toString(hours)+ " hours and " + Integer.toString(min) + " minutes");
        updateData();
    }

    public void applyForJob(View view) {

        try{
            Calendar c = Calendar.getInstance();
            Date currentDate = new Date(c.getTimeInMillis());
            Time currentTime = new Time(c.getTimeInMillis());
            List<User> users = controller.getModelUsers();

            if(radioUndergrad.isChecked())	{ controller.changeCourseWorkerAcademicStatus(loadedEmployee.getUsername(), CourseWorker.AcademicStatus.UNDERGRADUATE); }
            else if (radioGrad.isChecked())	{ controller.changeCourseWorkerAcademicStatus(loadedEmployee.getUsername(), CourseWorker.AcademicStatus.GRADUATE); }
            else throw new Exception ("Academic status radio button doesn't work");
            controller.changeCourseWorkerPastExp(loadedEmployee.getUsername(), textAreaPastExperience.getText().toString());
            controller.applyForJob(loadedJob3, currentDate, currentTime, 1 , loadedEmployee.getUsername());
            loadedApplication = controller.getJobApplication( loadedEmployee, loadedJob3);

            String e = "<html>You have applied for "  + loadedJob3.getTitle();
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
            loadJob(view);
        }
        catch (Exception e)
        {
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }
    //*/
    public void viewEmploy(View view) {
        try{
            String[] employeeString = comboBoxEmployList.getSelectedItem().toString().split(" - ");
            String employeeName = employeeString[0];
            loadedEmployee = controller.getEmployee(employeeName, loadedJob3);
            textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
            String e =loadedEmployee.getName() + " successfully loaded";
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
            if (loadedEmployee.getAcademicStatus() == ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus.UNDERGRADUATE){
                radioUndergrad.setChecked(true);
                radioGrad.setChecked(false);
            }
            if (loadedEmployee.getAcademicStatus() == ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus.GRADUATE){
                radioUndergrad.setChecked(false);
                radioGrad.setChecked(true);
            }
            String e2 =loadedEmployee.getName() + " successfully loaded";
            Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();

            //buttonAccept & buttonDecline situational setup
            if (controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.ACCEPTED){
                buttonAccept.setEnabled(false);
                buttonDecline.setEnabled(false);
                e2 =loadedEmployee.getName() + "<html> is already employed for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            else if(controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.REJECTED){
                buttonAccept.setEnabled(false);
                buttonDecline.setEnabled(false);
                 e2 =loadedEmployee.getName() + "<html>'s application has already been rejected or has declined " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();

            }
            else if(controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.APPROVED){
                buttonAccept.setEnabled(false);
                buttonDecline.setEnabled(false);
                 e2 =loadedEmployee.getName() + "<html> has already had his/her job offer approved for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            else if(controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.APPLIED){
                buttonAccept.setEnabled(false);
                buttonDecline.setEnabled(false);
                 e2 =loadedEmployee.getName() + "<html> has applied for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();

                if (type == Type.INSTRUCTOR){
                    buttonAccept.setEnabled(true);
                    buttonDecline.setEnabled(true);
                }
            }
            else if(controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.OFFER){
                buttonAccept.setEnabled(false);
                buttonDecline.setEnabled(false);
                String e3 =loadedEmployee.getName() + "<html> has had his/her application accepted by an instructor for " + loadedJob3.getTitle();
                Snackbar.make(findViewById(R.id.activity_view_posting), e3.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();

                if (type == Type.ADMIN){
                    buttonAccept.setEnabled(true);
                    buttonDecline.setEnabled(true);
                }
            }

        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void loadJob(View view) {
        try {
            updateData();
            boolean succesfulLoad = false;
            List<ca.mcgill.ecse321.tamas.model.Job> jobs = controller.getCourseJobPostings(loadedCourse2);
            for(ca.mcgill.ecse321.tamas.model.Job job : jobs ){
                if(comboBoxJobList.getSelectedItem().toString().equals(job.getTitle())){
                    loadedJob3 = job;
                    succesfulLoad = true;
                }
            }
            if ( Job.Day.MONDAY.equals(loadedJob3.getDay())){
                checkMonday.setChecked(true);
            }
            else if ( Job.Day.TUESDAY.equals(loadedJob3.getDay())){
                checkTuesday.setChecked(true);
            }
            else if ( Job.Day.WEDNESDAY.equals(loadedJob3.getDay())){
                checkWednesday.setChecked(true);
            }
            else if ( Job.Day.THURSDAY.equals(loadedJob3.getDay())){
                checkThursday.setChecked(true);
            }
            else if ( Job.Day.FRIDAY.equals(loadedJob3.getDay())){
                checkFriday.setChecked(true);
            }
            textAreaJobDescription.setText(loadedJob3.getDescription());
            textFieldJobTitle.setText(loadedJob3.getTitle());


            if (Job.Type.GRADER.equals(loadedJob3.getType())){
                radioGrader.setChecked(true);
                radioTA.setChecked(false);
                radioLabTech.setChecked(false);
            }
            else if(Job.Type.TUTORIAL.equals(loadedJob3.getType())){
                radioGrader.setChecked(false);
                radioTA.setChecked(true);
                radioLabTech.setChecked(false);
            }
            else if(Job.Type.LAB.equals(loadedJob3.getType())){
                radioGrader.setChecked(false);
                radioTA.setChecked(false);
                radioLabTech.setChecked(true);
            }
            try{
                if (type == Type.ADMIN || type == Type.INSTRUCTOR ){
                    comboBoxEmployList.setAdapter(null);
                    ArrayAdapter<CharSequence> employListAdapter
                            = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
                    List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsAccepted = controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.ACCEPTED);
                    if (!applicantsAccepted.isEmpty()){
                        for (ca.mcgill.ecse321.tamas.model.User user: applicantsAccepted){
                            employListAdapter.add(user.getName()+ " - Employed");
                        }
                    }
                    List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsApproved = controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.APPROVED);
                    if (!applicantsApproved.isEmpty()){
                        for (ca.mcgill.ecse321.tamas.model.User user: applicantsApproved){
                            employListAdapter.add(user.getName()+ " - Approved");
                        }
                    }
                    List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsOffer = controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.OFFER);
                    if (!applicantsOffer.isEmpty()){
                        for (ca.mcgill.ecse321.tamas.model.User user: applicantsOffer){
                            employListAdapter.add(user.getName()+ " - To Approve");
                        }
                    }
                    List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsApplied = controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.APPLIED);
                    if (!applicantsApplied.isEmpty()){
                        for (ca.mcgill.ecse321.tamas.model.User user: applicantsApplied){
                            employListAdapter.add(user.getName()+ " - Applied");
                        }
                    }
                    List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsRejected = controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.REJECTED);
                    if (!applicantsRejected.isEmpty()){
                        for (ca.mcgill.ecse321.tamas.model.User user: applicantsRejected){
                            employListAdapter.add(user.getName()+ " - Rejected");
                        }
                    }
                    comboBoxEmployList.setAdapter(employListAdapter);
                    if (succesfulLoad){

                        String e = loadedJob3.getTitle() + " successfully loaded";
                        Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
                else if (type == Type.COURSEWORKER){
                    boolean appCheck= false;
                    List<JobApplication> jobApplications = loadedJob3.getJobApplications();
                    for (JobApplication jobApplication : jobApplications){
                        if(jobApplication.getWorker().getUsername() == loadedEmployee.getUsername()){
                            appCheck = true;
                            loadedApplication = jobApplication;
                        }
                    }
                    String e = loadedJob3.getTitle() + " successfully loaded";
                    Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                            Snackbar.LENGTH_SHORT)
                            .show();
                    buttonApplyJob.setEnabled(true);
                    buttonAccept.setEnabled(false);
                    buttonDecline.setEnabled(false);

                    if (appCheck){
                        if (loadedApplication.getApplicationStatusFullName() != null){

                            if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.APPROVED ){

                                String e2 = "<html>You have been sent a job offer for this job.";
                                Snackbar.make(findViewById(R.id.activity_view_posting), e2.toString(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                buttonAccept.setEnabled(true);
                                buttonDecline.setEnabled(true);
                                buttonApplyJob.setEnabled(false);
                            }
                            else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.ACCEPTED){

                                String e3 = "<html>You are already Employed for this Job";
                                Snackbar.make(findViewById(R.id.activity_view_posting), e3.toString(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                buttonApplyJob.setEnabled(false);
                            }
                            else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.REJECTED){
                                buttonApplyJob.setEnabled(false);
                                String e4 = "<html>You have declined this job or your application has been rejected.";
                                Snackbar.make(findViewById(R.id.activity_view_posting), e4.toString(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                            else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.OFFER){
                                buttonApplyJob.setEnabled(false);
                                String e5 = "<html>Your application for this job is being reviewed.";
                                Snackbar.make(findViewById(R.id.activity_view_posting), e5.toString(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                            else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.APPLIED){
                                buttonApplyJob.setEnabled(false);
                                String e6 = "<html>You have applied. Your application for this job is being reviewed.";
                                Snackbar.make(findViewById(R.id.activity_view_posting), e6.toString(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }
                        textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
                    }
                }
            }
            catch(Exception e1){
                Snackbar.make(findViewById(R.id.activity_view_posting), e1.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            updateHours();
        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void openAdminPage(View view)    {

        if (type == Type.ADMIN)
        {
            Intent intent = new Intent(this, AdminActivity2.class);
            startActivity(intent);
        }

        updateData();

    }

    public void deleteCourse(View view) {
        try {
            updateData();
            controller.removeCourse(loadedCourse2);
            updateData();
            loadCourses();
            String e = loadedCourse2.getName() + " successfully deleted";
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();

        } catch (InvalidInputException e) {
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    public void loadCourse(View view) {
        try {
            updateData();
            comboBoxJobList.setAdapter(null);
            String courseName = comboBoxCourseList.getSelectedItem().toString();
            ArrayAdapter<CharSequence> jobListAdapter
                    = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
            List<Course> courses = controller.getCourseList();
            /*if (!(courses.isEmpty())){
                for (Course course : courses) {
                    if (courseName == course.getName()){
                        loadedCourse2= course;
                        break;
                    }
                }
            }
            List<Job> jobs = loadedCourse2.getJobPostings();
            */
            int index = comboBoxCourseList.getSelectedItemPosition();
            List<ca.mcgill.ecse321.tamas.model.Job> jobs = controller.getCourseJobPostings(controller.getCourse(index));

            ca.mcgill.ecse321.tamas.model.Course course = controller.getCourse(index);
            loadedCourse2 = course;

            if (!(jobs.isEmpty())) {
                for (Job job : jobs) {
                    jobListAdapter.add(job.getTitle().toString());
                }
                String e = loadedCourse2.getName() + " successfully loaded";
                Snackbar.make(findViewById(R.id.activity_view_posting), e,
                        Snackbar.LENGTH_SHORT)
                        .show();
                comboBoxJobList.setAdapter(jobListAdapter);
            }
            textFieldCourseTitle.setText(loadedCourse2.getName());
            textFieldHoursAmnt.setText(Integer.toString(loadedCourse2.getHours()));
            textFieldTutorialAmnt.setText(Integer.toString(loadedCourse2.getTutorialCount()));
            textFieldLabAmnt.setText(Integer.toString(loadedCourse2.getLabCount()));
            textFieldStudentAmnt.setText(Integer.toString(loadedCourse2.getStudentsEnrolled()));
            textFieldBudgetAmnt.setText(Double.toString(loadedCourse2.getBudget()));

        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    private void updateData(){
        tamas = PersistenceMobile.initializeModelManager(datafile);
        authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
        controller = new ControllerMobile(tamas, authentication);
    }

    public void addCourse(View view)    {
        try{
            if(textFieldCourseTitle.getText().toString().length()>10){
                throw new Exception("Course name too long. Please enter a valid course code.");
            }
            controller.addNewCourse(textFieldCourseTitle.getText().toString(), Double.parseDouble(textFieldBudgetAmnt.getText().toString()), Integer.parseInt(textFieldStudentAmnt.getText().toString()), 3, Integer.parseInt(textFieldHoursAmnt.getText().toString()), Integer.parseInt(textFieldTutorialAmnt.getText().toString()), Integer.parseInt(textFieldLabAmnt.getText().toString()));
            loadCourses();
            String e = textFieldCourseTitle.getText().toString() + " successfully added";
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();

    }

    public void addCourseJob(String title, String description, Course course, Job.Day
        day, Job.Type type, Time startTime, Time endTime, Date deadlineDate, Time deadlineTime) throws InvalidInputException {
            updateData();
            boolean exists = false;
            Course tempCourse;
            List jobs = controller.getCourseJobPostings(course);
            Iterator i = jobs.iterator();

            while(i.hasNext()) {
                Job courses = (Job)i.next();
                if(title.equals(courses.getTitle())) {
                    exists = true;
                    break;
                }
            }

            if(title.length() > 0 && description.length() > 0 && !course.equals((Object)null) && !day.equals((Object)null) && !type.equals((Object)null) && !startTime.equals((Object)null) && !endTime.equals((Object)null) && !deadlineDate.equals((Object)null) && !deadlineTime.equals((Object)null) && !exists) {
                List var14 = controller.getCourseList();

                for(int var15 = 0; var15 < var14.size(); ++var15) {
                    if(((Course)var14.get(var15)).getName().equals(course.getName())) {
                        tempCourse =((Course)var14.get(var15));
                        tempCourse.addJobPosting(startTime, endTime, deadlineDate, deadlineTime, title, description, day, type);
                        tamas.removeCourse((Course)var14.get(var15));
                        tamas.addCourse(tempCourse);
                        controller = new ControllerMobile(tamas, authentication);
                        controller.saveModel();
                        return;
                    }
                }

            } else {
                throw new InvalidInputException("A parameter has a bad value.");
            }
    }
    //*/
    public void loadCourses() {
        updateData();
        comboBoxCourseList.setAdapter(null);
        ArrayAdapter<CharSequence> courseListAdapter
                = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        List<Course> courses = controller.getCourseList();
        if (!(courses.isEmpty())){
            for (Course course : courses) {
                courseListAdapter.add(course.getName());
            }
            comboBoxCourseList.setAdapter(courseListAdapter);
        }
    }

    private void initialSetup() {


        fieldTotalHoursSum.setEnabled(false);
        checkMonday.setChecked(true);
        radioUndergrad.setChecked(true);

        if (type == Type.COURSEWORKER) {


            //Panel1
            comboBoxEmployList.setEnabled(false);
            buttonViewEmploy.setEnabled(false);
            textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
            buttonAccept.setText("Accept Job Offer");
            buttonDecline.setText("Decline Job Offer");
            buttonAccept.setEnabled(false);
            buttonDecline.setEnabled(false);

            //Panel2
            textFieldJobTitle.setEnabled(false);
            textAreaJobDescription.setEnabled(false);
            buttonAddJob.setEnabled(false);
            buttonDeleteJob.setEnabled(false);

            //Panel3
            datePickerAppDeadline.setEnabled(false);
            timeSpinnerAppDeadline.setEnabled(false);
            timeSpinnerStartTime.setEnabled(false);
            timeSpinnerEndTime.setEnabled(false);
            checkMonday.setEnabled(false);
            checkTuesday.setEnabled(false);
            checkWednesday.setEnabled(false);
            checkThursday.setEnabled(false);
            checkFriday.setEnabled(false);
            radioGrader.setEnabled(false);
            radioTA.setEnabled(false);
            radioLabTech.setEnabled(false);

            //Panel4
            textFieldCourseTitle.setEnabled(false);
            textFieldHoursAmnt.setEnabled(false);
            textFieldLabAmnt.setEnabled(false);
            textFieldTutorialAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            textFieldBudgetAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            buttonAddCourse.setEnabled(false);
            buttonAdminPage.setEnabled(false);


        }

        if (type == Type.INSTRUCTOR) {

            //Panel1
            textAreaPastExperience.setEnabled(false);
            radioGrad.setEnabled(false);
            radioUndergrad.setEnabled(false);
            buttonApplyJob.setEnabled(false);
            buttonAccept.setText("Accept Application");
            buttonDecline.setText("Reject Application");
            buttonAccept.setEnabled(false);
            buttonDecline.setEnabled(false);

            //Panel2
            //All Enabled

            //Panel3
            //All Enabled

            //Panel4
            textFieldCourseTitle.setEnabled(false);
            textFieldHoursAmnt.setEnabled(false);
            textFieldLabAmnt.setEnabled(false);
            textFieldTutorialAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            textFieldBudgetAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            textFieldStudentAmnt.setEnabled(false);
            buttonAddCourse.setEnabled(false);
            buttonAdminPage.setEnabled(false);
        }

        if (type == Type.ADMIN) {

            //Panel1
            textAreaPastExperience.setEnabled(false);
            radioGrad.setEnabled(false);
            radioUndergrad.setEnabled(false);
            buttonApplyJob.setEnabled(false);
            buttonAccept.setText("Accept Employment");
            buttonDecline.setText("Reject Employment");
            buttonAccept.setEnabled(false);
            buttonDecline.setEnabled(false);

            //Panel2
            textFieldJobTitle.setEnabled(false);
            textAreaJobDescription.setEnabled(false);
            buttonAddJob.setEnabled(false);
            buttonDeleteJob.setEnabled(false);

            //Panel3
            datePickerAppDeadline.setEnabled(false);
            timeSpinnerAppDeadline.setEnabled(false);
            timeSpinnerStartTime.setEnabled(false);
            timeSpinnerEndTime.setEnabled(false);
            checkMonday.setEnabled(false);
            checkTuesday.setEnabled(false);
            checkWednesday.setEnabled(false);
            checkThursday.setEnabled(false);
            checkFriday.setEnabled(false);
            radioGrader.setEnabled(false);
            radioTA.setEnabled(false);
            radioLabTech.setEnabled(false);

            //Panel4
            //All Enabled

        }
    }

}
