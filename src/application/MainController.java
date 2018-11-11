package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	@FXML
	private TextArea textArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
	}
	
	//Function for opening a file and appending the contents to the textArea
	public void SelectOpen(ActionEvent event) {
		
		FileChooser fc = new FileChooser();
		
		//Opens the filechooser in a specific directory
		fc.setInitialDirectory(
				new File ("C:\\Users\\Robin\\Documents"));
		//Adds a filter to only show PDF files
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TXT files", "*.txt"));
		//Variable for the chosen text file
		File selectedFile = fc.showOpenDialog(null);
		
		try {			
			//The chosen file is run through the readFile function and the text is added to the textarea
			textArea.clear();
			textArea.appendText(readFile(selectedFile));
			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void SelectSaveAs(ActionEvent event) {
		FileChooser fc = new FileChooser();
		
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TXT files", "*.txt"));
		File file = fc.showSaveDialog(null);
		
		if(file != null) {
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
	
	//Saves the content of the textArea to a text file
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
}











