package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


import javafx.scene.control.ToggleGroup;


public class ViewBookedAppointments implements Initializable {

	

    @FXML
    public Button backtomenu;

    @FXML
    public ToggleGroup reportViewTgl;
	
	

	

public void backToMenu(ActionEvent event) throws IOException {
    Main m = new Main();

    m.changeScene("MainMenu.fxml");

}





@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	
	
	
}
	
	

}
