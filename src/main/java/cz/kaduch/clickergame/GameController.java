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
    private Button backTo;
    @FXML
    private Label worker;
    @FXML
    private Label vehicle;
    @FXML
    private Label factory;
    @FXML
    private Button btnWorker;
    @FXML
    private Button btnVehicle;
    @FXML
    private Button btnFactory;

    private int scoreNumber;
    private int numberOfWorkers;
    private int numberOfCars;
    private int numberOfFactories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // return back to log in stage
        backTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utility.changeScene(event, "admin.fxml", "Log In", null, 800, 400);
            }
        });
        //make meteroid clickable
        asteroid.setPickOnBounds(true);
        asteroid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scoreNumber=scoreNumber+(numberOfWorkers*10)+(numberOfCars*1000)+(numberOfFactories*100000);
                score.setText("Score: " + scoreNumber);
            }
        });



    }

    public void welcomeUser(String user) {
        welcome.setText("Welcome " + user);
    }
}

