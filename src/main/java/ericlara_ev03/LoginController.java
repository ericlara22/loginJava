package ericlara_ev03;

import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button buttonLogin;
    
    private String resp;

    @FXML
    private void login() throws IOException, SQLException {
        
        dbConnection c = new dbConnection();
        
        String rut = txtUser.getText();
        String password = txtPassword.getText();
        
        if(rut.equals("") || password.equals("")){
            if(rut.equals("")){
                resp = "El campo USUARIO está vacio";
            }

            if(password.equals("")){
                resp = "El campo CONTRASEÑA está vacio";
            }
        } else {
            
        }
        
        
        

        
        
        
        App.setRoot("users");
    }
    


    
    
}
