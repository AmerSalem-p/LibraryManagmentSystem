package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class EmployeeCell extends ListCell<Employee> {

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private GridPane gridPane;

	private ImageView ivMale = new ImageView();

	private Connection con;

	@Override
	protected void updateItem(Employee employee, boolean empty) {
		super.updateItem(employee, empty);

		if (empty) {
			setText(null);
			setGraphic(null);

		} else {
			setText(null);
			File file;
			if (employee.getGender().compareTo("Male") == 0) {
				file = new File("bin/images/MANC.png");
			} else {
				file = new File("bin/images/WOMANC.png");
			}

			Image image = new Image(file.toURI().toString());
			ivMale.setImage(image);
			ivMale.setFitHeight(30);
			ivMale.setFitWidth(30);

			Text id = new Text(String.valueOf(employee.getEID()));
			Text name = new Text(employee.getEName());
			Text email = new Text(employee.getEmail());
			Date date = employee.getDateOfBirth();
			Text age = new Text();
			Text job = new Text(employee.getJob());
			Text address = new Text();
			Text StatusT = new Text("Active");
			Text StatusF = new Text("Inactive");

			try {
				db();
				Statement s = null;
				ResultSet rs = null;
				s = con.createStatement();

				rs = s.executeQuery("select city from address where zip=" + employee.getLivesIn() + ";");
				rs.next();
				address.setText(rs.getString(1));

				con.close();
			} catch (SQLException e) {

			}

			java.sql.Date sqlDate = java.sql.Date.valueOf(date.toString());
			LocalDate localDate2 = sqlDate.toLocalDate();
			age.setText(getAge(localDate2.getYear(), localDate2.getMonthValue(), localDate2.getDayOfMonth()));

			GridPane gp = new GridPane();
			VBox vb = new VBox();
			vb.getChildren().addAll(name, email);
			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(5);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(10);
			ColumnConstraints col3 = new ColumnConstraints();
			col3.setPercentWidth(55);

			ColumnConstraints col5 = new ColumnConstraints();
			col5.setPercentWidth(15);

			ColumnConstraints col7 = new ColumnConstraints();
			col7.setPercentWidth(15);
			ColumnConstraints col8 = new ColumnConstraints();
			col8.setPercentWidth(15);

			gp.getColumnConstraints().addAll(col1, col2, col3, col5, col7, col8);

			gp.add(id, 0, 0);
			gp.add(ivMale, 1, 0);
			gp.add(vb, 2, 0);

			gp.add(job, 3, 0);

			gp.add(address, 4, 0);
			if (employee.isStatus()) {
				StatusT.setFill(Color.LAWNGREEN);
				gp.add(StatusT, 5, 0);
			} else {
				StatusF.setFill(Color.RED);
				gp.add(StatusF, 5, 0);
			}

			gp.setHgap(10);

			setGraphic(gp);

		}
	}

	private String getAge(int year, int month, int day) {
		Calendar dob = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		dob.set(year, month, day);
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		Integer ageInt = new Integer(age);
		String ageS = ageInt.toString();
		return ageS;
	}

	public void db() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "Amer123@@");
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}