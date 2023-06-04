package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Statement;

public class FinanceData {
    @FXML
    private TextField yearField, expField, incomeField;
    @FXML
    private Button submitBtn;

    public void formSubmt(ActionEvent event) {
        String farmid = ManagerPanel.farmid;
        String exp = expField.getText();
        String income = incomeField.getText();
        String year = yearField.getText();
        if (!exp.isEmpty() && !income.isEmpty() && !year.isEmpty()) {
            DataBaseConnector connect = new DataBaseConnector();
            Connection con = connect.getConnection();
            String qry = "insert into finance values('" + farmid + "'," + exp + "," + income + ",'" + year + "')";
            try {
                Statement st = con.createStatement();
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Data succesfully updated");
                info.showAndWait();
                submitBtn.getScene().getWindow().hide();
                st.executeUpdate(qry);
                ManagerPanel.fncBtn.fire();
            } catch (Exception e) {
                e.printStackTrace();
                Alert war = new Alert(Alert.AlertType.ERROR);
                war.setHeaderText("Error occurred");
                war.showAndWait();
            }

        }
    }
}

