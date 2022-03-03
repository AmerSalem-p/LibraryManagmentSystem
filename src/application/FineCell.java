package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;;

public class FineCell extends ListCell<Fine> {

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private GridPane gridPane;

	@Override
	protected void updateItem(Fine Fine, boolean empty) {
		super.updateItem(Fine, empty);
		if (empty) {
			setText(null);
			setGraphic(null);

		} else {
			setText(null);
			Text SID = new Text(String.valueOf(Fine.getSID()));
			Text ISBN = new Text(String.valueOf(Fine.getISBN()));
			Text Amount = new Text(String.valueOf(Fine.getAmount()));

			GridPane gp = new GridPane();

			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(20);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(50);
			ColumnConstraints col3 = new ColumnConstraints();
			col3.setPercentWidth(30);

			gp.getColumnConstraints().addAll(col1, col2, col3);

			gp.add(SID, 0, 0);
			gp.add(ISBN, 1, 0);
			gp.add(Amount, 2, 0);

			setGraphic(gp);
		}
	}
}