module com.example.gasbill_generator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires org.controlsfx.controls;


    opens com.example.gasbill_generator to javafx.fxml;
    exports com.example.gasbill_generator;
}