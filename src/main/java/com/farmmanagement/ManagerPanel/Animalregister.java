package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class Animalregister {
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private Button submitBtn;
    @FXML
    private Label warnLabel;
    public void formSubmt(ActionEvent event) throws IOException {
        int flag=0;
        String n=name.getText();
        String qty=quantity.getText();

        if(n.isEmpty() || qty.isEmpty())
        {
            warnLabel.setText("Please fill all the field");
        }
        try{
            Integer.parseInt(qty);

        }catch (NumberFormatException e){
            warnLabel.setText("Quantity should be number");
            flag=1;

        }
        if(flag==0){
            insertToDb(n,qty);
        }


    }

    private void insertToDb(String n,String qty) throws IOException {
        int s=Integer.parseInt(qty);
        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        AnimalController wobj=new AnimalController();
        String farmid=wobj.farmid;
        String qry="insert into animal(name,quantity,farmid)values('"+n+"','"+qty+"','"+farmid+"')";

        try{
            Statement st=con.createStatement();
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Data succesfully updated");
            info.showAndWait();
            submitBtn.getScene().getWindow().hide();
            st.executeUpdate(qry);
            ManagerPanel.alBtn.fire();
        }
        catch (Exception e){
            e.printStackTrace();
            Alert war=new Alert(Alert.AlertType.ERROR);
            war.setHeaderText("Error occurred");
            war.showAndWait();


        }
    }
    }

