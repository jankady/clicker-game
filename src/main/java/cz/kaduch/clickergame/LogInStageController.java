package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
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
    Main m = new Main();

    public void checkSignIn(ActionEvent event)throws IOException {
        signInUser();
    }

    //Method for sign up user to admin mode or casual
    public void signInUser() throws IOException {


    }

    public void signUpUser(ActionEvent event)throws IOException {
        m.changeScene("NewAccount.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String user=username.getText().toLowerCase().trim();
        String userPassword=password.getText().trim();
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (user.equals("admin") && userPassword.equals("tajneHeslo")) {

                    Utility.changeScene(event,"admin.fxml","admin",user);
                } else if (user.equals("1") && userPassword.equals("1")) {
                    //name and password from database
                    Utility.changeScene(event,"game.fxml","game",user);

                } else if (user.isEmpty()||userPassword.isEmpty()) {
                    wrongMessage.setText("Please enter data");
                }
                else {
                    wrongMessage.setText("Wrong username or password");
                }
            }
        });


    }
}