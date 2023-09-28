
package application;


import org.junit.Before;
import org.junit.Test;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.BeforeClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteAppointmentTestUserScheduling {

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
    public void testDeleteAppointment() throws SQLException {
        // Prepare test data
        String enteredID = "688";
        String enteredName = "tosen";
        int realID = Integer.parseInt(enteredID);

      

        // Create an instance of DeleteAppointment
        DeleteAppointment deleteAppointment = new DeleteAppointment();

        // Manually initialize the necessary fields
        deleteAppointment.idField = new TextField();
        deleteAppointment.nameField = new TextField();

        deleteAppointment.idField.setText(enteredID);
        deleteAppointment.nameField.setText(enteredName);

        // Call the clickDeleteButton method on the JavaFX application thread
        Platform.runLater(() -> deleteAppointment.clickDeleteButton(null));

        // Wait for JavaFX operations to complete
        try {
            Thread.sleep(4000); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the appointment is deleted from the database
        assertFalse(isAppointmentExists(realID, enteredName));
    }

    

    private boolean isAppointmentExists(int id, String name) throws SQLException {
        Statement statement = connection.createStatement();
        String selectQuery = "SELECT * FROM user_appointments WHERE user_id = " + id + " AND name = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(selectQuery);
        return resultSet.next();
        
    }
}
