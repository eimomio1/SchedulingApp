package application;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSql {

    public static void writeToDatabase(String userName, String userPassword) {

        String url = "jdbc:postgresql://localhost:5432/loginForm";
        String user = "postgres";
        String password = "retrate12";

        String name = userName;
       
        String pass = userPassword;

        // query
        String query = "INSERT INTO records(username, password) VALUES(?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, pass);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}