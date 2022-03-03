package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeMenuController implements Initializable {

	@FXML
	private Text tEmpId;

	@FXML
	private Text tEmpName;

	@FXML
	private Text tTime;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tEmpName.setText(Employee.Curr_Employee.getEName());
		tEmpName.setWrappingWidth(150);
		tEmpId.setText(String.valueOf(Employee.Curr_Employee.getEID()));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime now = LocalTime.now();
		tTime.setText(dtf.format(now).toString());
	}

	@FXML
	void exit(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void openBooks(MouseEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("books.fxml"));
			Scene scene = new Scene(root, 1000, 540);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Books");
			stage.getIcons().add(new Image("images/icon.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex2) {
			System.out.println(ex2);
		}
	}

	@FXML
	void openSubscribers(MouseEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Subscribers.fxml"));
			Scene scene = new Scene(root, 800, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Subscribers");
			stage.getIcons().add(new Image("images/icon.png"));
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex2) {
			System.out.println(ex2);
		}
	}

	@FXML
	void openBorrow(MouseEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Borrow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Issue/Return");
			stage.getIcons().add(new Image("images/icon.png"));
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex2) {
			System.out.println(ex2);
		}
	}

	@FXML
	void openFines(MouseEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Fines.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Fines");
			stage.getIcons().add(new Image("images/icon.png"));
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex2) {
			System.out.println(ex2);
		}
	}

	@FXML
	void openPassword(MouseEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Password.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Change Password");
			stage.getIcons().add(new Image("images/icon.png"));
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex2) {
			System.out.println(ex2);
		}
	}
}
