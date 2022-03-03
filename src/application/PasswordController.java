package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PasswordController {
	@FXML
	private TextField tfOld;

	@FXML
	private TextField tfNew;

	@FXML
	private TextField tfNewConf;

	private Connection con;

	@FXML
	void Change(ActionEvent event) throws Exception {
		int Id = Employee.Curr_Employee.getEID();
		db();
		Statement s = null;
		ResultSet rs = null;
		s = con.createStatement();
		rs = s.executeQuery("select * from employee where password=md5('" + tfOld.getText() + "') and eid=" + Id + ";");
		if (!rs.next()) {
			alert(AlertType.INFORMATION, "Wrong", "Old Password Is Incorrect");
			return;
		}

		if (tfNew.getText().compareTo(tfNewConf.getText()) != 0) {
			alert(AlertType.INFORMATION, "Wrong", "New Passwords Dont Match");
			return;
		}
		s.executeUpdate("update employee set password=md5('" + tfNew.getText() + "') where EID = " + Id + ";");
		alert(AlertType.INFORMATION, "Done", "Password Changed Successfully");
		Stage stage = (Stage) tfOld.getScene().getWindow();
		stage.close();
		con.close();
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

	public void alert(AlertType type, String title, String text) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("images/icon.png"));
		alert.setContentText(text);
		alert.showAndWait();
	}
}
