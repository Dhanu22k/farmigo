package com.farmmanagement.ManagerPanel;

import com.farmmanagement.Main;
import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkerUpdate {
    @FXML
    private TextField searchField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField farmidField;
    @FXML
    private Label warnLabel;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
   String name,dob,gender,farmid,salary,wid;
    public void seacrhAction(ActionEvent event) {
        nameField.setText("");
        dobField.setText("");
        salaryField.setText("");
        genderField.setText("");
        farmidField.setText("");
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();

        String fid=ManagerPanel.farmid;
        String qry = "select workerid,name,dob,salary,gender,farmid from worker where workerid='"+searchField.getText()+"'and farmid='"+fid+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet output = statement.executeQuery(qry);
            while (output.next()) {
                wid=output.getString("workerid");
                name=output.getString("name");
                nameField.setText(name);
                dob=output.getString("dob");
                dobField.setText(dob);
                salary= String.valueOf(output.getInt("salary"));
               salaryField.setText(salary);
               gender=output.getString("gender");
               genderField.setText(gender);
               farmid=output.getString("farmid");
                farmidField.setText(farmid);
                warnLabel.setText("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(nameField.getText().isBlank())
        {
            warnLabel.setText("Please enter valid worker id that comes only under you");
        }
        

    }

    public void upadateAction(ActionEvent event) throws SQLException {
        name=nameField.getText();
        dob=dobField.getText();
        salary=salaryField.getText();
        gender=genderField.getText();
        farmid=farmidField.getText();
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        WorkerController wcobj=new WorkerController();
        String fid=wcobj.farmid;
        String qry ="update worker set name='"+name+"',salary='"+salary+"',gender='"+gender+"',dob='"+dob+"',farmid='"+farmid+"' where workerid='"+wid+"'";
        Statement statement = connectDB.createStatement();
        Alert up=new Alert(Alert.AlertType.CONFIRMATION);
        up.setHeaderText("are you sure");
        if(up.showAndWait().get()==ButtonType.OK) {
            int output = statement.executeUpdate(qry);
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully updated");
            info.showAndWait();
            updateBtn.getScene().getWindow().hide();
            ManagerPanel.wkrBtn.fire();
        }

    }

    public void deleteAction(ActionEvent event) throws SQLException, IOException {
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String query="delete from worker where workerid='"+wid+"'";

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("are you sure");
        if (alert.showAndWait().get()== ButtonType.OK){
            int output=statement.executeUpdate(query);
            nameField.setText("");
            dobField.setText("");
            salaryField.setText("");
            genderField.setText("");
            farmidField.setText("");
            searchField.setText("");
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully deleted");
            info.showAndWait();
            deleteBtn.getScene().getWindow().hide();
            ManagerPanel.wkrBtn.fire();

        }
    }
}
