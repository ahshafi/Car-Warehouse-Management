package server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataModel.Car;
import dataModel.Manufacturer;
import javafx.scene.control.skin.LabelSkin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;

public class ClientThread extends Thread {
    private Socket socket;
    private int requstCode;
    private JsonFactory jsonFactory;
    public static int MANUFACTURER_VERFICATION=1;
    public static int ALL_CAR_DATA=2;
    public static int ADD_CAR=3;
    public static int DELETE_CAR=4;
    public static int SAVE_CAR=5;
    public static int ALL_IS_WELL=0;
    public static int USED_REGISTRATION_NUMBER=6;
    public static int CAR_NON_EXISTENT=7;
    public static int BUY_CAR=8;
    public static int OUT_OF_STOCK=9;
    public static int SEARCH_CAR=10;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        //System.out.println(requstCode);
        try {
            getRequestFromClient() ;
            if(requstCode==MANUFACTURER_VERFICATION){
                Manufacturer manufacturer=(Manufacturer) Singleton.getInstance().readJSON(socket,Manufacturer.class);
                System.out.println(manufacturer.getUsername()+" "+manufacturer.getPassword());
                Singleton.getInstance().sendBoolean(socket,manufacturer.verify());
                //dos.close();
            }else if(requstCode==ALL_CAR_DATA){
                Singleton.getInstance().sendJSON(socket,Car.carList);
            }else if(requstCode==ADD_CAR){
                Car car=(Car) Singleton.getInstance().readJSON(socket,Car.class);
                if(Car.carList.contains(car)==false) {
                    Car.carList.add(car);
                    sendResponsetoClient(ALL_IS_WELL);
                }else {
                    sendResponsetoClient(USED_REGISTRATION_NUMBER);
                }
            }else if(requstCode==DELETE_CAR){
                Car car=(Car) Singleton.getInstance().readJSON(socket,Car.class);
                if(Car.carList.contains(car)){
                    Car.carList.remove(Car.carList.indexOf(car));
                    sendResponsetoClient(ALL_IS_WELL);
                }else {
                    sendResponsetoClient(CAR_NON_EXISTENT);
                }
            }else if(requstCode==SAVE_CAR){
                Car car=(Car) Singleton.getInstance().readJSON(socket,Car.class);
                if(Car.carList.contains(car)){
                    Car.carList.remove(Car.carList.indexOf(car));
                    Car.carList.add(car);
                    sendResponsetoClient(ALL_IS_WELL);
                }else {
                    sendResponsetoClient(CAR_NON_EXISTENT);
                }

            }else if(requstCode==BUY_CAR) {
                Car car = (Car) Singleton.getInstance().readJSON(socket , Car.class);
                if (Car.carList.contains(car) == false) {
                    sendResponsetoClient(CAR_NON_EXISTENT);

                } else if (Car.carList.get(Car.carList.indexOf(car)).getQuantity() == 0) {
                    sendResponsetoClient(OUT_OF_STOCK);
                } else {
                    car = Car.carList.get(Car.carList.indexOf(car));
                    car.setQuantity(car.getQuantity() - 1);
                    sendResponsetoClient(ALL_IS_WELL);
                }
            }else if(requstCode==SEARCH_CAR){

                String registrationNumber=(String) Singleton.getInstance().readJSON(socket,String.class);
                String carMake=(String) Singleton.getInstance().readJSON(socket,String.class);
                String carModel=(String) Singleton.getInstance().readJSON(socket,String.class);
                System.out.println(registrationNumber+" "+carMake+" "+carModel);
                Singleton.getInstance().sendJSON(socket,Car.search(registrationNumber,carMake,carModel));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getRequestFromClient() throws Exception{
        requstCode=Singleton.getInstance().readInt(socket);
    }
    private void sendResponsetoClient(int response) throws Exception{
        Singleton.getInstance().sendInt(socket,response);
    }
}
