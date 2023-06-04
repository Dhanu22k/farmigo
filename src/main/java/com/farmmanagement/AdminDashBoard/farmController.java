package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class farmController implements Initializable {

    @FXML
    private TableView<Farms> FarmTable;
    @FXML
    private TableColumn<Farms,String> idColumn;
    @FXML
    private TableColumn<Farms,String> mgridColumn;
    @FXML
    private TableColumn<Farms,String> landsizeColumn;
    @FXML
    private TableColumn<Farms,String> locationColumn;
    @FXML
    private TextField searchField;


    ObservableList<Farms> Farmlist= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnector connectNow=new DataBaseConnector();
        Connection connectDB=connectNow.getConnection();
        String query="select * from farm";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery(query);

            while (rs.next())
            {
                String id=rs.getString("farmid");
                int mgrid=rs.getInt("mgrid");
                String landsize = rs.getString("land_size");
                String location=rs.getString("location");
                Farmlist.add(new Farms(id,mgrid,landsize,location));
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<>("farmid"));
            mgridColumn.setCellValueFactory(new PropertyValueFactory<>("mgrid"));
            landsizeColumn.setCellValueFactory(new PropertyValueFactory<>("landsize"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

            FarmTable.setItems(Farmlist);

            FilteredList<Farms> filteredData=new FilteredList<>(Farmlist, b -> true);
            searchField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(Farms-> {
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null)
                    {
                        return true;
                    }
                    String searchKeyword=newValue.toLowerCase();
                    if(Farms.getFarmid().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;

                    }else if(Farms.getLocation().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }

                    else {
                        return false;
                    }
                });
            });

            SortedList<Farms> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(FarmTable.comparatorProperty());
            FarmTable.setItems(sortedData);

        }
        catch (SQLException e){
            Logger.getLogger(farmController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }

@FXML
private CheckBox checkField;
    @FXML
    private Toggle UpdateEnableBtn;

    @FXML
    private Button registerBtn,updateBtn;

    @FXML private TextField farmidField,mgridField,landsizeField,locationField;
    public void RegisterAction(ActionEvent event) {
        if (!farmidField.getText().isEmpty() && !mgridField.getText().isEmpty() && !landsizeField.getText().isEmpty() && !locationField.getText().isEmpty()) {
            DataBaseConnector connectNow = new DataBaseConnector();
            Connection connectDB = connectNow.getConnection();
            try {
                Statement st=connectDB.createStatement();
                String query="insert into farm values('"+farmidField.getText()+"',"+mgridField.getText()+",'"+landsizeField.getText()+"','"+locationField.getText()+"')";
                if(!st.execute(query))
                {
                    Alert al=new Alert(Alert.AlertType.INFORMATION);
                    al.setHeaderText("Register Successfull");
                    al.showAndWait();
                }


            } catch (SQLException e) {
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Error Occured Please fill all the details correctly");
                al.showAndWait();
                throw new RuntimeException(e);

            }


        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Enter all the Field");
            al.showAndWait();
        }


    }

    public void checkboxAction(ActionEvent event) {
        if(checkField.isSelected())
        {
            registerBtn.setDisable(false);
            farmidField.setEditable(true);
            mgridField.setEditable(true);
            landsizeField.setEditable(true);
            locationField.setEditable(true);

        }
        else {
            registerBtn.setDisable(true);
            farmidField.setEditable(false);
            mgridField.setEditable(false);
            landsizeField.setEditable(false);
            locationField.setEditable(false);
        }
    }


    public void UpdateCheckBoxAction(ActionEvent event) {
             String id;
            mgridField.setEditable(true);
            landsizeField.setEditable(true);
            locationField.setEditable(true);
            TextInputDialog td=new TextInputDialog();
            td.setHeaderText("Enter farm id or manager id");
           td.showAndWait();
            id= td.getResult();
            if(id!=null) {
                checkField.setDisable(true);
                updateBtn.setDisable(false);
                registerBtn.setDisable(true);
                DataBaseConnector connectNow = new DataBaseConnector();
                Connection connectDB = connectNow.getConnection();
                try {
                    Statement st = connectDB.createStatement();
                    ResultSet rs = st.executeQuery("select * from farm where farmid='" + id + "'");
                    while (rs.next()) {
                        farmidField.setText(rs.getString("farmid"));
                        mgridField.setText(rs.getString("mgrid"));
                        landsizeField.setText(rs.getString("land_size"));
                        locationField.setText(rs.getString("location"));
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("id+null");
                checkField.setDisable(false);
                updateBtn.setDisable(true);
                farmidField.setText("");
                mgridField.setText("");
                landsizeField.setText("");
                locationField.setText("");

            }


    }


    public void updateAction(ActionEvent event) {
        if (!farmidField.getText().isEmpty() && !mgridField.getText().isEmpty() && !landsizeField.getText().isEmpty() && !locationField.getText().isEmpty()) {
            DataBaseConnector connectNow = new DataBaseConnector();
            Connection connectDB = connectNow.getConnection();
            try {
                Statement st=connectDB.createStatement();

                String query="update farm set mgrid="+mgridField.getText()+",land_size='"+landsizeField.getText()+"',location='"+locationField.getText()+"' where farmid='"+farmidField.getText()+"'";
                if(!st.execute(query))
                {
                    Alert al=new Alert(Alert.AlertType.INFORMATION);
                    al.setHeaderText("Update Successfull");
                    al.showAndWait();
                }


            } catch (SQLException e) {
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Error Occured Please fill all the details correctly");
                al.showAndWait();
                throw new RuntimeException(e);

            }


        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Enter all the Field");
            al.showAndWait();
        }





        checkField.setDisable(false);
        farmidField.setText("");
        mgridField.setText("");
        landsizeField.setText("");
        locationField.setText("");

    }
}
