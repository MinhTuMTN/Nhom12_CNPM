/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.controller;

import com.dao.DAOTaiKhoan;
import com.entity.NhanVien;
import com.main.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblNotification;

    private DAOTaiKhoan daoTaiKhoan = new DAOTaiKhoan();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String userName = txtUser.getText();
        String password = txtPass.getText();

        if (userName.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("")) {
            new MyDialog("Lỗi", "Đăng nhập không thành công",
                    "Thiếu thông tin đăng nhập",
                    btnLogin.getScene().getWindow(), AlertType.ERROR).ShowDialog();
            return;
        }

        if (daoTaiKhoan.checkTaiKhoan(userName, password)) {
            NhanVien nhanVien = daoTaiKhoan.getNhanVienByUserName(userName);
            switch (nhanVien.getLoaiNhanVien()) {
                case "Quản lý":
                    App.setRoot("manager/MainMenu");
                    break;
                case "Thu ngân":
                    App.setRootWithCashierId("cashier/MainMenu", nhanVien.getMaNhanVien());
                    break;
                case "Phục vụ":
                    App.setRootWithWaiterId("waiter/MainMenu", nhanVien.getMaNhanVien());
                    break;
                case "Đầu bếp":
                    App.setRootWithCookId("cook/MainMenu", nhanVien.getMaNhanVien());
                    break;
            }
        } else {
            new MyDialog("Lỗi", "Đăng nhập không thành công",
                    "Thông tin đăng nhập không chính xác",
                    btnLogin.getScene().getWindow(), AlertType.ERROR).ShowDialog();
        }
    }

    @FXML
    private void keyPress(KeyEvent event) throws IOException {
        lblNotification.setText("");
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getTarget() == txtPass) {
                handleLogin(null);
            } else if (event.getTarget() == txtUser) {
                txtPass.requestFocus();
            } else {
                txtPass.requestFocus();
            }

        }
    }
}
