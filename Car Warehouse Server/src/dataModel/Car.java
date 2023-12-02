package dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private String registrationNumber;
    private int yearMade;
    private String color1,color2,color3;
    private String carMake;
    private String carModel;
    private int price;
    private int quantity;

    @JsonIgnore
    public static List<Car> carList=new ArrayList<>();

    public Car() {

    }
    public Car(String registrationNumber , int yearMade , String color1 , String color2 , String color3 , String carMake , String carModel , int price , int quantity) {
        this.registrationNumber = registrationNumber;
        this.yearMade = yearMade;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.carMake = carMake;
        this.carModel = carModel;
        this.price = price;
        this.quantity=quantity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getYearMade() {
        return yearMade;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonIgnore

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Car)
            return this.registrationNumber.equals(((Car)obj).registrationNumber);
        return false;
    }

    public static List<Car> search(String registraionNumber,String carMake,String carModel){
        List<Car> list=new ArrayList<>();
        for(Car car: carList){
            if((registraionNumber.equals("") || registraionNumber.equals(car.getRegistrationNumber()))
            && (carMake.equals("") || carMake.equals(car.getCarMake())) && (carModel.equals("") || carModel.equals(car.getCarModel()))){
                list.add(car);
            }
        }
        return list;
    }
}
