package ca.mcgill.ecse321.tamas.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.tamas.authentication.Authentication;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.CourseWorker.AcademicStatus;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.Job.Day;
import ca.mcgill.ecse321.tamas.model.Job.Type;
import ca.mcgill.ecse321.tamas.model.JobApplication;
import ca.mcgill.ecse321.tamas.model.Tamas;
import ca.mcgill.ecse321.tamas.model.User;
import ca.mcgill.ecse321.tamas.persistence.PersistenceMobile;

public class ControllerMobile {
	private Tamas tamas;
	private Authentication authentication;

	public ControllerMobile(Tamas tamas, Authentication authentication){
		this.tamas = tamas;
		this.authentication = authentication;
	}
	
//	public static boolean loadAuthentication() {
//		try {
//			authentication = PersistenceMobile.loadAuthenticationFromXMLwithXStream("users.xml");
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			authentication = new Authentication();
//			// false implies that we could not load an existing Authentication
//			// instance and are proceeding with a new empty one.
//			return false;
//		}
//	}

//
//	public static boolean loadModel() {
//		try {
//			tamas = PersistenceMobile.loadModelFromXMLwithXStream("data.xml");
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			tamas = new Tamas();
//			// false implies that we could not load an existing Model instance
//			// and are proceeding with a new empty one.
//			return false;
//		}
//	}
//
	public void saveAuthentication() {
		PersistenceMobile.saveAuthenticationToXMLwithXStream(authentication);
	}
	
	public void saveModel() {
		PersistenceMobile.saveModelToXMLwithXStream(tamas);
	}

	public List<ca.mcgill.ecse321.tamas.authentication.User> getAuthenticationUsers() {
		return authentication.getUsers();
	}

	public List<ca.mcgill.ecse321.tamas.model.User> getModelUsers() {
		return tamas.getUsers();
	}

	public List<Course> getCourseList() {
		return tamas.getCourses();
	}

	public List<Job> getCourseJobPostings(Course course) {
		return course.getJobPostings();
	}

	public List<JobApplication> getCourseWorkerJobApplications(String username) throws InvalidInputException {
		ca.mcgill.ecse321.tamas.model.CourseWorker worker = null;
		List<User> users = getModelUsers();
		for (ca.mcgill.ecse321.tamas.model.User user : users) {
			if (user.getUsername().equals(username) && user instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				worker = (ca.mcgill.ecse321.tamas.model.CourseWorker) user;
			}
		}
		if (worker.equals(null)) {
			throw new InvalidInputException("Failed to get courseworker for username.");
		}
		return worker.getJobApplications();
	}

	public List<Evaluation> getCourseWorkerEvaluations(String username) throws InvalidInputException {
		ca.mcgill.ecse321.tamas.model.CourseWorker worker = null;
		List<User> users = getModelUsers();
		for (ca.mcgill.ecse321.tamas.model.User user : users) {
			if (user.getUsername().equals(username) && user instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				worker = (ca.mcgill.ecse321.tamas.model.CourseWorker) user;
			}
		}
		if (worker.equals(null)) {
			throw new InvalidInputException("Failed to get courseworker for username.");
		}
		return worker.getEvals();
	}

