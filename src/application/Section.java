package application;

import java.util.ArrayList;

public class Section {
	private String Category;
	private int Floor;
	static ArrayList<Section> list = new ArrayList<>();

	public Section(String Category, int Floor) {
		this.Category = Category;
		this.Floor = Floor;
		list.add(this);
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public int getFloor() {
		return Floor;
	}

	public void setFloor(int floor) {
		Floor = floor;
	}

	@Override
	public String toString() {
		return Category;
	}
}
