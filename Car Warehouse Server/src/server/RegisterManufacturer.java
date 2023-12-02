package server;

import dataModel.Manufacturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterManufacturer {
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    public void register(ActionEvent e) throws Exception {
        Manufacturer.manufacturers.add(new Manufacturer(usernameTextField.getText(),passwordTextField.getText()));
        Node source = (Node)  e.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        //Toast.makeText(stage, "Manufacturer registered successfully", 3500, 500, 500);
        stage.close();
    }
}
