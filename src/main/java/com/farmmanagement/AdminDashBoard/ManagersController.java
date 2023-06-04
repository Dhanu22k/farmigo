package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.ManagerPanel.ManagerPanel;
import com.farmmanagement.ManagerPanel.workers;
import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagersController implements Initializable {
    String mgrid=AdminDashBoard.userid;
    @FXML
    private TableView<Managers> ManagerTable;
    @FXML
    private TableColumn<Managers,String> idColumn;
    @FXML
    private TableColumn<Managers,String> nameColumn;
    @FXML
    private TableColumn<Managers,String> dobColumn;
    @FXML
    private TableColumn<Managers,String> genderColumn;
    @FXML
    private TableColumn<Managers,String> salaryColumn;
    @FXML
    private TableColumn<Managers,String> phoneColumn;
    @FXML
    private TableColumn<Managers,String> addressColumn;

    @FXML
    private TextField searchField;


    ObservableList<Managers> Managerlist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        String query="select * from manager";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery(query);

            while (rs.next())
            {
                String id=rs.getString("mgrid");
                String name=rs.getString("name");
                String dob= rs.getString("dob");
                int salary=rs.getInt("salary");
                String gender=rs.getString("gender");
                String phone=rs.getString("phone");
                String address=rs.getString("address");
                Managerlist.add(new Managers(id,name,dob,salary,gender,phone,address));
            }

           idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

            ManagerTable.setItems(Managerlist);

            FilteredList<Managers> filteredData=new FilteredList<>(Managerlist, b -> true);
            searchField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(Managers-> {
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null)
                    {
                        return true;
                    }
                    String searchKeyword=newValue.toLowerCase();
                    if(Managers.getName().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;

                }else if(Managers.id.toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }

                    else {
                        return false;
                    }
                });
            });

            SortedList<Managers> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ManagerTable.comparatorProperty());
            ManagerTable.setItems(sortedData);

        }
        catch (SQLException e){
            Logger.getLogger(ManagersController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }
    Stage stage = new Stage();
    private Parent root;
    public void RegisterAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ManagerRegister.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.setTitle("WorkerRegister panel");
        stage.setResizable(false);
        stage.show();

    }

    public void RemoveAction(ActionEvent event) {
    }

    public void BlockAction(ActionEvent event) {
    }
}
