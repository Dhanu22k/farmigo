package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Workerregister {
    @FXML
    private TextField wname;
    @FXML
    private TextField gender;
    @FXML
    private TextField salary;
    @FXML
    private DatePicker dob;
    @FXML
    private Button submitBtn;
    @FXML
    private Label warnLabel;

    public void formSubmt(ActionEvent event) throws IOException {
        int flag=0;
       String name=wname.getText();
       String gdr=gender.getText();
       String sal=salary.getText();
       String d= String.valueOf(dob.getValue());
        if(name.isEmpty() || gdr.isEmpty()||sal.isEmpty()||d.isEmpty())
        {
            warnLabel.setText("Please fill all the field");
        }
        try{
            Integer.parseInt(sal);

        }catch (NumberFormatException e){
            warnLabel.setText("salary should be number");
            flag=1;

        }
        if(flag==0){
            insertToDb(name,gdr,sal,d);
        }


    }

    private void insertToDb(String name, String gdr, String sal, String d) throws IOException {
        int s=Integer.parseInt(sal);
        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();

        String farmid=ManagerPanel.farmid;
        System.out.println(ManagerPanel.farmid);
        String qry="insert into worker(name,dob,salary,gender,farmid)values('"+name+"','"+d+"',"+s+",'"+dob.getValue()+"','"+farmid+"')";

        try{
            Statement st=con.createStatement();
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully updated");
            info.showAndWait();
            submitBtn.getScene().getWindow().hide();
            st.executeUpdate(qry);
            ManagerPanel.wkrBtn.fire();
        }
        catch (Exception e){
            e.printStackTrace();
            Alert war=new Alert(Alert.AlertType.ERROR);
            war.setHeaderText("Error occurred");
            war.showAndWait();


        }
    }


}
