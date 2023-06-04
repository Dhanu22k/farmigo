module com.farmmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.farmmanagement to javafx.fxml;
    exports com.farmmanagement;
    exports com.farmmanagement.databaseConnector;
    opens com.farmmanagement.databaseConnector to javafx.fxml;
    exports com.farmmanagement.AdminDashBoard;
    opens com.farmmanagement.AdminDashBoard to javafx.fxml;
    exports com.farmmanagement.ManagerPanel;
    opens com.farmmanagement.ManagerPanel to javafx.fxml;

}