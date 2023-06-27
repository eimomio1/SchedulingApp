package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class FXMLDocumentController implements Initializable {
    
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
    private TableView<Appointment> table;
   
    @FXML
    private TableColumn<Appointment, String> colDate;

    @FXML
    private TableColumn<Appointment, Integer> colId;

    @FXML
    private TableColumn<Appointment, String> colLocation;

    @FXML
    private TableColumn<Appointment, String> colDescrip;

    @FXML
    private TableColumn<Appointment, String> colTitle;
    
    @FXML
    private TableColumn<Appointment, String> colStart;
    
    @FXML
    private TableColumn<Appointment, String> colEnd;
    
    @FXML
    private TableColumn<Appointment, String> colName;
    
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
        showBooks();
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
    
    public ObservableList<Appointment> getAppointmentsList(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM appointments";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Appointment appointmentVar;
            while(rs.next()){
      appointmentVar = new Appointment(rs.getInt("id"), rs.getString("description"), rs.getString("location"), rs.getString("title"),rs.getString("date"), rs.getString("starttime"),rs.getString("endtime"),rs.getString("name"));
            	appointmentList.add(appointmentVar);
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return appointmentList;
    }
    
    public void showBooks(){
        ObservableList<Appointment> list = getAppointmentsList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        colDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
        colStart.setCellValueFactory(new PropertyValueFactory<Appointment, String>("starttime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endtime"));
        colName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("name"));
        
        table.setItems(list);
    }
    private void insertRecord(){

    	 String query = "INSERT INTO appointments VALUES (" + tfId.getText() + ",'" + tfDescript.getText() + "','" + tfLocation.getText() + "','"
    	                + tfTitle.getText() + "','" + tfDate.getValue() + "','" +tfStart.getValue() + "','" +tfEnd.getValue() + "','" +tfName.getText() + "'" + ")";

        executeQuery(query);
        showBooks();
    }
    private void updateRecord(){
        String query = "UPDATE  appointments SET  description  = '" + tfDescript.getText() + "', location = '" + tfLocation.getText() + "', title = '" +
                tfTitle.getText() + "', date = '" + tfDate.getValue() + "', starttime = '" + tfStart.getValue() + "', endtime = '" +tfEnd.getValue() + "', name = '" + tfName.getText() +
                "' WHERE id = " + tfId.getText() + "";
        executeQuery(query);
        showBooks();
    }
    private void deleteButton(){
        String query = "DELETE FROM appointments WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showBooks();
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