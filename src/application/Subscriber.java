package application;

import java.util.Date;

public class Subscriber {
	private int SId;
	private Date DateOfBirth;
	private String SName;
	private String Phone;
	private String Gender;
	private int Address;

	public Subscriber(int sId, Date dateofbirth, String sName, String phone, String gender, int address) {
		super();
		SId = sId;
		DateOfBirth = dateofbirth;
		SName = sName;
		Phone = phone;
		Gender = gender;
		Address = address;
	}

	public int getSId() {
		return SId;
	}

	public void setSId(int sId) {
		SId = sId;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Date dateofbirth) {
		DateOfBirth = dateofbirth;
	}

	public String getSName() {
		return SName;
	}

	public void setSName(String sName) {
		SName = sName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public int getAddress() {
		return Address;
	}

	public void setAddress(int address) {
		Address = address;
	}
}
