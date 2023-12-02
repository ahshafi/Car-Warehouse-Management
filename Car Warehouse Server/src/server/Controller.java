package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Button registerManufacturerButton;

    @FXML
    public void registerManufacturer(ActionEvent e) throws Exception {
        Singleton.getInstance().switchScene("register_manufacturer.fxml");
    }
}
