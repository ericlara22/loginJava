package ericlara_ev03;
import java.sql.*;


public class dbConnection {
    private String host, port, user, db, pass;
    private Connection connector;
    
    
    dbConnection() throws SQLException{
        host= "localhost";
        port = "3306";
        user="root";
        db = "taller3"; 
        pass="";
        
        connector = DriverManager.getConnection(
        "jdbc:mariadb://"+host+":"+port+"/"+db,user,pass
        );
        System.out.println("Connection OK!");
        
        
        
    }
    
    public static Connection getConnection() throws SQLException{
        String host, port, user, db, pass;
        host= "localhost";
        port = "3306";
        user="root";
        db = "taller3"; 
        pass="";
        Connection connector = DriverManager.getConnection(
        "jdbc:mariadb://"+host+":"+port+"/"+db,user,pass
        );
        System.out.println("Connection OK!");
        return connector;
    }
    
    
    
        public boolean login(String rut, String password) throws SQLException {
            boolean status = false;

                PreparedStatement statement = connector.prepareStatement(
                
                """
                SELECT rut FROM usuarios WHERE 
                """
                
                );
            
            return status;
            
        }
    

        public void showUsers() throws SQLException {
            Statement statement = connector.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios");
            while(resultSet.next() ){
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                
            }
            
        }
        
        public void createUser(String rut, String nombre, String apellido, int edad, String contrasena) throws SQLException{
            try(PreparedStatement statement = connector.prepareStatement(
                """ 
                INSERT INTO USUARIOS (rut, nombre, apellido, edad, contrasena)
                VALUES (?,?,?,?)
                """
            )){
                statement.setString(0,rut);
                statement.setString(1,nombre);
                statement.setString(2,apellido);
                statement.setInt(3,edad);
                statement.setString(4,contrasena);
                statement.executeUpdate();
            }
        }
    
}

