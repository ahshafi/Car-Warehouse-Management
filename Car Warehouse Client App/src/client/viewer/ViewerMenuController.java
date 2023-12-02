package client.viewer;

import client.Singleton;
import client.exceptions.NonExistentException;
import client.exceptions.OutOfStockException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

public class ViewerMenuController {

    public static int ALL_CAR_DATA=2;
    public static int BUY_CAR=8;
    public static int SEARCH_CAR=10;
    private List<Car> carlist;
    @FXML
    private BorderPane viewerControlMenu;
    @FXML
    private ListView<Car> carListView;
    @FXML
    private Text registrationTextField;
    @FXML
    private Text yearMadeTextfield;
    @FXML
    private Text color1Textfield;
    @FXML
    private Text color2Textfield;
    @FXML
    private Text color3Textfield;
    @FXML
    private Text carMakeTextfield;
    @FXML
    private Text carModelTextfield;
    @FXML
    private Text priceTextField;
    @FXML
    private Text quantityTextField;
    @FXML
    private TextField searchByRegistraionField;
    @FXML
    private TextField searchByCarMakeField;
    @FXML
    private TextField searchByCarModelField;
    @FXML
    private Text error;

    public void initialize() throws Exception{
        loadCars();

    }

    private void loadCars() throws Exception{
        ClientSocket clientSocket=new ClientSocket("localhost",4999);
        clientSocket.sendRequest(ALL_CAR_DATA);
        carlist= FXCollections.observableArrayList((List<Car>) Singleton.getInstance()
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
        Stage stage=(Stage)viewerControlMenu.getScene().getWindow();
        Singleton.getInstance().goBack(stage);

    }

    public void reload() throws Exception{
        Stage stage=(Stage)viewerControlMenu.getScene().getWindow();
        Singleton.getInstance().getSceneTrack().removeLast();
        Singleton.getInstance().switchScene("/client/viewer/viewer_menu.fxml",stage);

    }

    public void buy() throws Exception{
        try {
            Car car=carListView.getSelectionModel().getSelectedItem();
            //car.verify();
            ClientSocket clientSocket=new ClientSocket("localhost",4999);
            clientSocket.sendRequest(BUY_CAR);
            Singleton.getInstance().sendJSON(clientSocket,car);
            clientSocket.getResponse();
            Thread.sleep(5);
            clientSocket.close();
            reload();
        } catch (OutOfStockException e) {
            error.setText(e.getMessage());
        }catch (NonExistentException e){
            error.setText(e.getMessage());
        }
    }

    public void search() throws Exception{
        ClientSocket clientSocket=new ClientSocket("localhost",4999);
        clientSocket.sendRequest(SEARCH_CAR);
        String registraionNumber=searchByRegistraionField.getText();
        String carMake=searchByCarMakeField.getText();
        String carModel=searchByCarModelField.getText();
        Singleton.getInstance().sendJSON(clientSocket,registraionNumber);
        Singleton.getInstance().sendJSON(clientSocket,carMake);
        Singleton.getInstance().sendJSON(clientSocket,carModel);
        carlist= FXCollections.observableArrayList((List<Car>) Singleton.getInstance()
                .readJSON(clientSocket,new TypeReference<List<Car>>(){}));
        carListView.getItems().setAll(carlist);
        carListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carListView.getSelectionModel().selectFirst();
        clientSocket.close();
    }

    private void showCarInfo(Car selectedCar){
        registrationTextField.setText(selectedCar.getRegistrationNumber());
        yearMadeTextfield.setText(String.valueOf(selectedCar.getYearMade()));
        color1Textfield.setText(selectedCar.getColor1());
        color2Textfield.setText(selectedCar.getColor2());
        color3Textfield.setText(selectedCar.getColor3());
        carMakeTextfield.setText(selectedCar.getCarMake());
        carModelTextfield.setText(selectedCar.getCarModel());
        priceTextField.setText(String.valueOf(selectedCar.getPrice()));
        quantityTextField.setText(String.valueOf(selectedCar.getQuantity()));
    }



}
