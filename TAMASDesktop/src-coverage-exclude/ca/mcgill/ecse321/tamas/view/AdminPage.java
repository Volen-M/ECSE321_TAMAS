package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import ca.mcgill.ecse321.tamas.application.TamasApplicationStartup;
import ca.mcgill.ecse321.tamas.authentication.User;
import ca.mcgill.ecse321.tamas.controller.Controller;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.view.MainPage.Type;

public class AdminPage extends JFrame {

	private JPanel panel1;
	private JPanel panel2;
	private JLabel labelUserList;
	private JComboBox<String> comboBoxUserList;
	private JButton buttonUserList;
	private JPanel panelUserType;
	private ButtonGroup groupUserType;
	private JRadioButton checkCourseWorker;
	private JRadioButton checkAdmin;
	private JRadioButton checkInstructor;
	private JButton buttonBack;
	private JLabel labelName;
	private JLabel labelNameProc;
	private JLabel labelUsername;
	private JLabel labelUsernameProc;
	private JLabel labelMcGillID;
	private JTextField fieldMcGillID;
	private JLabel labelPassword;
	private JTextField fieldPassword;
	private JButton buttonSaveUser;
	private ca.mcgill.ecse321.tamas.authentication.User loadedUser;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminPage(){
		initComponents();
	}


	private void initComponents() {

		//Page Settings
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Desktop TAMAS");
		this.setLocationRelativeTo(null);

		//Page Layout
		panel1 = new JPanel();
		panel2 = new JPanel();
		labelUserList = new JLabel("User List:");
		comboBoxUserList = new JComboBox<String>();
		buttonUserList = new JButton("Load User");
		buttonBack = new JButton("Back to Main Page");
		labelUsername = new JLabel("Username:");
		labelUsernameProc = new JLabel(" ");
		labelUsernameProc.setBackground(Color.WHITE);
		labelName = new JLabel("Name:");
		labelNameProc = new JLabel(" ");
		labelMcGillID = new JLabel("McGill ID:");
		fieldMcGillID = new JTextField();
		labelPassword = new JLabel("Password:");
		fieldPassword = new JTextField();
		buttonSaveUser = new JButton("Save User");

		panelUserType = new JPanel(new GridLayout(0, 1));
		Border border = BorderFactory.createTitledBorder("User Type");
		panelUserType.setBorder(border);
		groupUserType = new ButtonGroup();
		checkCourseWorker = new JRadioButton("Course Worker");
		checkInstructor = new JRadioButton("Instructor");
		checkAdmin = new JRadioButton("Departement Admin");

		Font font = labelUsername.getFont();
		Font titleFont = new Font(font.getFontName(), Font.ITALIC, font.getSize());
		labelUsername.setFont(titleFont);
		labelName.setFont(titleFont);
		labelMcGillID.setFont(titleFont);
		labelPassword.setFont(titleFont);
		
		panelUserType.add(checkCourseWorker);
		panelUserType.add(checkInstructor);
		panelUserType.add(checkAdmin);
		groupUserType.add(checkCourseWorker);
		groupUserType.add(checkInstructor);
		groupUserType.add(checkAdmin);


		panel1.add(labelUserList); panel1.add(comboBoxUserList); panel1.add(buttonUserList);
		panel1.add(panelUserType); panel1.add(buttonBack);
		panel2.add(labelUsername); panel2.add(labelUsernameProc); panel2.add(labelName);
		panel2.add(labelNameProc); panel2.add(labelMcGillID); panel2.add(fieldMcGillID);
		panel2.add(buttonSaveUser);

		GroupLayout layoutPanel1 = new GroupLayout(panel1);
		panel1.setLayout(layoutPanel1);
		layoutPanel1.setAutoCreateGaps(true);
		layoutPanel1.setAutoCreateContainerGaps(true);
		layoutPanel1.setHorizontalGroup(layoutPanel1.createSequentialGroup()
				.addGroup(layoutPanel1.createParallelGroup().addComponent(labelUserList)
						.addComponent(comboBoxUserList)
						.addComponent(buttonUserList, 200, 200, 400)
						.addComponent(panelUserType)
						.addComponent(buttonBack, 200, 200, 400)));


		layoutPanel1.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { comboBoxUserList, buttonUserList });

