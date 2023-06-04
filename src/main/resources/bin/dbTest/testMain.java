package dbTest;

import com.farmmanagement.databaseConnector.DataBaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class testMain {
    public static void main(String args[])
    {
        System.out.println("welcome");
        DataBaseConnector1 connect=new DataBaseConnector1();
        Connection con= connect.getConnection();
        String qry="select uname from user";

        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(qry);
            while (rs.next())
            {
                System.out.println(rs.getString("uname"));
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
}
