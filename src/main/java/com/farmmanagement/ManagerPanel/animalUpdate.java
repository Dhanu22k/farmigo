package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class animalUpdate {

    @FXML
    private TextField searchField,nameField,farmidField,quantityField;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Label warnLabel;
    String name,farmid,aid,quantity;

    public void seacrhAction(ActionEvent event) {

        nameField.setText("");
        quantityField.setText("");
        farmidField.setText("");
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();

        String fid=ManagerPanel.farmid;
        String qry = "select animalid,name,farmid,quantity from animal where animalid='"+searchField.getText()+"'and farmid='"+fid+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet output = statement.executeQuery(qry);
            while (output.next()) {
                aid=output.getString("animalid");
                name=output.getString("name");
                nameField.setText(name);
                farmid=output.getString("farmid");
                farmidField.setText(farmid);
                quantity=output.getString("quantity");
               quantityField.setText(quantity);
                warnLabel.setText("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(nameField.getText().isBlank())
        {
            warnLabel.setText("Please enter valid crops id that comes only under you");
        }

    }

    public void upadateAction(ActionEvent event) throws SQLException {
        name=nameField.getText();
        farmid=farmidField.getText();
        quantity=quantityField.getText();

        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();

        String fid=ManagerPanel.farmid;
        String qry ="update animal set name='"+name+"',farmid='"+farmid+"',quantity="+quantity+" where animalid="+searchField.getText()+"";
        Statement statement = connectDB.createStatement();
        Alert up=new Alert(Alert.AlertType.CONFIRMATION);
        up.setHeaderText("are you sure");
        if(up.showAndWait().get()== ButtonType.OK) {
            int output = statement.executeUpdate(qry);
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully updated");
            info.showAndWait();
            updateBtn.getScene().getWindow().hide();
            ManagerPanel.alBtn.fire();
        }
    }

    public void deleteAction(ActionEvent event) throws SQLException {

        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String query="delete from animal where animalid='"+aid+"'";

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("are you sure");
        if (alert.showAndWait().get()== ButtonType.OK) {
            int output=statement.executeUpdate(query);
            nameField.setText("");
           quantityField.setText("");
            farmidField.setText("");
            searchField.setText("");
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully deleted");
            info.showAndWait();
            deleteBtn.getScene().getWindow().hide();
            ManagerPanel.alBtn.fire();

        }
    }
    }
