package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket=new ServerSocket(4999);
            while(true){
                new ClientThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void runServer(){

    }
}