		layoutPanel1.setVerticalGroup(layoutPanel1.createSequentialGroup()
				.addComponent(labelUserList)
				.addComponent(comboBoxUserList)
				.addComponent(buttonUserList)
				.addComponent(panelUserType)
				.addComponent(buttonBack)
				);

		GroupLayout layoutPanel2 = new GroupLayout(panel2);
		panel2.setLayout(layoutPanel2);
		layoutPanel2.setAutoCreateGaps(true);
		layoutPanel2.setAutoCreateContainerGaps(true);
		layoutPanel2.setHorizontalGroup(layoutPanel2.createSequentialGroup()
				.addGroup(layoutPanel2.createParallelGroup()
						.addComponent(labelUsername)
						.addComponent(labelUsernameProc)
						.addComponent(labelName)
						.addComponent(labelNameProc)
						.addComponent(labelMcGillID)
						.addComponent(fieldMcGillID)
						.addComponent(labelPassword)
						.addComponent(fieldPassword)
						.addComponent(buttonSaveUser, 200, 200, 400)
						));

		layoutPanel2.setVerticalGroup(layoutPanel2.createSequentialGroup()
				.addComponent(labelUsername)
				.addComponent(labelUsernameProc)
				.addComponent(labelName)
				.addComponent(labelNameProc)
				.addComponent(labelMcGillID)
				.addComponent(fieldMcGillID)
				.addComponent(labelPassword)
				.addComponent(fieldPassword)
				.addComponent(buttonSaveUser)
				);

		//----------------Frame Layout Setup------------------

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(panel1)
				.addComponent(panel2)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(panel1)
						.addComponent(panel2)
						));

		buttonBack.setVisible(false);
		pack();
		loadUsers();
		ListenForButton lForButton = new ListenForButton();
		buttonSaveUser.addActionListener(lForButton);
		buttonUserList.addActionListener(lForButton);
		checkCourseWorker.addActionListener(lForButton);
		checkAdmin.addActionListener(lForButton);
		checkInstructor.addActionListener(lForButton);
