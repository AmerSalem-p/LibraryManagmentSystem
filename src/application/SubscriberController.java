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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SubscriberController implements Initializable {
	@FXML
	private Rectangle rectSearch;

	@FXML
	private Rectangle rectAdd;

	@FXML
	private Rectangle rectStatus;

	@FXML
	private Pane pSearch;

	@FXML
	private Button btSearch;

	@FXML
	private TextField tfSearch;

	@FXML
	private Label lAge;

	@FXML
	private Text tId;

	@FXML
	private Text tName;

	@FXML
	private Text tAge;

	@FXML
	private Text tPhone;

	@FXML
	private Text tGender;

	@FXML
	private Text tAddress;

	@FXML
	private TextField tfNameEdit;

	@FXML
	private TextField tfPhoneEdit;

	@FXML
	private ComboBox<String> cbEdit;

	@FXML
	private DatePicker dbEdit;

	@FXML
	private ComboBox<String> cbAddress;

	@FXML
	private Button btDelete;

	@FXML
	private Button btEdit;

	@FXML
	private Button btDone;

	@FXML
	private Button btCancel;

	@FXML
	private Pane pAdd;

	@FXML
	private TextField tfNameAdd;

	@FXML
	private TextField tfPhoneAdd;

	@FXML
	private ComboBox<String> cbGenderAdd;

	@FXML
	private DatePicker dpDateAdd;

	@FXML
	private ComboBox<Address> cbAddressAdd;

	@FXML
	private Text tNewId;

	@FXML
	private Text tStatus;

	@FXML
	private Button btRenew;

	private String CurrentDate;
	private boolean flag = false;
	private Connection con;
	private static LocalDate dateofbirth;

	static int Current_Id;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbEdit.getItems().addAll("Male", "Female");
			cbEdit.setValue("Male");
			btDelete.setVisible(false);
			btEdit.setVisible(false);
			db();
			Statement s = null;
			ResultSet rs = null;
			s = con.createStatement();
			rs = s.executeQuery("select max(sid) from subscriber;");
			if (rs.next()) {
				int id = rs.getInt(1) + 1;
				tNewId.setText(String.valueOf(id));
			} else {
				tNewId.setText("1");
			}
			con.close();
		} catch (Exception ex2) {

		}
	}

	@FXML
	void addSubscriber(ActionEvent event) throws Exception {
		try {
			db();
			Statement s = null;
			s = con.createStatement();

			for (int i = 0; i < Address.list.size(); i++) {
				cbAddressAdd.getItems().add(Address.list.get(i));
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();

			s.executeUpdate("insert into subscriber (SID,SName,DateOfBirth,phone,gender,livesin)values ('"
					+ tNewId.getText() + "','" + tfNameAdd.getText() + "','" + dpDateAdd.getValue().toString() + "','"
					+ tfPhoneAdd.getText() + "','" + cbGenderAdd.getValue() + "'," + cbAddressAdd.getValue().getZIP()
					+ ");");

			s.executeUpdate("insert into Subscribtion (amount,SubDate,sid)values (" + 50 + ",'" + dtf.format(now) + "',"
					+ tNewId.getText() + ");");

			con.close();
			tfNameAdd.clear();
			tfPhoneAdd.clear();
			cbGenderAdd.setValue("Male");
			dpDateAdd.setValue(null);
			int id = Integer.parseInt(tNewId.getText()) + 1;
			tNewId.setText(String.valueOf(id));
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Subscriber Added Successfully");
			a.show();
			pAdd.setVisible(false);
			pSearch.setVisible(true);
			tfPhoneAdd.clear();
			tfNameAdd.clear();
		} catch (NullPointerException ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("One Or More Fields Are Empty");
			a.show();
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

	@SuppressWarnings("deprecation")
	@FXML
	void Search(ActionEvent event) throws Exception {
		try {
			int Id = Integer.parseInt(tfSearch.getText());
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from subscriber where SID =" + Id + ";");
			if (rs.next() == false) {
				tfSearch.clear();
				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("Subscriber Doesn't Exist");
				a.show();
				tId.setText("");
				tName.setText("");
				tAge.setText("");
				tPhone.setText("");
				tGender.setText("");
				tAddress.setText("");
				tStatus.setText("");
				btDelete.setVisible(false);
				btEdit.setVisible(false);
				return;
			}
			Subscriber sub = new Subscriber(rs.getInt(1), rs.getDate(3), rs.getString(2), rs.getString(4),
					rs.getString(5), rs.getInt(6));

			tId.setText(String.valueOf(sub.getSId()));
			tName.setText(sub.getSName());

			Date d = sub.getDateOfBirth();

			java.sql.Date sqlDate = java.sql.Date.valueOf(d.toString());
			LocalDate localDate2 = sqlDate.toLocalDate();
			tAge.setText(getAge(localDate2.getYear(), localDate2.getMonthValue(), localDate2.getDayOfMonth()));
			tPhone.setText(sub.getPhone());
			tGender.setText(sub.getGender());

			dateofbirth = localDate2;

			int ZIP = sub.getAddress();
			rs = s.executeQuery("select city from address where ZIP =" + ZIP + ";");
			rs.next();
			tAddress.setText(rs.getString(1));
			btDelete.setVisible(true);
			btEdit.setVisible(true);
			tfSearch.clear();

			s = con.createStatement();
			rs = s.executeQuery("select b.SubDate from Subscriber s, Subscribtion b where s.sid = b.sid and s.sid="
					+ tId.getText() + ";");
			rs.next();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();

			Date d2 = rs.getDate(1);
			d2.setYear(d2.getYear() + 1);

			if (d2.toString().compareTo(dtf.format(now)) <= 0) {
				tStatus.setText("InValid");
				tStatus.setFill(Color.RED);
			} else {
				tStatus.setText("Valid Until: " + d2.toString());
				tStatus.setFill(Color.GREEN);

			}
			con.close();
		} catch (Exception ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Wrong Or No Input");
			a.show();
		}
	}

	@FXML
	void RenewSubscribtion(ActionEvent event) throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		CurrentDate = dtf.format(now).toString();
		flag = true;
	}

	@FXML
	void Edit(ActionEvent event) throws Exception {
		rectStatus.setVisible(false);
		db();
		cbAddress.getItems().clear();
		btRenew.setVisible(true);
		tStatus.setVisible(false);
		Statement s = null;
		ResultSet rs = null;
		s = con.createStatement();
		rs = s.executeQuery("select city from address");
		while (rs.next())
			cbAddress.getItems().add(rs.getString(1));

		con.close();
		cbEdit.getItems().clear();
		cbEdit.getItems().addAll("Male", "Female");
		lAge.setText("Date Of Birth");
		tfNameEdit.setVisible(true);
		dbEdit.setVisible(true);
		tfPhoneEdit.setVisible(true);
		cbEdit.setVisible(true);
		cbAddress.setVisible(true);
		tName.setVisible(false);
		tAge.setVisible(false);
		tPhone.setVisible(false);
		tGender.setVisible(false);
		tAddress.setVisible(false);
		btCancel.setVisible(true);
		btDone.setVisible(true);
		btEdit.setVisible(false);
		btDelete.setVisible(false);
		tfNameEdit.setText(tName.getText());
		dbEdit.setValue(dateofbirth);
		tfPhoneEdit.setText(tPhone.getText());
		cbAddress.setValue(tAddress.getText());
		cbEdit.setValue(tGender.getText());
	}

	@FXML
	void Cancel(ActionEvent event) {
		rectStatus.setVisible(true);
		btRenew.setVisible(false);
		tStatus.setVisible(true);
		btRenew.setVisible(false);
		cbEdit.getItems().clear();
		cbAddress.getItems().clear();
		lAge.setText("Age");
		tfNameEdit.setVisible(false);
		dbEdit.setVisible(false);
		tfPhoneEdit.setVisible(false);
		cbEdit.setVisible(false);
		cbAddress.setVisible(false);
		tName.setVisible(true);
		tAge.setVisible(true);
		tPhone.setVisible(true);
		tGender.setVisible(true);
		tAddress.setVisible(true);
		btCancel.setVisible(false);
		btDone.setVisible(false);
		btEdit.setVisible(true);
		btDelete.setVisible(true);
	}

	@FXML
	void cancelAdd(ActionEvent event) {
		rectStatus.setVisible(true);
		pAdd.setVisible(false);
		pSearch.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	@FXML
	void Edit2(ActionEvent event) throws Exception {
		rectStatus.setVisible(true);
		lAge.setText("Age");
		btRenew.setVisible(false);
		tStatus.setVisible(true);
		db();
		Statement s = null;
		ResultSet rs = null;
		s = con.createStatement();
		if (tfNameEdit.getText().isEmpty() || dbEdit.getValue() == null || tfPhoneEdit.getText().isEmpty()
				|| cbEdit.getValue().isEmpty() || cbAddress.getValue().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Please Fill All The Fields");
			a.show();
		} else {

			rs = s.executeQuery("select zip from address where city='" + cbAddress.getValue() + "';");
			rs.next();
			String ZIP = rs.getString(1);

			s.executeUpdate("update Subscriber set sname ='" + tfNameEdit.getText() + "', DateOfBirth='"
					+ dbEdit.getValue().toString() + "',phone ='" + tfPhoneEdit.getText() + "',gender='"
					+ cbEdit.getValue() + "', livesin=" + ZIP + " where sid=" + tId.getText() + ";");

			if (flag) {
				s.executeUpdate(
						"update Subscribtion set SubDate ='" + CurrentDate + "' where sid=" + tId.getText() + ";");
				flag = false;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date convertedCurrentDate = sdf.parse(CurrentDate);
				convertedCurrentDate.setYear(convertedCurrentDate.getYear() + 1);
				tStatus.setText("Valid Until: " + sdf.format(convertedCurrentDate));
				tStatus.setFill(Color.GREEN);
			}

			tName.setText(tfNameEdit.getText());
			tGender.setText(cbEdit.getValue());
			tAddress.setText(cbAddress.getValue());
			tPhone.setText(tfPhoneEdit.getText());
			LocalDate date = dbEdit.getValue();
			tAge.setText(getAge(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
			con.close();
			tfNameEdit.setVisible(false);
			dbEdit.setVisible(false);
			tfPhoneEdit.setVisible(false);
			cbEdit.setVisible(false);
			cbAddress.setVisible(false);
			tName.setVisible(true);
			tAge.setVisible(true);
			tPhone.setVisible(true);
			tGender.setVisible(true);
			tAddress.setVisible(true);
			btCancel.setVisible(false);
			btDone.setVisible(false);
			btEdit.setVisible(true);
			btDelete.setVisible(true);
		}
	}

	@FXML
	void Delete(ActionEvent event) throws Exception {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setContentText("Are You Sure You Want To Delete This Subscriber");
		a.showAndWait();
		if (a.getResult() == ButtonType.CANCEL) {
			return;
		} else if (a.getResult() == ButtonType.OK) {
			db();
			Statement s = con.createStatement();
			ResultSet rs = null;
			rs = s.executeQuery("select * from subscriber s, borrow b where s.sid = " + tId.getText() + ";");
			if (rs.next()) {
				Alert a2 = new Alert(AlertType.WARNING);
				a2.show();
				a2.setContentText("Cant Delete, Subscriber Has Borrowed Books");
				return;
			} else {
				s.executeUpdate("delete from Subscriber where sid = " + Integer.parseInt(tId.getText()));
				s.close();
				con.close();
				tId.setText("");
				tName.setText("");
				tAge.setText("");
				tPhone.setText("");
				tGender.setText("");
				tAddress.setText("");
				tStatus.setText("");
			}
		}
	}

	@FXML
	void addClick(MouseEvent event) throws Exception {

		db();
		Statement s2 = null;
		ResultSet rs2 = null;
		s2 = con.createStatement();
		rs2 = s2.executeQuery("select max(sid) from subscriber;");
		if (rs2.next()) {
			int id = rs2.getInt(1) + 1;
			tNewId.setText(String.valueOf(id));
		} else {
			tNewId.setText("1");
		}
		cbGenderAdd.getItems().clear();
		cbAddressAdd.getItems().clear();
		pAdd.setVisible(true);
		for (int i = 0; i < Address.list.size(); i++) {
			cbAddressAdd.getItems().add(Address.list.get(i));
		}
		cbAddressAdd.setValue(Address.list.get(0));
		// while (rs.next())
		// cbAddressAdd.getItems().add(rs.getString(1));
		cbGenderAdd.getItems().addAll("Male", "Female");
		cbGenderAdd.setValue("Male");
		con.close();
	}

	@FXML
	void searchClick(MouseEvent event) {
		pAdd.setVisible(false);
	}

	@FXML
	void Back(MouseEvent event) {
		Stage stage = (Stage) rectAdd.getScene().getWindow();
		stage.close();
	}

	@FXML
	void mouse(MouseEvent event) {
		rectAdd.setFill(Color.GREY);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		rectAdd.setFill(Color.WHITE);
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
