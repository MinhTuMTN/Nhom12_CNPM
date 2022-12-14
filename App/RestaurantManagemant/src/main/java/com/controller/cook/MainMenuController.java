/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cook;

import com.controller.waiter.QuanLyDonHangController;
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

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class MainMenuController implements Initializable {

    @FXML
    private JFXButton btnCaTruc;
    @FXML
    private JFXButton btnXemDonHang;
    @FXML
    private AnchorPane pnMain;
    
    private Integer maDauBep;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnFunctionClick(ActionEvent event) throws IOException {
        JFXButton[] buttons = {btnCaTruc, btnXemDonHang};
        for (JFXButton button : buttons) {
            button.getStyleClass().remove("btnFunction-active");
        }
        ((JFXButton) event.getTarget()).getStyleClass().add("btnFunction-active");

        String formName = "";
        FXMLLoader loader;
        Parent root;
        if (event.getTarget() == btnXemDonHang) {
            formName = "cook/XemThongTinDonHang";
            loader = App.getFXMLLoader(formName);
            root = loader.load();
            XemThongTinDonHangController controller = loader.getController();
            controller.setMaDauBep(this.maDauBep);
        } else {
            return;
        }

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

    public void setMaDauBep(Integer maDauBep) {
        this.maDauBep = maDauBep;
        try {
            btnFunctionClick(new ActionEvent(null, btnXemDonHang));
        } catch (IOException ex) {
            Logger.getLogger(com.controller.waiter.MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