//		checkInstructor.setSelected(true);
	}
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonSaveUser){saveUser();}
			if (e.getSource() == buttonUserList){loadUser();}
			if(e.getSource() == checkCourseWorker){fieldMcGillID.setEnabled(true);}
			if(e.getSource() == checkAdmin){
				fieldMcGillID.setEnabled(false);
				fieldMcGillID.setText("");
			}
			if(e.getSource() == checkInstructor){
				fieldMcGillID.setEnabled(false);
				fieldMcGillID.setText("");
			}

		}

		private void loadUser() {
			try{
				String[] nameUsername = comboBoxUserList.getSelectedItem().toString().split(" - ");
				String userUsername = nameUsername[1];
				List<ca.mcgill.ecse321.tamas.authentication.User> users = Controller.getAuthenticationUsers();
				for (ca.mcgill.ecse321.tamas.authentication.User user : users){
					if (user.getUsername().equals(userUsername)){
						loadedUser = user;
						if (user instanceof ca.mcgill.ecse321.tamas.authentication.CourseWorker){
							checkCourseWorker.setSelected(true);
							checkInstructor.setSelected(false);
								checkAdmin.setSelected(false);
								fieldMcGillID.setEnabled(true);
							
						}
						if (user instanceof ca.mcgill.ecse321.tamas.authentication.Instructor){
							checkCourseWorker.setSelected(false);
							checkInstructor.setSelected(true);
							checkAdmin.setSelected(false);
							fieldMcGillID.setText("");
							fieldMcGillID.setEnabled(false);
						}
						if (user instanceof ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator){
							checkCourseWorker.setSelected(false);
							checkInstructor.setSelected(false);
							checkAdmin.setSelected(true);
							fieldMcGillID.setText("");
							fieldMcGillID.setEnabled(false);
						}
					}
				}
				
				labelNameProc.setText(loadedUser.getName());
				labelUsernameProc.setText(loadedUser.getUsername());
				fieldPassword.setText(loadedUser.getPassword());
				
				List<ca.mcgill.ecse321.tamas.model.User> usersModel = Controller.getModelUsers();
				for (ca.mcgill.ecse321.tamas.model.User userModel : usersModel){
					if (userModel.getUsername().equals(userUsername)){
						if (userModel instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
							fieldMcGillID.setText(Integer.toString(((ca.mcgill.ecse321.tamas.model.CourseWorker)userModel).getMcgillID()));
						}
					}
				}
			
			}
			catch (Exception e){
				genericError();
			}	
		}

		private void saveUser() {
			try{
				Controller.changeUserPassword(loadedUser.getUsername(), fieldPassword.getText(), fieldPassword.getText());
				ca.mcgill.ecse321.tamas.model.User tempUser = null;
				List<ca.mcgill.ecse321.tamas.model.User> usersModel = Controller.getModelUsers();
				for (ca.mcgill.ecse321.tamas.model.User userModel : usersModel){
					if (userModel.getUsername().equals(loadedUser.getUsername())){
						tempUser = userModel;
					}
				}
				if (tempUser != null){
					if (checkAdmin.isSelected()){
						if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
							Controller.deleteJobApplications(tempUser.getUsername());
						}
						Controller.changeUserType(tempUser.getUsername(), new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(tempUser.getUsername(), tempUser.getName()));
					
					}
					else if (checkCourseWorker.isSelected()){
						Controller.changeUserType(tempUser.getUsername(), new ca.mcgill.ecse321.tamas.model.CourseWorker(tempUser.getUsername(), tempUser.getName(),0, 0,""));
					}
					else if (checkInstructor.isSelected()){
						if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
							Controller.deleteJobApplications(tempUser.getUsername());
						}
						Controller.changeUserType(loadedUser.getUsername(), new ca.mcgill.ecse321.tamas.model.Instructor(tempUser.getUsername(), tempUser.getName()));
					}

					tempUser = null;
					List<ca.mcgill.ecse321.tamas.model.User> usersModel2 = Controller.getModelUsers();
					for (ca.mcgill.ecse321.tamas.model.User userModel2 : usersModel2){
						if (userModel2.getUsername().equals(loadedUser.getUsername())){
							tempUser = userModel2;
						}
					}
					if (tempUser != null){
						if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
							Controller.changeCourseWorkerMcGillID(tempUser.getUsername(), 190);
						}
					}
				}
				else {
					genericError();
				}
				loadUsers();
			}
			catch (Exception e){
				genericError();
			}
		}

		private void genericError() {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
					"Please review the fields you've inputed/chosen",
					"Warning Message",
					JOptionPane.ERROR_MESSAGE);
		} 

	}



	private void loadUsers() {
		comboBoxUserList.removeAllItems();
		List<ca.mcgill.ecse321.tamas.authentication.User> users = Controller.getAuthenticationUsers();
		for (ca.mcgill.ecse321.tamas.authentication.User user : users){
			if(!(user.getUsername().equals(TamasApplicationStartup.currentUser.getUsername()))){
				comboBoxUserList.addItem(user.getName() + " - " + user.getUsername());
			}
		}

	}
//	if (checkAdmin.isSelected()){
//		String username = tempUser.getUsername();
//		if (tempUser instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
//			List<ca.mcgill.ecse321.tamas.model.User> modelUsers = Controller.getModelUsers();
//			for (int i = 0; i < modelUsers.size(); i++) {
//				if (modelUsers.get(i).getUsername().equals(username) && modelUsers.get(i) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
//					List<JobApplication> jobApplications = Controller.getCourseWorkerJobApplications(modelUsers.get(i).getUsername());
//					for (int j = jobApplications.size()-1; j >= 0; j--){
//						jobApplications.get(j).delete();
//					}
//					List<JobApplication> jobApplications = job.getJobApplications();
//					for (JobApplication jobApplication : jobApplications){
//						if(jobApplication.getApplicationStatus().equals(status)){
//							courseWorkerList.add(jobApplication.getWorker());
//						}
//					}
//					
//					Controller.saveModel();
//					return;
//				} else if ((i == modelUsers.size() - 1) && !modelUsers.get(i).getUsername().equals(username)) {
//					throw new InvalidInputException("Could not delete course worker Job Applications");
//				}
//			}
//		}
//		Controller.changeUserType(tempUser.getUsername(), new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(tempUser.getUsername(), tempUser.getName()));
//	
//	}
}
