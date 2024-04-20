module workshoppidev {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    opens Controllers;
    opens models;
    opens services;
    // Supprimez la ligne suivante si le package "test" n'est pas utilisé dans votre application
    opens tests;
}
