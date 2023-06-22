package application;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;




public class DeleteAppointment implements Initializable {
	
	@FXML
	private Button returnButton;
	
	public void returnButtonMeth(ActionEvent apple) throws IOException {
		
		Main banana = new Main();
		banana.changeScene("MainMenu.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
