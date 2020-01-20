package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.tamas.application.TamasApplicationStartup;
import ca.mcgill.ecse321.tamas.controller.Controller;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.CourseWorker;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.User;
import ca.mcgill.ecse321.tamas.model.Job.Type;
import ca.mcgill.ecse321.tamas.model.JobApplication;
import ca.mcgill.ecse321.tamas.model.Tamas;

public class MainPage extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8545845536640669016L;

	
	//MainPanel init
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;

	//Main Panel 1 Components init
	private JLabel labelStatus;
	private JLabel labelEmployList;
	private JComboBox<String> comboBoxEmployList;
	private JButton buttonViewEmploy;
	private JButton buttonEvaluate;

	private JPanel panelPastExperience;
	private JScrollPane scrollBarPastExperience;
	private JLabel labelPastExperience;
	private JTextArea textAreaPastExperience;

	private JPanel panelEducation;
	private ButtonGroup buttonGroupEducation;
	private JRadioButton radioUndergrad;
	private JRadioButton radioGrad;

	private JButton buttonApplyJob;
	private JButton buttonAccept;
	private JButton buttonDecline;


	//Main Panel 2 Component init
	private JLabel labelEmpty;
	private JLabel labelCourseList;
	private JComboBox<String> comboBoxCourseList;
	private JButton buttonLoadClass;
	private JLabel labelJobList;
	private JComboBox<String> comboBoxJobList;
	private JButton buttonLoadJob;

	private JPanel panelJobDescription;
	private JLabel labelJobTitle;
	private JTextField textFieldJobTitle;
	private JScrollPane scrollBarJobDescription;
	private JLabel labelJobDescription;
	private JTextArea textAreaJobDescription; 

	private JButton buttonAddJob;
	private JButton buttonSaveJob;
	private JButton buttonDeleteJob;

	
	//Main Panel 3 Component init
	private JLabel labelUpdate;
	private JLabel labelAppDeadline;
	private JLabel labelAppDeadlineDate;
	private JDatePickerImpl datePickerAppDeadline;
	private JLabel labelAppDeadlineTime;
	private JSpinner timeSpinnerAppDeadline;
	private JLabel labelWorkTime;
	private JLabel labelStartTime;
	private JLabel labelEndTime;
	private JSpinner timeSpinnerStartTime;
	private JSpinner timeSpinnerEndTime;
	private JPanel panelDays;
	private ButtonGroup groupDays;
	private JRadioButton checkMonday;
	private JRadioButton checkTuesday;
	private JRadioButton checkWednesday;
	private JRadioButton checkThursday;
	private JRadioButton checkFriday;
	private JLabel labelTotalHours;
	private JTextField fieldTotalHoursSum;
	private JPanel panelJobType;
	private ButtonGroup buttonGroupJobType;
	private JRadioButton radioTA;
	private JRadioButton radioLabTech;
	private JRadioButton radioGrader;

	
	//Main Panel 4 Component init
	private JLabel labelEmpty2;
	private JLabel labelCourseList2;
	private JButton buttonLoadClass2;

	private JLabel labelCourseTitle;
	private JTextField textFieldCourseTitle;
	private JLabel labelHoursAmnt;
	private JTextField textFieldHoursAmnt;
	private JLabel labelLabAmnt;
	private JTextField textFieldLabAmnt;
	private JLabel labelTutorialAmnt;
	private JTextField textFieldTutorialAmnt;
	private JLabel labelStudentAmnt;
	private JTextField textFieldStudentAmnt;
	private JLabel labelBudgetAmnt;
	private JTextField textFieldBudgetAmnt; 

	private JButton buttonAddCourse;
	private JButton buttonSaveCourse;
	private JButton buttonDeleteCourse;
	private JButton buttonAdminPage;

	private ca.mcgill.ecse321.tamas.model.CourseWorker loadedEmployee;
	private Job loadedJob3;
	private JobApplication loadedApplication;
	private Course loadedCourse2;


	public enum Type { COURSEWORKER, INSTRUCTOR, ADMIN }
	private Type type;

	public MainPage() {
		ca.mcgill.ecse321.tamas.authentication.User currentUser2 = TamasApplicationStartup.currentUser;

		if (currentUser2 instanceof ca.mcgill.ecse321.tamas.authentication.CourseWorker){
			type = Type.COURSEWORKER;
			List<ca.mcgill.ecse321.tamas.model.User> users = Controller.getModelUsers();
			for (ca.mcgill.ecse321.tamas.model.User user: users){
				if (TamasApplicationStartup.currentUser.getUsername() == user.getUsername()){
					loadedEmployee = (ca.mcgill.ecse321.tamas.model.CourseWorker)user;
				}
			}
		}
		if (currentUser2 instanceof ca.mcgill.ecse321.tamas.authentication.Instructor){
			type = Type.INSTRUCTOR;
		}
		if (currentUser2 instanceof ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator){
			type = Type.ADMIN;
		}

		initComponents();
		this.setLocationRelativeTo(null);
	}



	private void initComponents() {

		//Page Settings
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Desktop TAMAS");

		//Component initialization
		//3 mainPanels are going to be placed left to right
		//MainPanel init
		panel1 = new JPanel();
		panel4 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		//----------------Main Panel 1 Components init------------------

		labelStatus = new JLabel("You are logged in as: " + type);
		labelStatus.setForeground(Color.red);
		labelEmployList = new JLabel("Applicant List:");
		comboBoxEmployList = new JComboBox<String>();
		buttonViewEmploy = new JButton("Load Worker");
		buttonEvaluate = new JButton("Evaluate Employee");
		panel1.add(labelStatus); panel1.add(labelEmployList); panel1.add(comboBoxEmployList); panel1.add(buttonViewEmploy); panel1.add(buttonEvaluate);


		panelPastExperience = new JPanel();
		labelPastExperience =new JLabel("<html>Profile Description:<br>-Please be aware any changes to this will overwrite<br>all past application profile descriptions.<br>-It is recommended to also link your CV here or your LinkedIn profile!");
		textAreaPastExperience = new JTextArea(20,30);
		textAreaPastExperience.setLineWrap(true);
		textAreaPastExperience.setWrapStyleWord(true);
		scrollBarPastExperience = new JScrollPane(textAreaPastExperience);
		panelPastExperience.add(scrollBarPastExperience);
		scrollBarPastExperience.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel1.add(labelPastExperience); panel1.add(panelPastExperience); 
		
		panelEducation = new JPanel(new GridLayout(0, 1));
		Border border = BorderFactory.createTitledBorder("Education Level");
		panelEducation.setBorder(border);
		buttonGroupEducation = new ButtonGroup();
		radioUndergrad = new JRadioButton("Undergraduate Student");
		radioGrad = new JRadioButton("Graduate Student");
		panelEducation.add(radioUndergrad);
		panelEducation.add(radioGrad);
		buttonGroupEducation.add(radioUndergrad);
		buttonGroupEducation.add(radioGrad);
		panel1.add(panelEducation);

		buttonApplyJob = new JButton("Apply to Job");
		buttonAccept = new JButton("Accept Job Application");
		buttonDecline = new JButton("Decline Job Application");
		panel1.add(buttonApplyJob); panel1.add(buttonAccept); panel1.add(buttonDecline);

		GroupLayout layoutPanel1 = new GroupLayout(panel1);
		panel1.setLayout(layoutPanel1);
		layoutPanel1.setAutoCreateGaps(true);
		layoutPanel1.setAutoCreateContainerGaps(true);
		layoutPanel1.setHorizontalGroup(layoutPanel1.createSequentialGroup()
				.addGroup(layoutPanel1.createParallelGroup().addComponent(labelStatus)
						.addComponent(labelEmployList)
						.addComponent(comboBoxEmployList, 200, 200, 400)
						.addComponent(buttonViewEmploy, 200, 200, 400)
						.addComponent(buttonEvaluate, 200, 200, 400)
						.addComponent(labelPastExperience)
						.addComponent(panelPastExperience)
						.addComponent(panelEducation)
						.addComponent(buttonApplyJob, 200, 200, 400)
						.addComponent(buttonAccept, 200, 200, 400)
						.addComponent(buttonDecline, 200, 200, 400)));

		layoutPanel1.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { comboBoxEmployList, buttonAccept });

		layoutPanel1.setVerticalGroup(layoutPanel1.createSequentialGroup()
				.addComponent(labelStatus)
				.addComponent(labelEmployList)
				.addComponent(comboBoxEmployList)
				.addComponent(buttonViewEmploy)
				.addComponent(buttonEvaluate)
				.addComponent(labelPastExperience)
				.addComponent(panelPastExperience)
				.addComponent(panelEducation)
				.addComponent(buttonApplyJob)
				.addComponent(buttonAccept)
				.addComponent(buttonDecline)
				);


		//----------------Main Panel 2 Components init------------------

		labelEmpty = new JLabel("");
		labelCourseList = new JLabel("Course List:");
		comboBoxCourseList = new JComboBox<String>();
		buttonLoadClass = new JButton("Load Course's Job List");
		labelJobList = new JLabel("Job List:");
		comboBoxJobList = new JComboBox<String>();
		buttonLoadJob = new JButton("Load Job");
		panel2.add(labelEmpty); panel2.add(labelCourseList); panel2.add(comboBoxCourseList); panel2.add(buttonLoadClass); panel2.add(labelJobList); panel2.add(comboBoxJobList); panel2.add(buttonLoadJob);

		panelJobDescription = new JPanel();
		labelJobTitle = new JLabel("Job Title:");
		textFieldJobTitle = new JTextField();
		panel2.add(labelJobTitle); panel2.add(textFieldJobTitle);

		labelJobDescription = new JLabel("Job Description:");
		textAreaJobDescription = new JTextArea(24,30);
		textAreaJobDescription.setLineWrap(true);
		textAreaJobDescription.setWrapStyleWord(true);
		scrollBarJobDescription = new JScrollPane(textAreaJobDescription);
		panelJobDescription.add(scrollBarJobDescription);
		scrollBarJobDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		buttonAddJob = new JButton("Add Job");
		buttonSaveJob = new JButton("Save Job");
		buttonDeleteJob = new JButton("Delete Job");
		panel2.add(buttonAddJob); panel2.add(buttonSaveJob); panel2.add(buttonDeleteJob);

		GroupLayout layoutPanel2 = new GroupLayout(panel2);
		panel2.setLayout(layoutPanel2);
		layoutPanel2.setAutoCreateGaps(true);
		layoutPanel2.setAutoCreateContainerGaps(true);
		layoutPanel2.setHorizontalGroup(layoutPanel2.createSequentialGroup()
				.addGroup(layoutPanel2.createParallelGroup().addComponent(labelEmpty)
						.addComponent(labelCourseList)
						.addComponent(comboBoxCourseList)
						.addComponent(buttonLoadClass, 200, 200, 400)
						.addComponent(labelJobList)
						.addComponent(comboBoxJobList)
						.addComponent(buttonLoadJob, 200, 200, 400)
						.addComponent(labelJobTitle)
						.addComponent(textFieldJobTitle)
						.addComponent(labelJobDescription)
						.addComponent(panelJobDescription)
						.addComponent(buttonAddJob, 200, 200, 400)
						.addComponent(buttonSaveJob, 200, 200, 400)
						.addComponent(buttonDeleteJob, 200, 200, 400)
						));

		layoutPanel2.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { comboBoxJobList, buttonLoadJob });
		layoutPanel2.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { comboBoxCourseList, buttonLoadClass });
		layoutPanel2.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldJobTitle, buttonLoadClass });

		layoutPanel2.setVerticalGroup(layoutPanel2.createSequentialGroup()
				.addComponent(labelEmpty)
				.addComponent(labelCourseList)
				.addComponent(comboBoxCourseList)
				.addComponent(buttonLoadClass)
				.addComponent(labelJobList)
				.addComponent(comboBoxJobList)
				.addComponent(buttonLoadJob)
				.addComponent(labelJobTitle)
				.addComponent(textFieldJobTitle)
				.addComponent(labelJobDescription)
				.addComponent(panelJobDescription)
				.addComponent(buttonAddJob)
				.addComponent(buttonSaveJob)
				.addComponent(buttonDeleteJob)
				);


		//----------------Main Panel 3 Components init------------------

		Font font = labelEmployList.getFont();
		Font titleFont = new Font(font.getFontName(), Font.ITALIC, font.getSize());

		labelAppDeadline = new JLabel("<html><center><i><b>Application Deadline Information</b></i></center>");
