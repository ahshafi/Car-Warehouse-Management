package client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataModel.ClientSocket;
import dataModel.Manufacturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientHomeController {

    public static int MANUFACTURER_VERIFICATION=1;
    @FXML
    private Button loginButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private HBox loginWindow;
    @FXML
    public void login(ActionEvent e) throws Exception{
        Stage stage=(Stage) loginWindow.getScene().getWindow();
        if(idTextField.getText().equals("viewer"))
            Singleton.getInstance().switchScene("/client/viewer/viewer_menu.fxml",stage);

        else {
            ClientSocket clientSocket=new ClientSocket("localhost",4999);
            clientSocket.sendRequest(MANUFACTURER_VERIFICATION);
            Singleton.getInstance().sendJSON(clientSocket,getUser());
            boolean response=Singleton.getInstance().readBoolean(clientSocket);
            System.out.println(response);
            clientSocket.close();
            if(response) {
                Singleton.getInstance().switchScene("/client/manufacturer/menu/manufacturer_menu.fxml" , stage);
            }
        }
    }

    private Manufacturer getUser(){
        String id=idTextField.getText();
        String password=passwordTextField.getText();
        return new Manufacturer(id,password);
    }

}
