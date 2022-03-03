package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.scene.control.cell.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.*;
import java.io.IOException;
import java.net.URL;

public class Controller implements Initializable {
	@FXML
	private TableView<Book> TV;
	@FXML
	private TableColumn<Book, Long> tcISBN;
	@FXML
	private TableColumn<Book, String> tcTitle;
	@FXML
	private TableColumn<Book, String> tcAuthor;
	@FXML
	private TableColumn<Book, Integer> tcEdition;
	@FXML
	private TableColumn<Book, String> tcPublisher;
	@FXML
	private TableColumn<Book, Section> tcSection;
	@FXML
	private TableColumn<Book, Integer> tcYear_Of_Production;
	@FXML
	private TableColumn<Book, String> tcDate_Of_Entry;
	@FXML
	private TableColumn<Book, String> tcCountry;
	@FXML
	private TextField tfISBN;
	@FXML
	private TextField tfTitle;
	@FXML
	private TextField tfAuthor;
	@FXML
	private TextField tfPublisher;
	@FXML
	private TextField tfEdition;
	@FXML
	private TextField tfYear;
	@FXML
	private TextField tfCategory;
	@FXML
	private TextField tfCountry;
	@FXML
	private TextField sISBN;
	@FXML
	private TextField sTitle;
	@FXML
	private TextField sAuthor;
	@FXML
	private TextField sPublisher;
	@FXML
	private TextField sEdition;
	@FXML
	private TextField sYear;
	@FXML
	private TextField sCategory;
	@FXML
	private TextField sCountry;
	@FXML
	private Button btAddBook;
	@FXML
	private Button btSearchBook;
	@FXML
	private Label L1;
	@FXML
	private Label L2;
	@FXML
	private Label L3;
	@FXML
	private Label L4;
	@FXML
	private Label L5;
	@FXML
	private Label L6;
	@FXML
	private Label L7;
	@FXML
	private Label L8;

	@FXML
	private TextField tfSearch;

	@FXML
	private ComboBox<String> cbSearch;

	@FXML
	private ComboBox<Section> cbSection;

	@FXML
	private Pane pMain;

	@FXML
	private Pane pAdd;

	@FXML
	private Text tEmpName;

	@FXML
	private Text tEmpId;
	static ObservableList<Book> data = FXCollections.observableArrayList();

