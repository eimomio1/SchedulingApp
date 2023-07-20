package application;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleAppointUserScheduling {

    private Connection connection;

    @BeforeClass
    public static void setUpClass() {
        // Initialize the JavaFX toolkit
        new JFXPanel();
    }

    @Before
    public void setUp() throws SQLException {
        // Set up the database connection
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "1234";
        connection = DriverManager.getConnection(dbUrl, username, password);
    }

    @Test
    public void testScheduleAppointment() throws SQLException {
        // Prepare test data
        String selectedContact = "Shedletsky";
        String selectedDate = "2023-07-12";
        String selectedTimeRange = "5:15 PM - 6:45 PM";
        String email = "imomiom627@gmail.com";

        // Create an instance of UserViewSchedule
        UserViewSchedule userViewSchedule = new UserViewSchedule();

        // Manually initialize the necessary fields
        userViewSchedule.tfNameSc = new ComboBox<>();
        userViewSchedule.tfDateSc = new ComboBox<>();
        userViewSchedule.tfStartEndSc = new ComboBox<>();
        userViewSchedule.tfEmail = new TextField();

        userViewSchedule.tfNameSc.getSelectionModel().select(selectedContact);
        userViewSchedule.tfDateSc.getSelectionModel().select(selectedDate);
        userViewSchedule.tfStartEndSc.getSelectionModel().select(selectedTimeRange);
        userViewSchedule.tfEmail.setText(email);

        // Call the handleSchedule method on the JavaFX application thread
        Platform.runLater(() -> userViewSchedule.handleSchedule(null));

        // Wait for JavaFX operations to complete
        try {
            Thread.sleep(7000); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the appointment is scheduled and matches the most recent entry in the user_appointments table
        assertTrue(isAppointmentScheduled(selectedContact, email, selectedDate, selectedTimeRange));
    }

    private boolean isAppointmentScheduled(String selectedContact, String email, String selectedDate, String selectedTimeRange) throws SQLException {
        // Retrieve the matching ID from the appointments table
        String selectIdQuery = "SELECT id FROM appointments WHERE name = ? AND date = ? AND starttime = ? AND endtime = ?";
        PreparedStatement selectIdStatement = connection.prepareStatement(selectIdQuery);
        selectIdStatement.setString(1, selectedContact);
        selectIdStatement.setString(2, selectedDate);
        selectIdStatement.setString(3, selectedTimeRange.split(" - ")[0]);
        selectIdStatement.setString(4, selectedTimeRange.split(" - ")[1]);
        ResultSet idResultSet = selectIdStatement.executeQuery();

        if (idResultSet.next()) {
            int id = idResultSet.getInt("id");

            // Check if the ID, contact, and email match the corresponding entry in the user_appointments table
            String selectQuery = "SELECT * FROM user_appointments WHERE user_id = ? AND name = ? AND email = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, id);
            selectStatement.setString(2, selectedContact);
            selectStatement.setString(3, email);
            ResultSet resultSet = selectStatement.executeQuery();

            return resultSet.next();
        }

        return false;
    }
}
