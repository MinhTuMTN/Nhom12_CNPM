/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.manager;

import com.jfoenix.controls.JFXButton;
import com.main.App;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class MainMenuController implements Initializable {

    @FXML
    private JFXButton btnCaTruc;
    @FXML
    private JFXButton btnLuong;
    @FXML
    private JFXButton btnTaiKhoan;
    @FXML
    private JFXButton btnBan;
    @FXML
    private JFXButton btnNhanVien;
    @FXML
    private JFXButton btnMonAn;
    @FXML
    private JFXButton btnThongKe;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane pnMain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnFunctionClick(new ActionEvent(null, btnMonAn));
    }

    @FXML
    private void btnFunctionClick(ActionEvent event) {
        JFXButton[] buttons = {btnBan, btnCaTruc, btnLuong, btnMonAn, btnNhanVien, btnTaiKhoan, btnThongKe};
        for (JFXButton button : buttons) {
            button.getStyleClass().remove("btnFunction-active");
        }
        ((JFXButton) event.getTarget()).getStyleClass().add("btnFunction-active");

        String formName = "";
        if (event.getTarget() == btnMonAn) {
            formName = "manager/QuanLyMonAn";
        } else if (event.getTarget() == btnCaTruc) {
            formName = "manager/QuanLyCaLam";
        } else {
            return;
        }
        try {
            pnMain.getChildren().remove(0);
        } catch (Exception e) {
        }
        try {
            Parent root = App.loadFXML(formName);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            pnMain.getChildren().add(root);
        } catch (IOException e) {
        }
    }

}
