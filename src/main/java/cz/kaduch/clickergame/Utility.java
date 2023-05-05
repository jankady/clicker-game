package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


@SuppressWarnings("GrazieInspection")
public class Utility {

    //method for changing scene (I absolutely don't understand it )
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, int width, int height) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(Utility.class.getResource(fxmlFile));
            root = loader.load();
            if (username != null) {
                GameController gameController = loader.getController();
                gameController.welcomeUser(username);
                gameController.setupGame(username);
            }
        } catch (IOException e) {
            System.out.println("something is wrong");
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();


    }

    //method for sign up new user
    public static void signUpUser(ActionEvent event, String username, String password, String rePassword) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clickergame", "root", "admin");  //connect to my SQL
            psCheckUserExist = connection.prepareStatement("SELECT * from main WHERE username = ?"); // from my databse select all usernames, ?- is parameter
            psCheckUserExist.setString(1, username); // check if username exist in my database 1 mean the number of ?, username is the parameter which i put there
            resultSet = psCheckUserExist.executeQuery(); // if this is empty it mean that that user dont exist

            // throw allert if user exist
            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User exist");
                alert.setContentText("This user already exist, try different name");
                alert.show();
            }
            // allert if the password do not match
            else if (password!=rePassword) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Passwords don't match");
                alert.setContentText("Passwords do not match, try enter them again");
                alert.show();
            }
            // create new user in database
            else {
                psInsert = connection.prepareStatement("INSERT INTO main (username, password, score, worker, vehicle, factory) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setInt(3, 0);
                psInsert.setInt(4,1);
                psInsert.setInt(5,0);
                psInsert.setInt(6,0);
                psInsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucessfull");
                alert.setContentText("Successfully created account " + username);
                alert.show();
                Utility.changeScene(event, "login.fxml", "Log In", null, 800, 400);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // this will happend always and end connection to database or it could resume in overflow memory
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try {
                    psCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    //method for sign in existing user
    public static boolean signInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clickergame", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT password FROM main WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
//            if (!resultSet.isBeforeFirst()) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("User not found");
//                alert.setContentText("This username was not found in the database");
//                alert.show();
//            } else {
            while (resultSet.next()) { //loop through all usernames until its end
                String retrivedPassword = resultSet.getString("password");
                if (retrivedPassword.equals(password)) {
                    Utility.changeScene(event, "game.fxml", "game", username, 1000, 600);
                }
            }
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
        return false;
    }
}
