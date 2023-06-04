package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
@FXML
private Label nameField,dobField,farmidField,genderField;
    String mgrid;
    String farmid;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       mgrid =ManagerPanel.mgrid;
        farmid=ManagerPanel.farmid;
        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        String query1="select * from manager where mgrid='"+mgrid+"'";
        try {
            Statement statement=connectDB.createStatement();
           ResultSet rs= statement.executeQuery(query1);
           while (rs.next())
           {
               nameField.setText(rs.getString("name"));
               dobField.setText(rs.getString("dob"));
               genderField.setText(rs.getString("gender"));
               farmidField.setText(farmid);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    Stage stage = new Stage();
    private Parent root;
    public void ChangeBioAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("BioUpdate.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,600,450);
        stage.setScene(scene);
        stage.setTitle("Change Bio panel");
        stage.setResizable(false);
        stage.show();

    }


    public void ChangePasswordAction(ActionEvent event) {
        String password,NewPassword;

        int count = 0;
        TextInputDialog td= new TextInputDialog();
        td.setHeaderText("Enter your current password");
        td.setResizable(false);
        Optional<String> passObj= td.showAndWait();
        password= passObj.get();

        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        try {
            Statement st = connectDB.createStatement();
            ResultSet rs = st.executeQuery("select count(*)as count from user where userid=" + mgrid + " and password='"+password+"'");
            while (rs.next()) {
                count = rs.getInt("count");
            }
            if (count == 1)
            {
                TextInputDialog td2= new TextInputDialog();

                td2.setHeaderText("Enter your new password");
                td2.setResizable(false);
                Optional<String> pass= td2.showAndWait();
                NewPassword= pass.get();
                int out=st.executeUpdate("update user set password='"+NewPassword+"' where userid="+mgrid+"");
                if(out==1)
                {
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Password changed");
                    a.showAndWait();
                    ManagerPanel.setBtn.fire();
                }
            }
            else{
                Alert a=new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Password is not valid");
                a.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



}
