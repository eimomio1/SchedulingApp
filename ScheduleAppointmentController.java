package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduleAppointmentController {
	  @FXML
	    private Button btnDelete;

	    @FXML
	    private Button btnSchedule;

	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private TableColumn<User, String> colDate;

	    @FXML
	    private TableColumn<User, String> colDescription;

	    @FXML
	    private TableColumn<User, String> colEnd;

	    @FXML
	    private TableColumn<User, Integer> colId;

	    @FXML
	    private TableColumn<User, String> colLocation;

	    @FXML
	    private TableColumn<User, String> colName;

	    @FXML
	    private TableColumn<User, String> colStart;

	    @FXML
	    private TableColumn<User, String> colTitle;

	    @FXML
	    private Button logout;

	    @FXML
	    private TableView<User> table;

	    @FXML
	    private ComboBox<String>tfDateSc;

	  

	    @FXML
	    private ComboBox<String> tfNameSc;

	    @FXML
	    private ComboBox<String> tfStartEndSc;

   
    
    public void userLogOut(ActionEvent event) throws IOException {
        Main m = new Main();
       
       
        m.changeScene("sample.fxml");
     

    }
    
    @FXML
    void handleContactSelection(ActionEvent event) {
        // Handle the contact selection event
        String selectedContact = tfNameSc.getValue();
        if (selectedContact != null) {
            ObservableList<String> availableDates = getAvailableDatesFromDatabase(selectedContact);
            tfDateSc.setItems(availableDates);
        }
    }

    @FXML
    void handleDateSelection(ActionEvent event) {
        // Handle the date selection event
        String selectedContact = tfNameSc.getValue();
        String selectedDate = tfDateSc.getValue();
        if (selectedContact != null && selectedDate != null) {
            ObservableList<String> availableTimes = getAvailableTimesFromDatabase(selectedContact);
            tfStartEndSc.setItems(availableTimes);
        }
    }


@FXML
    public void initialize() {
        // Populate the combo box with contact names from the database
        ObservableList<String> contactNames = getContactNamesFromDatabase();
        tfNameSc.setItems(contactNames);
     
        
       
    }

    private ObservableList<String> getContactNamesFromDatabase() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT name FROM appointments");

            while (resultSet.next()) {
                String contactName = resultSet.getString("name");
                contactNames.add(contactName);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactNames;
    }

    private ObservableList<String> getAvailableDatesFromDatabase(String contactName) {
        ObservableList<String> availableDates = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234");
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT date FROM appointments WHERE name = '" + contactName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                availableDates.add(date);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableDates;
    }

    private ObservableList<String> getAvailableTimesFromDatabase(String selectedContact) {
        ObservableList<String> availableTimes = FXCollections.observableArrayList();
        String selectedDate = tfDateSc.getValue();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234");
            Statement statement = connection.createStatement();
            String query = "SELECT starttime, endtime FROM appointments WHERE name = '" + selectedContact + "' AND date = '" + selectedDate + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String startTime = resultSet.getString("starttime");
                String endTime = resultSet.getString("endtime");
                String timeRange = startTime + " - " + endTime;
                availableTimes.add(timeRange);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableTimes;
    }

    @FXML
    void handleSchedule(ActionEvent event) {
        
    }
    
  
    

    
}