package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Singleton.getInstance().switchScene("client_home.fxml",primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
