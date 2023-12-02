package client.manufacturer.menu;

import client.Singleton;
import client.exceptions.EmptyFieldException;
import client.exceptions.NegativeException;
import client.exceptions.NonExistentException;
import client.exceptions.SpaceWithinException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import dataModel.Car;
import dataModel.ClientSocket;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManufacturerMenuController {
    public static int ALL_CAR_DATA=2;
    public static int DELETE_CAR=4;
    public static int SAVE_CAR=5;
    private List<Car> carlist;
    @FXML
    private BorderPane manufacturerControlMenu;
    @FXML
    private ListView<Car> carListView;
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
    public void initialize() throws Exception{
        loadCars();

    }

    private void loadCars() throws Exception{
        ClientSocket clientSocket=new ClientSocket("localhost",4999);
        clientSocket.sendRequest(ALL_CAR_DATA);
        carlist= FXCollections.observableArrayList((List<Car>)Singleton.getInstance()
                .readJSON(clientSocket,new TypeReference<List<Car>>(){}));
        carListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue<? extends Car> observableValue , Car car , Car t1) {
                if (t1!=null) {
                    Car selectedCar=carListView.getSelectionModel().getSelectedItem();
                    showCarInfo(selectedCar);
                }
            }
        }
        );
        carListView.getItems().setAll(carlist);
        carListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carListView.getSelectionModel().selectFirst();
        clientSocket.close();
    }
    public void goBack() throws Exception{
        //System.out.println(Singleton.getInstance().getLastScene());
        Stage stage=(Stage)manufacturerControlMenu.getScene().getWindow();
        Singleton.getInstance().goBack(stage);

    }
    public void add() throws Exception{
        //System.out.println(Singleton.getInstance().getLastScene());
        Stage stage=(Stage)manufacturerControlMenu.getScene().getWindow();
        Singleton.getInstance().switchScene("/client/manufacturer/addcar/add_car.fxml",stage);
    }

    public void reload() throws Exception{
        Stage stage=(Stage)manufacturerControlMenu.getScene().getWindow();
        Singleton.getInstance().getSceneTrack().removeLast();
        Singleton.getInstance().switchScene("/client/manufacturer/menu/manufacturer_menu.fxml",stage);

    }

    public void delete() throws Exception{
        try {
            ClientSocket clientSocket=new ClientSocket("localhost",4999);
            clientSocket.sendRequest(DELETE_CAR);
            Singleton.getInstance().sendJSON(clientSocket,carListView.getSelectionModel().getSelectedItem());
            clientSocket.getResponse();
            Thread.sleep(5);
            clientSocket.close();
            reload();
        }catch (NonExistentException e){
            error.setText(e.getMessage());
        }
    }

    public void save() throws Exception{
        try {
            Car car=getCar();
            car.verify();
            ClientSocket clientSocket=new ClientSocket("localhost",4999);
            clientSocket.sendRequest(SAVE_CAR);
            Singleton.getInstance().sendJSON(clientSocket,getCar());
            clientSocket.getResponse();
            Thread.sleep(5);
            clientSocket.close();
            reload();
        }catch (NumberFormatException e) {
            error.setText("Error: Year Made, Price and Quantity must be integers");
        }catch (SpaceWithinException e){
            error.setText(e.getMessage());
        }catch (NegativeException e){
            error.setText(e.getMessage());
        }catch (EmptyFieldException e){
            error.setText(e.getMessage());
        }catch (NonExistentException e){
            error.setText(e.getMessage());
        }
    }

    private void showCarInfo(Car selectedCar){
        yearMadeTextfield.setText(String.valueOf(selectedCar.getYearMade()));
        color1Textfield.setText(selectedCar.getColor1());
        color2Textfield.setText(selectedCar.getColor2());
        color3Textfield.setText(selectedCar.getColor3());
        carMakeTextfield.setText(selectedCar.getCarMake());
        carModelTextfield.setText(selectedCar.getCarModel());
        priceTextField.setText(String.valueOf(selectedCar.getPrice()));
        quantityTextField.setText(String.valueOf(selectedCar.getQuantity()));
    }

    private Car getCar(){
        Car car=new Car();
        car.setRegistrationNumber(carListView.getSelectionModel().getSelectedItem().getRegistrationNumber());
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
