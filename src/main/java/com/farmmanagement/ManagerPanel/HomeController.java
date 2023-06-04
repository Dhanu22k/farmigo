package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label mgridField;
    @FXML
    private Label farmidField;
    @FXML
    private Label locationField;
    @FXML
    private Label landsizeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        String farmid = null;
        String location = null;
        String landsize = null;
        String mid= ManagerPanel.mgrid;
        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        String qry="select * from farm where mgrid="+mid;
        try{
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(qry);
            while (rs.next())
            {
                farmid=rs.getString("farmid");
                landsize=rs.getString("land_size");
                location=rs.getString("location");
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
        farmidField.setText("Farm id:"+farmid);
        locationField.setText("Location:"+location);
        landsizeField.setText("Land size:"+landsize);
    }

}
