module Project1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
	requires java.sql;
	requires mysql.connector.j;

    exports application;
    exports applicationControllers;
    exports applicationModels;

    opens application to javafx.graphics, javafx.fxml;
    opens applicationControllers to javafx.graphics, javafx.fxml;
    opens applicationModels to javafx.graphics, javafx.fxml, javafx.base;
}
