module DuplicateProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;
	requires org.postgresql.jdbc;
	requires java.base;

	requires java.mail;
	requires junit;
	requires javafx.swing;
	requires org.testfx.junit;
	
	

	
	
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
