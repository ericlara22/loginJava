package ericlara_ev03;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TableView tableUsers;
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

    @FXML
    private Label txtRespuesta;
    
    private Object[] users;
    private String[] profiles = {"Administrador", "Usuario"};
  
   

    
    
    public void showAlert(String titulo, String msg){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle(titulo);
                alert.setContentText(msg);
                alert.showAndWait();
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
        private void getUsers() throws SQLException {
            /*Instanciar la clase dbConnection y llamar al metodo getConnection que hace la conexión con la BD*/
            dbConnection bd = new dbConnection();
            Connection connection = bd.getConnection();
            
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT rut, nombre, apellido FROM usuarios");
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columns = rsmd.getColumnCount();
            
            while(resultSet.next()){
                for (int i = 0; i < columns; i++) {
                    users[i] = resultSet.getObject(i+1);
                }
                
                
            }
            
        }
    
    
    @FXML
        private void createUser() throws SQLException {
            
            /*Instanciar la clase dbConnection y llamar al metodo getConnection que hace la conexión con la BD*/
            dbConnection bd = new dbConnection();
            Connection connection = bd.getConnection();
   
            String rut, nombre, apellido, contrasena, perfil;
            int edad;
            
            /*Capturando datos de los text field*/
            
           
            rut = txtRut.getText();
            nombre = txtNombre.getText();
            apellido = txtApellido.getText();
            if (txtEdad.getText() != ""){
                edad = Integer.parseInt(txtEdad.getText());
            } else {
                edad = 0;
            }            
            contrasena = "123456";
            if (cbPerfil.getSelectionModel().getSelectedItem() != null) {
                perfil = cbPerfil.getSelectionModel().getSelectedItem().toString();
            } else {
                perfil = "";
            }
            

            /*Combrobador datos por consola --BORRAR DESPUES--*/
            System.out.println(rut+"\n"+nombre+"\n"+apellido+"\n"+edad+"\n"+contrasena+"\n"+perfil);
            
            if (rut.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edad == 0 || contrasena.isEmpty() || perfil.isEmpty() ) {
                showAlert("Error", "Faltan datos por completar");
            } else {
                
                try {
                    /*Conexion a la bd*/
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usuarios(rut, nombre, apellido, edad, contrasena, perfil) VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1, rut);
                    preparedStatement.setString(2, nombre);
                    preparedStatement.setString(3, apellido);
                    preparedStatement.setInt(4, edad);
                    preparedStatement.setString(5, contrasena);
                    preparedStatement.setString(6, perfil);
                    
                    preparedStatement.executeUpdate();

                    showAlert("Mensaje", "Usuario creado exitosamente");
                    
                } catch (Exception e) {
                    showAlert("Mensaje", "Error en la base de datos");
                }
            }                    
        }

            
            //Connection c = dbConnection.getConnection();
            //PreparedStatement statement = c.prepareStatement("INSERT INTO usuarios (rut, nombre, apellido, edad, contrasena, perfil) VALUES (?,?,?,?,?)");


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPerfil.getItems().addAll(profiles);

        }
    
}