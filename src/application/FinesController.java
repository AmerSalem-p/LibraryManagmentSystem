package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FinesController implements Initializable {
	@FXML
	private ListView<Fine> lv;
	private Connection con;

	@FXML
	private TextField tfSearch;
	static ObservableList<Fine> data = FXCollections.observableArrayList();
	static ObservableList<Fine> dataS = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			lv.getItems().clear();
			Statement s = null;
			Statement s2 = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			s2 = con.createStatement();
			rs = s.executeQuery("select * from borrow;");
			LocalDate now = LocalDate.now();
			LocalDate localDate;
			long daysBetween;
			while (rs.next()) {
				localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(2)));
				daysBetween = ChronoUnit.DAYS.between(localDate, now);
				if (rs.getDate(2).toString().compareTo(now.toString()) < 0) {
					s2.executeUpdate(
							"insert ignore into fine (sid,isbn)values (" + rs.getInt(4) + "," + rs.getLong(3) + ");");

					s2.executeUpdate("update fine set amount=" + (daysBetween * 2) + " where sid=" + rs.getInt(4)
							+ " and isbn=" + rs.getLong(3) + ";");
				}
			}
			rs.close();
			con.close();
			getFines();
			lv.setItems(data);
			lv.setCellFactory(fineCell -> new FineCell());
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	void getFines() {
		try {
			data.clear();
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from fine;");
			while (rs.next()) {
				data.add(new Fine(rs.getInt(1), rs.getLong(2), rs.getDouble(3)));
			}
			rs.close();
			con.close();
		} catch (Exception ex) {

		}
	}

	@FXML
	void Back(MouseEvent event) {
		Stage stage = (Stage) lv.getScene().getWindow();
		stage.close();
	}

	@FXML
	void Pay(ActionEvent event) throws Exception {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setContentText("Are You Sure You Want To Pay This Fine");
		a.showAndWait();
		if (a.getResult() == ButtonType.CANCEL) {
			return;
		} else if (a.getResult() == ButtonType.OK) {
			Fine f = lv.getSelectionModel().getSelectedItem();
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from borrow where isbn=" + f.getISBN() + ";");
			if (!rs.next()) {
				s.executeUpdate("delete from fine where isbn=" + f.getISBN() + ";");
				con.close();
				data.remove(f);
				lv.setItems(data);
			} else {
				Alert al = new Alert(AlertType.INFORMATION);
				al.setContentText("Please Return Book First");
				al.show();
			}
		}
		con.close();
	}

	@FXML
	void Search(MouseEvent event) throws Exception {
		dataS.clear();
		String SID = tfSearch.getText();
		Statement s = null;
		ResultSet rs = null;
		db();
		s = con.createStatement();
		rs = s.executeQuery("select * from fine where sid=" + SID + ";");
		if (!rs.next()) {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("There Are No Matches");
			a.showAndWait();
			return;
		}
		rs.previous();
		while (rs.next()) {
			dataS.add(new Fine(rs.getInt(1), rs.getLong(2), rs.getDouble(3)));
		}
		con.close();
		lv.setItems(dataS);
	}

	@FXML
	void Refresh(MouseEvent event) {
		getFines();
		lv.setItems(data);
		tfSearch.clear();
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