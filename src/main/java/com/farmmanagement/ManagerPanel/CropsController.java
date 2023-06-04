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

public class CropsController implements Initializable {
    private String mgrid;
    @FXML
    private TableView<crops> cropsTable;
    @FXML
    private TableColumn<crops,String> cropsidColumn;
    @FXML
    private TableColumn<crops,String> nameColumn;
    @FXML
    private TableColumn<crops,String> farmidColumn;
    @FXML
    private TableColumn<crops,String> sizeColumn;
    @FXML
    private TextField searchField;
    public static String farmid;
    ObservableList<crops> cropslist= FXCollections.observableArrayList();

@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerPanel mobj=new ManagerPanel();
        String mgrid=mobj.mgrid;
        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        String query1="select farmid from farm where mgrid='"+mgrid+"'";

        farmid = null;
        try{
            Statement statement=connectDB.createStatement();
            ResultSet farmOutput=statement.executeQuery(query1);
            while (farmOutput.next())
            {
                farmid=farmOutput.getString("farmid");
            }
            String query=" select * from crops where farmid='"+farmid+"'";
            ResultSet output=statement.executeQuery(query);

            while (output.next())
            {
                String cid=output.getString("cropsid");
                String cname=output.getString("name");
                String size=output.getString("size");
                String cfarmid=output.getString("farmid");
                cropslist.add(new crops(cid,cname,cfarmid,size));
            }

            cropsidColumn.setCellValueFactory(new PropertyValueFactory<>("cropsid"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("cropsname"));
            sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
            farmidColumn.setCellValueFactory(new PropertyValueFactory<>("farmid"));

            cropsTable.setItems(cropslist);

            FilteredList<crops> filteredData=new FilteredList<>(cropslist, b -> true);
            searchField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(crops -> {
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null)
                    {
                        return true;
                    }
                    String searchKeyword=newValue.toLowerCase();
                    if(crops.getCropsname().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;

                    }else if(crops.cropsid.toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            });

            SortedList<crops> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(cropsTable.comparatorProperty());
            cropsTable.setItems(sortedData);
        }
        catch (SQLException e){
            Logger.getLogger(CropsController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }



    Stage stage = new Stage();
    private Parent root;

    public void registerAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("CropsRegister.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle("Crops register panel");
        stage.setResizable(false);
        stage.show();
    }

    public void updateAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Cropsupdate.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle("Crops register panel");
        stage.setResizable(false);
        stage.show();
    }
}
