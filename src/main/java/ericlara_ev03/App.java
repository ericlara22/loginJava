package ericlara_ev03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;



public class App extends Application {
    
    public static CurrentUser currentUser;
    
    public static void setCurrentUser (CurrentUser current){        
        App.currentUser = current;
    }
    
    public static CurrentUser getCurrentUser (){
        return currentUser;        
    }
    
    
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 400,500);
        stage.setScene(scene); 
        stage.setTitle("Iniciar sesión");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {       
       
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    
    }
    
     
    

}