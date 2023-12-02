module Car.Warehouse.Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    opens server;
    opens dataModel;
}