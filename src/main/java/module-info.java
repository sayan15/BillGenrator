module com.example.gasbill_generator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.mail;
    requires activation;
    requires itextpdf;
    requires flying.saucer.pdf;


    opens com.example.gasbill_generator to javafx.fxml;
    exports com.example.gasbill_generator;
}