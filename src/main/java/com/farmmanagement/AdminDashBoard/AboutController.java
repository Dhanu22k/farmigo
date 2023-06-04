package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    String id;

    public void ChangePasswordAction(ActionEvent event) {

        String password, NewPassword;
        id = AdminDashBoard.userid;
        int count = 0;
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter your current password");
        td.setResizable(false);
        td.showAndWait();
        password = td.getResult();

        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        try {
            Statement st = connectDB.createStatement();
            ResultSet rs = st.executeQuery("select count(*)as count from user where userid=" + id + " and password='" + password + "'");
            while (rs.next()) {
                count = rs.getInt("count");
            }
            if (count == 1) {
                TextInputDialog td2 = new TextInputDialog();

                td2.setHeaderText("Enter your new password");
                td2.setResizable(false);
                td2.showAndWait();
                NewPassword = td2.getResult();
                if (NewPassword != null) {
                    System.out.println("New pass" + NewPassword);
                    int out = st.executeUpdate("update user set password='" + NewPassword + "' where userid=" + id + "");
                    if (out == 1) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setHeaderText("Password changed");
                        a.showAndWait();
                    }
                }
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Password is not valid");
                a.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

@FXML
private Label nameField,dobField,genderField;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        try{
            Statement st=connectDB.createStatement();
            ResultSet rs=st.executeQuery("select name,dob,gender from admin where adminid="+AdminDashBoard.userid+"");
            while (rs.next())
            {
                nameField.setText(rs.getString("name"));
                dobField.setText(rs.getString("dob"));
                genderField.setText(rs.getString("gender"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
