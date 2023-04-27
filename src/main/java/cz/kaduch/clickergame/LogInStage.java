package cz.kaduch.clickergame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInStage {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;

    //Method for sgin up user to admin mode or casual
    public void signUpUser() throws IOException {
        String user=username.getText().toString().toLowerCase().trim();
        String userPassword=password.getText().toString().trim();

        if (user.equals("admin") && userPassword.equals("tajneHeslo")) {
            Main m = new Main();
            m.changeScene("admin.fxml");
        } else if (user.equals("1") && userPassword.equals("1")) {
            //name and password from database
        } else if (user.equals("")||userPassword.equals("")) {

        }
    }
}