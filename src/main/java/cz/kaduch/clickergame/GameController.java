package cz.kaduch.clickergame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private ImageView asteroid;
    @FXML
    private Label score;
    @FXML
    private Label welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void welcomeUser(String user) {
        welcome.setText("Welcome "+user);
    }
}

