package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SettingUpdate implements Initializable {
    @FXML
    private TextField UpdateNameField,UpdateAddressField,UpdateGenderField,UpdatePhoneField;
    @FXML
    private DatePicker UpdateDobField;
    @FXML
    private Label WarnLabel;
    @FXML
    private Button UpdateBtn;
    private String mgrid=ManagerPanel.mgrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        try{
            Statement st=connectDB.createStatement();
           ResultSet rs= st.executeQuery("select * from manager where mgrid="+mgrid+"");
           while (rs.next())
           {
               UpdateNameField.setText(rs.getString("name"));
               LocalDate ld=LocalDate.parse(rs.getString("dob"));
               UpdateDobField.setValue(ld);
               UpdateGenderField.setText(rs.getString("gender"));
               UpdatePhoneField.setText(rs.getString("phone"));
               UpdateAddressField.setText(rs.getString("address"));

           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SettingUpdateAction(ActionEvent event) {
        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        try {
            Statement st=connectDB.createStatement();
            st.executeUpdate("update manager set name='"+UpdateNameField.getText()+"',dob='"+UpdateDobField.getValue()+"',gender='"+UpdateGenderField.getText()+"',phone='"+UpdatePhoneField.getText()+"',address='"+UpdateAddressField.getText()+"' where mgrid="+mgrid+"");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UpdateBtn.getScene().getWindow().hide();
        ManagerPanel.setBtn.fire();




    }
}
