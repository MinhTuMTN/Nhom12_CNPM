/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cashier;

import com.controller.MyDialog;
import com.dao.DAODonHang;
import com.entity.ChiTietDonHang;
import com.entity.DonHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class ThanhToanController implements Initializable {

    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private TableView<ChiTietDonHang> tbThanhToan;
    @FXML
    private JFXTextField txtMaDonHang;
    @FXML
    private JFXTextField txtPhuThu;
    @FXML
    private JFXTextField txtPhucVu;
    @FXML
    private JFXTextField txtDauBep;
    @FXML
    private JFXTextField txtSoTienThanhToan;
    @FXML
    private JFXTextField txtSoTienKhachDua;
    @FXML
    private JFXTextField txtCoupon;
    @FXML
    private JFXTextField txtSoTienTraLai;
    @FXML
    private JFXButton btnThanhToan;

    private int maNhanVien;
    @FXML
    private JFXTextField txtTenKhachHang;

    private ObservableList<ChiTietDonHang> orderItems;

    private DAODonHang daoDonHang = new DAODonHang();
    @FXML
    private TableColumn<ChiTietDonHang, String> tbcTenMonAn;
    @FXML
    private TableColumn<ChiTietDonHang, Integer> tbcSoLuong;
    @FXML
    private TableColumn<ChiTietDonHang, Float> tbcDonGia;

    private DonHang order;
    @FXML
    private JFXButton btnCapNhatPhuThu;
    @FXML
    private JFXButton btnApDungCoupon;
    @FXML
    private JFXTextField txtTamTinh;

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbcTenMonAn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonAn().getTenMonAn()));
        tbcDonGia.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMonAn().getGiaTien()));
        txtSoTienKhachDua.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.order == null) {
                txtSoTienKhachDua.setText("");
            }

            float soTienKhachDua;
            try {
                if("".equals(newValue.trim()))
                    return;
                soTienKhachDua = Float.valueOf(newValue.trim());
                txtSoTienTraLai.setText(String.valueOf(soTienKhachDua - this.order.getSoTienThanhToan()));
            } catch (Exception e) {
                new MyDialog("Lỗi", "Vui lòng chỉ nhập số", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
            }
        });
    }

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
        String maBan = txtTimKiem.getText();

        try {
            Long.valueOf(maBan);
        } catch (Exception e) {
            new MyDialog("Lỗi", "Mã bàn không hợp lệ", Alert.AlertType.ERROR, btnApDungCoupon.getScene().getWindow()).ShowDialog();
        }
        this.order = daoDonHang.findDonHangChuaThanhToanByMaBan(Long.valueOf(maBan));
        if (order == null) {
            new MyDialog("Lỗi", "Mã bàn không hợp lệ", Alert.AlertType.ERROR, btnApDungCoupon.getScene().getWindow()).ShowDialog();
            loadOrderToControl();
            tbThanhToan.setItems(null);
            return;
        }

        this.orderItems = FXCollections.observableArrayList(this.order.getChiTietDonHang());
        if (this.orderItems != null) {
            tbThanhToan.setItems(this.orderItems);
            loadOrderToControl();
        }
    }

    private void loadOrderToControl() {
        if (this.order == null) {
            txtMaDonHang.setText("");
            txtPhucVu.setText("");
            txtDauBep.setText("");
            txtTenKhachHang.setText("");
            txtPhuThu.setText("");
            txtCoupon.setText("");
            txtTamTinh.setText("");
            txtSoTienThanhToan.setText("");
            txtSoTienKhachDua.setText("");
            return;
        }

        txtMaDonHang.setText(String.valueOf(order.getMaDonHang()));
        txtPhucVu.setText(order.getPhucVu().getHoTen());
        txtDauBep.setText(order.getDauBep().getHoTen());
        txtTenKhachHang.setText(order.getKhachHang().getHoTen());
        txtPhuThu.setText(String.valueOf(order.getPhuThu()));
        txtCoupon.setText(order.getCoupon() != null ? String.valueOf(order.getCoupon().getMaCoupon()) : "");
        txtTamTinh.setText(String.valueOf(daoDonHang.tinhTienMonAn(order)));
        txtSoTienThanhToan.setText(String.valueOf(order.getSoTienThanhToan()));
        txtSoTienKhachDua.setText("");
    }

    @FXML
    private void btnCapNhatPhuThuClicked(ActionEvent event) {
        Float phuThu;
        try {
            phuThu = Float.valueOf(txtPhuThu.getText().trim());
            if (phuThu < 0) {
                new MyDialog("Lỗi", "Vui lòng nhập số hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
                return;
            }

            this.order = daoDonHang.capNhatPhuThu(this.order, phuThu);
            new MyDialog("Thành công", "Cập nhật phụ thu thành công", Alert.AlertType.INFORMATION, btnThanhToan.getScene().getWindow()).ShowDialog();
        } catch (Exception e) {
            e.printStackTrace();
            new MyDialog("Lỗi", "Vui lòng nhập số hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
        }
        loadOrderToControl();
    }

    @FXML
    private void btnApDungCouponClicked(ActionEvent event) {
        Integer maCoupon;
        try {
            maCoupon = Integer.valueOf(txtCoupon.getText().trim());
            this.order = daoDonHang.apDungCoupon(this.order, maCoupon);

            if (this.order.getCoupon() != null) {
                new MyDialog("Thành công", "Áp dụng coupon thành công", Alert.AlertType.INFORMATION, btnThanhToan.getScene().getWindow()).ShowDialog();
            } else {
                new MyDialog("Lỗi", "Coupon không hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new MyDialog("Lỗi", "Coupon không hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
        }
        loadOrderToControl();
    }

    @FXML
    private void btnThanhToanClicked(ActionEvent event) {
        try {
            float soTienKhachDua = 0;
            try {
                soTienKhachDua = Float.valueOf(txtSoTienKhachDua.getText());
            } catch (Exception e) {
                new MyDialog("Lỗi", "Vui lòng nhập số tiền khách đưa hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
                return;
            }

            if (soTienKhachDua < this.order.getSoTienThanhToan()) {
                new MyDialog("Lỗi", "Vui lòng nhập số tiền khách đưa hợp lệ", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
                return;
            }
            daoDonHang.thanhToanDonHang(this.order, maNhanVien);
            this.order = null;
            loadOrderToControl();
            tbThanhToan.setItems(null);
            txtTimKiem.setText("");
            new MyDialog("Thành công", "Thanh toán đơn hàng thành công", Alert.AlertType.INFORMATION, btnThanhToan.getScene().getWindow()).ShowDialog();
        } catch (Exception e) {
            e.printStackTrace();
            new MyDialog("Lỗi", "Thanh toán đơn hàng thất bại", Alert.AlertType.ERROR, btnThanhToan.getScene().getWindow()).ShowDialog();
        }
    }

}
