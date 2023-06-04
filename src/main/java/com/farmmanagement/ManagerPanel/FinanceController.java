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

public class FinanceController implements Initializable {

    @FXML
    private BarChart IncomeBarChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int count=0;
        finance f[]=new finance[20];

        DataBaseConnector connect=new DataBaseConnector();
        Connection con= connect.getConnection();
        String qry="select * from finance where farmid='"+ManagerPanel.farmid+"' order by year";
        try {
            Statement st=con.createStatement();
            ResultSet rs1= st.executeQuery("select count(*)as count from finance where farmid='"+ManagerPanel.farmid+"'");

            while (rs1.next())
            {
                count= rs1.getInt("count");
            }
            ResultSet rs2=st.executeQuery(qry);
            int i=0;
            while (rs2.next()){
                f[i]=new finance(rs2.getString("farmid"),rs2.getInt("expenditure"),rs2.getInt("income"),rs2.getString("year"));
                i++;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //series 1 expenditure
        XYChart.Series series1=new XYChart.Series();
        series1.setName("Expenditure");
        for (int i=0;i<count;i++) {
            series1.getData().add(new XYChart.Data(f[i].year ,f[i].expenditure));
        }

        XYChart.Series series2=new XYChart.Series();
        series2.setName("Income");
        for (int i=0;i<count;i++) {
            series2.getData().add(new XYChart.Data(f[i].year ,f[i].income));
        }



        IncomeBarChart.getData().addAll(series1,series2);

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


    public void EnterDataAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FinanceData.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle("Data panel");
        stage.setResizable(false);
        stage.show();

    }


}

class finance{
    String farmid;
    int expenditure;
    int income;
    String year;

    public finance(String farmid, int expenditure, int income, String year) {
        this.farmid = farmid;
        this.expenditure = expenditure;
        this.income = income;
        this.year = year;
    }
}
