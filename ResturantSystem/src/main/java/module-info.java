module com.example.resturantsystem {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.resturantsystem to javafx.fxml;
    exports com.example.resturantsystem;
    exports com.example.resturantsystem.Controller;
    opens com.example.resturantsystem.Controller to javafx.fxml;
}