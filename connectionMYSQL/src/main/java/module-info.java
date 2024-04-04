module com.example.connectionmysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;


    opens com.example.connectionmysql to javafx.fxml;
    exports com.example.connectionmysql;
}