package ca.mcgill.ecse321.tamas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import ca.mcgill.ecse321.tamas.application.TamasApplicationStartup;
import ca.mcgill.ecse321.tamas.controller.Controller;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.CourseWorker;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Job;

public class EvaluationPage extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelEval;
	private JPanel panelEval;
	private JTextArea textAreaEval;
	private JButton buttonEval;
	private JButton buttonReturn;
	private JScrollPane scrollEval;
	private ca.mcgill.ecse321.tamas.model.CourseWorker loadedEmployee2 = null;
	private ca.mcgill.ecse321.tamas.model.Job loadedJob2 = null;
	private ca.mcgill.ecse321.tamas.model.Course loadedCourse2 = null;
	
    public EvaluationPage(ca.mcgill.ecse321.tamas.model.CourseWorker loadedEmployee, Course loadedCourse, Job loadedJob){
    	loadedEmployee2 = loadedEmployee;
    	loadedJob2 = loadedJob;
    	loadedCourse2 = loadedCourse;
  
    	initComponents();
        this.setLocationRelativeTo(null);
    }


	private void initComponents() {
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Desktop TAMAS");
        
        panelEval = new JPanel();
        labelEval = new JLabel("Employee Evaluation:");
        textAreaEval = new JTextArea(20,30);
		textAreaEval.setLineWrap(true);
		textAreaEval.setWrapStyleWord(true);
		scrollEval = new JScrollPane(textAreaEval);
		panelEval.add(scrollEval);
		scrollEval.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		buttonEval = new JButton("Submit Employee Evaluation");
		buttonReturn = new JButton("Return to Main Page");
		
		if(loadedEmployee2 instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
			List<Evaluation> evaluations = loadedEmployee2.getEvals();
			if(!evaluations.isEmpty()){
				for (Evaluation evaluation: evaluations ){
					if( evaluation.getJob().equals(loadedJob2)){
						textAreaEval.setText(evaluation.getDescription());
						textAreaEval.setEditable(false);
						buttonEval.setEnabled(false);
						break;
					}
				}
			}
		}
		else{
			textAreaEval.setText("Something went wrong, he/she is not a Course Worker");
			textAreaEval.setEditable(false);
		}

		
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(labelEval)
															.addComponent(panelEval)
															.addComponent(buttonEval, 200, 200, 400)));

		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(labelEval)
				.addComponent(panelEval)
				.addComponent(buttonEval)
				);
		
		pack();
		ListenForButton lForButton = new ListenForButton();
        buttonEval.addActionListener(lForButton);
		
	}
	
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonEval){
				try {
					Controller.addCourseWorkerEvaluation(loadedCourse2, loadedJob2,TamasApplicationStartup.currentUser.getUsername() , loadedEmployee2.getUsername(), textAreaEval.getText());
				} catch (InvalidInputException e1) {
					genericError();
				}
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

}
