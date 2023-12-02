package client.manufacturer.addcar;

import client.Singleton;
import client.exceptions.EmptyFieldException;
import client.exceptions.NegativeException;
import client.exceptions.SpaceWithinException;
import client.exceptions.UsedRegistrationNumberException;
import dataModel.Car;
import dataModel.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addCarController {

    public static int ADD_CAR=3;
    @FXML
    private BorderPane addCarMenu;
    @FXML
    private TextField registrationTextfield;
    @FXML
    private TextField yearMadeTextfield;
    @FXML
    private TextField color1Textfield;
    @FXML
    private TextField color2Textfield;
    @FXML
    private TextField color3Textfield;
    @FXML
    private TextField carMakeTextfield;
    @FXML
    private TextField carModelTextfield;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Text error;
    public void goBack() throws Exception{
        Stage stage=(Stage)addCarMenu.getScene().getWindow();
        Singleton.getInstance().goBack(stage);
    }

    public void save() throws Exception{
        try {
            Car car=getCar();
            car.verify();
            ClientSocket clientSocket=new ClientSocket("localhost",4999);
            clientSocket.sendRequest(ADD_CAR);
            Singleton.getInstance().sendJSON(clientSocket,car);
            clientSocket.getResponse();
            clientSocket.close();
            goBack();
        }catch (NumberFormatException e) {
            error.setText("Error: Year Made, Price and Quantity must be integers");
        }catch (SpaceWithinException e){
            error.setText(e.getMessage());
        }catch (NegativeException e){
            error.setText(e.getMessage());
        }catch (UsedRegistrationNumberException e){
            error.setText(e.getMessage());
        }catch (EmptyFieldException e){
            error.setText(e.getMessage());
        }
    }

    private Car getCar(){
        Car car=new Car();
        car.setRegistrationNumber(registrationTextfield.getText());
        car.setYearMade(Integer.parseInt(yearMadeTextfield.getText()));
        car.setColor1(color1Textfield.getText());
        car.setColor2(color2Textfield.getText());
        car.setColor3(color3Textfield.getText());
        car.setCarMake(carMakeTextfield.getText());
        car.setCarModel(carModelTextfield.getText());
        car.setPrice(Integer.parseInt(priceTextField.getText()));
        car.setQuantity(Integer.parseInt(quantityTextField.getText()));
        return car;
    }

}
