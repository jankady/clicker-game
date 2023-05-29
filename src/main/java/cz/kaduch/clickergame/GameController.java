package cz.kaduch.clickergame;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Pane root;
    @FXML
    private ImageView background1;
    @FXML
    private ImageView background2;
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
                showScore();
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

    // show how much i earned by one click
    public void showScore(){

        //create text and set color and font
        Text text=new Text();
        int oneClick = (numberOfWorkers) + (numberOfVehicles * 8) + (numberOfFactories * 190);
        text.setText("+"+oneClick);
        text.setFont(Font.font(25));
        text.setFill(Color.WHITE);

        Random randomX=new Random();
        Random randomY=new Random();

        //random show text
        text.setX(randomX.nextDouble(400)+400);
        text.setY(randomY.nextDouble(400)+100);

        root.getChildren().add(text);

        //animation where text move -70 on Y
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), text);
        translateTransition.setByY(-70);
        translateTransition.setOnFinished(event -> root.getChildren().remove(text)); //remove text when animation ends
        translateTransition.play();

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

    //move 2 pictures so it looks infinite moving
    public void animatedBackground() {
        //setting layouts
        background1.setLayoutX(0);
        background2.setLayoutX(630);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            // move images to left by 1px
            background1.setTranslateX(background1.getTranslateX() - 1);
            background2.setTranslateX(background2.getTranslateX() - 1);

            // if first image reach 5 then move to 630
            if (background1.getBoundsInParent().getMaxX() <= 2) {
                background1.setTranslateX(630);
            }

            // if second image reach 0 then move to 0
            if (background2.getBoundsInParent().getMaxX() <= 0) {
                background2.setTranslateX(0);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //welcome user label
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

