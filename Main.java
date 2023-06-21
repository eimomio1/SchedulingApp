package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollBar;

public class Main extends Application {
	
	
	public static Stage stg;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		stg = primaryStage; //Variable stg points to primary stage
		
		primaryStage.setResizable(true);
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		
		
		
        primaryStage.setTitle("Hello World");
       
        Scene mainScene = new Scene(root); // Root object
        stg.setScene(mainScene); //Set root scene
        stg.show(); //Show scene
        mainScene.getWindow().sizeToScene();

        // Switching to the larger scene resource, that doesn't fit in the window
       // mainScene.setRoot(scene2); // Scene is 400x400 but can only see 300x300
        
        // Resizing the window so the larger scene fits
        //mainScene.getWindow().sizeToScene(); // Window is now 400x400 & everything is visible
        
        
        
        
        
        
        //primaryStage.setScene(new Scene(root, 600, 400));
       // primaryStage.show();
        
       
       
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	 
	 public void resizeScene( String fxml) throws IOException {
		 
		
		 //changing current Scene ( Could be MainMen.fxml)to fit window
		 
	
		 
		 Parent scene2 = FXMLLoader.load(getClass().getResource(fxml)); //Loads FXML In
		 
		 Scene mainScene = new Scene(scene2);
		 stg.setScene(mainScene); //Set root scene
	        stg.show(); //Show scene
	        mainScene.getWindow().sizeToScene(); //Resize Window screen according to resolution
	        
		
	
	 }
	 
	 
	 
	 }
	 
	
	
	 
	 
	
