package ca.mcgill.ecse321.tamas.view;

import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.tamas.application.TamasApplicationStartup;
import ca.mcgill.ecse321.tamas.controller.Controller;

public class LoginPage extends JFrame {

    private JTextField userNameTextField;
    private JTextField ruserNameTextField;
    private JTextField mcGillIDTextField;
    private JTextField fullNameTextField;
    

    private JPasswordField passwordField;
    private JPasswordField rpasswordField;
    private JPasswordField rconfirmpasswordField;

    private JLabel userPasswordLabel;
    private JLabel userNameLabel;
    private JLabel ruserPasswordLabel;
    private JLabel ruserconfirmPasswordLabel;
    private JLabel ruserNameLabel;
    private JLabel loginLabel;
    private JLabel signupLabel;
    private JLabel emptyLabel;
    private JLabel mcGillIDLabel;
    private JLabel fullNameLabel;

    private JButton signupButton;
    private JButton loginButton;
    
    private boolean hideID2 = false;
    /**
     * 
     */
    private static final long serialVersionUID = 7524951465830622117L;

    public LoginPage() {
    	initComponents(false);
        this.setLocationRelativeTo(null);
    }
    public LoginPage(boolean hideID) {
    	hideID2= hideID;
    	initComponents(hideID2);
        this.setLocationRelativeTo(null);
    }

    private void initComponents(boolean hideID) {
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Desktop TAMAS");

        passwordField = new JPasswordField();
        rpasswordField = new JPasswordField();
        rconfirmpasswordField = new JPasswordField();

        userNameTextField = new JTextField();
        ruserNameTextField = new JTextField();
        mcGillIDTextField = new JTextField();
        fullNameTextField = new JTextField();

        userNameLabel = new JLabel();
        ruserNameLabel = new JLabel();
        userPasswordLabel = new JLabel();
        ruserPasswordLabel = new JLabel();
        ruserconfirmPasswordLabel = new JLabel();
        loginLabel = new JLabel();
        signupLabel = new JLabel();
        emptyLabel = new JLabel();
        mcGillIDLabel = new JLabel();
        fullNameLabel = new JLabel();
        
        loginButton = new JButton();
        signupButton = new JButton();

        

        // LABEL TEXT
        userNameLabel.setText("Username:");
        ruserNameLabel.setText("New Username:");
        userPasswordLabel.setText("Password:");
        ruserPasswordLabel.setText("New Password:");
        ruserconfirmPasswordLabel.setText("Confirm Password:");
        loginLabel.setText("Login");
        signupLabel.setText("Register");
        emptyLabel.setText("   ");
        mcGillIDLabel.setText("McGill ID:");
        fullNameLabel.setText("Full Name:");

        // BUTTON TEXT
        loginButton.setText("Login");
        signupButton.setText("Register");

        

        // layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup().addComponent(userNameLabel).addComponent(userPasswordLabel)
                        .addComponent(ruserNameLabel).addComponent(ruserPasswordLabel)
                        .addComponent(ruserconfirmPasswordLabel).addComponent(mcGillIDLabel).addComponent(fullNameLabel))

                .addGroup(layout.createParallelGroup().addComponent(loginLabel, 200, 200, 400)
                        .addComponent(userNameTextField, 200, 200, 400).addComponent(passwordField, 200, 200, 400)
                        .addComponent(loginButton).addComponent(emptyLabel).addComponent(signupLabel, 200, 200, 400)
                        .addComponent(ruserNameTextField, 200, 200, 400).addComponent(rpasswordField, 200, 200, 400)
                        .addComponent(rconfirmpasswordField, 200, 200, 400).addComponent(mcGillIDTextField, 200, 200, 400)
                        .addComponent(fullNameTextField, 200, 200, 400).addComponent(signupButton))

        );

        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { loginButton, userNameTextField });
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] { signupButton, userNameTextField });

        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(loginLabel)
                .addGroup(layout.createParallelGroup().addComponent(userNameLabel).addComponent(userNameTextField))
                .addGroup(layout.createParallelGroup().addComponent(userPasswordLabel).addComponent(passwordField))
                .addComponent(loginButton).addComponent(emptyLabel).addComponent(signupLabel)
                .addGroup(layout.createParallelGroup().addComponent(ruserNameLabel).addComponent(ruserNameTextField))
                .addGroup(layout.createParallelGroup().addComponent(ruserPasswordLabel).addComponent(rpasswordField))
                .addGroup(layout.createParallelGroup().addComponent(ruserconfirmPasswordLabel).addComponent(rconfirmpasswordField))
                .addGroup(layout.createParallelGroup().addComponent(mcGillIDLabel).addComponent(mcGillIDTextField))
                .addGroup(layout.createParallelGroup().addComponent(fullNameLabel).addComponent(fullNameTextField))
                .addComponent(signupButton)

        );

        
        
        loginButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }

        });
        
     
        
        signupButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }

        });
        
     
        pack();
        
        if (hideID2){
        	mcGillIDTextField.setEditable(false);
        }
    }

    private void performLogin() {

    	try{
    		TamasApplicationStartup.currentUser = Controller.verifyAuthenticationCredentials(userNameTextField.getText(), passwordField.getText());
    		if (!(TamasApplicationStartup.currentUser == null)){
    			new MainPage().setVisible(true);
    			this.setVisible(false);
    		}
    		else{
    			loginError();
    		}
    	}
    	catch (Exception e){ 
    		loginError();
    	}
    }

    private void performSignup() {

    	try{
    		int mcGillID = 0;
    		if (!hideID2){
    			mcGillID = Integer.parseInt(mcGillIDTextField.getText());
    		}
    		Controller.registerNewUser(ruserNameTextField.getText(), rpasswordField.getText(), 
    				rconfirmpasswordField.getText(), fullNameTextField.getText(), mcGillID);
//    		    			Controller.changeUserType(ruserNameTextField.getText(), new ca.mcgill.ecse321.tamas.model.Instructor(ruserNameTextField.getText(),fullNameTextField.getText()));
    		TamasApplicationStartup.currentUser = Controller.verifyAuthenticationCredentials(ruserNameTextField.getText(), rpasswordField.getText());
    		try{
    			TamasApplicationStartup.currentUser = Controller.verifyAuthenticationCredentials(ruserNameTextField.getText(), rpasswordField.getText());
    			if(hideID2){
    				Controller.changeUserType(ruserNameTextField.getText(), new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(ruserNameTextField.getText(), fullNameTextField.getText()));
    				TamasApplicationStartup.currentUser = Controller.verifyAuthenticationCredentials(ruserNameTextField.getText(), rpasswordField.getText());
    			}

    			if (!(TamasApplicationStartup.currentUser == null)){
    				new MainPage().setVisible(true);
    				this.setVisible(false);
    			}
    			else{
    				signupError();
    			}
    		}
    		catch(Exception e){
    			signupError();
    		}

    	}
    	catch(Exception e){
    		signupError();
    	}

    }

    private void loginError() {
    	JFrame frame = new JFrame();
    	JOptionPane.showMessageDialog(frame,
    			"Please review your login information!",
				"Login Error",
				JOptionPane.ERROR_MESSAGE);
    	
    } 
    
    private void signupError() {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
				"Please review your sign up information!",
				"Signup Error",
				JOptionPane.ERROR_MESSAGE);
    	
    }
}
