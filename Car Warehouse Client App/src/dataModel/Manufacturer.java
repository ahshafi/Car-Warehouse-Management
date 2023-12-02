package dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Manufacturer {
    private String username;
    private String password;
    @JsonIgnore
    public static List<Manufacturer> manufacturers=new ArrayList<>();

    public Manufacturer() {

    }

    public Manufacturer(String username , String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean verify(){
        return manufacturers.contains(this);
    }
}
