module GestionFacture {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.base;
    requires java.sql;
    requires javafx.fxml;

    opens com.example.gestionfacture to javafx.fxml;
    exports com.example.gestionfacture;

    opens com.example.gestionfacture.service to javafx.fxml;
    exports com.example.gestionfacture.service;

    opens com.example.gestionfacture.domaine to javafx.fxml;
    exports com.example.gestionfacture.domaine;
}