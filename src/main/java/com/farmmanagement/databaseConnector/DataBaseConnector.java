package com.farmmanagement.databaseConnector;
import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseConnector {
    public Connection dblink;
    public Connection getConnection(){
        String database="farmigo";
        String user="root";
        String password="123456";
        String url="jdbc:mysql://localhost:3306/"+database;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink=DriverManager.getConnection(url,user,password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dblink;
    }
}
