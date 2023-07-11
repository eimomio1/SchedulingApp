package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class UserViewBooking implements Initializable {
    
	@FXML
    private TextField tfName;
	
	
	 @FXML
	    private DatePicker tfDate;

	    @FXML
	    private TextField tfDescript;

	    @FXML
	    private TextField tfId;

	    @FXML
	    private TextField tfLocation;

	    @FXML
	    private TextField tfTitle;
	    
	    @FXML
	    private Button logout;
	    
	    @FXML
	    private Button menuButton;
	    
    
    @FXML
    private ComboBox<String> tfStart;
    
    @FXML
    private ComboBox<String> tfEnd;
    
    
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    
    
    
    private String calendarDate;
	  
	  
	    
	  private String description;
	    
	  private String location;

	  private String title;
	  
	  
	  
	  private String id;
	  
	  
	  private String contactName;
    
    
	  private String  startingTime;
	    private String endingTime;
    
    
    
    
    
    
    
	    @FXML
	    void backToMenu(ActionEvent event) throws IOException {
Main m = new Main();
m.changeScene("SelectionMenu.fxml");
	    }
	    

    
    @FXML
    private void handleButtonAction(ActionEvent event) {        
        
    if(checkIfInfoIsCorrect())	{
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
            
        }
         if(event.getSource() == btnDelete){
            deleteButton();
        }
    	  
    }
    
    }
    public void userLogOut(ActionEvent event) throws IOException {
        Main m = new Main();
       
       
        m.changeScene("sample.fxml");
     

    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   
        populateTimeComboBoxes();
        
        
    }
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","1234");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    
    public ObservableList<Admin> getAppointmentsList(){
        ObservableList<Admin> appointmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM appointments";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Admin appointmentVar;
            while(rs.next()){
      appointmentVar = new Admin(rs.getInt("id"), rs.getString("description"), rs.getString("location"), rs.getString("title"),rs.getString("date"), rs.getString("starttime"),rs.getString("endtime"),rs.getString("name"));
            	appointmentList.add(appointmentVar);
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return appointmentList;
    }
    
   
    private void insertRecord() {
        String selectedStartTime = tfStart.getValue();
        String selectedEndTime = tfEnd.getValue();

        DateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

        try {
            Date startTime = timeFormat.parse(selectedStartTime);
            Date endTime = timeFormat.parse(selectedEndTime);

            if (startTime.after(endTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Time Selection");
                alert.setHeaderText(null);
                alert.setContentText("Start time cannot be after end time!");
                alert.showAndWait();
                return;
            }

            Connection conn = getConnection();
            Statement st = conn.createStatement();

            // Check for overlapping or conflicting appointments
            String query = "SELECT * FROM appointments WHERE name = '" + tfName.getText() + "' AND date = '" + tfDate.getValue() + "'";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Date existingStartTime = timeFormat.parse(rs.getString("starttime"));
                Date existingEndTime = timeFormat.parse(rs.getString("endtime"));

                if (!(endTime.before(existingStartTime) || startTime.after(existingEndTime))) {
                    // Overlapping or conflicting appointment found
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment Conflict");
                    alert.setHeaderText(null);
                    alert.setContentText("The appointment time conflicts with an existing appointment for the same contact!");
                    alert.showAndWait();
                    return;
                }
            }

            // No conflicts found, proceed with inserting the appointment
            query = "INSERT INTO appointments VALUES (" + tfId.getText() + ",'" + tfDescript.getText() + "','" + tfLocation.getText() + "','"
                    + tfTitle.getText() + "','" + tfDate.getValue() + "','" + tfStart.getValue() + "','" + tfEnd.getValue() + "','" + tfName.getText() + "'" + ")";
            st.executeUpdate(query);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Booked");
            alert.setHeaderText(null);
            alert.setContentText("The appointment has been successfully booked!");
            alert.showAndWait();

        

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord(){
    	
    	String selectedStartTime = tfStart.getValue();
	    String selectedEndTime = tfEnd.getValue();

	    DateFormat timeFormat = new SimpleDateFormat("h:mm a");

	    try {
	        Date startTime = timeFormat.parse(selectedStartTime);
	        Date endTime = timeFormat.parse(selectedEndTime);

	        if (startTime.after(endTime)) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Invalid Time Selection");
	            alert.setHeaderText(null);
	            alert.setContentText("Start time cannot be after end time!");
	            alert.showAndWait();
	            return;
	        }
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
        String query = "UPDATE  appointments SET  description  = '" + tfDescript.getText() + "', location = '" + tfLocation.getText() + "', title = '" +
                tfTitle.getText() + "', date = '" + tfDate.getValue() + "', starttime = '" + tfStart.getValue() + "', endtime = '" +tfEnd.getValue() + "', name = '" + tfName.getText() +
                "' WHERE id = " + tfId.getText() + "";
        executeQuery(query);
     
    }
    private void deleteButton(){
        String query = "DELETE FROM appointments WHERE id =" + tfId.getText() + "";
        executeQuery(query);
     
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
   
   
   
    
    
    
    
    
    
    private boolean checkIfInfoIsCorrect() { 
		 
    	
    	
		
        gatherInfo();
        

   	 
		  
		  
        if ( id.isBlank() ||description.isBlank() || location.isBlank() ||
              title.isBlank()||calendarDate.isBlank() || startingTime.isBlank() || endingTime.isBlank() || contactName.isBlank() ) {
            Alert sendAlertForMissingInfo = new Alert(Alert.AlertType.ERROR);
            
            sendAlertForMissingInfo.setTitle("Blank or Missing Info");
            sendAlertForMissingInfo.setContentText("Fill out every field!");
            sendAlertForMissingInfo.showAndWait();
            return false;
        }
        
        else  {
        	
        	
        	
        	return true;
        }
  
}
    
    
    private void gatherInfo() { //This method gathers data from Text Fields, Combo Boxes, etc..
		  
		
		 
		  endingTime = (String.valueOf(tfEnd.getValue()));
	       startingTime = (String.valueOf(tfStart.getValue()));
		  calendarDate = (String.valueOf(tfDate.getValue()));
	     id = tfId.getText().trim();
	        description = tfDescript.getText().trim();
	        location = tfLocation.getText().trim();
	        title = tfTitle.getText().trim();
	        contactName = tfName.getText().trim();
	        
	        
	      
	   
	    }
    
    private void populateTimeComboBoxes() {
        // Populate the start time combo box
    	for (int hour = 1; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String time = String.format("%d:%02d PM", hour, minute);
                tfStart.getItems().add(time);
            }
        }

        // Populate the end time combo box
        for (int hour = 1; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String time = String.format("%d:%02d PM", hour, minute);
                tfEnd.getItems().add(time);
            }
        }
    }
    
    
  
	   
        
    
}