package com.main;

import com.controller.cashier.MainMenuController;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("The Moon Restaurant");
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Images\\Logo\\logo_small.png");
        stage.getIcons().add(new Image(file.toURI().toString()));
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        ((Stage)scene.getWindow()).setMaximized(true);
    }
    
    public static void setRootWithCashierId(String fxml, int id) throws IOException{
        FXMLLoader loader = getFXMLLoader(fxml);
        Parent root = loader.load();
        
        MainMenuController controller = loader.getController();
        controller.setMaNhanVienThuNgan(id);
        scene.setRoot(root); 
        ((Stage)scene.getWindow()).setMaximized(true);       
    }
    
    public static void setRootWithWaiterId(String fxml, int id) throws IOException{
        FXMLLoader loader = getFXMLLoader(fxml);
        Parent root = loader.load();
        
        com.controller.waiter.MainMenuController controller = loader.getController();
        controller.setMaNhanVienPhucVu(id);
        scene.setRoot(root); 
        ((Stage)scene.getWindow()).setMaximized(true);       
    }
    
    public static void setRootWithCookId(String fxml, int id) throws IOException{
        FXMLLoader loader = getFXMLLoader(fxml);
        Parent root = loader.load();
        
        com.controller.cook.MainMenuController controller = loader.getController();
        controller.setMaDauBep(id);
        scene.setRoot(root); 
        ((Stage)scene.getWindow()).setMaximized(true);       
    }
    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/view/" + fxml + ".fxml"));
        return fxmlLoader;
    }
    
    public static void main(String[] args) {
        launch();
    }

}