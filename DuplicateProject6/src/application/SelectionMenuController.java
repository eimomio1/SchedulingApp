package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SelectionMenuController {

    @FXML
    private Button buttonOne;

    @FXML
    private Button buttonTwo;

    @FXML
    private Label wrongLogIn;

    
    @FXML
    private Button logout;

    @FXML
    void redirectToLogout(ActionEvent event) throws IOException {

    	Main m = new Main();
    	m.changeScene("sample.fxml");
    }
    
    @FXML
    void redirectToBooking(ActionEvent event) throws IOException {

    	Main m = new Main();
    	m.changeScene("UserViewBooking.fxml");
    }

    @FXML
    void redirectToSchedule(ActionEvent event) throws IOException {
    	

    	Main m = new Main();
    	m.changeScene("UserViewSchedule.fxml");

    }

}
