module com.example.fitconnectjavafx2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fitconnect to javafx.fxml;
    exports com.example.fitconnect;
}