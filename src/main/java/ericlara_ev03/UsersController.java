package ericlara_ev03;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsersController implements Initializable{

    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    
    @FXML
    private TableView<?> tableUsers;
    @FXML
    private TextField txtRut;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEdad;
    @FXML
    private ComboBox<String> cbPerfil;
    private String resp;
    private String[] profiles = {"Administrador", "Usuario"};
    @FXML
    private Label txtRespuesta;
  
    
    public String createUser(String rut, String nombre, String apellido, int edad, String contrasena){
        resp= "Rut Válido";
        if(ValidarRut(rut)){
            
        } else {
            resp = "El rut es inválido";
        }
        return resp;
    }
    
    
    public Boolean ValidarRut(String rut){
    int op=2, sum=0;
       
    String rutPart = rut.substring(0, rut.length()-2);
    String verificador = rut.substring(rut.length()-1,rut.length());

       
        for (int i = rutPart.length()-1; i >= 0; i--) {
            sum += Integer.parseInt(rutPart.charAt(i)+"")*op;
            op++;
            if (op > 7) {
                op = 2;
            }

        }
       
    int resto = 11-sum%11;
    String digitoFinal;
        if (resto == 10) {
            digitoFinal="K";
        } else {
            if (resto == 11) {
                digitoFinal="0";
            } else {
                digitoFinal=resto+"";
            }
    }
           
        if (digitoFinal.equals(verificador)) {
            return true;

        } else{
            return false;
        }
   
    }
    
    @FXML
        private void createUser() throws SQLException {
            
            try {
            String rut = txtRut.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String contrasena = "123456";
            String perfil = cbPerfil.getSelectionModel().getSelectedItem().toString();
            System.out.println(perfil);
            System.out.println(nombre);
            resp = "Usuario creado exitosamente";
            txtRespuesta.setText(resp);
        } catch (Exception e) {
            resp = "Faltan datos que ingresar";
            txtRespuesta.setText(resp);
        }
            
                
            
            //Connection c = dbConnection.getConnection();
            //PreparedStatement statement = c.prepareStatement("INSERT INTO usuarios (rut, nombre, apellido, edad, contrasena, perfil) VALUES (?,?,?,?,?)");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPerfil.getItems().addAll(profiles);
    }
    
}