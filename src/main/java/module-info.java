module cz.kaduch.clickergame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens cz.kaduch.clickergame to javafx.fxml;
    exports cz.kaduch.clickergame;
}