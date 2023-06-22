package application;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	  private Button cancelButton;
	  
	  @FXML
	  private Button savebutton;
	  
	  
	  
	  

	    @FXML
	    private ChoiceBox<String> choiceBoxOne;

	    @FXML
	    private ChoiceBox<String> choiceBoxTwo;
	    
	  
	    
	    
	    
	  
	  private String calendarDate;
	  
	  private String title;
	    
	  private String description;
	    
	  private String location;

	  private String type;
	  

	  
	  
	  
	  private void gatherInfo() { //This method gathers data from Text Fields, Combo Boxes, etc..
		  
		
	        
	        
		
		  setCalendarDate(String.valueOf(datePickerBox.getValue()));
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


	public void buttonSaveScheduleAppointment(ActionEvent event) throws IOException { //Save to sql database
		 
		String url = "jdbc:postgresql://localhost:5432/loginForm";
	        String user = "postgres";
	        String password = "retrate12";

		
		 if (checkIfInfoIsCorrect()) {
		        gatherInfo();
		        
		        
		        String query = "INSERT INTO testing(title, description, location, type, calendarDate) VALUES(?, ?, ?, ?, ?)";

		        try (Connection con = DriverManager.getConnection(url,user,password);
		             PreparedStatement pst = con.prepareStatement(query)) {

		            pst.setString(1, title);
		            pst.setString(2, description);
		            pst.setString(3, location);
		            pst.setString(4, type);
		            pst.setString(5, calendarDate);
		           
		            // Set other column values

		            pst.executeUpdate();
		            
		            Main m = new Main();
		            m.changeScene("MainMenu.fxml");
		            System.out.println("Successfully inserted into the database.");

		        } catch (SQLException ex) {
		        	 Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);
		        }
		        
		 }
		
		
		
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

	public String getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(String calendarDate) {
		this.calendarDate = calendarDate;
	}

	


	  
	  
	  
	  
	 
	  
	
	  
	
	

}