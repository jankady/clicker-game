package cz.kaduch.clickergame;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.Media;


import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private ImageView background;
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
    private int numberOfWorkers = 1;
    private int numberOfVehicles;
    private int numberOfFactories;
    private boolean animationPlaying = false;

    private int workerPrice;
    private int vehiclePrice;
    private int factoryPrice;

    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animatedBackground();

        // return back to log in stage
        backTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clickergame", "root", "admin");
                    preparedStatement = connection.prepareStatement("UPDATE main SET score = ?, worker = ?, vehicle = ?, factory = ? where username= ?");
                    preparedStatement.setInt(1, scoreNumber);
                    preparedStatement.setInt(2, numberOfWorkers);
                    preparedStatement.setInt(3, numberOfVehicles);
                    preparedStatement.setInt(4, numberOfFactories);
                    preparedStatement.setString(5, username);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();

                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }


                Utility.changeScene(event, "login.fxml", "Log In", null, 800, 400);
            }
        });

        //make meteroid clickable
        asteroid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                asteroid.setPickOnBounds(true);
                animation();
                scoreNumber = scoreNumber + (numberOfWorkers) + (numberOfVehicles * 8) + (numberOfFactories * 190);
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
                    btnWorker.setText("Price " + workerPriceAlgorithm());
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
                    btnVehicle.setText("Price " + vehiclePriceAlgorithm());
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
                    btnFactory.setText("Price " + factoryPriceAlgorithm());
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

    public void animatedBackground() {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(2),background);
        translate.setByX(-60);

        Timeline timeline= new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                translate.playFromStart();
            }
        }));
        timeline.play();
    }

    public void welcomeUser(String user) {
        welcome.setText("Welcome " + user);
        username = user;

    }

    //show all numbers like score and number of upgrades
    public void setupGame(String usernameData) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clickergame", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT score, worker, vehicle, factory FROM main WHERE username = ?");
//            System.out.println(usernameData);
            preparedStatement.setString(1, usernameData);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                scoreNumber = resultSet.getInt("score");
                numberOfWorkers = resultSet.getInt("worker");
                numberOfVehicles = resultSet.getInt("vehicle");
                numberOfFactories = resultSet.getInt("factory");
            }

            btnWorker.setText("Price " + workerPriceAlgorithm());
            btnVehicle.setText("Price " + vehiclePriceAlgorithm());
            btnFactory.setText("Price " + factoryPriceAlgorithm());
            worker.setText(String.valueOf(numberOfWorkers));
            vehicle.setText(String.valueOf(numberOfVehicles));
            factory.setText(String.valueOf(numberOfFactories));
            score.setText("Score: " + scoreNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    //formula is nextPrice= base price (number of bought items * multiplayer)
    //calculating next worker price
    public int workerPriceAlgorithm() {
        workerPrice = (int) (10 * (numberOfWorkers * 1.14));
        return workerPrice;
    }

    //calculating next vehicle price
    public int vehiclePriceAlgorithm() {
        vehiclePrice = (int) (150 * ((numberOfVehicles + 1) * 1.15));
        return vehiclePrice;
    }

    //calculating next factory price
    public int factoryPriceAlgorithm() {
        factoryPrice = (int) (2000 * ((numberOfFactories + 1) * 1.17));
        return factoryPrice;
    }

}

