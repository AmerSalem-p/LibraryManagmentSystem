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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatsController implements Initializable {

	private Connection con;
	@FXML
	private Text TNumOfBooks;
	@FXML
	private Text TNumOfEmployee;
	@FXML
	private Text TNumOfSubscribers;
	@FXML
	private Text TEarnings;
	@FXML
	private Text TNumOfBorrowed;
	@FXML
	private Text TValueOfFines;
	@FXML
	private Text TMost;

	@FXML
	private Text TNoSection;
	@FXML
	private ComboBox<Section> cbSection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbSection.getItems().clear();
			cbSection.setPromptText("Choose Section");
			for (int i = 0; i < Section.list.size(); i++) {
				cbSection.getItems().add(Section.list.get(i));
			}
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("SELECT count(*) FROM BOOK;");
			if (!rs.next()) {
				TNumOfBooks.setText("No Books");
			} else {
				TNumOfBooks.setText(rs.getString(1));
			}

			rs = s.executeQuery("SELECT count(*) FROM EMPLOYEE;");
			if (!rs.next()) {
				TNumOfEmployee.setText("No Employees");
			} else {
				TNumOfEmployee.setText(rs.getString(1));
			}

			rs = s.executeQuery("SELECT count(*) FROM Subscriber;");
			if (rs.next()) {
				TNumOfSubscribers.setText(rs.getString(1));
			} else {
				TNumOfSubscribers.setText("No Subscribers");
			}

			int e = Integer.parseInt(rs.getString(1));
			TEarnings.setText(e * 50 + "");

			rs = s.executeQuery("SELECT count(*) FROM Borrow;");
			if (!rs.next()) {
				TNumOfBorrowed.setText("0");
			} else {
				TNumOfBorrowed.setText(rs.getString(1));
			}

			Statement s2 = null;
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

			rs = s.executeQuery("SELECT sum(amount) FROM fine;");
			if (!rs.next()) {
				TValueOfFines.setText("0");
			} else {
				TValueOfFines.setText(String.valueOf(rs.getInt(1)));
			}

			rs = s.executeQuery(
					"select distinct b.section,count(bb.isbn) as num from book b,borrow bb where b.isbn=bb.isbn group by b.section order by 2 desc ;");
			if (!rs.next()) {
				TMost.setText("No Borrowed Books");
			} else {
				TMost.setText(rs.getString(1));
			}

			cbSection.setOnAction(e2 -> {
				try {
					db();
					Statement s3 = null;
					ResultSet rs2 = null;
					s3 = con.createStatement();
					rs2 = s3.executeQuery("select count(*) from book where section like'"
							+ cbSection.getValue().getCategory() + "';");
					rs2.next();
					TNoSection.setText(String.valueOf(rs2.getInt(1)));
					con.close();
				} catch (Exception ex) {

				}
			});
			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void Back(MouseEvent event) throws Exception {
		Stage stage = (Stage) TNumOfBooks.getScene().getWindow();
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
}
