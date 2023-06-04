package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {
    Stage stage = new Stage();
    private Parent root;

    public void workerRegister(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Workerregister.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("WorkerRegister panel");
        stage.setResizable(false);
        stage.show();

    }

    public void cropsRegister(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CropsRegister.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.setTitle("Crops register panel");
        stage.setResizable(false);
        stage.show();
    }

    public void animalRegister(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Animalregister.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("AnimalRegister panel");
        stage.setResizable(false);
        stage.show();
    }


    //    product register section
    @FXML
    private TextField ProductNameField;
    @FXML
    private TextField ProductSalesField;
    @FXML
    private Label warnLabel;
    @FXML
    private Button submitBtn;

    public void ProductformSubmt(ActionEvent event) {

        int flag = 0;
        String pname = ProductNameField.getText();
        String psales = ProductSalesField.getText();
        if (pname.isEmpty() || psales.isEmpty()) {
            flag = 1;
            warnLabel.setText("Please fill all the field");
        }
        if (flag == 0) {
            DataBaseConnector connect = new DataBaseConnector();
            Connection con = connect.getConnection();

            String farmid = ManagerPanel.farmid;
            String qry = "insert into product(productname,total_sales,farmid)values('" + pname + "'," + psales + ",'" + farmid + "')";

            try {
                Statement st = con.createStatement();
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Data succesfully updated");
                info.showAndWait();
                submitBtn.getScene().getWindow().hide();
                st.executeUpdate(qry);
                ManagerPanel.slBtn.fire();
            } catch (Exception e) {
                e.printStackTrace();
                Alert war = new Alert(Alert.AlertType.ERROR);
                war.setHeaderText("Error occurred");
                war.showAndWait();


            }
        }
    }

    @FXML
    private TextField ProductSearchField;
    private String fid;
    private int productid;

    public void ProductSeacrhAction(ActionEvent event) {
        ProductNameField.setText("");
        ProductSalesField.setText("");
        fid = ManagerPanel.farmid;
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        String qry = "select productid,productname,total_sales from product where productid='" + ProductSearchField.getText() + "'or productname='"+ProductSearchField.getText()+"'  and farmid='" + fid + "'";
        if (!ProductSearchField.getText().isEmpty()) {
            try {
                Statement statement = connectDB.createStatement();
                ResultSet output = statement.executeQuery(qry);
                while (output.next()) {
                    productid = output.getInt("productid");
                    ProductNameField.setText(output.getString("productname"));
                    ProductSalesField.setText(output.getString("total_sales"));
                    warnLabel.setText("");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            warnLabel.setText("Please enter valid Product id or name that ");
        }

    }

    @FXML
    private Button updateBtn;

    public void ProductUpadateAction(ActionEvent event) throws SQLException {

        if(!ProductSalesField.getText().isEmpty()||!ProductNameField.getText().isEmpty()){
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        String qry = "update product set productname='" + ProductNameField.getText() + "',total_sales=" + ProductSalesField.getText() + " where productid=+" + productid + " and  farmid='" + fid + "'";
        Statement statement = connectDB.createStatement();
        Alert up = new Alert(Alert.AlertType.CONFIRMATION);
        up.setHeaderText("are you sure");
        if (up.showAndWait().get() == ButtonType.OK) {
            int output = statement.executeUpdate(qry);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully updated");
            info.showAndWait();
            updateBtn.getScene().getWindow().hide();
            ManagerPanel.slBtn.fire();
        }
        }
        else {
            warnLabel.setText("Please fill all the details");
        }

    }

    @FXML
    private Button deleteBtn;

    public void ProductDeleteAction(ActionEvent event) throws SQLException {
        DataBaseConnector connectNow = new DataBaseConnector();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String query = "delete from product where productid=" + productid + "";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("are you sure");
        if (alert.showAndWait().get() == ButtonType.OK) {
            int output = statement.executeUpdate(query);
            ProductNameField.setText("");
            ProductSearchField.setText("");
            ProductSalesField.setText("");
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("data succesfully deleted");
            info.showAndWait();
            deleteBtn.getScene().getWindow().hide();
            ManagerPanel.slBtn.fire();

        }
    }
}