	public ca.mcgill.ecse321.tamas.authentication.User verifyAuthenticationCredentials(String username,
			String password) {
		List<ca.mcgill.ecse321.tamas.authentication.User> users = getAuthenticationUsers();
		for (ca.mcgill.ecse321.tamas.authentication.User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public void registerNewUser(String username, String password, String repeatedPassword, String name,
			int mcgillID) throws Exception {
		if (password.equals(repeatedPassword)) {
			List<ca.mcgill.ecse321.tamas.authentication.User> authUsers = getAuthenticationUsers();
			for (ca.mcgill.ecse321.tamas.authentication.User user : authUsers) {
				if (user.getUsername().equals(username)) {
					throw new Exception("Username already exists.");
				}
			}
			List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
			for (ca.mcgill.ecse321.tamas.model.User user : modelUsers) {
				if (user.getUsername().equals(username)) {
					throw new Exception("Username already exists.");
				}
			}
			if (authUsers.size() != modelUsers.size()) {
				throw new Exception("Either data.xml or users.xml is corrupt.");
			} else {
				if (authentication.hasUsers() && tamas.hasUsers()) {
					authentication
					.addUser(new ca.mcgill.ecse321.tamas.authentication.CourseWorker(username, password, name));
					tamas.addUser(new ca.mcgill.ecse321.tamas.model.CourseWorker(username, name, 0, mcgillID, ""));
				} else {
					// First user registered must be a department administrator.
					authentication.addUser(new ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator(username,
							password, name));
					tamas.addUser(new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(username, name));
				}
				saveAuthentication();
				saveModel();
			}
		} else {
			throw new Exception("The password is not equivalent to the repeated password string.");
		}
	}

	public void changeUserPassword(String username, String newPassword, String newPasswordRepeated)
			throws Exception {
		if (newPassword.equals(newPasswordRepeated)) {
			List<ca.mcgill.ecse321.tamas.authentication.User> authUsers = getAuthenticationUsers();
			for (int i = 0; i < authUsers.size(); i++) {
				if (authUsers.get(i).getUsername().equals(username)) {
					authUsers.get(i).setPassword(newPassword);
					saveAuthentication();
					return;
				}
			}
		} else {
			throw new Exception("The password is not equivalent to the repeated password string.");
		}
	}

	public void addNewCourse(String name, double budget, int studentsEnrolled, int credits, int hours,
			int tutorialCount, int labCount) throws InvalidInputException {
		boolean exists = false;
		List<Course> courses = getCourseList();
		for (Course course: courses){
			if (name.equals(course.getName())){
				exists = true;
				break;
			}
		}
		
		if (name.length() > 0 && budget > 0.0d && studentsEnrolled > 0 && credits > 0 && hours > 0 && tutorialCount >= 0
				&& labCount >= 0 && !(exists)) {
			tamas.addCourse(name, budget, studentsEnrolled, credits, hours, tutorialCount, labCount);
			saveModel();
		} else {
			throw new InvalidInputException("A parameter has a bad value.");
		}
	}

	public void removeCourse(Course course) throws InvalidInputException {
		List<Course> courses = getCourseList();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).equals(course)) {
				courses.get(i).delete();
				saveModel();
				return;
			} else if ((i == courses.size() - 1) && !courses.get(i).equals(course)) {
				throw new InvalidInputException("Could not remove course.");
			}
		}
	}

	public void addCourseJob(String title, String description, Course course, Day day, Type type, Time startTime,
			Time endTime, Date deadlineDate, Time deadlineTime) throws InvalidInputException {
		boolean exists = false;
		List<Job> jobs = getCourseJobPostings(course);
		for (Job job: jobs){
			if (title.equals(job.getTitle())){
				exists = true;
				break;
			}
		}
			
		if (title.length() > 0 && description.length() > 0 && !course.equals(null) && !day.equals(null)
				&& !type.equals(null) && !startTime.equals(null) && !endTime.equals(null) && !deadlineDate.equals(null)
				&& !deadlineTime.equals(null) && !(exists)) {
			List<Course> courses = tamas.getCourses();
			for (int i = 0; i < courses.size(); i++) {
				if (courses.get(i).equals(course)) {
					courses.get(i).addJobPosting(startTime, endTime, deadlineDate, deadlineTime, title, description, day, type);
					saveModel();
					return;
				}
			}
		} else {
			throw new InvalidInputException("A parameter has a bad value.");
		}
	}

	public void removeCourseJob(Course course, Job job) throws InvalidInputException {
		List<Job> jobs = getCourseJobPostings(course);
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).equals(job)) {
				jobs.get(i).delete();
				saveModel();
				return;
			} else if ((i == jobs.size() - 1) && !jobs.get(i).equals(job)) {
				throw new InvalidInputException("Could not remove course job.");
			}
		}
	}

	public void allocateWorkerToJob(Job job, String username) throws InvalidInputException {
		ca.mcgill.ecse321.tamas.model.CourseWorker worker = null;
		List<User> users = getModelUsers();
		for (ca.mcgill.ecse321.tamas.model.User user : users) {
			if (user.getUsername().equals(username) && user instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				worker = (ca.mcgill.ecse321.tamas.model.CourseWorker) user;
			}
		}
		if (worker.equals(null)) {
			throw new InvalidInputException("Failed to get courseworker for username.");
		}
		if (!job.equals(null) && !worker.equals(null)) {
			List<Course> courses = tamas.getCourses();
			for (Course specificCourse : courses) {
				List<Job> jobs = specificCourse.getJobPostings();
				for (Job specificJob : jobs) {
					if (specificJob.equals(job)) {
						specificJob.setAllocatedWorker(worker);
						saveModel();
						return;
					}
				}
			}
		} else {
			throw new InvalidInputException("A parameter has a bad value.");
		}
	}

	public void changeUserType(String username, Object newUserType) throws InvalidInputException {
		List<ca.mcgill.ecse321.tamas.authentication.User> authUsers = getAuthenticationUsers();
		List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
		if (newUserType instanceof ca.mcgill.ecse321.tamas.authentication.CourseWorker
				|| newUserType instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
			int authIndex = -1;
			int modelIndex = -1;

			for (int i = 0; i < authUsers.size(); i++) {
				if (authUsers.get(i).getUsername().equals(username)) {
					authIndex = i;
					break;
				}
			}

			for (int i = 0; i < modelUsers.size(); i++) {
				if (modelUsers.get(i).getUsername().equals(username)) {
					modelIndex = i;
					break;
				}
			}

			if ((authIndex == -1) || (modelIndex == -1)) {
				throw new InvalidInputException("Could not find user for username.");
			}

			ca.mcgill.ecse321.tamas.authentication.CourseWorker replacementAuthUser = new ca.mcgill.ecse321.tamas.authentication.CourseWorker(
					username, authUsers.get(authIndex).getPassword(), authUsers.get(authIndex).getName());
			ca.mcgill.ecse321.tamas.model.CourseWorker replacementModelUser = new ca.mcgill.ecse321.tamas.model.CourseWorker(
					username, modelUsers.get(modelIndex).getName(), 0, 0, "");

			authUsers.get(authIndex).delete();
			modelUsers.get(modelIndex).delete();

			authentication.addUser(replacementAuthUser);
			tamas.addUser(replacementModelUser);

			saveAuthentication();
			saveModel();
		} else if (newUserType instanceof ca.mcgill.ecse321.tamas.authentication.Instructor
				|| newUserType instanceof ca.mcgill.ecse321.tamas.model.Instructor) {
			int authIndex = -1;
			int modelIndex = -1;

			for (int i = 0; i < authUsers.size(); i++) {
				if (authUsers.get(i).getUsername().equals(username)) {
					authIndex = i;
					break;
				}
			}

			for (int i = 0; i < modelUsers.size(); i++) {
				if (modelUsers.get(i).getUsername().equals(username)) {
					modelIndex = i;
					break;
				}
			}

			if ((authIndex == -1) || (modelIndex == -1)) {
				throw new InvalidInputException("Could not find user for username.");
			}

			ca.mcgill.ecse321.tamas.authentication.Instructor replacementAuthUser = new ca.mcgill.ecse321.tamas.authentication.Instructor(
					username, authUsers.get(authIndex).getPassword(), authUsers.get(authIndex).getName());
			ca.mcgill.ecse321.tamas.model.Instructor replacementModelUser = new ca.mcgill.ecse321.tamas.model.Instructor(
					username, modelUsers.get(modelIndex).getName());

			authUsers.get(authIndex).delete();
			modelUsers.get(modelIndex).delete();

			authentication.addUser(replacementAuthUser);
			tamas.addUser(replacementModelUser);

			saveAuthentication();
			saveModel();
		} else if (newUserType instanceof ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator
				|| newUserType instanceof ca.mcgill.ecse321.tamas.model.DepartmentAdministrator) {
			int authIndex = -1;
			int modelIndex = -1;

			for (int i = 0; i < authUsers.size(); i++) {
				if (authUsers.get(i).getUsername().equals(username)) {
					authIndex = i;
					break;
				}
			}

			for (int i = 0; i < modelUsers.size(); i++) {
				if (modelUsers.get(i).getUsername().equals(username)) {
					modelIndex = i;
					break;
				}
			}

			if ((authIndex == -1) || (modelIndex == -1)) {
				throw new InvalidInputException("Could not find user for username.");
			}

			ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator replacementAuthUser = new ca.mcgill.ecse321.tamas.authentication.DepartmentAdministrator(
					username, authUsers.get(authIndex).getPassword(), authUsers.get(authIndex).getName());
			ca.mcgill.ecse321.tamas.model.DepartmentAdministrator replacementModelUser = new ca.mcgill.ecse321.tamas.model.DepartmentAdministrator(
					username, modelUsers.get(modelIndex).getName());

			authUsers.get(authIndex).delete();
			modelUsers.get(modelIndex).delete();

			authentication.addUser(replacementAuthUser);
			tamas.addUser(replacementModelUser);

			saveAuthentication();
			saveModel();
		} else {
			throw new InvalidInputException("Invalid object newUserType.");
		}
	}

	public void changeJobApplicationStatus(JobApplication jobapplication,
			JobApplication.ApplicationStatus newStatus) throws InvalidInputException {
		if (!jobapplication.equals(null) && !newStatus.equals(null)) {
			jobapplication.setApplicationStatus(newStatus);
			saveModel();
			return;
		} else {
			throw new InvalidInputException("A parameter has a bad value.");
		}
	}

	public void addCourseWorkerEvaluation(Course course, Job job, String evaluatorUsername,
			String courseWorkerUsername, String description) throws InvalidInputException {
		ca.mcgill.ecse321.tamas.model.CourseWorker worker = null;
		ca.mcgill.ecse321.tamas.model.Instructor evaluator = null;
		List<User> users = getModelUsers();
		for(ca.mcgill.ecse321.tamas.model.User user : users) {
			if(user.getUsername().equals(courseWorkerUsername) && user instanceof ca.mcgill.ecse321.tamas.model.CourseWorker){
				worker = (ca.mcgill.ecse321.tamas.model.CourseWorker)user;
			} else if (user.getUsername().equals(evaluatorUsername)) {
				evaluator = (ca.mcgill.ecse321.tamas.model.Instructor)user;
			}
		}
		if(worker.equals(null) || evaluator.equals(null)){
			throw new InvalidInputException("Failed to get courseworker/instructor for username.");
		}
		if (!course.equals(null) && !job.equals(null) && !evaluator.equals(null) && !worker.equals(null)) {
			List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
			for (ca.mcgill.ecse321.tamas.model.User specificUser : modelUsers) {
				if (specificUser.equals(worker)) {
					((ca.mcgill.ecse321.tamas.model.CourseWorker) specificUser)
					.addEval(new Evaluation(description, course, job, evaluator, worker));
					saveModel();
					return;
				}
			}
		} else {
			throw new InvalidInputException("A parameter has a bad value.");
		}
	}

	public void changeCourseWorkerAcademicStatus(String username, AcademicStatus newAcademicStatus)
			throws InvalidInputException {
		List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
		for (int i = 0; i < modelUsers.size(); i++) {
			if (modelUsers.get(i).getUsername().equals(username)
					&& modelUsers.get(i) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				switch (newAcademicStatus) {
				case GRADUATE:
					((ca.mcgill.ecse321.tamas.model.CourseWorker) modelUsers.get(i))
					.setAcademicStatus(AcademicStatus.GRADUATE);
					saveModel();
					break;
				case UNDERGRADUATE:
					((ca.mcgill.ecse321.tamas.model.CourseWorker) modelUsers.get(i))
					.setAcademicStatus(AcademicStatus.UNDERGRADUATE);
					saveModel();
					break;
				default:
					throw new InvalidInputException("Invalid academic status passed to this method.");
				}
				return;
			}
		}
	}

	public void changeCourseWorkerMcGillID(String username, int newIDNumber) throws InvalidInputException {
		List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
		for (int i = 0; i < modelUsers.size(); i++) {
			if (modelUsers.get(i).getUsername().equals(username)
					&& modelUsers.get(i) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				((ca.mcgill.ecse321.tamas.model.CourseWorker) modelUsers.get(i)).setMcgillID(newIDNumber);
				saveModel();
				return;
			} else if ((i == modelUsers.size() - 1) && !modelUsers.get(i).getUsername().equals(username)) {
				throw new InvalidInputException("Could not change course worker McGill ID.");
			}
		}
	}

	public void changeCourseWorkerPastExp(String username, String newExp) throws InvalidInputException {
		List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
		for (int i = 0; i < modelUsers.size(); i++) {
			if (modelUsers.get(i).getUsername().equals(username)
					&& modelUsers.get(i) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				((ca.mcgill.ecse321.tamas.model.CourseWorker) modelUsers.get(i)).setPastWorkerExperience(newExp);
				saveModel();
				return;
			} else if ((i == modelUsers.size() - 1) && !modelUsers.get(i).getUsername().equals(username)) {
				throw new InvalidInputException("Could not change course worker past experience.");
			}
		}
	}

	public void applyForJob(Job job, Date applicationDate, Time applicationTime, int rank, String username)
			throws InvalidInputException {
		if (!job.equals(null) && !applicationDate.equals(null) && !applicationTime.equals(null) && rank > -1
				&& !username.equals(null) && username.length() > 0) {
			ca.mcgill.ecse321.tamas.model.CourseWorker worker = null;
			List<User> users = getModelUsers();
			for (ca.mcgill.ecse321.tamas.model.User user : users) {
				if (user.getUsername().equals(username) && user instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
					worker = (ca.mcgill.ecse321.tamas.model.CourseWorker) user;
				}
			}
			if (worker.equals(null)) {
				throw new InvalidInputException("Failed to get courseworker for username.");
			}
			List<Course> courses = tamas.getCourses();
			for (Course specificCourse : courses) {
				List<Job> jobs = specificCourse.getJobPostings();
				for (Job specificJob : jobs) {
					if (specificJob.equals(job)) {
						//specificJob.addJobApplication(applicationDate, applicationTime, rank, worker);
						worker.addJobApplication(specificJob.addJobApplication(applicationDate, applicationTime, rank, worker));
						saveModel();
						return;
					}
				}
			}
		} else {
			throw new InvalidInputException("A job application parameter has a bad value.");
		}
	}

	public ca.mcgill.ecse321.tamas.model.Course getCourse(int index) throws InvalidInputException{
		if (!(index<0)) {
			return tamas.getCourse(index);
		} else {
			throw new InvalidInputException("The entered index is bad.");
		}
	}

	public ca.mcgill.ecse321.tamas.model.JobApplication getJobApplication(ca.mcgill.ecse321.tamas.model.CourseWorker courseWorker, ca.mcgill.ecse321.tamas.model.Job job) throws InvalidInputException{
		List<JobApplication> jobApplications = getCourseWorkerJobApplications(courseWorker.getUsername());
        for (ca.mcgill.ecse321.tamas.model.JobApplication jobApplication : jobApplications) {
            if (jobApplication.getJob().getTitle().equals(job.getTitle())) {
            	return jobApplication;
            }
        }
        return null;
	}
	
	public List<ca.mcgill.ecse321.tamas.model.CourseWorker> getApplicantList(Job job, JobApplication.ApplicationStatus status) throws InvalidInputException{
		List<ca.mcgill.ecse321.tamas.model.CourseWorker> courseWorkerList = new ArrayList<ca.mcgill.ecse321.tamas.model.CourseWorker>();
		
		List<JobApplication> jobApplications = job.getJobApplications();
		for (JobApplication jobApplication : jobApplications){
			if(jobApplication.getApplicationStatus().equals(status)){
				courseWorkerList.add(jobApplication.getWorker());
			}
		}
		return courseWorkerList;
		
	}
	
	public ca.mcgill.ecse321.tamas.model.CourseWorker getEmployee(String employeeName, Job job) throws InvalidInputException{
		List<JobApplication> jobApplications = job.getJobApplications();
		for (ca.mcgill.ecse321.tamas.model.JobApplication jobApplication : jobApplications)	{
			if(jobApplication.getWorker().getName().equals(employeeName))	{
				return jobApplication.getWorker();
			}
		}
		return null;
	}
	
	public void deleteJobApplications(String username) throws InvalidInputException{
		List<ca.mcgill.ecse321.tamas.model.User> modelUsers = getModelUsers();
		for (int i = 0; i < modelUsers.size(); i++) {
			if (modelUsers.get(i).getUsername().equals(username) && modelUsers.get(i) instanceof ca.mcgill.ecse321.tamas.model.CourseWorker) {
				List<JobApplication> jobApplications = getCourseWorkerJobApplications(modelUsers.get(i).getUsername());
				for (int j = jobApplications.size()-1; j >= 0; j--){
					jobApplications.get(j).delete();
				}
				saveModel();
				return;
			} else if ((i == modelUsers.size() - 1) && !modelUsers.get(i).getUsername().equals(username)) {
				throw new InvalidInputException("Could not delete course worker Job Applications");
			}
		}
	}
}
