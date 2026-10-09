module uabc.david.practica3algo2k26 {
    requires javafx.controls;
    requires javafx.fxml;


    opens uabc.david.practica3algo2k26 to javafx.fxml;
    exports uabc.david.practica3algo2k26;
    exports uabc.david.practica3algo2k26.Vista;
}