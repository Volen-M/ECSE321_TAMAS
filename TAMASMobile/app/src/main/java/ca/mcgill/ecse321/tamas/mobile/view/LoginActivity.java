package ca.mcgill.ecse321.tamas.mobile.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import ca.mcgill.ecse321.tamas.controller.ControllerMobile;
import ca.mcgill.ecse321.tamas.model.Tamas;
import ca.mcgill.ecse321.tamas.persistence.PersistenceMobile;
import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.authentication.UserAuth;
import tamas.ecse321.mcgill.ca.view.R;


public class LoginActivity extends AppCompatActivity  {

    public static ca.mcgill.ecse321.tamas.authentication.UserAuth currentUser = null;
    public boolean hideID = true;
    private Authentication authentication = null;
    private Tamas tamas = null;
    private String userfile;
    private String datafile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userfile = getFilesDir().getAbsolutePath() + "/users.xml";
        datafile = getFilesDir().getAbsolutePath() + "/data.xml";
       tamas = PersistenceMobile.initializeModelManager(datafile);
       authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
       initComponents();

    }

    public void initComponents(){
       ControllerMobile controller = new ControllerMobile(tamas,authentication);
       EditText mcGillIDTextField  = (EditText) findViewById(R.id.mcgillID);
       List<UserAuth> listusers = controller.getAuthenticationUsers();

        if (listusers.size() == 0){
            mcGillIDTextField.setEnabled(false);
            hideID = true;
        }
        else{
            hideID = false;
        }
    }

    public void login(View view) {
        ControllerMobile controller = new ControllerMobile(tamas,authentication);
        EditText userNameTextField = (EditText)findViewById(R.id.login_username);
        EditText passwordField = (EditText)findViewById(R.id.login_password);
        try{
            currentUser = controller.verifyAuthenticationCredentials(userNameTextField.getText().toString(), passwordField.getText().toString());
            if (!(currentUser == null)){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                String e = "Login was unsuccessful";
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                //loginError();
            }
        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
            //loginError();
        }
    }

    public void register(View view) {
        try{
            ControllerMobile controller = new ControllerMobile(tamas,authentication);
            EditText ruserNameTextField = (EditText)findViewById(R.id.register_username);
            EditText mcGillIDTextField = (EditText)findViewById(R.id.mcgillID);
            EditText fullNameTextField = (EditText)findViewById(R.id.fullName);
            EditText rpasswordField = (EditText)findViewById(R.id.register_password);
            EditText rconfirmpasswordField = (EditText)findViewById(R.id.register_password2);

            int mcGillID = 0;

            if (!hideID){
                mcGillID = Integer.parseInt(mcGillIDTextField.getText().toString());
            }

            controller.registerNewUser(ruserNameTextField.getText().toString(), rpasswordField.getText().toString(),
                    rconfirmpasswordField.getText().toString(), fullNameTextField.getText().toString(), mcGillID);
            userfile = getFilesDir().getAbsolutePath() + "/users.xml";
            datafile = getFilesDir().getAbsolutePath() + "/data.xml";
            tamas = PersistenceMobile.initializeModelManager(datafile);
            authentication = PersistenceMobile.initializeAuthenticationManager(userfile);
            controller = new ControllerMobile(tamas,authentication);

            currentUser = controller.verifyAuthenticationCredentials(ruserNameTextField.getText().toString(), rpasswordField.getText().toString());
            try{
                currentUser = controller.verifyAuthenticationCredentials(ruserNameTextField.getText().toString(), rpasswordField.getText().toString());
                if(hideID){
                    controller.changeUserType(ruserNameTextField.getText().toString(), new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(ruserNameTextField.getText().toString(), fullNameTextField.getText().toString()));
                    currentUser = controller.verifyAuthenticationCredentials(ruserNameTextField.getText().toString(), rpasswordField.getText().toString());
                }

                if (!(currentUser == null)){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    String e = "Sign Up was unsuccessful";
                    Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                            Snackbar.LENGTH_SHORT)
                            .show();
                    //signupError();
                }
            }
            catch(Exception e){
                Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show();
                //signupError();
            }

        }
        catch(Exception e){
            Snackbar.make(findViewById(R.id.activity_view_posting), e.toString(),
                    Snackbar.LENGTH_SHORT)
                    .show();
            //signupError();
        }
    }
}

