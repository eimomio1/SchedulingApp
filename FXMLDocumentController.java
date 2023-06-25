package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class FXMLDocumentController implements Initializable {
    
	 @FXML
	    private DatePicker tfDate;

	    @FXML
	    private TextField tfDescript;

	    @FXML
	    private TextField tfId;

	    @FXML
	    private TextField tfLocation;

	    @FXML
	    private TextField tfType;
	    
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
    private TableColumn<Appointment, String> colType;
    
    @FXML
    private TableColumn<Appointment, String> colStart;
    
    @FXML
    private TableColumn<Appointment, String> colEnd;
    
    @FXML
    private TextField tfStart;
    
    @FXML
    private TextField tfEnd;
    
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {        
        
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
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
    }
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/loginForm", "postgres","retrate12");
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
            	appointmentVar = new Appointment(rs.getInt("id"), rs.getString("description"), rs.getString("location"), rs.getString("type"),rs.getString("date"), rs.getString("starttime"),rs.getString("endtime"));
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
        colType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
        colStart.setCellValueFactory(new PropertyValueFactory<Appointment, String>("starttime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endtime"));
        
        table.setItems(list);
    }
    private void insertRecord(){

    	 String query = "INSERT INTO appointments VALUES (" + tfId.getText() + ",'" + tfDescript.getText() + "','" + tfLocation.getText() + "','"
    	                + tfType.getText() + "','" + tfDate.getValue() + "','" +tfStart.getText() + "','" +tfEnd.getText() + "'" + ")";

        executeQuery(query);
        showBooks();
    }
    private void updateRecord(){
        String query = "UPDATE  appointments SET  description  = '" + tfDescript.getText() + "', location = '" + tfLocation.getText() + "', type = '" +
                tfType.getText() + "', date = '" + tfDate.getValue() + "', starttime = '" + tfStart.getText() + "', endtime = '" +tfEnd.getText() + "' WHERE id = " + tfId.getText() + "";
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
        
    
}