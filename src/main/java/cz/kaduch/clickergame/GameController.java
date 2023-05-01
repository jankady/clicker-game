package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private ImageView asteroid;
    @FXML
    private Label score;
    @FXML
    private Label welcome;
    @FXML
    private Button backtoLogin;
    @FXML
    private Label worker;
    @FXML
    private Label vehicle;
    @FXML
    private Label factory;

    private int scoreNumber;
    private int numberOfWorkers;
    private int numberOfCars;
    private int numberOfFactories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //make meteroid clickable
        asteroid.setPickOnBounds(true);
        asteroid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scoreNumber++;
                score.setText("Score: " + scoreNumber);
            }
        });
        // return back to log in stage
        backtoLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utility.changeScene(event, "login.fxml", "Log In", null, 800, 400);
            }
        });



    }

    public void welcomeUser(String user) {
        welcome.setText("Welcome " + user);
    }
}

