package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BorrowCell extends ListCell<Borrow> {

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private GridPane gridPane;

	@Override
	protected void updateItem(Borrow borrow, boolean empty) {
		super.updateItem(borrow, empty);

		if (empty) {
			setText(null);
			setGraphic(null);

		} else {
			setText(null);

			Text SID = new Text(String.valueOf(borrow.getSID()));
			Text ISBN = new Text(String.valueOf(borrow.getISBN()));
			Text BDate = new Text(borrow.getBorrowDate().toString());
			Text RDate = new Text(borrow.getRetDate().toString());

			GridPane gp = new GridPane();
			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(10);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(30);
			ColumnConstraints col3 = new ColumnConstraints();
			col3.setPercentWidth(30);
			ColumnConstraints col5 = new ColumnConstraints();
			col5.setPercentWidth(30);

			gp.getColumnConstraints().addAll(col1, col2, col3, col5);

			gp.add(SID, 0, 0);
			gp.add(ISBN, 1, 0);
			gp.add(BDate, 2, 0);
			gp.add(RDate, 3, 0);

			gp.setHgap(20);

			setGraphic(gp);

		}
	}
}