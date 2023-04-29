package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInStageController {

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
    Main m = new Main();

    public void checkSignIn(ActionEvent event)throws IOException {
        signInUser();
    }

    //Method for sign up user to admin mode or casual
    public void signInUser() throws IOException {

        String user=username.getText().toLowerCase().trim();
        String userPassword=password.getText().trim();

        if (user.equals("admin") && userPassword.equals("tajneHeslo")) {

            m.changeScene("admin.fxml");
        } else if (user.equals("1") && userPassword.equals("1")) {
            //name and password from database
            m.changeScene("game.fxml");

        } else if (user.isEmpty()||userPassword.isEmpty()) {
            wrongMessage.setText("Please enter data");
        }
        else {
            wrongMessage.setText("Wrong username or password");
        }
    }

    public void signUpUser(ActionEvent event)throws IOException {
        m.changeScene("NewAccount.fxml");
    }
}