	private Connection con;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tcISBN.setCellValueFactory(new PropertyValueFactory<Book, Long>("ISBN"));
			tcTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
			tcAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("Author"));
			tcEdition.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Edition"));
			tcPublisher.setCellValueFactory(new PropertyValueFactory<Book, String>("Publisher"));
			tcSection.setCellValueFactory(new PropertyValueFactory<Book, Section>("Category"));
			tcYear_Of_Production.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Year_Of_Production"));
			tcDate_Of_Entry.setCellValueFactory(new PropertyValueFactory<Book, String>("Date_Of_Entry"));
			tcCountry.setCellValueFactory(new PropertyValueFactory<Book, String>("Country"));
			data.clear();
			tcTitle.prefWidthProperty().bind(TV.widthProperty().divide(2));
			tcAuthor.prefWidthProperty().bind(TV.widthProperty().divide(4));
			tcPublisher.prefWidthProperty().bind(TV.widthProperty().divide(4));
			tcSection.prefWidthProperty().bind(TV.widthProperty().divide(6));

			getBook();
			TV.setItems(data);
			TV.setEditable(true);
			TV.setStyle("-fx-table-cell-border-color: transparent;");

			cbSearch.getItems().addAll("ISBN", "Title", "Author", "Edition", "Publisher", "Section", "Year", "Country");
			cbSearch.setPromptText("Choose Category");

			
			tcTitle.setCellFactory(TextFieldTableCell.forTableColumn());
			tcAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
			tcEdition.setCellFactory(TextFieldTableCell.<Book, Integer>forTableColumn(new IntegerStringConverter()));
			tcPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
			tcYear_Of_Production
					.setCellFactory(TextFieldTableCell.<Book, Integer>forTableColumn(new IntegerStringConverter()));
			tcCountry.setCellFactory(TextFieldTableCell.forTableColumn());
			TV.refresh();

		} catch (NullPointerException ex) {

		}
	}

	@FXML
	void updateAuthor(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setAuthor(e.getNewValue().toString());
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set author = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updateCategory(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setCategory(e.getNewValue().toString());
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set section = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updateCountry(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setCountry(e.getNewValue().toString());
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set Country = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updateEdition(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setEdition(Integer.parseInt(e.getNewValue().toString()));
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set edition = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updatePublisher(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setPublisher(e.getNewValue().toString());
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set publisher = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updateTitle(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setTitle(e.getNewValue().toString());
		Long ISBN = b.getISBN();
		db();
		executeStatement("update book set title = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void updateYear(CellEditEvent e) throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		b.setYear_Of_Production(Integer.parseInt(e.getNewValue().toString()));
		Long ISBN = b.getISBN();
		db();
		executeStatement(
				"update book set YearOfProduction = '" + e.getNewValue().toString() + "' where ISBN = " + ISBN + ";");
		con.close();
	}

	@FXML
	void addBook() throws IOException {
		cbSection.getItems().clear();
		for (int i = 0; i < Section.list.size(); i++) {
			cbSection.getItems().add(Section.list.get(i));
		}
		cbSection.setValue(Section.list.get(0));
		pMain.setVisible(false);
		pAdd.setVisible(true);
		tfISBN.clear();
		tfTitle.clear();
		tfAuthor.clear();
		tfEdition.clear();
		tfPublisher.clear();
		tfYear.clear();
		tfCountry.clear();
		TV.refresh();
	}

	@FXML
	void Cancel() throws IOException {
		pMain.setVisible(true);
		pAdd.setVisible(false);
		TV.refresh();
	}

	@FXML
	void addBook2() throws Exception {
		try {
			String Title = tfTitle.getText();
			String Author = tfAuthor.getText();
			String Country = tfCountry.getText();
			String Category = cbSection.getValue().getCategory();
			String Publisher = tfPublisher.getText();
			String ISBN = tfISBN.getText();
			String Edition = tfEdition.getText();
			String Year = tfYear.getText();

			if (Title.isEmpty() || Author.isEmpty() || Country.isEmpty() || Category.isEmpty() || Publisher.isEmpty()
					|| ISBN.isEmpty() || Edition.isEmpty() || Year.isEmpty()) {
				alert(AlertType.ERROR, "Can't add", "Please fill all fields.");
				return;
			}

			if (!tfISBN.getText().isEmpty() && !isNumeric(ISBN)) {
				alert(AlertType.ERROR, "Can't add", "ISBN must be number.");
				return;
			}

			if (!tfEdition.getText().isEmpty() && !isNumeric(Edition)) {
				alert(AlertType.ERROR, "Can't add", "Edition must be number.");
				return;
			}

			if (!tfYear.getText().isEmpty() && !isNumeric(Year)) {
				alert(AlertType.ERROR, "Can't add", "Year must be number.");
				return;
			}

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();

			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();

			rs = s.executeQuery("SELECT 'B' FROM BOOK WHERE ISBN = " + ISBN + ";");
			if (rs.next()) {
				alert(AlertType.ERROR, "Can't add", "ISBN is already in use.");
				return;
			}

			executeStatement("insert into book values ('" + Title + "','" + Author + "','" + ISBN + "','" + Edition
					+ "','" + Publisher + "','" + Category + "','" + Year + "','" + dtf.format(now) + "','" + Country
					+ "');");

			Book b = new Book(Title, Author, Country, Category, Publisher, Long.parseLong(ISBN),
					Integer.parseInt(Edition), Integer.parseInt(Year), dtf.format(now));
			data.add(b);
			con.close();
			pAdd.setVisible(false);
			pMain.setVisible(true);
		} catch (Exception ex) {
			System.out.println("There are no matches");
		}

	}

	@FXML
	void Refresh(MouseEvent event) {
		TV.setItems(data);
		tfSearch.clear();
		cbSearch.setValue(null);
		cbSearch.setPromptText("Choose Category");
		TV.refresh();
	}

	@FXML
	void Back(MouseEvent event) {
		Stage stage = (Stage) cbSearch.getScene().getWindow();
		stage.close();
	}

	@FXML
	void searchBook() throws Exception {
		ObservableList<Book> temp = FXCollections.observableArrayList();
		try {
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			if (cbSearch.getValue().compareTo("ISBN") == 0) {
				long ISBN = Long.parseLong(tfSearch.getText());
				rs = s.executeQuery("select * from book where ISBN LIKE " + ISBN + ";");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			} else if (cbSearch.getValue().compareTo("Title") == 0) {
				String Title = tfSearch.getText();
				rs = s.executeQuery("select * from book where Title LIKE '%" + Title + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			} else if (cbSearch.getValue().compareTo("Author") == 0) {

				String Author = tfSearch.getText();
				rs = s.executeQuery("select * from book where Author LIKE '%" + Author + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}
			}

			else if (cbSearch.getValue().compareTo("Edition") == 0) {
				int Edition = Integer.parseInt(tfSearch.getText());
				rs = s.executeQuery("select * from book where Edition= " + Edition + ";");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}
			}

			else if (cbSearch.getValue().compareTo("Publisher") == 0) {
				String Publisher = tfSearch.getText();
				rs = s.executeQuery("select * from book where Publisher LIKE '%" + Publisher + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			} else if (cbSearch.getValue().compareTo("Section") == 0) {
				String Section = tfSearch.getText();
				rs = s.executeQuery("select * from book where Section LIKE '%" + Section + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			} else if (cbSearch.getValue().compareTo("Year") == 0) {
				int Year = Integer.parseInt(tfSearch.getText());
				rs = s.executeQuery("select * from book where YearOfProduction=" + Year + ";");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			} else if (cbSearch.getValue().compareTo("Country") == 0) {
				String Country = tfSearch.getText();
				rs = s.executeQuery("select * from book where Country LIKE '%" + Country + "%';");
				if (rs.next() == false) {
					alert(AlertType.INFORMATION, "Info", "There Are No Matches.");
					return;
				}
				temp.clear();
				rs.previous();
				while (rs.next()) {
					temp.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6),
							rs.getString(5), rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
				}

			}
			TV.setItems(temp);
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
	void removeBook() throws SQLException {
		Book b = TV.getSelectionModel().getSelectedItem();
		if (b == null) {
			alert(AlertType.INFORMATION, "Warning", "Please select the book you want to delete from the list");
			return;
		}
		Long ISBN = b.getISBN();
		Statement s = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		db();
		s = con.createStatement();
		rs = s.executeQuery("select isbn from borrow where isbn =" + ISBN + ";");
		s = con.createStatement();
		rs2 = s.executeQuery("select isbn from fine where isbn=" + ISBN + ";");
		if (!rs.next() && !rs2.next()) {
			data.remove(b);
			executeStatement("delete from book where isbn = " + ISBN);
		} else {
			alert(AlertType.WARNING, "Warning", "Can Not Delete, Book Is Borrowed Or Has A Fine");
		}
		TV.refresh();
		con.close();
	}

	public void getBook() {
		try {
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("select * from book;");
			while (rs.next()) {
				data.add(new Book(rs.getString(1), rs.getString(2), rs.getString(9), rs.getString(6), rs.getString(5),
						rs.getLong(3), rs.getInt(4), rs.getInt(7), rs.getString(8)));
			}
			con.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

	public void getStat() {
		try {
			Statement s = null;
			ResultSet rs = null;
			db();
			s = con.createStatement();
			rs = s.executeQuery("SELECT count(*) FROM BOOK;");
			rs.next();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex);
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

	public void executeStatement(String quere) {
		try {
			Statement s = con.createStatement();
			s.executeUpdate(quere);
			s.close();
			con.close();
		} catch (SQLException ex) {
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

	@FXML
	void exit() {
		System.exit(0);
	}
}
