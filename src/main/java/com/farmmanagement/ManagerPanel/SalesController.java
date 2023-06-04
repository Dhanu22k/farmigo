package com.farmmanagement.ManagerPanel;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    @FXML
    private BarChart salesBarChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int count=0;
        product p[]=new product[20];

        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        String qry="select * from product where farmid='"+ManagerPanel.farmid+"'";
            try {
                Statement st=con.createStatement();
               ResultSet rs1= st.executeQuery("select count(*)as count from product where farmid='"+ManagerPanel.farmid+"'");

               while (rs1.next())
               {
                 count= rs1.getInt("count");
               }
               ResultSet rs2=st.executeQuery(qry);
                int i=0;
               while (rs2.next()){
                   p[i]=new product(rs2.getInt("productid"),rs2.getString("productname"),rs2.getString("farmid"),rs2.getInt("total_sales"));
                   i++;
               }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        XYChart.Series series=new XYChart.Series();
        series.setName("Product");
        for (int i=0;i<count;i++) {
            series.getData().add(new XYChart.Data(p[i].productname ,p[i].total_sales));
        }

        salesBarChart.getData().addAll(series);

    }
    Stage stage = new Stage();
    private Parent root;
    public void registerAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ProductRegister.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle("Product register panel");
        stage.setResizable(false);
        stage.show();
    }

    public void updateAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("productUpdate.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle("Product update panel");
        stage.setResizable(false);
        stage.show();
    }
}

class product {
    public int productid;
    public String productname;
    public String  farm_id;
    public int total_sales;

    public product(int productid, String productname, String farm_id, int total_sales) {
        this.productid = productid;
        this.productname = productname;
        this.farm_id = farm_id;
        this.total_sales = total_sales;
    }


}

