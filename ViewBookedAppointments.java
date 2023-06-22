package application;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
public class ViewBookedAppointments implements Initializable {

	@FXML
    private TableColumn<Appointment, String> Date;

    @FXML
    private TableColumn<Appointment, String> Description;

    @FXML
    private TableColumn<Appointment, String> Location;

    @FXML
    private TableColumn<Appointment, String> Title;

    @FXML
    private TableColumn<Appointment, String> Type;

    @FXML
    private TableView<Appointment> appointmentTableView;
	 
	
    @FXML
    public Button backtomenu;

    @FXML
    public ToggleGroup reportViewTgl;
	
    
    private Connection conn;
    
    private ObservableList<Appointment>list;
    
    //DbDatabase is the connection class
    private DbDatabase dbDataBase;
	

	

public void backToMenu(ActionEvent event) throws IOException {
    Main m = new Main();

    m.changeScene("MainMenu.fxml");

}





@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	 

	dbDataBase = new DbDatabase();
	populateTableView();
	
}


private void populateTableView() {
	try {
	//Instantiate list
	list = FXCollections.observableArrayList();
	
	//Select query string
	String query = ("SELECT * FROM testing");
	
	//Run query and put results  in resultSet
	conn =dbDataBase.getConnection();
	ResultSet set = conn.createStatement().executeQuery(query);
	
	
	//Loop through resultset, exract data and append it to our list
	
	
	while(set.next()) {
		
		//Create a Appointment object, add data to it and finally append it to list
		
		Appointment appointment = new Appointment();
		appointment.setTitle(set.getString("title"));
		appointment.setDescription(set.getString("description"));
		appointment.setLocation(set.getString("location"));
		appointment.setType(set.getString("type"));
		appointment.setDate(set.getString("calendardate"));
		
		list.add(appointment);
		
	}
	
	//Set property to tableview columns
	//Use the same property that is in Appointment class
	Title.setCellValueFactory(new PropertyValueFactory<>("title"));
	Description.setCellValueFactory(new PropertyValueFactory<>("description"));
	Location.setCellValueFactory(new PropertyValueFactory<>("location"));
	Type.setCellValueFactory(new PropertyValueFactory<>("type"));
	Date.setCellValueFactory(new PropertyValueFactory<>("date"));
	
	//set data to tableview
	appointmentTableView.setItems(list);
	
	
	
} catch(SQLException ex) {
	
	Logger.getLogger(ViewBookedAppointments.class.getName()).log(Level.SEVERE,null,ex);
}









}


}
