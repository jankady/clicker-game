package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utility {


    //method for changing scene (I absolutely don't understand it )
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, int width, int height) {
        Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(Utility.class.getResource(fxmlFile));
                root = loader.load();
                GameController gameController = loader.getController();
                gameController.welcomeUser(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();


    }
}
