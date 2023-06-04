package com.farmmanagement.ManagerPanel;

import com.farmmanagement.Main;
import com.farmmanagement.databaseConnector.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ManagerPanel {
private Parent root;

@FXML
private Button homeBtn,workersBtn,animalBtn,cropsBtn,salesBtn,financeBtn,registerBtn,settingsBtn,logoutBtn;
public static Button hmBtn,wkrBtn,alBtn,cpBtn,slBtn,fncBtn,regBtn,setBtn,logBtn;
@FXML
private BorderPane borderpane;
@FXML
private Label idLabel;

public static String mgrid;
public static String farmid;

    Stage stage = new Stage();
    public void load(String uid) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
        root=loader.load();
        ManagerPanel obj=loader.getController();
        obj.fullLoad(uid);
        obj.homeAction();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manger panel");
        stage.setMaximized(true);
        stage.show();
    }

public void fullLoad(String uid){
    hmBtn=homeBtn;
    wkrBtn=workersBtn;alBtn=animalBtn;cpBtn=cropsBtn;slBtn=salesBtn;fncBtn=financeBtn;regBtn=registerBtn;setBtn=settingsBtn;
    DataBaseConnector connect=new DataBaseConnector();
    Connection con= connect.getConnection();
    try {
        Statement st=con.createStatement();
       ResultSet rs =st.executeQuery("select * from farm where mgrid='"+uid+"'");
       while (rs.next())
       {
           farmid=rs.getString("farmid");
       }

    }
     catch (SQLException e) {
        throw new RuntimeException(e);
    }
    idLabel.setText(uid);
    mgrid=uid;
}




    public void homeAction() throws IOException {

        AnchorPane view=FXMLLoader.load(getClass().getResource("home.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(homeBtn);
    }

    public void workersAction() throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("workers.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(workersBtn);
    }

    public void animalAction(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("animal.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(animalBtn);

    }
    public void cropsAction(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("crops.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(animalBtn);
        this.btnHighlight(cropsBtn);
    }

    public void salesAction(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("sales.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(registerBtn);
        this.btnHighlight(salesBtn);

    }
    public void registerAction() throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("register.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(registerBtn);
    }


   public void financeAction(ActionEvent event) throws IOException {
       AnchorPane view=FXMLLoader.load(getClass().getResource("finance.fxml"));
       borderpane.setCenter(view);
       this.btnHighlight(financeBtn);
    }

    public void settingsAction(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("settings.fxml"));
        borderpane.setCenter(view);
        this.btnHighlight(registerBtn);
        this.btnHighlight(settingsBtn);
    }

    public void logoutAction(ActionEvent event) throws IOException {
        this.btnHighlight(logoutBtn);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("are you sure");
        if (alert.showAndWait().get()== ButtonType.OK){
            logoutBtn.getScene().getWindow().hide();
            Main obj=new Main();
            obj.start(stage);
        }

    }


    public void btnHighlight(Button currentBtn)
    {
        Button[] btn=new Button[]{homeBtn,animalBtn,cropsBtn,registerBtn,workersBtn,salesBtn,financeBtn,settingsBtn,logoutBtn};
        for(int i=0;i<8;i++){
        if (currentBtn!=btn[i]) {
            btn[i].setStyle("-fx-background-color: #80ffcc");
        }
        else {
          currentBtn.setStyle("-fx-background-color:#00b36b");
        }
    }
    }
}
