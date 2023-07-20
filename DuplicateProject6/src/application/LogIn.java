package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogIn {

    public LogIn() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    @FXML
    private Button signupButton;

    public void userLog(ActionEvent event) throws IOException {
        if (validateFields()) {
            checkLogin();
        } else {
            displayErrorMessage("Error", "Empty Fields", "Please enter a username and password.");
        }
    }

    @FXML
    void userSignUp(ActionEvent event) throws IOException {

        Main m = new Main();
        m.changeScene("RegistrationForm.fxml");
    }

    private boolean validateFields() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    private void checkLogin() throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "1234");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM records WHERE username = '" + enteredUsername + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (enteredPassword.equals(storedPassword)) {
                    // Password matches, login successful
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + enteredUsername + "!");
                    alert.showAndWait();
                    Main m = new Main();
                    m.changeScene("SelectionMenu.fxml");
                    // Perform further actions for a successful login
                    // ...

                } else {
                    // Incorrect password
                    displayErrorMessage("Login Error", "Incorrect password", "Please enter the correct password.");
                }
            } else if (username.getText().equals("admin") && password.getText().equals("admin123")) {
                Main m = new Main();
                wrongLogIn.setText("Success!");

                m.changeScene("AdminMenu.fxml");

            } else {
                // User not found
                displayErrorMessage("Login Error", "User not found", "Please enter a valid username.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayErrorMessage(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}