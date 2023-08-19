module com.mypersonalnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires json.simple;


    opens com.mypersonalnetwork to javafx.fxml;
    exports com.mypersonalnetwork;
    exports com.mypersonalnetwork.database.connection;
    exports com.mypersonalnetwork.controller;
}