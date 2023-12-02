package client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataModel.Car;
import dataModel.ClientSocket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Singleton {
    private static Singleton instance=new Singleton();
    private ObjectMapper objectMapper;
    private Deque<String> sceneTrack;
    private Singleton(){
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        objectMapper=new ObjectMapper(jsonFactory);
        sceneTrack=new ArrayDeque<>();
    }

    public static Singleton getInstance(){
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public Deque<String> getSceneTrack() {
        return sceneTrack;
    }

    public void switchScene(String fxml, Stage stage) throws Exception{
        sceneTrack.addLast(fxml);
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene=new Scene(root) ;
        stage.setScene(scene);
        stage.show();
    }
    public void goBack(Stage stage) throws Exception{
        sceneTrack.removeLast();
        Parent root = FXMLLoader.load(getClass().getResource(sceneTrack.getLast()));
        Scene scene=new Scene(root) ;
        stage.setScene(scene);
        stage.show();
    }
    public void sendInt(Socket socket, int i) throws Exception{
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        dos.writeInt(i);
    }
    public void sendBoolean(Socket socket,boolean i) throws Exception{
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        dos.writeBoolean(i);
    }
    public <T> void sendJSON(Socket socket,T obj) throws Exception{
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(objectMapper.writeValueAsString(obj));
    }
    public int readInt(Socket socket) throws Exception{
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        return dis.readInt();
    }
    public boolean readBoolean(Socket socket) throws Exception{
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        return dis.readBoolean();
    }
    public Object readJSON(Socket socket,Class type) throws Exception{
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        return objectMapper.readValue(dis.readUTF(),type);
    }
    public <T> Object readJSON(Socket socket, TypeReference<T> t) throws Exception{
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        return objectMapper.readValue(dis.readUTF(),t);
    }


}
