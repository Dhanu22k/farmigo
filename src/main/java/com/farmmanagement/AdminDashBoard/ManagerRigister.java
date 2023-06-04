package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ManagerRigister {
    @FXML
    private TextField nameField, salaryField, genderField, phoneField, addressField;
    @FXML
    private DatePicker dobField;

    public void submitAction(ActionEvent event) {
        int max;
        int id = 0;
        if (!nameField.getText().isEmpty() && !salaryField.getText().isEmpty() && !genderField.getText().isEmpty() && !phoneField.getText().isEmpty() && !addressField.getText().isEmpty()) {
            DataBaseConnector connectNow = new DataBaseConnector();
            Connection connectDB = connectNow.getConnection();
            try {
                Statement st = connectDB.createStatement();
             ResultSet rs= st.executeQuery("select max(userid)as mvalue from user");
                while (rs.next()){
                    max=rs.getInt("mvalue");
                    id=max+1;
                }
                st.executeQuery("call insertUser("+id+",'@123456','manager')");


                String query="insert into manager values("+id+",'"+nameField.getText()+"','"+dobField.getValue()+"',"+salaryField.getText()+",'"+genderField.getText()+"','"+phoneField.getText()+"','"+addressField.getText()+"')";
                if(!st.execute(query))
                {
                    Alert al=new Alert(Alert.AlertType.INFORMATION);
                    al.setHeaderText("Register Successfull");
                    al.showAndWait();
                }


            } catch (SQLException e) {
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Error Occured Please fill all the details correctly");
                al.showAndWait();
                throw new RuntimeException(e);

            }


        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Enter all the Field");
            al.showAndWait();
        }
    }


}
