package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

   /* public Connection connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/pharmacySystemDatabase";
            String user = "root";
            String pass = "Userdfcec7";

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            return connection;


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        return null;
    }*/
   private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/pharmacySystemDatabase?autoReconnect=true&useSSL=false";
    private String user = "root";
    private String pass = "Userdfcec7";

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url,user,pass);
        System.out.println("Connected with database");
        return connection;
    }

}
