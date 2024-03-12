module com.example.hcwtest{
    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires java.naming;

    requires org.hibernate.orm.core;




    opens com.example.hcwtest to javafx.fxml,org.hibernate.orm.core;
    exports com.example.hcwtest;
    exports com.example.hcwtest.controller;
    opens com.example.hcwtest.controller to javafx.fxml;

    exports com.example.hcwtest.entity;
    opens com.example.hcwtest.entity to org.hibernate.orm.core;
}
