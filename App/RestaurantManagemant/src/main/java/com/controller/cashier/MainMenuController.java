/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cashier;

import com.jfoenix.controls.JFXButton;
import com.main.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class MainMenuController implements Initializable {

    @FXML
    private JFXButton btnTiepNhan;
    @FXML
    private JFXButton btnThanhToan;
    @FXML
    private JFXButton btnCaTruc;
    @FXML
    private AnchorPane pnMain;
    
    private int maNhanVienThuNgan;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void setMaNhanVienThuNgan(int maNhanVienThuNgan) {        
        
        this.maNhanVienThuNgan = maNhanVienThuNgan;
        try {
            btnFunctionClick(new ActionEvent(null, btnTiepNhan));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    
    @FXML
    private void btnFunctionClick(ActionEvent event) throws IOException {
        JFXButton[] buttons = {btnTiepNhan, btnThanhToan, btnCaTruc};
        for(JFXButton button : buttons)
            button.getStyleClass().remove("btnFunction-active");
        ((JFXButton) event.getTarget()).getStyleClass().add("btnFunction-active");
        
        String formName = "";
        FXMLLoader loader;
        Parent root;
        if (event.getTarget() == btnThanhToan){
            formName = "cashier/ThanhToan";
            loader = App.getFXMLLoader(formName);
            root = loader.load();
            ThanhToanController controller = loader.getController();
            controller.setMaNhanVien(this.maNhanVienThuNgan);
        }            
        else if (event.getTarget() == btnTiepNhan){
            formName = "cashier/TiepNhan";
            loader = App.getFXMLLoader(formName);
            root = loader.load();
            TiepNhanController controller = loader.getController();
            controller.maNhanVien = this.maNhanVienThuNgan;
        }            
        else 
            return;

   
            try {
                pnMain.getChildren().remove(0);
            } catch (Exception e) {
            }
            
            
            loader.getController();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            pnMain.getChildren().add(root);
              
    }
    
}
