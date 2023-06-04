package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class cropsRegister {
    @FXML
    private TextField name;
    @FXML
    private TextField size;
    @FXML
    private Button submitBtn;
    @FXML
    private Label warnLabel;

    public void formSubmt(ActionEvent event) throws IOException {
            int flag=0;
            String n=name.getText();
            String s=size.getText();
            if(n.isEmpty() || s.isEmpty())
            {
                warnLabel.setText("Please fill all the field");
            }
            if(flag==0){
                insertToDb(n,s);
            }


        }

        private void insertToDb(String n, String s) throws IOException {
            DataBaseConnector connect=new DataBaseConnector();
            Connection con= connect.getConnection();

            String farmid=ManagerPanel.farmid;
            String qry="insert into crops(name,size,farmid)values('"+n+"','"+s+"','"+farmid+"')";

            try{
                Statement st=con.createStatement();
                Alert info=new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Data succesfully updated");
                info.showAndWait();
                submitBtn.getScene().getWindow().hide();
                st.executeUpdate(qry);
                ManagerPanel.cpBtn.fire();
            }
            catch (Exception e){
                e.printStackTrace();
                Alert war=new Alert(Alert.AlertType.ERROR);
                war.setHeaderText("Error occurred");
                war.showAndWait();


            }
        }

    }

