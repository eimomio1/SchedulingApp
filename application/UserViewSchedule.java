package application;
import javafx.scene.control.ButtonBar;
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

import javafx.scene.control.TextField;


import javafx.util.Pair;

import javafx.scene.control.ButtonType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javafx.scene.control.Dialog;


import java.util.Optional;

import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;


public class UserViewSchedule {
	  
	
	@FXML
	    private Button btnDelete;

	    @FXML
	    private Button btnSchedule;

	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button menuButton;

	    @FXML
	    private Button logout;

	    @FXML
	    private Button viewButton;

	    @FXML
	     ComboBox<String>tfDateSc;

	    @FXML
	     TextField tfEmail;

	    @FXML ComboBox<String> tfNameSc;

	    @FXML
	     ComboBox<String> tfStartEndSc;

	    
	    @FXML
	    void viewApptButton(ActionEvent event) {
	        // Create a dialog for entering ID# and name
	        Dialog<Pair<String, String>> dialog = new Dialog<>();
	        dialog.setTitle("View Appointment");
	        dialog.setHeaderText("Enter ID# and Name");

	        // Set the dialog content
	        GridPane grid = new GridPane();
	        grid.setHgap(10);
	        grid.setVgap(10);

	        TextField idField = new TextField();
	        idField.setPromptText("ID#");
	        TextField nameField = new TextField();
	        nameField.setPromptText("Name");

	        grid.add(new Label("ID#:"), 0, 0);
	        grid.add(idField, 1, 0);
	        grid.add(new Label("Name:"), 0, 1);
	        grid.add(nameField, 1, 1);

	        dialog.getDialogPane().setContent(grid);

	        // Add buttons to the dialog
	        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	        // Wait for user input and process it
	        dialog.setResultConverter(buttonType -> {
	            if (buttonType == ButtonType.OK) {
	                return new Pair<>(idField.getText(), nameField.getText());
	            }
	            return null;
	        });

	        // Show the dialog and process the entered ID# and name
	        dialog.showAndWait().ifPresent(idName -> {
	            String id = idName.getKey();
	            String name = idName.getValue();
	            displayAppointmentDetails(id, name);
	        });
	    }

