package ericlara_ev03;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UsersController implements Initializable{
    
    private Object[] users;
    private String[] profiles = {"Administrador", "Usuario"};

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
    
    @FXML
    private TableColumn <UserModel, String> columnRut;
    @FXML
    private TableColumn <UserModel, String> columnNombre;
    @FXML
    public TableColumn <UserModel, String> columnApellido;
    @FXML
    public ObservableList<UserModel> userList;
    
    private int index=-1;

    

    
    /*Metodo para mostrar alertas que toma un titulo y un mensaje*/
    public void showAlert(String titulo, String msg){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle(titulo);
                alert.setContentText(msg);
                alert.showAndWait();
    }
    
    public Optional<ButtonType> showConfirmation(String titulo, String msg){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle(titulo);
                alert.setContentText(msg);
                Optional<ButtonType> result = alert.showAndWait();
                return result;
                
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
        public ObservableList<UserModel> getUsers() throws SQLException {
            /*Instanciar la clase dbConnection y llamar al metodo getConnection que hace la conexión con la BD*/
            dbConnection bd = new dbConnection();
            Connection connection = bd.getConnection();
            
            
            userList = FXCollections.observableArrayList();
            
            
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuarios");
                ResultSet resultSet = preparedStatement.executeQuery();
                
                while(resultSet.next()){
                    
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int edad = resultSet.getInt("edad");
                String contrasena = resultSet.getString("contrasena");
                String perfil = resultSet.getString("perfil");
                
                userList.add(new UserModel(rut, nombre, apellido, contrasena, perfil, edad));

            }
                
            } catch (Exception e) {
                showAlert("Error", "Error en la base de datos");
            }
  
            return userList;       
            
        }
        
   @FXML
        private void getSelected() throws SQLException {
            dbConnection bd = new dbConnection();
            Connection connection = bd.getConnection();
            
            index = tableUsers.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                
                return;
            }
            
            String clickedRut = columnRut.getCellData(index).toString();
            
            
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuarios WHERE rut = ? ");
                preparedStatement.setString(1, clickedRut);

                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
    
                
                txtRut.setText(resultSet.getString("rut"));
                txtNombre.setText(resultSet.getString("nombre"));
                txtApellido.setText(resultSet.getString("apellido"));
                txtEdad.setText(String.valueOf(resultSet.getInt("edad")));                
                cbPerfil.setValue(resultSet.getString("perfil"));

    
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
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
            
            
            /*Valida int y selector vacios vacios y los transforma a valores falsy de lo contrario da error al asignar un valor vacio*/            
            if (txtEdad.getText() != ""){
                edad = Integer.parseInt(txtEdad.getText());
            } else {
                edad = 0;
            }            
            contrasena = "123456";
            if (cbPerfil.getSelectionModel().getSelectedItem() != null) {
                perfil = cbPerfil.getSelectionModel().getSelectedItem();
            } else {
                perfil = "";
            }
            
            
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
            /*ACTUALIZA TABLA*/
            init();
        }

    @FXML
        private void updateUser() throws SQLException {
            if (txtRut.getText() != "") {
            dbConnection bd = new dbConnection();
            Connection connection = bd.getConnection();

            Optional<ButtonType> result = showConfirmation("Editar Usuario", "Esta seguro que desea editar el usuario?");

                if (result.get() == ButtonType.OK) {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usuarios SET rut = ?, nombre = ?, apellido = ?, edad = ?, contrasena = ?, perfil = ? WHERE rut = ?");
                    preparedStatement.setString(1, txtRut.getText());
                    preparedStatement.setString(2, txtNombre.getText());
                    preparedStatement.setString(3, txtApellido.getText());
                    preparedStatement.setInt(4, Integer.parseInt(txtEdad.getText() ));
                    preparedStatement.setString(5, "123456");
                    preparedStatement.setString(6, cbPerfil.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(7, txtRut.getText());                  
                                                           
                    
                    preparedStatement.executeUpdate();
                    showAlert("Confirmación", "Usuario actualizado");
                } else {

                }   
            } else {
                showAlert("Error", "Ningún usuario seleccionado");
            }         
                            
            
            init();
        
        }

    @FXML
        private void deleteUser() throws SQLException {
            /*Primero se valida que hayan usuarios seleccionados*/
            /*-PRENDIENTE VALIDAD QUE SEA ADMIN Y QUE NO SE ELIMINE A SI MISMO--*/
            if (txtRut.getText() != "") {
                dbConnection bd = new dbConnection();
                Connection connection = bd.getConnection();

                getSelected();

                Optional<ButtonType> result = showConfirmation("Borrar Usuario", "Esta seguro que desea borrar el usuario?");

                if (result.get() == ButtonType.OK) {
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usuarios WHERE rut = ?");
                    preparedStatement.setString(1, txtRut.getText());
                    preparedStatement.executeQuery();
                } else {

                }   
            } else {
                showAlert("Error", "Ningún usuario seleccionado");
            }         
                            
            
            init();
            
        
        
        
        }
    
    public void init() throws SQLException{
        getUsers();
        tableUsers.setItems(userList);
        txtRut.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");             
        cbPerfil.setValue("");
        
    }

    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getUsers();
        } catch (SQLException ex) {
            showAlert("Error", "No hay usuarios");
        }
  
        
        
        cbPerfil.getItems().addAll(profiles);
        
        columnRut.setCellValueFactory(new PropertyValueFactory<UserModel, String>("rut"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<UserModel, String>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<UserModel, String>("apellido"));
        
        tableUsers.setItems(userList);
        
        
        }
    
}