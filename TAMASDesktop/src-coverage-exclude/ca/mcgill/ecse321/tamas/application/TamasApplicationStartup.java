package ca.mcgill.ecse321.tamas.application;

import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.authentication.User;
import ca.mcgill.ecse321.tamas.controller.Controller;
import ca.mcgill.ecse321.tamas.persistence.Persistence;
import ca.mcgill.ecse321.tamas.view.AdminPage;
import ca.mcgill.ecse321.tamas.view.EvaluationPage;
import ca.mcgill.ecse321.tamas.view.LoginPage;
import ca.mcgill.ecse321.tamas.view.MainPage;

public class TamasApplicationStartup {

	public static ca.mcgill.ecse321.tamas.authentication.User currentUser;
	
    public static void main(String[] args) {
        Controller.loadAuthentication();
        Controller.loadModel();
        
        currentUser = null;

    	boolean hideID = false;
    	if (Controller.getAuthenticationUsers().size() == 0){
    		hideID=true;
    	}
        new LoginPage(hideID).setVisible(true);
//        new MainPage().setVisible(true);
//        new AdminPage().setVisible(true);
    }

}
