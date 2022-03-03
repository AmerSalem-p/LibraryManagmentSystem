package application;

import java.util.ArrayList;

public class Address {

	private int ZIP;
	private String City;
	static ArrayList<Address> list = new ArrayList<>();

	public Address(int ZIP, String City) {
		this.City = City;
		this.ZIP = ZIP;
		list.add(this);
	}

	public int getZIP() {
		return ZIP;
	}

	public void setZIP(int zIP) {
		ZIP = zIP;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@Override
	public String toString() {
		return City;
	}
	
	
}
