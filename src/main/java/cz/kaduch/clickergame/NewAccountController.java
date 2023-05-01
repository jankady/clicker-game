package cz.kaduch.clickergame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAccountController implements Initializable {

@FXML
private Button backTo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utility.changeScene(event,"login.fxml","Log In",null,800,400);
            }
        });
    }
}
