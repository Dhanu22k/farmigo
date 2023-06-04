package com.farmmanagement.ManagerPanel;

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

public class WorkerController implements Initializable {
    private String mgrid;
   @FXML
    private TableView<workers> workerTable;
   @FXML
    private TableColumn<workers,String> workeridColumn;
    @FXML
    private TableColumn<workers,String> nameColumn;
    @FXML
    private TableColumn<workers,String> dobColumn;
    @FXML
    private TableColumn<workers,String> salaryColumn;
    @FXML
    private TableColumn<workers,String> genderColumn;
    @FXML
    private TableColumn<workers,String> farmidColumn;
    @FXML
    private TextField searchField;
    public String farmid;
    ObservableList<workers> workerlist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerPanel mobj=new ManagerPanel();
        String mgrid=mobj.mgrid;
        farmid = mobj.farmid;
       DataBaseConnector connectNow=new DataBaseConnector();
       Connection connectDB=connectNow.getConnection();
       String query1="select farmid from farm where mgrid='"+mgrid+"'";
       

       try{
           Statement statement=connectDB.createStatement();
           ResultSet farmOutput=statement.executeQuery(query1);
           while (farmOutput.next())
           {
               farmid=farmOutput.getString("farmid");
           }
           String query=" select workerid,name,dob,salary,gender,farmid from worker where farmid='"+farmid+"'";
           ResultSet output=statement.executeQuery(query);

           while (output.next())
           {
               String wid=output.getString("workerid");
               String wname=output.getString("name");
               String wdob= output.getString("dob");
               int wsalary=output.getInt("salary");
               String wgender=output.getString("gender");
               String wfarmid=output.getString("farmid");
               workerlist.add(new workers(wid,wname,wdob,wsalary,wgender,wfarmid));
           }

           workeridColumn.setCellValueFactory(new PropertyValueFactory<>("workerid"));
           nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
           dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
           salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
           genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
          farmidColumn.setCellValueFactory(new PropertyValueFactory<>("farmid"));

          workerTable.setItems(workerlist);

           FilteredList<workers>filteredData=new FilteredList<>(workerlist,b -> true);
           searchField.textProperty().addListener((observable,oldValue,newValue)->{
               filteredData.setPredicate(workers -> {
                   if(newValue.isEmpty()||newValue.isBlank()||newValue==null)
                   {
                       return true;
                   }
                   String searchKeyword=newValue.toLowerCase();
                   if(workers.getName().toLowerCase().indexOf(searchKeyword)>-1){
                   return true;

                   }else if(workers.getWorkerid().toLowerCase().indexOf(searchKeyword)>-1){
                   return true;
                   }

                   else {
                       return false;
                   }
               });
           });

           SortedList<workers>sortedData=new SortedList<>(filteredData);
           sortedData.comparatorProperty().bind(workerTable.comparatorProperty());
           workerTable.setItems(sortedData);
       }
       catch (SQLException e){
           Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE,null,e);
           e.printStackTrace();
       }

    }

    Stage stage = new Stage();
    private Parent root;

    public void registerAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Workerregister.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.setTitle("WorkerRegister panel");
        stage.setResizable(false);
        stage.show();

    }


    public void updateAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Workerupdate.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.setTitle("Workerupdate panel");
        stage.setResizable(false);
        stage.show();
    }

}
