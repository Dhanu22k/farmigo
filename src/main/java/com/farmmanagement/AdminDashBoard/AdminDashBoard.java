package com.farmmanagement.AdminDashBoard;

import com.farmmanagement.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminDashBoard implements Initializable {
    private Parent root;
    static String userid;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label idField;
    @FXML
    private Pane homeBtn,managerBtn,farmsBtn,aboutBtn,logoutBtn;

    Stage stage = new Stage();
    public void load(String uid) throws IOException{
        userid=uid;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("AdminDashBoard.fxml"));
        root=loader.load();
        AdminDashBoard obj=loader.getController();
        obj.HomeAction();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("AdminDashBoard");
        stage.setMaximized(true);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    idField.setText(userid);

    }
    public void HomeAction() throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("home.fxml"));
        borderPane.setCenter(view);
        this.btnHighlight(homeBtn);
    }

    public void ManagerAction(MouseEvent mouseEvent) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("Managers.fxml"));
        borderPane.setCenter(view);
        this.btnHighlight(managerBtn);
    }
    public void farmsAction(MouseEvent mouseEvent) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("farms.fxml"));
        borderPane.setCenter(view);
        this.btnHighlight(farmsBtn);
    }


    public void AboutAction(MouseEvent mouseEvent) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("about.fxml"));
        borderPane.setCenter(view);
        this.btnHighlight(aboutBtn);
    }

    public void LogoutAction(MouseEvent mouseEvent) throws IOException {
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
    public void btnHighlight(Pane currentBtn)
    {
        Pane[] btn=new Pane[]{homeBtn,managerBtn,farmsBtn,aboutBtn,logoutBtn};
        for(int i=0;i<5;i++){
            if (currentBtn!=btn[i]) {
                btn[i].setStyle("-fx-background-color: #80ffcc");
            }
            else {
                currentBtn.setStyle("-fx-background-color:#00b36b");
            }
        }
    }


}