	    public void displayAppointmentDetails(String id, String name) {
	        try {
	            int appointmentId = Integer.parseInt(id);

	            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
	                    "postgres", "1234");

	            // Check if the appointment exists in the user_appointments table
	            String checkQuery = "SELECT COUNT(*) FROM user_appointments WHERE user_id = ? AND name = ?";
	            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
	            checkStatement.setInt(1, appointmentId);
	            checkStatement.setString(2, name);
	            ResultSet checkResult = checkStatement.executeQuery();

	            int count = 0;
	            if (checkResult.next()) {
	                count = checkResult.getInt(1);
	            }

	            checkResult.close();
	            checkStatement.close();

	            if (count > 0) {
	                // Appointment exists in user_appointments table, proceed to retrieve details
	                String selectQuery = "SELECT description, location, title, date, starttime, endtime, name " +
	                        "FROM appointments WHERE id = ? AND name = ?";
	                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	                selectStatement.setInt(1, appointmentId);
	                selectStatement.setString(2, name);
	                ResultSet resultSet = selectStatement.executeQuery();

	                if (resultSet.next()) {
	                    // Display the appointment details
	                    String description = resultSet.getString("description");
	                    String location = resultSet.getString("location");
	                    String title = resultSet.getString("title");
	                    String date = resultSet.getString("date");
	                    String startTime = resultSet.getString("starttime");
	                    String endTime = resultSet.getString("endtime");
	                    String appointmentName = resultSet.getString("name");

	                    String details = "Appointment Details:\n" +
	                            "ID#: " + id + "\n" +
	                            "Name: " + name + "\n" +
	                            "Description: " + description + "\n" +
	                            "Location: " + location + "\n" +
	                            "Title: " + title + "\n" +
	                            "Date: " + date + "\n" +
	                            "Start Time: " + startTime + "\n" +
	                            "End Time: " + endTime + "\n" +
	                            "Appointment Name: " + appointmentName;

	                    showAlert("Appointment Details", details);
	                } else {
	                    showAlert("Error", "No matching appointment found");
	                }

	                resultSet.close();
	                selectStatement.close();
	            } else {
	                showAlert("Error", "No matching appointments available");
	            }

	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            showAlert("Error", "An error occurred while retrieving appointment details");
	        } catch (NumberFormatException e) {
	            showAlert("Error", "Invalid appointment ID");
	        }
	    }

	    
	    
	    
	    @FXML
	    void backToMenu(ActionEvent event) throws IOException {

	    	Main m = new Main();
	    	m.changeScene("SelectionMenu.fxml");
	    	
	    }
	    
	    
	    @FXML
	    void clickRescheduleButton(ActionEvent event) {
	        // Create a dialog box to prompt the user for ID and name
	        Dialog<Pair<String, String>> dialog = new Dialog<>();
	        dialog.setTitle("Enter Appointment Details");
	        dialog.setHeaderText("Please enter the ID and name of the appointee.");

	        // Set the button types
	        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

	        // Create text fields for ID and name
	        TextField idTextField = new TextField();
	        idTextField.setPromptText("ID");
	        TextField nameTextField = new TextField();
	        nameTextField.setPromptText("Name");

	        // Set the dialog content
	        GridPane grid = new GridPane();
	        grid.add(new Label("ID:"), 0, 0);
	        grid.add(idTextField, 1, 0);
	        grid.add(new Label("Name:"), 0, 1);
	        grid.add(nameTextField, 1, 1);
	        dialog.getDialogPane().setContent(grid);

	        // Convert the result to a pair of ID and name when confirm is clicked
	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == confirmButton) {
	                return new Pair<>(idTextField.getText(), nameTextField.getText());
	            }
	            return null;
	        });

	        // Wait for the user to enter ID and name
	        Optional<Pair<String, String>> result = dialog.showAndWait();

	        if (result.isPresent()) {
	            Pair<String, String> appointmentDetails = result.get();
	            String id = appointmentDetails.getKey();
	            String name = appointmentDetails.getValue();

	            // Check if the ID and name are not empty
	            if (id.isEmpty() || name.isEmpty()) {
	                // Display an error message if ID or name is missing
	                showErrorMessage("Please enter the ID and name of the appointee.");
	                return;
	            }

	            try {
	                // Create a database connection
	                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
	                        "postgres", "1234");

	                // Check if the appointment exists in the appointments table
	                PreparedStatement appointmentQuery = connection.prepareStatement(
	                        "SELECT id FROM appointments WHERE id = ? AND name = ?");
	                appointmentQuery.setInt(1, Integer.parseInt(id));
	                appointmentQuery.setString(2, name);
	                ResultSet appointmentResult = appointmentQuery.executeQuery();

	                if (!appointmentResult.next()) {
	                    // No appointment found, display an error message
	                    showErrorMessage("No appointment found for the specified ID and name.");
	                    appointmentResult.close();
	                    appointmentQuery.close();
	                    connection.close();
	                    return;
	                }

	                // Retrieve the available dates and times for the appointee
	                PreparedStatement availabilityQuery = connection.prepareStatement(
	                        "SELECT id, date, starttime FROM appointments WHERE name = ?");
	                availabilityQuery.setString(1, name);
	                ResultSet availabilityResult = availabilityQuery.executeQuery();

	                if (!availabilityResult.next()) {
	                    // No available appointments found, display an error message
	                    showErrorMessage("I'm sorry, all appointments for this appointee are already booked.");
	                    availabilityResult.close();
	                    availabilityQuery.close();
	                    appointmentResult.close();
	                    appointmentQuery.close();
	                    connection.close();
	                    return;
	                }

	                // Create a ComboBox to display the available dates and times
	                ComboBox<String> comboBox = new ComboBox<>();
	                do {
	                    int appointmentId = availabilityResult.getInt("id");
	                    String date = availabilityResult.getString("date");
	                    String time = availabilityResult.getString("starttime");
	                    comboBox.getItems().add(appointmentId + ": " + date + " " + time);
	                } while (availabilityResult.next());

	                // Create a dialog box to prompt the user to select a date and time
	                Dialog<String> timeDialog = new Dialog<>();
	                timeDialog.setTitle("Select Appointment Time");
	                timeDialog.setHeaderText("Please select a date and time for the appointment.");

	                ButtonType confirmTimeButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
	                timeDialog.getDialogPane().getButtonTypes().addAll(confirmTimeButton, ButtonType.CANCEL);

	                timeDialog.getDialogPane().setContent(comboBox);

	                // Convert the result to the selected appointment ID when confirm is clicked
	                timeDialog.setResultConverter(timeDialogButton -> {
	                    if (timeDialogButton == confirmTimeButton) {
	                        return comboBox.getSelectionModel().getSelectedItem();
	                    }
	                    return null;
	                });

	                // Wait for the user to make a selection
	                Optional<String> timeResult = timeDialog.showAndWait();

	                if (timeResult.isPresent()) {
	                    String selectedAppointment = timeResult.get();
	                    String[] appointmentParts = selectedAppointment.split(": ");
	                    int selectedAppointmentId = Integer.parseInt(appointmentParts[0]);

	                    // Check if the selected appointment ID is already scheduled in user_appointments table
	                    PreparedStatement checkQuery = connection.prepareStatement(
	                            "SELECT user_id FROM user_appointments WHERE user_id = ?");
	                    checkQuery.setInt(1, selectedAppointmentId);
	                    ResultSet checkResult = checkQuery.executeQuery();

	                    if (checkResult.next()) {
	                        // Appointment is already scheduled, display an error message
	                        showErrorMessage("This appointment is already scheduled.");
	                        checkResult.close();
	                        checkQuery.close();
	                        availabilityResult.close();
	                        availabilityQuery.close();
	                        appointmentResult.close();
	                        appointmentQuery.close();
	                        connection.close();
	                        return;
	                    }

	                    // Update the user_appointments table with the new appointment ID
	                    PreparedStatement updateQuery = connection.prepareStatement(
	                            "UPDATE user_appointments SET user_id = ? WHERE user_id = ?");
	                    updateQuery.setInt(1, selectedAppointmentId);
	                    updateQuery.setInt(2, Integer.parseInt(id));
	                    updateQuery.executeUpdate();
	                    updateQuery.close();

	                    // Display a success message
	                    showInfoMessage("Appointment rescheduled successfully!");

	                    // Get the appointment details
	                    String appointmentDetailsText = getAppointmentDetails(selectedAppointmentId);

	                    // Send the confirmation email
	                    String recipientEmail = getEmailFromUserAppointments(selectedAppointmentId);
	                    sendEmail(recipientEmail, "Appointment Rescheduled", appointmentDetailsText);

	                    checkResult.close();
	                    checkQuery.close();
	                }

	                availabilityResult.close();
	                availabilityQuery.close();
	                appointmentResult.close();
	                appointmentQuery.close();
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                showErrorMessage("An error occurred while accessing the database.");
	            } catch (MessagingException e) {
	                e.printStackTrace();
	                showErrorMessage("Failed to send confirmation email. Appointment rescheduled successfully, but email could not be sent.");
	            }
	        }
	    }






	    private void showErrorMessage(String message) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    private void showInfoMessage(String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }


	    



    
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
        String selectedContact = tfNameSc.getValue();
        String selectedDate = tfDateSc.getValue();
        String selectedTimeRange = tfStartEndSc.getValue();
        String email = tfEmail.getText();

        if (selectedContact == null || selectedDate == null || selectedTimeRange == null || email.isEmpty()) {
            // Display an alert if any of the fields are not selected or if email is empty
            showAlert("Error", "Please select contact, date, and time range, and enter email");
            return;
        }

        String[] timeRangeParts = selectedTimeRange.split(" - ");
        String startTime = timeRangeParts[0];
        String endTime = timeRangeParts[1];

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234");

            // Retrieve the specific ID and name from the appointments table
            String selectQuery = "SELECT id, name, title, location, description FROM appointments WHERE name = ? AND date = ? AND starttime = ? AND endtime = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, selectedContact);
            selectStatement.setString(2, selectedDate);
            selectStatement.setString(3, startTime);
            selectStatement.setString(4, endTime);
            ResultSet resultSet = selectStatement.executeQuery();

            int appointmentId = -1;
            String appointmentName = "";
            String appointmentTitle = "";
            String appointmentLocation = "";
            String appointmentDescription = "";
            if (resultSet.next()) {
                appointmentId = resultSet.getInt("id");
                appointmentName = resultSet.getString("name");
                appointmentTitle = resultSet.getString("title");
                appointmentLocation = resultSet.getString("location");
                appointmentDescription = resultSet.getString("description");
            }

            resultSet.close();
            selectStatement.close();

            if (appointmentId != -1) {
                // Check if the entry already exists in the user_appointments table
                String checkQuery = "SELECT COUNT(*) FROM user_appointments WHERE user_id = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setInt(1, appointmentId);
                ResultSet checkResult = checkStatement.executeQuery();

                int count = 0;
                if (checkResult.next()) {
                    count = checkResult.getInt(1);
                }

                checkResult.close();
                checkStatement.close();

                if (count == 0) {
                    // Insert the data into the user_appointments table
                    String insertQuery = "INSERT INTO user_appointments (user_id, name, email) VALUES (?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setInt(1, appointmentId);
                    insertStatement.setString(2, appointmentName);
                    insertStatement.setString(3, email);
                    insertStatement.executeUpdate();
                    insertStatement.close();

                    // Send the email
                    try {
                        sendAppointmentConfirmationEmail(email, appointmentName, appointmentTitle, appointmentLocation, appointmentDescription, startTime, endTime, selectedDate, appointmentId);
                        showAlert("Success", "Appointment scheduled successfully!" + " \nEmail sent to: " + email);
                    } catch (Exception e) {
                        showAlert("Error", "Failed to send email. Appointment scheduled, but email could not be sent.");
                        e.printStackTrace();
                    }

                    
                } else {
                    showAlert("Error", "Duplicate entry. This appointment has already been scheduled.");
                }
            } else {
                showAlert("Error", "No matching appointment found");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while scheduling the appointment");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   
    private void sendAppointmentConfirmationEmail(String email, String name, String title, String location, String description, String startTime, String endTime, String date, int appointmentID) throws MessagingException {
        String subject = "Appointment Confirmation";
        String message = "You have successfully scheduled an appointment with " + name + "\n\n"
                + "Title: " + title + "\n"
                + "Location: " + location + "\n"
                + "Description: " + description + "\n"
                + "Start Time: " + startTime + "\n"
                + "End Time: " + endTime + "\n"
                + "Date of Appointment: " + date + "\n"
        + "Appointment ID (Make sure you remember this) : " + appointmentID + "\n";

        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Set up the sender's credentials
        String senderEmail = "ScheduleSketcher@gmail.com"; // Replace with your actual email address
        String senderPassword = "mtimdixwewqywtqe"; // Replace with your actual email password

        // Create a session with the sender's credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Create a new email message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(senderEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        // Send the email
        Transport.send(emailMessage);
    }
   
    @FXML
    void handleDelete(ActionEvent event) throws IOException {
    	
    	
    Main m = new Main();
    m.changeScene("DeleteAppointment.fxml");
    
    
    
   
    	
    	
    	
    }

    
    
   
    private String getAppointmentDetails(int appointmentId) {
        // Retrieve the appointment details from the appointments table
        String query = "SELECT name, title, location, description, starttime, endtime, date FROM appointments WHERE id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String title = resultSet.getString("title");
                String location = resultSet.getString("location");
                String description = resultSet.getString("description");
                String startTime = resultSet.getString("starttime");
                String endTime = resultSet.getString("endtime");
                String date = resultSet.getString("date");

                return "You have successfully rescheduled an appointment with " + name + "\n\n"
                        + "Title: " + title + "\n"
                        + "Location: " + location + "\n"
                        + "Description: " + description + "\n"
                        + "Start Time: " + startTime + "\n"
                        + "End Time: " + endTime + "\n"
                        + "Date of Appointment: " + date + "\n"
                        + "Appointment ID (Make sure you remember this) : " + appointmentId + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Failed to retrieve appointment details.";
    }

    private String getEmailFromUserAppointments(int appointmentId) {
        // Retrieve the email from the user_appointments table using the appointmentId
        String query = "SELECT email FROM user_appointments WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendEmail(String email, String subject, String message) throws MessagingException {
        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Set up the sender's credentials
        String senderEmail = "ScheduleSketcher@gmail.com"; // Replace with your actual email address
        String senderPassword = "mtimdixwewqywtqe"; // Replace with your actual email password

        // Create a session with the sender's credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Create a new email message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(senderEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        // Send the email
        Transport.send(emailMessage);
    }
   
   
    
    
    
}

