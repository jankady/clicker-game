package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utility {

    public static void changeScene(ActionEvent event,String fxmlFile, String title, String username) {
        Parent root= null;

        try {
            FXMLLoader loader=new FXMLLoader(Utility.class.getResource(fxmlFile));
            root=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,800,400));
        stage.show();

    }
}
