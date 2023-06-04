package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class cropsUpdate {


@FXML
private TextField searchField,nameField,farmidField,sizeField;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Label warnLabel;
    String name,farmid,cid,size;
    public void seacrhAction(ActionEvent event) {
        nameField.setText("");
        sizeField.setText("");
        farmidField.setText("");
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();

        String fid=ManagerPanel.farmid;
        String qry = "select cropsid,name,farmid,size from crops where cropsid='"+searchField.getText()+"'and farmid='"+fid+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet output = statement.executeQuery(qry);
            while (output.next()) {
                cid=output.getString("cropsid");
                name=output.getString("name");
                nameField.setText(name);
                farmid=output.getString("farmid");
                farmidField.setText(farmid);
                size=output.getString("size");
                sizeField.setText(size);
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
        size=sizeField.getText();

        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();

        String fid=ManagerPanel.farmid;
        String qry ="update crops set name='"+name+"',farmid='"+farmid+"',size='"+size+"' where cropsid='"+cid+"'";
        Statement statement = connectDB.createStatement();
        Alert up=new Alert(Alert.AlertType.CONFIRMATION);
        up.setHeaderText("are you sure");
        if(up.showAndWait().get()== ButtonType.OK) {
            int output = statement.executeUpdate(qry);
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully updated");
            info.showAndWait();
            updateBtn.getScene().getWindow().hide();
            ManagerPanel.cpBtn.fire();
        }

    }

    public void deleteAction(ActionEvent event) throws SQLException {
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String query="delete from crops where cropsid='"+cid+"'";

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("are you sure");
        if (alert.showAndWait().get()== ButtonType.OK) {
            int output=statement.executeUpdate(query);
            nameField.setText("");
            sizeField.setText("");
            farmidField.setText("");
            searchField.setText("");
            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully deleted");
            info.showAndWait();
            deleteBtn.getScene().getWindow().hide();
            ManagerPanel.cpBtn.fire();

           }
        }

    }

