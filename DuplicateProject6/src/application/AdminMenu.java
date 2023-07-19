package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AdminMenu {
	 
	@FXML
	    private Button logoutButton;

    @FXML
    private Button buttonOne;

    @FXML
    private Label wrongLogIn;

   
    
    @FXML
    private Button buttonTwo;


    
    
    
    @FXML
    void redirectToAppt(ActionEvent event) throws IOException {

    	Main m = new Main();
    	m.changeScene("ScheduleAppointmentController.fxml");
    	
    	
    	
    	
    }
    
    
    @FXML
    void logoutNow(ActionEvent event) throws IOException {

    	Main m = new Main();
    	m.changeScene("sample.fxml");
    }
    
    @FXML
    void redirectToBookedAppt(ActionEvent event) throws IOException {

    	Main m = new Main();
    	m.changeScene("BookAppointmentController.fxml");
    }

}

