package dataModel;

import client.Singleton;
import client.exceptions.NonExistentException;
import client.exceptions.OutOfStockException;
import client.exceptions.UsedRegistrationNumberException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;

public class ClientSocket extends Socket {


    public static int USED_REGISTRATION_NUMBER=6;
    public static int CAR_NON_EXISTENT=7;
    public static int OUT_OF_STOCK=9;
    public ClientSocket(String host , int port ) throws IOException {
        super(host , port);

    }
    public void sendRequest(int requestCode) throws Exception{
        Singleton.getInstance().sendInt(this,requestCode);
    }
    public void getResponse() throws Exception{
        int response=Singleton.getInstance().readInt(this);
        if(response==USED_REGISTRATION_NUMBER)
            throw new UsedRegistrationNumberException();
        if(response==CAR_NON_EXISTENT)
            throw new NonExistentException("The car ");
        if(response==OUT_OF_STOCK)
            throw new OutOfStockException();
    }
}
