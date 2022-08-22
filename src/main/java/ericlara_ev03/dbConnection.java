package ericlara_ev03;
import java.io.IOException;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class dbConnection {
    private String host, port, user, db, pass;
    private Connection connector;
    
       
    public Connection getConnection() throws SQLException{
        String host, port, user, db, pass;
        host= "localhost";
        port = "3306";
        user="root";
        db = "taller3"; 
        pass="";
        Connection connector = DriverManager.getConnection(
        "jdbc:mariadb://"+host+":"+port+"/"+db,user,pass
        );
        return connector;
    }
    
    
    
}

