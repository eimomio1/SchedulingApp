package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ViewBookedAppointments {

	
	
	
private Button backtomenu;	
	

public void backToMenu(ActionEvent event) throws IOException {
    Main m = new Main();

    m.resizeScene("MainMenu.fxml");

}
	
	

}
