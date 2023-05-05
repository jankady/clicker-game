package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAccountController implements Initializable {

    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordReEnter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String user = username.getText().toLowerCase().trim();
                String userPassword = password.getText().trim();
                String userRePassword = passwordReEnter.getText();

                if (user.isEmpty()||userPassword.isEmpty()||userRePassword.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Enter data");
                    alert.setContentText("Fill in all fields");
                    alert.show();
                }
                else Utility.signUpUser(event,user,userPassword,userRePassword);
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utility.changeScene(event, "login.fxml", "Log In", null, 800, 400);
            }
        });
    }
}
