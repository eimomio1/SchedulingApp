package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RegistrationForm {

	 @FXML
	    private Button buttonTwo;

	    @FXML
	     TextField nameField;

	    @FXML
	     PasswordField passwordField;

	    @FXML
	    private Button submitButton;
	    
	    
	    String consoleOutput;

	    @FXML
	    void backToLogin(ActionEvent event) throws IOException {

	    	Main m = new Main();
	    	m.changeScene("sample.fxml");
	    	
	    	
	    }

    @FXML
    void clickRegistrationButton(ActionEvent event) throws IOException {

    	checkRegistrationForm();
    	
    	
    }
    
    void checkRegistrationForm() {
        String username = nameField.getText();
        String password = passwordField.getText();

        // Prompt the user to re-enter the password
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Password Verification");
        passwordDialog.setHeaderText(null);
        passwordDialog.setContentText("Please re-enter your password:");

        Optional<String> result = passwordDialog.showAndWait();
        if (result.isPresent()) {
            String reenteredPassword = result.get();

            if (password.equals(reenteredPassword)) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "1234");
                    Statement statement = connection.createStatement();

                    // Check if the username already exists
                    String checkQuery = "SELECT * FROM records WHERE username = '" + username + "'";
                    ResultSet resultSet = statement.executeQuery(checkQuery);

                    if (resultSet.next()) {
                        // Username already exists, display an alert message
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Registration Error");
                        alert.setHeaderText("Username already exists");
                        alert.setContentText("Please choose a different username.");
                        alert.showAndWait();

                        // Close resources and return
                        resultSet.close();
                        statement.close();
                        connection.close();
                        return;
                    }

                    // Username is unique, proceed with registration
                    String insertQuery = "INSERT INTO records (username, password) VALUES ('" + username + "', '" + password + "')";
                    statement.executeUpdate(insertQuery);

                    statement.close();
                    connection.close();

                    Main m = new Main();
                    m.changeScene("sample.fxml");

                    System.out.println("Registration successful!");
                    consoleOutput = "Registration successful!";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Passwords do not match, display an alert message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Password Verification Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match. Please try again.");
                alert.showAndWait();
            }
        }
    }


    
    

}
