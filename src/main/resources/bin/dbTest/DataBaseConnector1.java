package dbTest;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnector1 {
    public Connection dblink;
    public Connection getConnection(){
        String database="test";
        String user="root";
        String password="";
        String url="jdbc:mysql://localhost:3308/"+database;

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
