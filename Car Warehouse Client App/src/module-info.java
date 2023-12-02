module Car.Warehouse.Client.App {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    opens client;
    opens dataModel;
    opens client.manufacturer.menu;
    opens client.manufacturer.addcar;
    opens client.exceptions;
    opens client.viewer;
}