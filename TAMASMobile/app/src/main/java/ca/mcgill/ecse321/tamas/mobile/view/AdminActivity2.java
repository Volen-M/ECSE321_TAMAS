package ca.mcgill.ecse321.tamas.mobile.view;

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

import java.util.List;

import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.authentication.UserAuth;
import ca.mcgill.ecse321.tamas.controller.ControllerMobile;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Tamas;
import ca.mcgill.ecse321.tamas.persistence.PersistenceMobile;
import tamas.ecse321.mcgill.ca.view.R;

public class AdminActivity2 extends AppCompatActivity {

    public boolean hideID = true;
    private Authentication authentication = null;
    private Tamas tamas = null;
    private String userfile;
    private String datafile;
    private RadioButton checkCourseWorker;
    private RadioButton checkInstructor;
    private RadioButton checkAdmin;
    private EditText fieldMcGillID;
    private EditText fieldPassword;
    private TextView labelNameProc;
    private TextView labelUsernameProc;
    private Button buttonuserList;
    private Button buttonSaveUser;
    private Spinner comboBoxUserList;
    private ControllerMobile controller;
    private ca.mcgill.ecse321.tamas.authentication.UserAuth loadedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);
        userfile = getFilesDir().getAbsolutePath() + "/users.xml";
        datafile = getFilesDir().getAbsolutePath() + "/data.xml";
        tamas = PersistenceMobile.initializeModelManager(datafile);
        authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
        controller = new ControllerMobile(tamas, authentication);

        checkCourseWorker =(RadioButton)findViewById(R.id.co);
        checkInstructor =(RadioButton)findViewById(R.id.te);
        checkAdmin =(RadioButton)findViewById(R.id.ad);
        labelUsernameProc =(TextView)findViewById(R.id.username2);
        fieldMcGillID =(EditText)findViewById(R.id.mcgillID_admin);
        fieldPassword =(EditText)findViewById(R.id.password_admin);
        labelNameProc =(TextView)findViewById(R.id.name2);
        buttonuserList =(Button)findViewById(R.id.loadUser_button);
        buttonSaveUser =(Button)findViewById(R.id.saveUser_button);
        comboBoxUserList =(Spinner)findViewById(R.id.userSpinner);
        loadUsers();
    }


    private void updateData(){
        tamas = PersistenceMobile.initializeModelManager(datafile);
        authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
        controller = new ControllerMobile(tamas, authentication);
    }

    public void loadUser(View view) {
        try{
            String[] nameUsername = comboBoxUserList.getSelectedItem().toString().split(" - ");
            String userUsername = nameUsername[1];
            List<UserAuth> users = controller.getAuthenticationUsers();
            for (ca.mcgill.ecse321.tamas.authentication.UserAuth user : users){
                if (user.getUsername().equals(userUsername)){
                    loadedUser = user;
                    if (user instanceof ca.mcgill.ecse321.tamas.authentication.CourseWorkerAuth){
                        checkCourseWorker.setChecked(true);
                        checkInstructor.setChecked(false);
                        checkAdmin.setChecked(false);

                    }
                    if (user instanceof ca.mcgill.ecse321.tamas.authentication.InstructorAuth){
                        checkCourseWorker.setChecked(false);
                        checkInstructor.setChecked(true);
                        checkAdmin.setChecked(false);
                        fieldMcGillID.setText("");
                    }
                    if (user instanceof ca.mcgill.ecse321.tamas.authentication.DepartmentAdministratorAuth){
                        checkCourseWorker.setChecked(false);
                        checkInstructor.setChecked(false);
                        checkAdmin.setChecked(true);
                        fieldMcGillID.setText("");
                    }
                }
            }

            labelNameProc.setText(loadedUser.getName());
            labelUsernameProc.setText(loadedUser.getUsername());
            fieldPassword.setText(loadedUser.getPassword());

            List<ca.mcgill.ecse321.tamas.model.User> usersModel = controller.getModelUsers();
            for (ca.mcgill.ecse321.tamas.model.User userModel : usersModel){
                if (userModel.getUsername().equals(userUsername)){
                    if (userModel instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
                        fieldMcGillID.setText(Integer.toString(((ca.mcgill.ecse321.tamas.model.CourseWorker)userModel).getMcgillID()));
                    }
                }
            }

        }
        catch (Exception e){

            Snackbar.make(findViewById(R.id.activity_view_posting), "Please review the fields you've inputed/chosen",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();
    }

    public void saveUser(View view) {
        try{
            controller.changeUserPassword(loadedUser.getUsername(), fieldPassword.getText().toString(), fieldPassword.getText().toString());
            ca.mcgill.ecse321.tamas.model.User tempUser = null;
            List<ca.mcgill.ecse321.tamas.model.User> usersModel = controller.getModelUsers();
            for (ca.mcgill.ecse321.tamas.model.User userModel : usersModel){
                if (userModel.getUsername().equals(loadedUser.getUsername())){
                    tempUser = userModel;
                }
            }
            if (tempUser != null){
                if (checkAdmin.isChecked()){
                    if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
                        controller.deleteJobApplications(tempUser.getUsername());
                    }
                    controller.changeUserType(tempUser.getUsername(), new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(tempUser.getUsername(), tempUser.getName()));

                }
                else if (checkCourseWorker.isChecked()){
                    controller.changeUserType(tempUser.getUsername(), new ca.mcgill.ecse321.tamas.model.CourseWorker(tempUser.getUsername(), tempUser.getName(),0, 0,""));
                }
                else if (checkInstructor.isChecked()){
                    if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
                        controller.deleteJobApplications(tempUser.getUsername());
                    }
                    controller.changeUserType(loadedUser.getUsername(), new ca.mcgill.ecse321.tamas.model.Instructor(tempUser.getUsername(), tempUser.getName()));
                }

                tempUser = null;
                List<ca.mcgill.ecse321.tamas.model.User> usersModel2 = controller.getModelUsers();
                for (ca.mcgill.ecse321.tamas.model.User userModel2 : usersModel2){
                    if (userModel2.getUsername().equals(loadedUser.getUsername())){
                        tempUser = userModel2;
                    }
                }
                if (tempUser != null){
                    if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
                        controller.changeCourseWorkerMcGillID(tempUser.getUsername(), 190);
                    }
                }
            }
            else {

                Snackbar.make(findViewById(R.id.activity_view_posting), "Please review the fields you've inputed/chosen",
                        Snackbar.LENGTH_SHORT)
                        .show();            }
            loadUsers();
        }
        catch (Exception e){

            Snackbar.make(findViewById(R.id.activity_view_posting), "Please review the fields you've inputed/chosen",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        updateData();
    }


    private void loadUsers() {
        comboBoxUserList.setAdapter(null);


        List<ca.mcgill.ecse321.tamas.authentication.UserAuth> users = controller.getAuthenticationUsers();
        ArrayAdapter<CharSequence> userAdapter
                = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        List<Course> courses = controller.getCourseList();
        if (!(courses.isEmpty())){
            for (ca.mcgill.ecse321.tamas.authentication.UserAuth user: users) {
                if(!(user.getUsername().equals(LoginActivity.currentUser.getUsername()))){
                    userAdapter.add(user.getName() + " - " + user.getUsername());
                }
            }
            comboBoxUserList.setAdapter(userAdapter);
        }
    }
}