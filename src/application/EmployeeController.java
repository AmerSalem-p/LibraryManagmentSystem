package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class EmployeeController implements Initializable {
	@FXML
	private TextField edID;
	@FXML
	private TextField edName;
	@FXML
	private TextField edJob;
	@FXML
	private TextField edAddress;
	@FXML
	private TextField edAge;
	@FXML
	private TextField edSalary;
	@FXML
	private TextField edEmail;
	@FXML
	private TextField edSection;
	@FXML
	private TextField esID;
	@FXML
	private TextField esName;
	@FXML
	private TextField esJob;
	@FXML
	private TextField esAddress;
	@FXML
	private TextField esAge;
	@FXML
	private TextField esSalary;
	@FXML
	private TextField esEmail;
	@FXML
	private TextField esSection;
	@FXML
	private Button btAddEmployee;
	@FXML
	private Button eAdd;
	@FXML
	private Button eSearch;
	@FXML
	private Button eRemove;
	@FXML
	private Button btSearchEmployee;
	@FXML
	private TextField tfSearch;

	@FXML
	private Rectangle rectBlue;

	@FXML
	private ComboBox<String> cbSearch;
	@FXML
	private ListView<Employee> lv;

	@FXML
	private Text tName;

	@FXML
	private Text tAge;

	@FXML
	private Text tGender;

	@FXML
	private Text tPhone;

	@FXML
	private Text tAddress;

	@FXML
	private Text tEmail;

	@FXML
	private Text tId;

	@FXML
	private Text tJob;

	@FXML
	private Text tSection;

	@FXML
	private Text tSalary;

	@FXML
	private ImageView ivPhoto;

	@FXML
	private ImageView ivEdit;

	@FXML
	private TextField tfName;

	@FXML
	private DatePicker dpDate;

	@FXML
	private ComboBox<String> cbGender;

	@FXML
	private TextField tfPhone;

	@FXML
	private TextField tfEmail;

	@FXML
	private ComboBox<Address> cbAddress;

	@FXML
	private TextField tfSalary;

	@FXML
	private TextField tfJob;

	@FXML
	private ComboBox<String> cbSection;

	@FXML
	private Button btCancel;

	@FXML
	private Button btDone;

	@FXML
	private Button btReactivate;

	private File mFile = new File("bin/images/MANC.png");
	private File fFile = new File("bin/images/WOMANC.png");
	private File cFile = new File("bin/images/CIRCLE.png");

	static ObservableList<Employee> data2 = FXCollections.observableArrayList();
	private Connection con;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btReactivate.setVisible(false);
		try {
			lv.getItems().clear();
			getEmployee();
			lv.setItems(data2);
			lv.setCellFactory(employeecell -> new EmployeeCell());
			cbSearch.getItems().addAll("Name", "Id", "Job", "Section", "Address", "Gender");
			ivPhoto.setImage(new Image(cFile.toURI().toString()));
			cbSearch.setValue("Name");
			ivEdit.setVisible(false);
			btDone.setVisible(false);
			btCancel.setVisible(false);
			btAddEmployee.setVisible(false);
			lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (lv.getSelectionModel().getSelectedItem() == null) {
						return;
					}
					ivEdit.setVisible(true);
					if (lv.getSelectionModel().getSelectedItem().getGender().compareTo("Male") == 0) {
						ivPhoto.setImage(new Image(mFile.toURI().toString()));
					} else {
						ivPhoto.setImage(new Image(fFile.toURI().toString()));
					}

					tName.setText(lv.getSelectionModel().getSelectedItem().getEName());
					tName.setWrappingWidth(250);
					tGender.setText(lv.getSelectionModel().getSelectedItem().getGender());
					tPhone.setText(lv.getSelectionModel().getSelectedItem().getPhone());
					tEmail.setText(lv.getSelectionModel().getSelectedItem().getEmail());
					tId.setText(String.valueOf(lv.getSelectionModel().getSelectedItem().getEID()));
					tJob.setText(lv.getSelectionModel().getSelectedItem().getJob());
					tSection.setText(lv.getSelectionModel().getSelectedItem().getSection());
					tSalary.setText(String.valueOf(lv.getSelectionModel().getSelectedItem().getSalary()));
					Date d = lv.getSelectionModel().getSelectedItem().getDateOfBirth();
					java.sql.Date sqlDate = java.sql.Date.valueOf(d.toString());
					LocalDate localDate2 = sqlDate.toLocalDate();
					tAge.setText(getAge(localDate2.getYear(), localDate2.getMonthValue(), localDate2.getDayOfMonth()));

					int ZIP = lv.getSelectionModel().getSelectedItem().getLivesIn();
					for (int i = 0; i < Address.list.size(); i++) {
						if (Address.list.get(i).getZIP() == ZIP) {
							tAddress.setText(Address.list.get(i).toString());
							break;
						}
					}
				}
			});

		} catch (NullPointerException ex) {

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

	public void getEmployee() {
		try {
			data2.clear();
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from employee;");
			while (rs.next()) {
				data2.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
						rs.getBoolean(12)));
			}
			con.close();
		} catch (Exception ex) {

		}
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

	@FXML
	void addEmployee2() throws Exception {
		if (tfName.getText().isEmpty() || dpDate.getValue() == null || tfPhone.getText().isEmpty()
				|| tfEmail.getText().isEmpty() || tfJob.getText().isEmpty() || tfSalary.getText().isEmpty()) {
			alert(AlertType.ERROR, "Can't add", "Please Fill All Fields.");
			return;
		}

		if (!isNumeric(tfSalary.getText())) {
			alert(AlertType.ERROR, "Can't add", "Salary Must Be Number.");
			return;
		}

		if (!isNumeric(tfPhone.getText())) {
			alert(AlertType.ERROR, "Can't add", "Phone Must Be Number.");
			return;
		}

		java.util.Date date = java.sql.Date.valueOf(dpDate.getValue());

		Employee e = new Employee(Integer.parseInt(tId.getText()), date, tfName.getText(),
				Integer.parseInt(tfSalary.getText()), tfJob.getText(), tfEmail.getText(), cbAddress.getValue().getZIP(),
				cbSection.getValue(), tfPhone.getText(), cbGender.getValue(), true);

		ArrayList<Integer> al = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			al.add(i);
		}

		String pass = "";
		Collections.shuffle(al);
		for (int i = 0; i < 4; i++) {
			pass += al.get(i);
		}
		db();
		Statement s = null;
		s = con.createStatement();
		s.executeUpdate(
				"insert into employee (DateOfBirth,ename,salary,job,email,livesin,section,password,phone,gender,status) values ('"
						+ dpDate.getValue().toString() + "','" + tfName.getText() + "','" + tfSalary.getText() + "','"
						+ tfJob.getText() + "','" + tfEmail.getText() + "','" + cbAddress.getValue().getZIP() + "','"
						+ cbSection.getValue() + "', " + "MD5('" + pass + "'),'" + tfPhone.getText() + "','"
						+ cbGender.getValue() + "', true " + ");");
		data2.add(e);
		Refresh(null);
		con.close();
		visibleFields(false);
		btAddEmployee.setVisible(false);
		alert(AlertType.INFORMATION, "Successful", "Employee Added Successfully.");
		alert(AlertType.INFORMATION, "Successful", "New Password Is: " + pass);
	}

	@FXML
	void addEmployee() throws Exception {
		cbAddress.getItems().clear();
		cbSection.getItems().clear();
		cbGender.getItems().clear();
		cbGender.getItems().addAll("Male", "Female");
		cbGender.setValue("Male");
		for (int i = 0; i < Address.list.size(); i++) {
			cbAddress.getItems().add(Address.list.get(i));
		}
		for (int i = 0; i < Section.list.size(); i++) {
			cbSection.getItems().add(Section.list.get(i).getCategory());
		}
		cbAddress.setValue(cbAddress.getItems().get(0));
		cbSection.setValue(cbSection.getItems().get(0));
		visibleFields(true);
		ivPhoto.setImage(new Image(cFile.toURI().toString()));
		ivEdit.setVisible(false);
		btAddEmployee.setVisible(true);
		btDone.setVisible(false);
		db();
		Statement s = null;
		ResultSet rs = null;
		s = con.createStatement();
		rs = s.executeQuery("select max(e.eid) from employee e;");
		rs.next();
		tId.setText(String.valueOf((Integer.parseInt(rs.getString(1)) + 1)));
		con.close();
	}

	@FXML
	void Refresh(MouseEvent event) {
		getEmployee();
		lv.setItems(data2);
		tfSearch.clear();
		ivPhoto.setImage(new Image(cFile.toURI().toString()));
		tName.setText("");
		tGender.setText("");
		tPhone.setText("");
		tEmail.setText("");
		tId.setText("");
		tJob.setText("");
		tSection.setText("");
		tSalary.setText("");
		tAge.setText("");
		tAddress.setText("");
		ivEdit.setVisible(false);
	}

	@FXML
	void Back(MouseEvent event) {
		Stage stage = (Stage) cbSearch.getScene().getWindow();
		stage.close();
	}

	@FXML
	void searchEmployee() throws Exception {
		ObservableList<Employee> temp = FXCollections.observableArrayList();
		try {
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			if (cbSearch.getValue().compareTo("Id") == 0) {
				int EID = Integer.parseInt(tfSearch.getText());
				rs = s.executeQuery("select * from employee where EID = " + EID + ";");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}

			} else if (cbSearch.getValue().compareTo("Name") == 0) {
				String Name = tfSearch.getText();
				rs = s.executeQuery("select * from employee where ename LIKE '%" + Name + "%" + "';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}

			} else if (cbSearch.getValue().compareTo("Job") == 0) {
				String Job = tfSearch.getText();
				rs = s.executeQuery("select * from employee where job LIKE '%" + Job + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}
			}

			else if (cbSearch.getValue().compareTo("Section") == 0) {
				String Section = tfSearch.getText();
				rs = s.executeQuery("select * from employee where Section LIKE '%" + Section + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}

			} else if (cbSearch.getValue().compareTo("Gender") == 0) {
				String Gender = tfSearch.getText();
				rs = s.executeQuery("select * from employee where gender LIKE '" + Gender + "';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}

			} else if (cbSearch.getValue().compareTo("Address") == 0) {
				int ZIP = 0;
				for (int i = 0; i < Address.list.size(); i++) {
					if (Address.list.get(i).getCity().compareToIgnoreCase(tfSearch.getText()) == 0) {
						ZIP = Address.list.get(i).getZIP();
						break;
					}
				}

				rs = s.executeQuery("select * from employee where LivesIn=" + ZIP + ";");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Employee(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10), rs.getString(11),
							rs.getBoolean(12)));
				}

			}
			lv.setItems(temp);
			con.close();
		} catch (NumberFormatException ex2) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Wrong Input");
			a.showAndWait();
		} catch (NullPointerException ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Please Choose A Category");
			a.showAndWait();
		}
	}

	@FXML
	void Edit() throws Exception {
		if (!lv.getSelectionModel().getSelectedItem().isStatus()) {
			btReactivate.setVisible(true);
		}
		visibleTexts(false);
		cbAddress.getItems().clear();
		cbSection.getItems().clear();
		cbAddress.getItems().clear();
		cbGender.getItems().clear();
		cbGender.getItems().addAll("Male", "Female");
		visibleFields(true);

		for (int i = 0; i < Section.list.size(); i++)
			cbSection.getItems().add(Section.list.get(i).getCategory());

		for (int i = 0; i < Address.list.size(); i++)
			cbAddress.getItems().add(Address.list.get(i));

		tfName.setText(tName.getText());
		cbGender.setValue(tGender.getText());

		Date d = lv.getSelectionModel().getSelectedItem().getDateOfBirth();
		java.sql.Date sqlDate = java.sql.Date.valueOf(d.toString());
		LocalDate localDate2 = sqlDate.toLocalDate();
		dpDate.setValue(localDate2);
		tfPhone.setText(tPhone.getText());

		for (int i = 0; i < Address.list.size(); i++) {
			if (Address.list.get(i).getCity().compareTo(tAddress.getText()) == 0) {
				cbAddress.setValue(Address.list.get(i));
				break;
			}
		}
		tfEmail.setText(tEmail.getText());
		tfJob.setText(tJob.getText());
		cbSection.setValue(tSection.getText());
		tfSalary.setText(tSalary.getText());
	}

	@FXML
	void Edit2() throws Exception {
		btReactivate.setVisible(false);
		if (tfName.getText().isEmpty() || dpDate.getValue() == null || tfPhone.getText().isEmpty()
				|| tfEmail.getText().isEmpty() || tfJob.getText().isEmpty() || tfSalary.getText().isEmpty()) {
			alert(AlertType.ERROR, "Can't add", "Please Fill All Fields.");
			return;
		}

		if (!isNumeric(tfSalary.getText())) {
			alert(AlertType.ERROR, "Can't add", "Salary Must Be Number.");
			return;
		}

		if (!isNumeric(tfPhone.getText())) {
			alert(AlertType.ERROR, "Can't add", "Phone Must Be Number.");
			return;
		}

		db();
		Statement s = null;
		s = con.createStatement();
		s.executeUpdate("update employee set ename ='" + tfName.getText() + "', DateOfBirth='"
				+ dpDate.getValue().toString() + "',phone ='" + tfPhone.getText() + "',gender='" + cbGender.getValue()
				+ "', livesin=" + cbAddress.getValue().getZIP() + ",Email='" + tfEmail.getText() + "', job='"
				+ tfJob.getText() + "',salary=" + tfSalary.getText() + ",section='" + cbSection.getValue()
				+ "' where Eid=" + tId.getText() + ";");
		con.close();

		LocalDate date = dpDate.getValue();
		tAge.setText(getAge(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
		tName.setText(tfName.getText());
		tGender.setText(cbGender.getValue());
		tPhone.setText(tfPhone.getText());
		tAddress.setText(cbAddress.getValue().getCity());
		tEmail.setText(tfEmail.getText());
		tJob.setText(tfJob.getText());
		tSection.setText(cbSection.getValue());
		tSalary.setText(tfSalary.getText());

		getEmployee();
		lv.setItems(data2);
		Refresh(null);
		visibleFields(false);
		visibleTexts(true);
		alert(AlertType.INFORMATION, "Successful", "Employee Edited Successfully.");
	}

	@FXML
	void Cancel() throws IOException {
		btReactivate.setVisible(false);
		visibleFields(false);
		visibleTexts(true);
		btAddEmployee.setVisible(false);
	}

	@FXML
	void removeEmployee() throws SQLException {
		if (lv.getSelectionModel().getSelectedIndex() == -1) {
			alert(AlertType.ERROR, "Can't Remove", "Please Select Employee To Remove.");
			return;
		}
		if (lv.getSelectionModel().getSelectedItem().getEID() == 1) {
			alert(AlertType.ERROR, "Can't Remove", "Can Not Remove Maneger");
			return;
		}

		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setContentText("Are You Sure You Want To Delete This Employee");
		a.showAndWait();
		if (a.getResult() == ButtonType.CANCEL) {
			return;
		} else if (a.getResult() == ButtonType.OK) {
			db();
			Statement s = null;
			s = con.createStatement();
			s.executeUpdate("update employee set status = false where eid ="
					+ lv.getSelectionModel().getSelectedItem().getEID() + ";");
			con.close();
			Refresh(null);
			alert(AlertType.INFORMATION, "Successful", "Employee Removed Successfully.");
		}
	}

	@FXML
	void Reactivate(ActionEvent event) throws Exception {
		db();
		Statement s = null;
		s = con.createStatement();
		s.executeUpdate("update employee set status = 1 where eid=" + tId.getText() + ";");
		alert(AlertType.INFORMATION, "Success", "Employee ReActivated Successfully");
		Employee e = data2.get(Integer.parseInt(tId.getText()) - 1);
		e.setStatus(true);
		data2.set(Integer.parseInt(tId.getText()) - 1, e);
		lv.refresh();
		con.close();
		btReactivate.setVisible(false);
	}

	public void visibleTexts(boolean b) {
		tName.setVisible(b);
		tGender.setVisible(b);
		tPhone.setVisible(b);
		tEmail.setVisible(b);
		tJob.setVisible(b);
		tSection.setVisible(b);
		tSalary.setVisible(b);
		tAge.setVisible(b);
		tAddress.setVisible(b);
	}

	public void visibleFields(boolean b) {
		if (!b) {
			tfName.clear();
			dpDate.setValue(null);
			tfPhone.clear();
			tfEmail.clear();
			tfJob.clear();
			tfSalary.clear();
		}
		btDone.setVisible(b);
		btCancel.setVisible(b);
		cbGender.setVisible(b);
		tfName.setVisible(b);
		dpDate.setVisible(b);
		tfPhone.setVisible(b);
		cbAddress.setVisible(b);
		tfEmail.setVisible(b);
		tfJob.setVisible(b);
		cbSection.setVisible(b);
		tfSalary.setVisible(b);
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
