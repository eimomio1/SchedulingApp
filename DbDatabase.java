package application;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;


public class DbDatabase {

	
	
	//Creates a database connection class
	
	protected Connection connection;
	
	
	
	//Connection method that returns connection instance
	
	public Connection getConnection() {
		
		//Connection string which includes host, port #, and database Name
		final String connectionString = "jdbc:postgresql://localhost:5432/loginForm";
		
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(DbDatabase.class.getName()).log(Level.SEVERE, null, e);
		}
		
		//Creates connection using drivermanager accepting three parameters, connection sring, database name, and database password
		try {
			connection = DriverManager.getConnection(connectionString,"postgres","retrate12");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(DbDatabase.class.getName()).log(Level.SEVERE, null, e);
		}

		//return connection
		
		return connection;
	}
	
	
	
	
}
