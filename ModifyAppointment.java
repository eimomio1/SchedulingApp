package application;



import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;




public class ModifyAppointment implements Initializable {
	
	@FXML
	private Button mainmenu;
	
	public void backToMenu(ActionEvent snickers) throws IOException {
		
		Main object = new Main();
		object.changeScene("MainMenu.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}