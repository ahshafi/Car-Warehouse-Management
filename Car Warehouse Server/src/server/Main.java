package server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataModel.Car;
import dataModel.Manufacturer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {


    static void load() throws Exception{
        FileInputStream fis;
        ObjectMapper objectMapper = new ObjectMapper();

        fis=new FileInputStream("carList.json");
        Car.carList=objectMapper.readValue(fis,new TypeReference<>(){});
        fis.close();

        fis=new FileInputStream("manufacturers.json");
        Manufacturer.manufacturers=objectMapper.readValue(fis,new TypeReference<>(){});
        fis.close();
    }
    static void save() throws Exception{
        FileOutputStream fos;
        ObjectMapper objectMapper = new ObjectMapper();

        fos=new FileOutputStream("carList.json");
        fos.write(objectMapper.writeValueAsBytes(Car.carList));
        fos.close();

        fos=new FileOutputStream("manufacturers.json");
        fos.write(objectMapper.writeValueAsBytes(Manufacturer.manufacturers));
        fos.close();

    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        load();
        new ServerThread().start();
//        for(int i=0;i<manufacturers.size();i++)
//            System.out.println(manufacturers.get(i).getUsername());
        Singleton.getInstance().switchScene("admin_home.fxml");
    }

    @Override
    public void stop() throws Exception {
        save();
    }
}
