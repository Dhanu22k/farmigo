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


public class AnimalController implements Initializable {

    private String mgrid;
    @FXML
    private TableView<animals> animalTable;
    @FXML
    private TableColumn<animals,String> animalidColumn;
    @FXML
    private TableColumn<animals,String> nameColumn;
    @FXML
    private TableColumn<animals,String> quantityColumn;
    @FXML
    private TableColumn<animals,String> farmidColumn;
    @FXML
    private TextField searchField;
    public static String farmid;
    ObservableList<animals> animalslist= FXCollections.observableArrayList();

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
            String query=" select animalid,farmid,name,quantity from animal where farmid='"+farmid+"'";
            ResultSet output=statement.executeQuery(query);

            while (output.next())
            {
                String id=output.getString("animalid");
                String name=output.getString("name");
                String quantity=output.getString("quantity");
                String farmid=output.getString("farmid");

                animalslist.add(new animals(id,name,quantity,farmid));
            }

            animalidColumn.setCellValueFactory(new PropertyValueFactory<>("animalid"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
           quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            farmidColumn.setCellValueFactory(new PropertyValueFactory<>("farmid"));

            animalTable.setItems(animalslist);

            FilteredList<animals> filteredData=new FilteredList<>(animalslist, b -> true);
            searchField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(animals -> {
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null)
                    {
                        return true;
                    }
                    String searchKeyword=newValue.toLowerCase();
                    if( animals.getName().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;

                    }else if(animals.getAnimalid().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            });

            SortedList<animals> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(animalTable.comparatorProperty());
            animalTable.setItems(sortedData);
        }
        catch (SQLException e){
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }


    Stage stage = new Stage();
    private Parent root;
    public void registerAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Animalregister.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.setTitle("AnimalRegister panel");
        stage.setResizable(false);
        stage.show();
    }

    public void updateAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("animalUpdate.fxml"));
        root=loader.load();
        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.setTitle("AnimalRegister panel");
        stage.setResizable(false);
        stage.show();

    }
}
