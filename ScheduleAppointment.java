package application;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;


     //Adds Schedule to the Main Menu
  public class ScheduleAppointment implements Initializable {
	 
	
	@FXML
	  private TextField titleField;
	  @FXML
	  private TextField descriptionField;
	  @FXML
	  private TextField locationField;
	  @FXML
	  private TextField descriptionField1;
	  
	  @FXML
	    private DatePicker datePickerBox;
	  
	  @FXML
	  private ComboBox startComboBox;
	  
	  @FXML
	  private Button cancelButton;
	  
	  @FXML
	  private Button savebutton;
	  
	  
	  
	  
	  
	  
	  private String title;
	    
	  private String description;
	    
	  private String location;

	  private String type;
	  
	  
	  private void gatherInfo() { //This method gathers data from Text Fields, Combo Boxes, etc..
	       
	        title = titleField.getText().trim();
	        description = descriptionField.getText().trim();
	        location = locationField.getText().trim();
	        type = descriptionField1.getText().trim();
	       
	       //
	        
	        
	   
	    }
	   
	  /** validateInformation. Validates all the users input information. Gathers all information. If the fields are blank or null
	     * displays and alert warning the user to correct them. If the start time is after then end time displays an alert telling the user to correct them.
	     * if the times are invalid when compared to business hours, displays alert for the user to correct them. Returns true
	     * and sets start and end times if all validations are passed.
	     * @return boolean. False if validations are not passed. True otherwise. */
	  
	  
	  
	
	  
	 
	  
	  
	 
	

	
	public void initialize(URL arg0, ResourceBundle arg1) { //Tells each column what its gonna take from object for user
		
		
	}
	  
	
	 public void cancelButtonThatReturnsToMainMenu(ActionEvent event) throws IOException {
	        Main m = new Main();
	  
	        m.changeScene("MainMenu.fxml");

	    }


	public void buttonSaveScheduleAppointment(ActionEvent event) {
		
		
		
		
		
		
	}
	
	
	  private boolean checkIfInfoIsCorrect() { 
			 
			
		  
			
	        gatherInfo();
	        if ( title.isBlank() || description.isBlank() || location.isBlank() ||
	              type.isBlank() ) {
	            Alert sendAlertForMissingInfo = new Alert(Alert.AlertType.ERROR);
	            
	            sendAlertForMissingInfo.setTitle("Blank or Missing Info");
	            sendAlertForMissingInfo.setContentText("Fill out every field!");
	            sendAlertForMissingInfo.showAndWait();
	            return false;
	        }
	        
	        else {
	        	
	        	return true;
	        }
	  
}
	  
	  
	  
	  
	  
	 
	  
	
	  
	
	

}
