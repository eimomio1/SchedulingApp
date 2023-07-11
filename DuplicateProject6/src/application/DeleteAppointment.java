package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.ButtonType;

import java.util.Optional;



public class DeleteAppointment {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    void clickDeleteButton(ActionEvent event) {
        String enteredID = idField.getText();
        String enteredName = nameField.getText();
        int realID = Integer.parseInt(enteredID);
        

        if (!enteredID.isEmpty() && !enteredName.isEmpty()) {
            try {
                Connection connection = getConnection(); // Obtain the database connection
                String selectQuery = "SELECT email FROM user_appointments WHERE user_id = ? AND name = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setInt(1, realID);
                selectStatement.setString(2, enteredName);

                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    String email = resultSet.getString("email");

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete the appointment?");
                    confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        String deleteQuery = "DELETE FROM user_appointments WHERE user_id = ? AND name = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                        deleteStatement.setInt(1, realID);
                        deleteStatement.setString(2, enteredName);

                        int rowsDeleted = deleteStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            showAlert("Success", "Appointment deleted successfully");

                            // Send cancellation email
                            String senderEmail = "ScheduleSketcher@gmail.com";
                            String senderPassword = "mtimdixwewqywtqe";
                            sendAppointmentCancellationEmail(email, enteredName, enteredID, senderEmail, senderPassword);
                        } else {
                            showAlert("Error", "Failed to delete appointment");
                        }
                    }
                } else {
                    showAlert("Error", "Appointment not found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while deleting the appointment");
            }
        } else {
            showAlert("Error", "No appointment selected");
        }
    }


    private Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "1234";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void sendAppointmentCancellationEmail(String recipientEmail, String name, String id, String senderEmail, String senderPassword) {
        String subject = "Appointment Cancellation";
        String message = "Please refrain from responding to this email as your appointment with " + name + " (ID: " + id + ") has been cancelled. Any replies to this message will be directed to an unmonitored mailbox.";

        // Set up email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new email message
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            // Send the email
            Transport.send(emailMessage);

            System.out.println("Cancellation email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send cancellation email to: " + recipientEmail);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("UserViewSchedule.fxml");
    }
}
