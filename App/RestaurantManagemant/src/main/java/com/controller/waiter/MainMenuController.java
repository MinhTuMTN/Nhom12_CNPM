/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller.waiter;

import com.controller.cashier.ThanhToanController;
import com.controller.cashier.TiepNhanController;
import com.jfoenix.controls.JFXButton;
import com.main.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ViVi
 */
public class MainMenuController {

    @FXML
    private JFXButton btnCaTruc;

    private int maNhanVienPhucVu;
    @FXML
    private JFXButton btnQuanLyDonHang;
    @FXML
    private AnchorPane pnMain;

    @FXML
    private void btnFunctionClick(ActionEvent event) throws IOException {
        JFXButton[] buttons = {btnCaTruc, btnQuanLyDonHang};
        for (JFXButton button : buttons) {
            button.getStyleClass().remove("btnFunction-active");
        }
        ((JFXButton) event.getTarget()).getStyleClass().add("btnFunction-active");

        String formName = "";
        FXMLLoader loader;
        Parent root;
        if (event.getTarget() == btnQuanLyDonHang) {
            formName = "waiter/QuanLyDonHang";
            loader = App.getFXMLLoader(formName);
            root = loader.load();
            QuanLyDonHangController controller = loader.getController();
            controller.setMaNhanVienPhucVu(this.maNhanVienPhucVu);
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

    public void setMaNhanVienPhucVu(int maNhanVienPhucVu) {
        this.maNhanVienPhucVu = maNhanVienPhucVu;
        try {
            btnFunctionClick(new ActionEvent(null, btnQuanLyDonHang));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
