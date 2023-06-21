
package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
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



    public void userLog(ActionEvent event) throws IOException {
        checkLogin();
        
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        if(username.getText().toString().equals("javacoding") && password.getText().toString().equals("123")) {
        	
            wrongLogIn.setText("Success!");

	        System.out.println(username.getText());
	        System.out.println(password.getText());
	       
	        JavaPostgreSql.writeToDatabase(username.getText(), password.getText());
             
        
            m.resizeScene("MainMenu.fxml");
        }

        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter your data.");
        }


        else {
            wrongLogIn.setText("Wrong username or password!");
        }
    }
    
	
   
	 
	 

}