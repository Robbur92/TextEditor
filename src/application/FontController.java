package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FontController implements Initializable {
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ListView<String> fontListView;
	@FXML
	private ListView<String> styleListView;
	@FXML
	private ListView<Integer> sizeListView;
	@FXML
	private TextField chosenFont;
	@FXML
	private TextField chosenStyle;
	@FXML
	private TextField chosenSize;
	@FXML
	private Label textExample;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		fontListView.getItems().clear();
		fontListView.getItems().addAll(
				"Helvetica",
				"Baskerville",
				"Times",
				"Arial",
				"Gotham"
				);
		styleListView.getItems().clear();
		styleListView.getItems().addAll(
				"Vanlig",
				"Kursiv",
				"Fet",
				"Fet kursiv"
				);
		
		sizeListView.getItems().clear();
		sizeListView.getItems().addAll(
				8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72
				);
		fontListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				chosenFont.clear();
				chosenFont.appendText(fontListView.getSelectionModel().getSelectedItem());
			}
		});
		styleListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				chosenStyle.clear();
				chosenStyle.appendText(styleListView.getSelectionModel().getSelectedItem());
			}
		});
		sizeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				chosenSize.clear();
				chosenSize.appendText(sizeListView.getSelectionModel().getSelectedItem().toString());
			}
		});
	}
	
	public void CloseFontWindow(ActionEvent event) {
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();	
	}
}
