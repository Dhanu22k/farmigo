package com.farmmanagement;

import com.farmmanagement.AdminDashBoard.AdminDashBoard;
import com.farmmanagement.ManagerPanel.ManagerPanel;
import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private TextField userid;
    @FXML
    private TextField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label WarningLabel;



//    Validating the login button click event
    @FXML
    protected void loginBtnClick() throws IOException {
        String uid = userid.getText();
        String psw = password.getText();
        if (uid.toString().isEmpty() || psw.toString().isEmpty()) {
            WarningLabel.setText("please fill all details");

        } else {
            this.find(uid,psw);

        }
    }


    public void find(String uid,String password ){
        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        String qry="select * from user";

        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(qry);
            while (rs.next())
            {
                if(rs.getString("userid").equals(uid)&&rs.getString("password").equals(password))
                {
                   this.roleFind(rs,uid);
                   break;
                }
                else {
                    WarningLabel.setText("Username and password not matching");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }

    public void roleFind(ResultSet rs,String uid) throws IOException, SQLException {

        if(rs.getString("role").equals("admin"))
        {
            loginBtn.getScene().getWindow().hide();
            AdminDashBoard obj=new AdminDashBoard();
            obj.load(uid);


        }
        else if(rs.getString("role").equals("manager")){
            loginBtn.getScene().getWindow().hide();
            ManagerPanel obj=new ManagerPanel();
            obj.load(uid);
        }
    }

}

