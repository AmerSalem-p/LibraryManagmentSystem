package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.temporal.ChronoUnit;

public class BorrowController implements Initializable {

	@FXML
	private TextField tfSubId;

	@FXML
	private TextField tfBookId;

	@FXML
	private Button Borrow;

	@FXML
	private DatePicker dpDate;

	@FXML
	private TextField tfSubIdReturn;

	@FXML
	private TextField tfBookIdReturn;

	@FXML
	private ListView<Borrow> lv;

	private Connection con;
	static ObservableList<Borrow> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lv.getItems().clear();
		getBorrow();
		lv.setItems(data);
		lv.setCellFactory(borrowcell -> new BorrowCell());

		lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (lv.getSelectionModel().getSelectedItem() == null) {
					tfSubIdReturn.clear();
					tfBookIdReturn.clear();
					return;
				}
				tfSubIdReturn.setText(String.valueOf(lv.getSelectionModel().getSelectedItem().getSID()));
				tfBookIdReturn.setText(String.valueOf(lv.getSelectionModel().getSelectedItem().getISBN()));
			}
		});

	}

	void getBorrow() {
		try {
			data.clear();
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from borrow;");
			while (rs.next()) {
				data.add(new Borrow(rs.getInt(4), rs.getLong(3), rs.getDate(1), rs.getDate(2)));
			}
			con.close();
		} catch (Exception ex) {

		}
	}

	@SuppressWarnings("deprecation")
	@FXML
	void Borrow(ActionEvent event) throws Exception {
		Statement s = null;
		ResultSet rs = null;
		if (tfSubId.getText().isEmpty() || tfBookId.getText().isEmpty() || dpDate.getValue() == null) {
			alert(AlertType.WARNING, "WARNING", "One Or More Fields Are Empty");
			return;
		}

		if (!isNumeric(tfSubId.getText()) || !isNumeric(tfBookId.getText())) {
			alert(AlertType.WARNING, "WARNING", "Wrong Input");
			return;
		}

		db();
		s = con.createStatement();
		String SubId = tfSubId.getText();
		String BookId = tfBookId.getText();
		rs = s.executeQuery("select * from subscriber where sid = " + SubId + ";");
		if (!rs.next()) {
			alert(AlertType.WARNING, "WARNING", "Subscriber Does Not Exist");
		}

		rs = s.executeQuery("select * from book where isbn = " + BookId + ";");
		if (!rs.next()) {
			alert(AlertType.WARNING, "WARNING", "Book Does Not Exist");
			return;
		}

		rs = s.executeQuery("select * from Borrow where ISBN = " + BookId + ";");
		if (rs.next()) {
			alert(AlertType.INFORMATION, "INFORMATION", "Book Has Already Been Borrowed");
			return;
		}

		rs = s.executeQuery("select SubDate from Subscribtion where sid = " + SubId + ";");
		rs.next();
		Date d = rs.getDate(1);
		Date d2 = d;
		d2.setYear(d2.getYear() + 1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		if (dtf.format(now).toString().compareTo(d2.toString()) > 0) {
			alert(AlertType.INFORMATION, "INFORMATION", "Subscribtion Expired On " + d2.toString());
			return;
		}

		if (dtf.format(now).toString().compareTo(dpDate.getValue().toString()) > 0) {
			alert(AlertType.INFORMATION, "INFORMATION", "Return Date Can Not Be In The Past");
			return;
		}

		if (d2.toString().compareTo(dpDate.getValue().toString()) < 0) {
			alert(AlertType.INFORMATION, "INFORMATION", "Return Date Can Not Be After Subscribtion Expiration Date");
			return;
		}

		s.executeUpdate("insert into borrow values('" + dtf.format(now) + "','" + dpDate.getValue().toString() + "','"
				+ BookId + "','" + SubId + "');");
		alert(AlertType.INFORMATION, "INFORMATION", "Book Has Been Borrowed Successfully");

		LocalDate now2 = LocalDate.now();
		java.util.Date date = java.sql.Date.valueOf(dpDate.getValue());
		java.util.Date date2 = java.sql.Date.valueOf(now2);

		data.add(new Borrow(Integer.parseInt(tfSubId.getText()), Long.parseLong(tfBookId.getText()), date2, date));
		lv.refresh();
		tfSubId.clear();
		tfBookId.clear();
		dpDate.setValue(null);
		con.close();
	}

	@FXML
	void Return(ActionEvent event) throws Exception {
		Statement s = null;
		ResultSet rs = null;
		if (tfSubIdReturn.getText().isEmpty() || tfBookIdReturn.getText().isEmpty()) {
			alert(AlertType.WARNING, "WARNING", "One Or More Fields Are Empty");
			return;
		}

		if (!isNumeric(tfSubIdReturn.getText()) || !isNumeric(tfBookIdReturn.getText())) {
			alert(AlertType.WARNING, "WARNING", "Wrong Input");
			return;
		}

		String SubId = tfSubIdReturn.getText();
		String BookId = tfBookIdReturn.getText();

		db();
		s = con.createStatement();
		rs = s.executeQuery("select * from subscriber where sid = " + SubId + ";");
		if (!rs.next()) {
			alert(AlertType.WARNING, "WARNING", "Subscriber Does Not Exist");
			return;
		}

		rs = s.executeQuery("select * from book where isbn = " + BookId + ";");
		if (!rs.next()) {
			alert(AlertType.WARNING, "WARNING", "Book Does Not Exist");
			return;
		}

		rs = s.executeQuery("select * from  borrow where isbn = " + BookId + ";");
		if (!rs.next()) {
			alert(AlertType.WARNING, "WARNING", "Book Has Not Been Borrowed Yet");
			return;
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate now = LocalDate.now();

		rs = s.executeQuery("select Ret_Date from  borrow where isbn = " + BookId + ";");
		rs.next();
		Date d = rs.getDate(1);

		s.executeUpdate("delete from borrow where isbn=" + BookId + ";");

		if (dtf.format(now).toString().compareTo(d.toString()) > 0) {
			LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(d));
			long daysBetween = ChronoUnit.DAYS.between(localDate, now);
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setContentText("Book Has A Fine Of Value: " + daysBetween * 2 + "\n Would You Like To Pay Now");
			a.showAndWait();
			if (a.getResult() == ButtonType.CANCEL) {
				s.executeUpdate(
						"insert ignore into fine values (" + SubId + "," + BookId + "," + (daysBetween * 2) + ");");
			} else if (a.getResult() == ButtonType.OK) {
				s.executeUpdate("delete from fine where isbn=" + BookId + ";");
			}
		}

		alert(AlertType.INFORMATION, "INFORMATION", "Book Has Been Returned Successfully");

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getISBN() == Long.parseLong(tfBookIdReturn.getText())) {
				data.remove(i);
				break;
			}
		}
		tfSubIdReturn.clear();
		tfBookIdReturn.clear();
		lv.refresh();
		con.close();
	}

	@FXML
	void Back(MouseEvent event) {
		Stage stage = (Stage) Borrow.getScene().getWindow();
		stage.close();
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

	public boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}