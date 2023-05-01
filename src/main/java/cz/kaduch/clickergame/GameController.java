package cz.kaduch.clickergame;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
    private int numberOfWorkers=1;
    private int numberOfVehicles;
    private int numberOfFactories;
    private boolean animationPlaying = false;

    private int workerPrice;
    private int vehiclePrice;
    private int factoryPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showStats();

        // return back to log in stage
        backTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utility.changeScene(event, "login.fxml", "Log In", null, 800, 400);
            }
        });

        //make meteroid clickable
        asteroid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                asteroid.setPickOnBounds(true);
                animation();
                scoreNumber = scoreNumber + (numberOfWorkers) + (numberOfVehicles * 5) + (numberOfFactories * 20);
                score.setText("Score: " + scoreNumber);

            }
        });

        //button for workers
        btnWorker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (scoreNumber >= workerPrice) {
                    scoreNumber = scoreNumber - workerPrice;
                    numberOfWorkers++;
                    worker.setText(String.valueOf(numberOfWorkers));
                    score.setText("Score: " + scoreNumber);
                    btnWorker.setText("Price " +workerPriceAlgorithm());
                }
            }
        });

        //button for vehicles
        btnVehicle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (scoreNumber >= vehiclePrice) {
                    scoreNumber = scoreNumber - vehiclePrice;
                    numberOfVehicles++;
                    vehicle.setText(String.valueOf(numberOfVehicles));
                    score.setText("Score: " + scoreNumber);
                    btnVehicle.setText("Price" +vehiclePriceAlgorithm());
                }
            }
        });

        //button for factories
        btnFactory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (scoreNumber >= factoryPrice) {
                    scoreNumber = scoreNumber - factoryPrice;
                    numberOfFactories++;
                    factory.setText(String.valueOf(numberOfFactories));
                    score.setText("Score: " + scoreNumber);
                    btnFactory.setText("Price" +factoryPriceAlgorithm());
                }
            }
        });


    }

    //when click on meteroit it will call this method and it will do animation, if user click more times it will
    // only play animation once and play it after it finished
    public void animation() {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.25), asteroid);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        if (!animationPlaying) {
            animationPlaying = true;
            scale.setOnFinished(e -> animationPlaying = false);
            scale.play();
        }
    }

    //show all numbers like score and number of upgrades
    public void showStats() {
        btnWorker.setText("Price " +workerPriceAlgorithm());
        btnVehicle.setText("Price" +vehiclePriceAlgorithm());
        btnFactory.setText("Price" +factoryPriceAlgorithm());
        worker.setText(String.valueOf(numberOfWorkers));
        vehicle.setText(String.valueOf(numberOfVehicles));
        factory.setText(String.valueOf(numberOfFactories));
        score.setText("Score: " + scoreNumber);
    }


    public void welcomeUser(String user) {
        welcome.setText("Welcome " + user);
    }
    //formula is nextPrice= base price (number of bought items * multiplayer)
    //calculating next worker price
    public int workerPriceAlgorithm() {
         workerPrice = (int) (10 * (numberOfWorkers*1.14));
        return workerPrice;
    }
//calculating next vehicle price
    public int vehiclePriceAlgorithm() {
        vehiclePrice= (int) (150*((numberOfVehicles+1)*1.15));
        return vehiclePrice;
    }
    //calculating next factory price
    public int factoryPriceAlgorithm() {
        factoryPrice = (int) (2000*((numberOfFactories+1)*1.17));
        return factoryPrice;
    }
}

