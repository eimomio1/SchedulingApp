module NewJavaFXProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;
	requires org.postgresql.jdbc;
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	
}
