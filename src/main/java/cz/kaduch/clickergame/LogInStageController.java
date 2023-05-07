package cz.kaduch.clickergame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class LogInStageController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label wrongMessage;

    //checking if buttons were pressed if yes then it will change scene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check if username and password are filled and change scene
        logInButton.setOnAction(event -> {
            String user = username.getText().toLowerCase().trim();
            String userPassword = password.getText().trim();
           if (user.isEmpty() || userPassword.isEmpty()) {
                wrongMessage.setText("Please enter data");
            } else if (!Utility.signInUser(event, user, userPassword)) {
               wrongMessage.setText("Wrong username or password");
           } else if (user=="admin" && userPassword=="admin") {
               // code for admin access
           } else {
               Utility.signInUser(event,user,userPassword);
            }
        });

        //checking pressing new account button
        signUpButton.setOnAction(event -> Utility.changeScene(event, "NewAccount.fxml", "New Account", null, 800, 400));

    }
}