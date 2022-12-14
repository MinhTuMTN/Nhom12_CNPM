/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cook;

import com.controller.MyDialog;
import com.dao.DAODonHang;
import com.entity.ChiTietDonHang;
import com.entity.DonHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ViVi
 */
public class ThucHienDonHangController implements Initializable {

    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private TableView<ChiTietDonHang> tbChiTietDonHang;
    @FXML
    private TableColumn<ChiTietDonHang, Integer> tbcMaMonAn;
    @FXML
    private TableColumn<ChiTietDonHang, String> tbcTenMonAn;
    @FXML
    private TableColumn<ChiTietDonHang, Integer> tbcSoLuong;
    @FXML
    private TableColumn<ChiTietDonHang, Float> tbcGiaTien;

    
    private DonHang order;
    @FXML
    private JFXButton btnHoanThanh;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcMaMonAn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getMonAn().getMaMonAn()));
        tbcTenMonAn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMonAn().getTenMonAn()));
        tbcSoLuong.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getSoLuong()));
        tbcGiaTien.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getMonAn().getGiaTien()));
    }    

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
    }

    @FXML
    private void btnHoanThanhClicked(ActionEvent event) {
        new DAODonHang().capNhatTrangThaiDonHangDauBep(order);
        new MyDialog("Thành công", "Cập nhật trạng thái đơn hàng thành công", Alert.AlertType.INFORMATION, btnHoanThanh.getScene().getWindow()).ShowDialog();
        ((Stage)btnHoanThanh.getScene().getWindow()).close();
    }

    public void setOrder(DonHang order) {
        this.order = order;
        tbChiTietDonHang.setItems(FXCollections.observableArrayList(order.getChiTietDonHang()));
    }
    
}
