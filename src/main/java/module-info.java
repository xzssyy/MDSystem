module school.mdsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;


    opens school.mdsystem.student.listener to javafx.fxml;
    exports school.mdsystem.student.listener;
    opens school.mdsystem.student.controller to javafx.fxml;
    exports school.mdsystem.student.controller;
    exports school.mdsystem;

    opens school.mdsystem.student.model to javafx.fxml;
    exports school.mdsystem.student.model;

    opens school.mdsystem.delivery to javafx.graphics, javafx.fxml;
    exports school.mdsystem.delivery;

    opens school.mdsystem.administor to javafx.graphics, javafx.fxml;
    exports school.mdsystem.administor;

    opens school.mdsystem.logistic to javafx.graphics, javafx.fxml;
    exports school.mdsystem.logistic;
    opens school.mdsystem to javafx.fxml, javafx.graphics;
    exports school.mdsystem.login;
    opens school.mdsystem.login to javafx.fxml;

}