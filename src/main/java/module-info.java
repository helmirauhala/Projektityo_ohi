module org.example.projektityo_ohi2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.projektityo_ohi2 to javafx.fxml;
    exports org.example.projektityo_ohi2;
}