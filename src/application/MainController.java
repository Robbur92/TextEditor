package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	@FXML
	private Menu fileMenu;
	@FXML
	private TextArea textArea;
	@FXML
	private MenuItem newFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem saveFileAs;
	@FXML
	private MenuItem closeEditor;
	
	@FXML
	private Menu editMenu;
	@FXML
	private MenuItem undoText;
	@FXML
	private MenuItem cutText;
	@FXML
	private MenuItem copyText;
	@FXML
	private MenuItem pasteText;
	@FXML
	private MenuItem deleteText;
	@FXML
	private MenuItem markAllText;
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private Menu formatMenu;
	@FXML
	private MenuItem textFont;
	
	@FXML
	private Menu helpMenu;
	@FXML
	private MenuItem showHelp;
	@FXML 
	private MenuItem aboutEditor;
	
	private File openedFile;
	
	String copiedText = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	
	public void CreateNewFile(ActionEvent event) {
		
		textArea.clear();
		/*try {
			File file = new File(JOptionPane.showInputDialog("Name of textfile: ") + ".txt");
			
			if(!file.exists()) {
				file.createNewFile();
			}	
			
			
		}
		catch(IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}	*/
	}
	//Function for opening a file and appending the contents to the textArea
	public void OpenFile(ActionEvent event) {
		
		FileChooser fc = new FileChooser();
		
		//Opens the filechooser in a specific directory
		fc.setInitialDirectory(
				new File ("C:\\Users\\Robin\\Documents"));
		//Adds a filter to only show PDF files
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TXT files", "*.txt"));
		//Variable for the chosen text file
		File selectedFile = fc.showOpenDialog(null);
		openedFile = selectedFile;
		
		try {			
			//The chosen file is run through the readFile function and the text is added to the textarea
			textArea.clear();
			textArea.appendText(readFile(selectedFile));
			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void SaveFileAs(ActionEvent event) {
		FileChooser fc = new FileChooser();
		
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TXT files", "*.txt"));
		File file = fc.showSaveDialog(null);
		
		if(file != null) {
			UnaryOperator<Change> filter = c -> {
			    c.setText(c.getText().replaceAll("\r", "\n"));
			    return c ;
			};
			textArea.setTextFormatter(new TextFormatter<>(filter));
			SaveFile(textArea.getText(), file);
		}
	}
	
	//Function that reads the text file, saves it as a string and returns it
	private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String fileText;
            //Adds the text in the file to the fileText variable
            while ((fileText = bufferedReader.readLine()) != null) {
                stringBuffer.append(fileText);
            }
 
            //Catches exceptions
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
	
	//Saves the content of the textArea to a new text file
	public void SaveFile(String text, File file) {
		
		try {
			FileWriter fw;
			fw = new FileWriter(file);
			fw.write(text);
			fw.close();
		}
		catch(IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void SaveToCurrentFile(ActionEvent event) {
		try {
			UnaryOperator<Change> filter = c -> {
			    c.setText(c.getText().replaceAll("\r", "\n"));
			    return c ;
			};
			textArea.setTextFormatter(new TextFormatter<>(filter));
			FileWriter fw;
			if(openedFile == null) {
				JOptionPane.showMessageDialog(null, "Ingen fil er valgt, lagre som ny fil!","Alert",JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				fw = new FileWriter(openedFile);
				fw.write(textArea.getText());
				fw.close();
			}
			
		}
		catch(IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Opens the font window to change style, font and fontsize
	public void openFontWindow(ActionEvent event) {
		try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/FontWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Textformat");
            stage.setScene(new Scene(root1));  
            stage.show();
          }
		catch(Exception e) {
	           JOptionPane.showMessageDialog(null, "Error opening font window", "Font window error", 0);
	    }
	}
	
	public void DeleteSelected(ActionEvent event) {
		textArea.setText(textArea.getText().replace(textArea.getSelectedText(), ""));
	}
	
	public void CopySelectedText(ActionEvent event) {
		copiedText = textArea.getSelectedText();
	}
	
	public void PasteCopiedText(ActionEvent event) {
		textArea.appendText("" + copiedText);
	}
	
	public void CutSelectedText(ActionEvent event) {
		copiedText = textArea.getSelectedText();
		textArea.setText(textArea.getText().replace(textArea.getSelectedText(), ""));
	}
	
	public void SelectAllText(ActionEvent event) {
		textArea.selectAll();
	}
	
	public void CloseWindow(ActionEvent event) {
		Platform.exit();
	}
}











