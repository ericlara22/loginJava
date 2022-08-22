package ericlara_ev03;

import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
    

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button buttonLogin;
    
    
    
    
    
    public void showAlert(String titulo, String msg){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle(titulo);
                alert.setContentText(msg);
                alert.showAndWait();
    }
    

  

    @FXML
    private void login() throws IOException, SQLException {
        
        dbConnection bd = new dbConnection();
        Connection connection = bd.getConnection();
        
              
        String rut = txtUser.getText();
        String password = txtPassword.getText();
        
        if(rut.equals("") || password.equals("")){
            if(rut.equals("")){
                showAlert("Error", "Debe completar el campo Usuario");
            }

            if(password.equals("")){
                showAlert("Error", "Debe ingresar su contraseña");
            }
        } else {
            
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from usuarios WHERE rut = ? AND contrasena = ?");
                preparedStatement.setString(1, rut);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                
                if (resultSet.first()) {
                    String perfil = resultSet.getString("perfil");
                    boolean admin = false;
                    if ("Administrador".equals(perfil)) {
                        admin = true;
                    } 
                    
                    
                    
                    
                    String loggedRut = resultSet.getString("rut");
                    String loggedNombre = resultSet.getString("nombre");
                    String loggedApellido = resultSet.getString("apellido"); 
                    
                    CurrentUser current = new CurrentUser();
                    current.setRut(loggedRut);
                    current.setNombre(loggedNombre);
                    current.setApellido(loggedApellido);                    
                    current.setAdmin(admin);
                    
                    App.setCurrentUser(current);
                    
                    
                    Parent root = FXMLLoader.load(getClass().getResource("users.fxml"));
                    Stage stage = (Stage)buttonLogin.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Administración de Usuarios");
                    
                    
                    //App.setRoot("users");
                    
                    
                   
                    
                    
                    
                    
                } else {
                    showAlert("Error", "Datos de ingreso invalidos");
                    txtPassword.setText("");
                }

             
            
        }
               
        /*
        
        
        javafx.scene.control.cell.PropertyValueFactory
        
        
        
        */
        
        
                
        
    }
    


    
    
}
