package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;  
import java.io.IOException;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


public class AfterLogin {

    @FXML
    private Button logout;
    
    @FXML
    private Button schedulebutton;
   
   
    
	  @FXML
	  private Button viewappointment;
	  
	  @FXML
	  private Button deleteButton;
	

	  public void hitDeleteButton(ActionEvent event) throws IOException {
	        Main m = new Main();
	  
	        m.changeScene("DeleteAppointment.fxml");

	    }
    
	
	
	
	  public void hitViewAppointment(ActionEvent event) throws IOException {
	        Main m = new Main();
	  
	        m.changeScene("ViewBookedAppointments.fxml");

	    }
    
    
    
    
    
    
   
//This method goes hand to hand with logout button on Main Menu Page and changes to the next scene

    
    
    
    
    
    
    
    
    
    
    public void userLogOut(ActionEvent event) throws IOException {
        Main m = new Main();
       
       
        m.changeScene("sample.fxml");
     

    }
    
  //This method goes hand to hand with Schedule Appointment button on Main Menu Page and changes to next scene
    
    public void hitScheduleButton(ActionEvent event) throws IOException {
        Main m = new Main();
  
        m.changeScene("ScheduleAppointment.fxml");

    }




	public void hitModifyAppointment(ActionEvent event) throws IOException {
		
		 Main m = new Main();
		  
	        m.changeScene("ModifyAppointment.fxml");
		
	}
    
    
    
}
