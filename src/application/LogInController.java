package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LogInController {
	@FXML
	private TextField tfId;

	@FXML
	private PasswordField tfPassword;

	@FXML
	private Button btLogIn;

	private Connection con;

	@FXML
	void login(ActionEvent event) throws Exception {
		db();
		Statement s = null;
		ResultSet rs = null;
		s = con.createStatement();

		String Id = tfId.getText();
		String Password = tfPassword.getText();

		if (Id.length() == 0 || Password.length() == 0) {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Please Fill All The Fields");
			a.show();
			return;
		}

		rs = s.executeQuery(
				"select * from employee where password=md5('" + Password + "') and eid=" + Id + " and status = 1;");
		if (rs.next()) {
			try {
				Statement ss = null;
				ResultSet rss = null;
				db();
				ss = con.createStatement();
				rss = ss.executeQuery("select * from Address;");
				Address a;
				while (rss.next()) {
					a = new Address(rss.getInt(1), rss.getString(2));
				}
				ss = con.createStatement();
				rss = ss.executeQuery("select * from section;");
				Section sec;
				while (rss.next()) {
					sec = new Section(rss.getString(2), rss.getInt(1));
				}
				con.close();
			}

			catch (Exception ex) {

			}

			if (Id.compareTo("1") == 0) {
				Employee.Curr_Employee = new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10),
						rs.getString(11), rs.getBoolean(12));
				Stage stage2 = (Stage) btLogIn.getScene().getWindow();
				stage2.close();
				Stage primaryStage = new Stage();
				Pane root = (Pane) FXMLLoader.load(getClass().getResource("menu.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("Turmusayya Public Library");
				primaryStage.getIcons().add(new Image("images/icon.png"));
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();
				return;
			} else {
				Employee.Curr_Employee = new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10),
						rs.getString(11), rs.getBoolean(12));
				Stage stage2 = (Stage) btLogIn.getScene().getWindow();
				stage2.close();
				Stage primaryStage = new Stage();
				Pane root = (Pane) FXMLLoader.load(getClass().getResource("EmployeeMenu.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("Turmusayya Public Library");
				primaryStage.getIcons().add(new Image("images/icon.png"));
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.show();
				return;
			}
		}
		tfId.clear();
		tfPassword.clear();
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Invalid Information");
		a.show();
		con.close();
	}

	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
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