//		labelAppDeadline.setFont(titleFont);
		labelAppDeadlineDate = new JLabel("Application Deadline Date:");
		labelAppDeadlineTime = new JLabel("Application Deadline Time:");

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerAppDeadline = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		timeSpinnerAppDeadline = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(timeSpinnerAppDeadline, "HH:mm");
		timeSpinnerAppDeadline.setEditor(startTimeEditor);

		labelWorkTime = new JLabel("Job Information");
		labelWorkTime.setFont(titleFont);
		labelStartTime = new JLabel("Work Start Time");
		labelEndTime = new JLabel("Work End Time"); 

		timeSpinnerStartTime = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor2 = new JSpinner.DateEditor(timeSpinnerStartTime, "HH:mm");
		timeSpinnerStartTime.setEditor(startTimeEditor2);

		timeSpinnerEndTime = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor3 = new JSpinner.DateEditor(timeSpinnerEndTime, "HH:mm");
		timeSpinnerEndTime.setEditor(startTimeEditor3);

		panelDays = new JPanel(new GridLayout(0,1));
		Border border4 = BorderFactory.createTitledBorder("Working Day:");
		panelDays.setBorder(border4);
		groupDays = new ButtonGroup();
		checkMonday = new JRadioButton("Monday");
		checkTuesday = new JRadioButton("Tuesday");
		checkWednesday = new JRadioButton("Wednesday");
		checkThursday = new JRadioButton("Thursday");
		checkFriday = new JRadioButton("Friday");
		panelDays.add(checkMonday); panelDays.add(checkTuesday); panelDays.add(checkWednesday); panelDays.add(checkThursday); panelDays.add(checkFriday);
		groupDays.add(checkMonday); groupDays.add(checkTuesday); groupDays.add(checkWednesday); groupDays.add(checkThursday); groupDays.add(checkFriday);

		labelTotalHours = new JLabel("Total Work Hours per Week:");
		fieldTotalHoursSum = new JTextField();

		panelJobType = new JPanel(new GridLayout(0, 1));
		Border border3 = BorderFactory.createTitledBorder("Job Type:");
		panelJobType.setBorder(border3);
		buttonGroupJobType = new ButtonGroup();
		radioGrader = new JRadioButton("Grader");
		radioTA = new JRadioButton("Teaching Assistant");
		radioLabTech = new JRadioButton("Lab Technician");
		panelJobType.add(radioGrader);
		panelJobType.add(radioTA);
		panelJobType.add(radioLabTech);
		buttonGroupJobType.add(radioGrader);
		buttonGroupJobType.add(radioTA);
		buttonGroupJobType.add(radioLabTech);


		GroupLayout layoutPanel3 = new GroupLayout(panel3);
		panel3.setLayout(layoutPanel3);
		layoutPanel3.setAutoCreateGaps(true);
		layoutPanel3.setAutoCreateContainerGaps(true);
		layoutPanel3.setHorizontalGroup(layoutPanel3.createSequentialGroup()
				.addGroup(layoutPanel3.createParallelGroup()
						.addComponent(labelAppDeadline)
						.addComponent(labelAppDeadlineDate)
						.addComponent(datePickerAppDeadline)
						.addComponent(labelAppDeadlineTime)
						.addComponent(timeSpinnerAppDeadline)
						.addComponent(labelWorkTime)
						.addComponent(labelStartTime)
						.addComponent(timeSpinnerStartTime)
						.addComponent(labelEndTime)
						.addComponent(timeSpinnerEndTime)
						.addComponent(panelDays)
						.addComponent(labelTotalHours)
						.addComponent(fieldTotalHoursSum)
						.addComponent(panelJobType)
						));

		layoutPanel3.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { timeSpinnerAppDeadline, datePickerAppDeadline });
		layoutPanel3.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { timeSpinnerStartTime, datePickerAppDeadline });
		layoutPanel3.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { timeSpinnerEndTime, datePickerAppDeadline });
		layoutPanel3.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { timeSpinnerEndTime, fieldTotalHoursSum });


		layoutPanel3.setVerticalGroup(layoutPanel3.createSequentialGroup()
				.addComponent(labelAppDeadline)
				.addComponent(labelAppDeadlineDate)
				.addComponent(datePickerAppDeadline)
				.addComponent(labelAppDeadlineTime)
				.addComponent(timeSpinnerAppDeadline)
				.addComponent(labelWorkTime)
				.addComponent(labelStartTime)
				.addComponent(timeSpinnerStartTime)
				.addComponent(labelEndTime)
				.addComponent(timeSpinnerEndTime)
				.addComponent(panelDays)
				.addComponent(labelTotalHours)
				.addComponent(fieldTotalHoursSum)
				.addComponent(panelJobType)
				);


		//----------------Main Panel 3 Components init------------------
		panel4 = new JPanel();
		labelUpdate = new JLabel(" ");
		Dimension labelUpdateDim = new Dimension(340,100);
		labelUpdate.setMinimumSize(labelUpdateDim);
		labelUpdate.setPreferredSize(labelUpdateDim);
		labelUpdate.setMaximumSize(labelUpdateDim);
		Border labelUpdateBorder = BorderFactory.createTitledBorder("Update Panel:");
		labelUpdate.setBorder(labelUpdateBorder);
		labelUpdate.setForeground(Color.red);
		labelCourseList2 = new JLabel("Course List:");
		buttonLoadClass2 = new JButton("Load Course");
		panel4.add(labelUpdate); panel4.add(labelCourseList2); panel4.add(buttonLoadClass2);

		labelCourseTitle = new JLabel("Course Title");
		textFieldCourseTitle = new JTextField();
		labelHoursAmnt =  new JLabel("Amount of Course Hours:");
		textFieldHoursAmnt = new JTextField();
		labelLabAmnt = new JLabel("Amount of Lab Sessions:");
		textFieldLabAmnt = new JTextField();
		labelTutorialAmnt = new JLabel("Amount of Tutorial Sessions");
		textFieldTutorialAmnt = new JTextField();
		labelStudentAmnt = new JLabel("Amount of Enrolled Students");
		textFieldStudentAmnt = new JTextField();
		labelBudgetAmnt = new JLabel("Budget Amount (CAD):");
		textFieldBudgetAmnt = new JTextField(); 
		panel4.add(labelCourseTitle); panel4.add(textFieldCourseTitle); panel4.add(labelHoursAmnt); panel4.add(textFieldHoursAmnt);
		panel4.add(labelLabAmnt); panel4.add(textFieldLabAmnt); panel4.add(labelTutorialAmnt); panel4.add(textFieldTutorialAmnt);
		panel4.add(labelStudentAmnt); panel4.add(textFieldStudentAmnt); panel4.add(labelBudgetAmnt); panel4.add(textFieldBudgetAmnt);


		buttonAddCourse = new JButton("Add Course");
		buttonSaveCourse = new JButton("Save Course");
		buttonDeleteCourse = new JButton("Delete Course");
		buttonAdminPage = new JButton("Open Admin Page");
		ImageIcon image = new ImageIcon("Image/McGill_Redmen_Logo.png");
		JLabel labelLogo = new JLabel("", image, JLabel.CENTER);
		Dimension imgDim = new Dimension(330,315);
		labelLogo.setSize(imgDim);
		labelLogo.setPreferredSize(imgDim);
		labelLogo.setMinimumSize(imgDim);
		labelLogo.setMaximumSize(imgDim);
		panel4.add(buttonAddCourse); panel4.add(buttonSaveCourse); panel4.add(buttonDeleteCourse);

		GroupLayout layoutPanel4 = new GroupLayout(panel4);
		panel4.setLayout(layoutPanel4);
		layoutPanel4.setAutoCreateGaps(true);
		layoutPanel4.setAutoCreateContainerGaps(true);
		layoutPanel4.setHorizontalGroup(layoutPanel4.createSequentialGroup()
				.addGroup(layoutPanel4.createParallelGroup().addComponent(labelUpdate)
						.addComponent(buttonLoadClass2, 200, 200, 400)
						.addComponent(labelCourseTitle)
						.addComponent(textFieldCourseTitle)
						.addComponent(labelHoursAmnt)
						.addComponent(textFieldHoursAmnt)
						.addComponent(labelLabAmnt)
						.addComponent(textFieldLabAmnt)
						.addComponent(labelTutorialAmnt)
						.addComponent(textFieldTutorialAmnt)
						.addComponent(labelStudentAmnt)
						.addComponent(textFieldStudentAmnt)
						.addComponent(labelBudgetAmnt)
						.addComponent(textFieldBudgetAmnt)
						.addComponent(buttonAddCourse, 200, 200, 400)
						.addComponent(buttonSaveCourse, 200, 200, 400)
						.addComponent(buttonDeleteCourse, 200, 200, 400)
						.addComponent(buttonAdminPage, 200, 200, 400)
						.addComponent(labelLogo)
						));

		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldCourseTitle, buttonLoadClass2 });
		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldHoursAmnt, buttonLoadClass2 });
		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldLabAmnt, buttonLoadClass2 });
		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldTutorialAmnt, buttonLoadClass2 });
		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldStudentAmnt, buttonLoadClass2 });
		layoutPanel4.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { textFieldBudgetAmnt, buttonLoadClass2 });
		
		layoutPanel4.setVerticalGroup(layoutPanel4.createSequentialGroup()
				.addComponent(labelUpdate)
				.addComponent(buttonLoadClass2)
				.addComponent(labelCourseTitle)
				.addComponent(textFieldCourseTitle)
				.addComponent(labelHoursAmnt)
				.addComponent(textFieldHoursAmnt)
				.addComponent(labelLabAmnt)
				.addComponent(textFieldLabAmnt)
				.addComponent(labelTutorialAmnt)
				.addComponent(textFieldTutorialAmnt)
				.addComponent(labelStudentAmnt)
				.addComponent(textFieldStudentAmnt)
				.addComponent(labelBudgetAmnt)
				.addComponent(textFieldBudgetAmnt)
				.addComponent(buttonAddCourse)
				.addComponent(buttonSaveCourse)
				.addComponent(buttonDeleteCourse)
				.addComponent(buttonAdminPage)
				.addComponent(labelLogo)
				);

		loadCourses();
		//----------------Frame Layout Setup------------------

		//Situational Setup
				Dimension mainPanelSize = new Dimension(370,810);
				panel1.setPreferredSize(mainPanelSize);
				panel2.setPreferredSize(mainPanelSize);
				panel3.setPreferredSize(mainPanelSize);
				panel4.setPreferredSize(mainPanelSize);
				panel4.setMinimumSize(mainPanelSize);
				
				
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(panel1)
				.addComponent(panel2)
				.addComponent(panel3)
				.addComponent(panel4)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(panel1)
						.addComponent(panel2)
						.addComponent(panel3)
						.addComponent(panel4)
						));
		ListenForButton lForButton = new ListenForButton();
		ListenForChange listener = new ListenForChange();
		
		
		//Generic Button Setup

		Border panelsBorder = BorderFactory.createBevelBorder(NORMAL);
		panel1.setBorder(panelsBorder);
		panel2.setBorder(panelsBorder);
		panel3.setBorder(panelsBorder);
		panel4.setBorder(panelsBorder);
		
		buttonAccept.addActionListener(lForButton);
		buttonAddCourse.addActionListener(lForButton);
		buttonAddJob.addActionListener(lForButton);
		buttonAdminPage.addActionListener(lForButton);
		buttonApplyJob.addActionListener(lForButton);
		buttonDecline.addActionListener(lForButton);
		buttonDeleteCourse.addActionListener(lForButton);
		buttonDeleteJob.addActionListener(lForButton);
		buttonEvaluate.addActionListener(lForButton);
		buttonLoadClass.addActionListener(lForButton);
		buttonLoadClass2.addActionListener(lForButton);
		buttonLoadJob.addActionListener(lForButton);
		buttonSaveCourse.addActionListener(lForButton);
		buttonSaveJob.addActionListener(lForButton);
		buttonViewEmploy.addActionListener(lForButton);
		checkMonday.addActionListener(lForButton);
		checkTuesday.addActionListener(lForButton);
		checkWednesday.addActionListener(lForButton);
		checkThursday.addActionListener(lForButton);
		checkFriday.addActionListener(lForButton);
		timeSpinnerStartTime.addChangeListener(listener);
		timeSpinnerEndTime.addChangeListener(listener);


		pack();

		
		buttonSaveJob.setEnabled(false);
		buttonSaveJob.setVisible(false);
		buttonSaveCourse.setEnabled(false);
		buttonSaveCourse.setVisible(false);
		buttonLoadClass2.setVisible(false);
		fieldTotalHoursSum.setEnabled(false);
		checkMonday.setSelected(true);
		radioUndergrad.setSelected(true);
		
		if (type == Type.COURSEWORKER){

			//Panel1
			comboBoxEmployList.setEnabled(false);
			buttonViewEmploy.setEnabled(false);
			buttonEvaluate.setEnabled(false);
			buttonEvaluate.setVisible(false);
			textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
			buttonAccept.setText("Accept Job Offer");
			buttonDecline.setText("Decline Job Offer");
			buttonAccept.setEnabled(false);
			buttonDecline.setEnabled(false);

			//Panel2
			textFieldJobTitle.setEditable(false);
			textAreaJobDescription.setEditable(false);
			buttonAddJob.setEnabled(false);
			buttonDeleteJob.setEnabled(false);
			buttonSaveJob.setEnabled(false);
			buttonAddJob.setVisible(false);
			buttonDeleteJob.setVisible(false);
			buttonSaveJob.setVisible(false);

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
			labelBudgetAmnt.setVisible(false);
			textFieldBudgetAmnt.setVisible(false);
			textFieldStudentAmnt.setEnabled(false);
			textFieldStudentAmnt.setEnabled(false);
			buttonAddCourse.setEnabled(false);
			buttonSaveCourse.setEnabled(false);
			buttonDeleteCourse.setEnabled(false);
			buttonAdminPage.setEnabled(false);
			buttonAddCourse.setVisible(false);
			buttonSaveCourse.setVisible(false);
			buttonDeleteCourse.setVisible(false);
			buttonAdminPage.setVisible(false);


		}

		if (type == Type.INSTRUCTOR){

			//Panel1
			textAreaPastExperience.setEnabled(false);
			radioGrad.setEnabled(false);
			radioUndergrad.setEnabled(false);
			buttonApplyJob.setEnabled(false);
			buttonApplyJob.setVisible(false);
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
			labelBudgetAmnt.setVisible(false);
			textFieldBudgetAmnt.setVisible(false);
			textFieldStudentAmnt.setEnabled(false);
			textFieldStudentAmnt.setEnabled(false);
			buttonAddCourse.setEnabled(false);
			buttonSaveCourse.setEnabled(false);
			buttonDeleteCourse.setEnabled(false);
			buttonAdminPage.setEnabled(false);
			buttonAddCourse.setVisible(false);
			buttonSaveCourse.setVisible(false);
			buttonDeleteCourse.setVisible(false);
			buttonAdminPage.setVisible(false);
		}

		if (type == Type.ADMIN){

			//Panel1
			buttonEvaluate.setEnabled(false);
			buttonEvaluate.setVisible(false);
			textAreaPastExperience.setEnabled(false);
			radioGrad.setEnabled(false);
			radioUndergrad.setEnabled(false);
			buttonApplyJob.setEnabled(false);
			buttonApplyJob.setVisible(false);
			buttonAccept.setText("Accept Employment");
			buttonDecline.setText("Reject Employment");
			buttonAccept.setEnabled(false);
			buttonDecline.setEnabled(false);

			//Panel2
			textFieldJobTitle.setEditable(false);
			textAreaJobDescription.setEditable(false);
			buttonAddJob.setEnabled(false);
			buttonDeleteJob.setEnabled(false);
			buttonSaveJob.setEnabled(false);
			buttonAddJob.setVisible(false);
			buttonDeleteJob.setVisible(false);
			buttonSaveJob.setVisible(false);

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

	private void loadCourses() {
		comboBoxCourseList.removeAllItems();
		List<Course> courses = Controller.getCourseList();
		for (Course course : courses){
			comboBoxCourseList.addItem(course.getName());
		}
	}
	
	private class ListenForChange implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
		    if(e.getSource() == timeSpinnerEndTime || e.getSource() == timeSpinnerStartTime){updateHours();}
		}
	}
		
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonViewEmploy){viewEmploy();}
			if (e.getSource() == buttonApplyJob){applyJob();}
			if (e.getSource() == buttonAccept){acceptJob(); }
			if (e.getSource() == buttonDecline){declineJob();}
			if (e.getSource() == buttonLoadClass){try {
				loadClass();
			} catch (InvalidInputException e1) {
				genericError(e1);
			}}
			if (e.getSource() == buttonLoadJob){loadJob();}
			if (e.getSource() == buttonSaveJob){}
			if (e.getSource() == buttonAddJob){addJob();}
			if (e.getSource() == buttonEvaluate){ evaluate();}
			if (e.getSource() == buttonDeleteJob){ deleteJob();}
			if (e.getSource() == buttonAddCourse){ addCourse();}
			if (e.getSource() == buttonSaveCourse){ }
			if (e.getSource() == buttonDeleteCourse){deleteCourse();}
			if (e.getSource() == buttonAdminPage){adminPageOpen();}
			if (e.getSource() == checkMonday || e.getSource() == checkTuesday || e.getSource() == checkWednesday || e.getSource() == checkThursday || e.getSource() == checkFriday){ updateHours();}
		}


		private void addCourse() {
			try{
				if(textFieldCourseTitle.getText().length()>10){throw new Exception("Course name too long. Please enter a valid course code.");}
				Controller.addNewCourse(textFieldCourseTitle.getText(), Double.parseDouble(textFieldBudgetAmnt.getText()), Integer.parseInt(textFieldStudentAmnt.getText()), 3, Integer.parseInt(textFieldHoursAmnt.getText()), Integer.parseInt(textFieldTutorialAmnt.getText()), Integer.parseInt(textFieldLabAmnt.getText()));
				loadCourses();
				labelUpdate.setText( "<html>" + textFieldCourseTitle.getText().toString() + " successfully added");
			}
			catch (Exception e){
				genericError(e);
			}
		}


		private void evaluate() {
			if (type == Type.INSTRUCTOR)
			{
				try{
					
					new EvaluationPage(loadedEmployee, loadedCourse2, loadedJob3).setVisible(true);
				}
				catch(Exception e){
					genericError(e);
				}
			}
		}


		private void adminPageOpen() {
			if (type == Type.ADMIN)
			{
				new AdminPage().setVisible(true);
			}
		}


		private void deleteCourse() {
			try {
				Controller.removeCourse(loadedCourse2);
				loadCourses();
				labelUpdate.setText( "<html>" +loadedCourse2.getName() + " successfully deleted");
			} catch (InvalidInputException e) {
				genericError(new Exception("<html><center>Make sure to load course beforehand: Could not load course.", e));
			}
		}

		@SuppressWarnings("deprecation")
		private void addJob() {
			try{
				Time startTime = new Time(1,1,1);
				startTime.setTime(((java.util.Date)timeSpinnerStartTime.getModel().getValue()).getTime());
				
				Time endTime = new Time(1,1,1);
				endTime.setTime(((java.util.Date)timeSpinnerEndTime.getModel().getValue()).getTime());
				
				Time deadlineTime = new Time(1,1,1);
				deadlineTime.setTime(((java.util.Date)timeSpinnerAppDeadline.getModel().getValue()).getTime());
				
				Date deadlineDate = new Date(1,1,1);
				deadlineDate.setTime(((java.util.Date) datePickerAppDeadline.getModel().getValue()).getTime());
				
				ca.mcgill.ecse321.tamas.model.Job.Day day = Job.Day.MONDAY;
				ca.mcgill.ecse321.tamas.model.Job.Type type = Job.Type.GRADER;
				
				day = Job.Day.MONDAY;
				if (checkMonday.isSelected()){ day = Job.Day.MONDAY;}
				else if (checkTuesday.isSelected()){ day = Job.Day.TUESDAY;}
				else if (checkWednesday.isSelected()){ day = Job.Day.WEDNESDAY;}
				else if (checkThursday.isSelected()){ day = Job.Day.THURSDAY;}
				else if (checkFriday.isSelected()){ day = Job.Day.FRIDAY;}
				else throw new Exception ("Radio buttons are busted.");

				type = Job.Type.GRADER;
				if (radioGrader.isSelected()){ type = Job.Type.GRADER;}
				else if (radioLabTech.isSelected()){ type = Job.Type.LAB;}
				else if (radioTA.isSelected()){ type = Job.Type.TUTORIAL;}
				else throw new Exception ("Radio buttons are broken.");

				Controller.addCourseJob(textFieldJobTitle.getText().toString(), textAreaJobDescription.getText().toString(), loadedCourse2, day , type, startTime, endTime, deadlineDate, deadlineTime);
				loadClass();
				labelUpdate.setText( "<html>" + textFieldJobTitle.getText().toString() + " successfully added");
			}
			catch(Exception e){
				genericError(e);
			}

		}


		private void deleteJob() {
			try{
				Controller.removeCourseJob(loadedCourse2, loadedJob3);
				loadClass();
				labelUpdate.setText( "<html>" +loadedJob3.getTitle() + " successfully deleted");
			}
			catch(Exception e){
				genericError(e);
			}
		}


		private void loadJob() {
			try {
				boolean succesfulLoad = false;
				List<ca.mcgill.ecse321.tamas.model.Job> jobs = Controller.getCourseJobPostings(loadedCourse2);
				for(ca.mcgill.ecse321.tamas.model.Job job : jobs ){
					if(comboBoxJobList.getSelectedItem().toString().equals(job.getTitle())){
						loadedJob3 = job;
						succesfulLoad = true;
					}
				}
				if ( Job.Day.MONDAY.equals(loadedJob3.getDay())){
					checkMonday.setSelected(true);
				}
				else if ( Job.Day.TUESDAY.equals(loadedJob3.getDay())){
					checkTuesday.setSelected(true);
				}
				else if ( Job.Day.WEDNESDAY.equals(loadedJob3.getDay())){
					checkWednesday.setSelected(true);
				}
				else if ( Job.Day.THURSDAY.equals(loadedJob3.getDay())){
					checkThursday.setSelected(true);
				}
				else if ( Job.Day.FRIDAY.equals(loadedJob3.getDay())){
					checkFriday.setSelected(true);
				}
				textAreaJobDescription.setText(loadedJob3.getDescription());
				textFieldJobTitle.setText(loadedJob3.getTitle());

				Calendar dl = Calendar.getInstance();
				dl.setTime(loadedJob3.getDeadlineDate());
				datePickerAppDeadline.getModel().setDate(dl.get(Calendar.YEAR), dl.get(Calendar.MONTH), dl.get(Calendar.DAY_OF_MONTH));
				datePickerAppDeadline.getModel().setSelected(true);
				timeSpinnerAppDeadline.setValue(loadedJob3.getDeadlineTime());
				timeSpinnerStartTime.setValue(loadedJob3.getStartTime());
				timeSpinnerEndTime.setValue(loadedJob3.getEndTime());

				if (Job.Type.GRADER.equals(loadedJob3.getType())){
					radioGrader.setSelected(true);
					radioTA.setSelected(false);
					radioLabTech.setSelected(false);
				}
				else if(Job.Type.TUTORIAL.equals(loadedJob3.getType())){
					radioGrader.setSelected(false);
					radioTA.setSelected(true);
					radioLabTech.setSelected(false);
				}
				else if(Job.Type.LAB.equals(loadedJob3.getType())){
					radioGrader.setSelected(false);
					radioTA.setSelected(false);
					radioLabTech.setSelected(true);
				}
				try{
					if (type == Type.ADMIN || type == Type.INSTRUCTOR ){
						comboBoxEmployList.removeAllItems();
						List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsAccepted = Controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.ACCEPTED);
						if (!applicantsAccepted.isEmpty()){
							for (ca.mcgill.ecse321.tamas.model.User user: applicantsAccepted){
								comboBoxEmployList.addItem(user.getName()+ " - Employed");
							}
						}
						List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsApproved = Controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.APPROVED);
						if (!applicantsApproved.isEmpty()){
							for (ca.mcgill.ecse321.tamas.model.User user: applicantsApproved){
								comboBoxEmployList.addItem(user.getName()+ " - Approved");
							}
						}
						List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsOffer = Controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.OFFER);
						if (!applicantsOffer.isEmpty()){
							for (ca.mcgill.ecse321.tamas.model.User user: applicantsOffer){
								comboBoxEmployList.addItem(user.getName()+ " - To Approve");
							}
						}
						List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsApplied = Controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.APPLIED);
						if (!applicantsApplied.isEmpty()){
							for (ca.mcgill.ecse321.tamas.model.User user: applicantsApplied){
								comboBoxEmployList.addItem(user.getName()+ " - Applied");
							}
						}
						List<ca.mcgill.ecse321.tamas.model.CourseWorker> applicantsRejected = Controller.getApplicantList(loadedJob3, JobApplication.ApplicationStatus.REJECTED);
						if (!applicantsRejected.isEmpty()){
							for (ca.mcgill.ecse321.tamas.model.User user: applicantsRejected){
								comboBoxEmployList.addItem(user.getName()+ " - Rejected");
							}
						}
						if (succesfulLoad){
							labelUpdate.setText( "<html>" +loadedJob3.getTitle() + " successfully loaded");
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
							labelUpdate.setText( "<html>" +loadedJob3.getTitle() + " successfully loaded");
							buttonApplyJob.setEnabled(true);
							buttonAccept.setEnabled(false);
							buttonDecline.setEnabled(false);
							if (appCheck){
								if (loadedApplication.getApplicationStatusFullName() != null){

									if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.APPROVED ){
										labelUpdate.setText("<html>You have been sent a job offer for this job.");
										buttonAccept.setEnabled(true);
										buttonDecline.setEnabled(true);
										buttonApplyJob.setEnabled(false);
									}
									else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.ACCEPTED){
										labelUpdate.setText("<html>You are already Employed for this Job");
										buttonApplyJob.setEnabled(false);
									}
									else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.REJECTED){
										labelUpdate.setText("<html>You have declined this job or your application has been rejected.");
										buttonApplyJob.setEnabled(false);
									}
									else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.OFFER){
										labelUpdate.setText("<html>Your application for this job is being reviewed.");
										buttonApplyJob.setEnabled(false);
									}
									else if (loadedApplication.getApplicationStatus() == JobApplication.ApplicationStatus.APPLIED){
										labelUpdate.setText("<html>You have applied. Your application for this job is being reviewed.");
										buttonApplyJob.setEnabled(false);
									}
								}
								textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
							}
						}
				}
				catch(Exception e1){
					genericError(new Exception("Could not load applicants", e1));
				}
				updateHours();
			}
			catch (Exception e){
				genericError(e);
			}
		}


		private void loadClass() throws InvalidInputException {
			try {
				comboBoxJobList.removeAllItems();
				int index = comboBoxCourseList.getSelectedIndex();
				List<ca.mcgill.ecse321.tamas.model.Job> jobs = Controller.getCourseJobPostings(Controller.getCourse(index));
				List<String> strings = new ArrayList<>(jobs.size());
				for (ca.mcgill.ecse321.tamas.model.Job job : jobs){
					comboBoxJobList.addItem(job.getTitle());
				}
				ca.mcgill.ecse321.tamas.model.Course course = Controller.getCourse(index);
				loadedCourse2 = course;
				textFieldCourseTitle.setText(course.getName());
				textFieldHoursAmnt.setText(Integer.toString(course.getHours()));
				textFieldTutorialAmnt.setText(Integer.toString(course.getTutorialCount()));
				textFieldLabAmnt.setText(Integer.toString(course.getLabCount()));
				textFieldStudentAmnt.setText(Integer.toString(course.getStudentsEnrolled()));
				textFieldBudgetAmnt.setText(Double.toString(course.getBudget()));
				labelUpdate.setText( "<html>" +loadedCourse2.getName() + " successfully loaded");
			}
			catch (Exception e){
				genericError(e);
			}
		}

		private void declineJob() {
			try{
				if (type == Type.COURSEWORKER){
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
					labelUpdate.setText("<html>You have rejected the job offer for " + loadedJob3.getTitle());
					loadJob();
				}
				if (type == Type.ADMIN){
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
					labelUpdate.setText("<html>You have rejected the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle());
					viewEmploy();
				}

				if (type == Type.INSTRUCTOR){
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.REJECTED);
					labelUpdate.setText("<html>You have rejected the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle());
					viewEmploy();
				}
			}
			catch (Exception e){
				genericError(e);
			}
		}

		private void acceptJob() {
			try{
				if (type == Type.COURSEWORKER){
					Controller.allocateWorkerToJob(loadedJob3, loadedEmployee.getUsername());
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.ACCEPTED);
					labelUpdate.setText("<html>You are now employed for " + loadedJob3.getTitle());
					loadJob();
				}
				if (type == Type.ADMIN){
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.APPROVED);
					labelUpdate.setText("<html>You have approved the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle());
					viewEmploy();
				}

				if (type == Type.INSTRUCTOR){
					loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
					Controller.changeJobApplicationStatus(loadedApplication, JobApplication.ApplicationStatus.OFFER);
					labelUpdate.setText("<html>You have sent for approval the job offer of " + loadedEmployee.getName() + "<html> for " + loadedJob3.getTitle());
					viewEmploy();
				}
			}
			catch (Exception e){
				genericError(e);
			}

		}

		private void viewEmploy() {
			try{
				String[] employeeString = comboBoxEmployList.getSelectedItem().toString().split(" - ");
				String employeeName = employeeString[0];
				loadedEmployee = Controller.getEmployee(employeeName, loadedJob3);
				if(!Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus().equals(JobApplication.ApplicationStatus.ACCEPTED)){buttonEvaluate.setEnabled(false);}
				else{buttonEvaluate.setEnabled(true);}
				textAreaPastExperience.setText(loadedEmployee.getPastWorkerExperience());
				labelUpdate.setText( "<html>" +loadedEmployee.getName() + " successfully loaded");
				if (loadedEmployee.getAcademicStatus() == ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus.UNDERGRADUATE){
					radioUndergrad.setSelected(true);
					radioGrad.setSelected(false);
				}
				if (loadedEmployee.getAcademicStatus() == ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus.GRADUATE){
					radioUndergrad.setSelected(false);
					radioGrad.setSelected(true);
				}
				labelUpdate.setText( "<html>" +loadedEmployee.getName() + " successfully loaded");

				//buttonAccept & buttonDecline situational setup
				if (Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.ACCEPTED){
					buttonAccept.setEnabled(false);
					buttonDecline.setEnabled(false);
					labelUpdate.setText("<html>" + loadedEmployee.getName() + "<html> is already employed for " + loadedJob3.getTitle());
				}
				else if(Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.REJECTED){
					buttonAccept.setEnabled(false);
					buttonDecline.setEnabled(false);
					labelUpdate.setText("<html>" + loadedEmployee.getName() + "<html>'s application has already been rejected or has declined " + loadedJob3.getTitle());

				}
				else if(Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.APPROVED){
					buttonAccept.setEnabled(false);
					buttonDecline.setEnabled(false);
					labelUpdate.setText("<html>" + loadedEmployee.getName() + "<html> has already had his/her job offer approved for " + loadedJob3.getTitle());
				}
				else if(Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.APPLIED){
					buttonAccept.setEnabled(false);
					buttonDecline.setEnabled(false);
					labelUpdate.setText("<html>" + loadedEmployee.getName() + "<html> has applied for " + loadedJob3.getTitle());
					
					if (type == Type.INSTRUCTOR){
						buttonAccept.setEnabled(true);
						buttonDecline.setEnabled(true);	
					}
				}
				else if(Controller.getJobApplication(loadedEmployee, loadedJob3).getApplicationStatus() == JobApplication.ApplicationStatus.OFFER){
					buttonAccept.setEnabled(false);
					buttonDecline.setEnabled(false);
					labelUpdate.setText("<html>" + loadedEmployee.getName() + "<html> has had his/her application accepted by an instructor for " + loadedJob3.getTitle());
					
					if (type == Type.ADMIN){
						buttonAccept.setEnabled(true);
						buttonDecline.setEnabled(true);
					}
				}

			}
			catch (Exception e){
				genericError(e);
			}

	}

		private void applyJob() {
			try{
				Calendar c = Calendar.getInstance();
				Date currentDate = new Date(c.getTimeInMillis());
				Time currentTime = new Time(c.getTimeInMillis());
				List<ca.mcgill.ecse321.tamas.model.User> users = Controller.getModelUsers();

				if(radioUndergrad.isSelected())	{ Controller.changeCourseWorkerAcademicStatus(loadedEmployee.getUsername(), CourseWorker.AcademicStatus.UNDERGRADUATE); }
				else if (radioGrad.isSelected())	{ Controller.changeCourseWorkerAcademicStatus(loadedEmployee.getUsername(), CourseWorker.AcademicStatus.GRADUATE); }
				else throw new Exception ("Academic status radio button doesn't work");
				if(loadedEmployee.getJobApplications().size()==3){throw new Exception("Only 3 active applications allowed at one time");}
				Controller.changeCourseWorkerPastExp(loadedEmployee.getUsername(), textAreaPastExperience.getText());
				Controller.applyForJob(loadedJob3, currentDate, currentTime, 1 , loadedEmployee.getUsername());
				loadedApplication = Controller.getJobApplication( loadedEmployee, loadedJob3);
				labelUpdate.setText("<html>You have applied for "  + loadedJob3.getTitle());
				loadJob();
			}
			catch (Exception e)
			{
				genericError(e);
			}

		}
	}



	private void genericError(Exception e) {
		JFrame frame = new JFrame();
		if(e.getMessage() != null){
			JOptionPane.showMessageDialog(frame,
					e.getMessage(),
					"Warning Message",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(frame,
					"Please check that you have properly filled in all required fields",
					"Warning Message",
					JOptionPane.ERROR_MESSAGE);
		}

	}



	public void updateHours() {
		String[] endDate = timeSpinnerEndTime.getValue().toString().split(" ");
		String[] endTime = endDate[3].split(":");
		String[] startDate = timeSpinnerStartTime.getValue().toString().split(" ");
		String[] startTime = startDate[3].split(":");
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
	}

}
