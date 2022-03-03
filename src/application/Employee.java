package application;

import java.util.Date;

public class Employee {
	private int EID;
	private Date DateOfBirth;
	private String EName;
	private int Salary;
	private String Job;
	private String Email;
	private int LivesIn;
	private String Section;
	private String Phone;
	private String Gender;
	private boolean Status;

	public static Employee Curr_Employee;

	public Employee(int eID, Date dateofbirth, String eName, int salary, String job, String email, int livesIn,
			String section, String phone, String gender, boolean status) {
		super();
		EID = eID;
		DateOfBirth = dateofbirth;
		EName = eName;
		Salary = salary;
		Job = job;
		Email = email;
		LivesIn = livesIn;
		Section = section;
		Phone = phone;
		Gender = gender;
		Status = status;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public int getEID() {
		return EID;
	}

	public void setEID(int eID) {
		EID = eID;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Date age) {
		DateOfBirth = age;
	}

	public String getEName() {
		return EName;
	}

	public void setEName(String eName) {
		EName = eName;
	}

	public int getSalary() {
		return Salary;
	}

	public void setSalary(int salary) {
		Salary = salary;
	}

	public String getJob() {
		return Job;
	}

	public void setJob(String job) {
		Job = job;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getLivesIn() {
		return LivesIn;
	}

	public void setLivesIn(int livesIn) {
		LivesIn = livesIn;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}

}