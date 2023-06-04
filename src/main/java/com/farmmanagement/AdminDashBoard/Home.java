package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Home implements Initializable {
@FXML
private Label ManagerLabel,FarmsLabel,WorkersLabel,AnimalsLabel,ProductsLabel,CropsLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        try{
            Statement st=con.createStatement();
            ResultSet rs =st.executeQuery("select count(*)as count from manager");
            while (rs.next()){
             ManagerLabel.setText(rs.getString("count"));
            }
            rs=st.executeQuery("select count(*)as count from farm");
            while (rs.next())
            {
                FarmsLabel.setText(rs.getString("count"));
            }

            rs=st.executeQuery("select count(*)as count from worker");
            while (rs.next())
            {
                WorkersLabel.setText(rs.getString("count"));
            }
            rs=st.executeQuery("select count(*)as count from product");
            while (rs.next())
            {
                ProductsLabel.setText(rs.getString("count"));
            }

            rs=st.executeQuery("select count(*)as count from animal");
            while (rs.next())
            {
                AnimalsLabel.setText(rs.getString("count"));
            }
            rs=st.executeQuery("select count(*)as count from crops");
            while (rs.next())
            {
                CropsLabel.setText(rs.getString("count"));
            }








        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
