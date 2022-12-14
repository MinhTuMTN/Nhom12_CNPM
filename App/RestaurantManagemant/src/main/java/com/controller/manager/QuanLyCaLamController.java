/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.manager;

import com.controller.MyDialog;
import com.dao.DAOCaTruc;
import com.entity.CaTruc;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class QuanLyCaLamController implements Initializable {

    @FXML
    private TableView<CaTruc> tbCaTruc;
    @FXML
    private TableColumn<CaTruc, Integer> tbcMaCaTruc;
    @FXML
    private TableColumn<CaTruc, Date> tbcNgayBatDau;
    @FXML
    private TableColumn<CaTruc, Date> tbcNgayKetThuc;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private JFXButton btnCapNhat;
    @FXML
    private JFXButton btnTao;
    @FXML
    private DatePicker dtNgayBDThem;
    @FXML
    private DatePicker dtNgayKTThem;
    @FXML
    private DatePicker dtNgayBatDauSua;
    @FXML
    private DatePicker dtNgayKetThucSua;

    private DAOCaTruc daoCaTruc = new DAOCaTruc();
    @FXML
    private DatePicker dtTimKiem;

    @FXML
    private void clickItemTableView(MouseEvent event) {
        try {
            CaTruc caTruc = tbCaTruc.getSelectionModel().getSelectedItem();
            dtNgayBatDauSua.setValue(caTruc.getNgayBatDau().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            dtNgayKetThucSua.setValue(caTruc.getNgayKetThuc().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            dtNgayBatDauSuaChanged(null);
        } catch (Exception e) {
        }
    }

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
        try {
            Date date = Date.from(dtTimKiem.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            dtNgayBDThem.setValue((new Date()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            dtNgayBDThemChanged(null);
            tbCaTruc.setItems(FXCollections.observableArrayList(daoCaTruc.findCaTrucTrongMotKhoang(date)));
            if (tbCaTruc.getItems() != null && !tbCaTruc.getItems().isEmpty()) {
                btnCapNhat.setDisable(false);
                tbCaTruc.getSelectionModel().selectFirst();
                clickItemTableView(null);
            } else {
                btnCapNhat.setDisable(true);
            }
        } catch (Exception e) {
            //btnCapNhat.setDisable(true);
        }

    }

    @FXML
    private void btnCapNhatOnAction(ActionEvent event) {
        try {
            Date ngayBatDau = Date.from(dtNgayBatDauSua.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date ngayKetThuc = Date.from(dtNgayKetThucSua.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            CaTruc caTruc = tbCaTruc.getSelectionModel().getSelectedItem();
            caTruc.setNgayBatDau(ngayBatDau);
            caTruc.setNgayKetThuc(ngayKetThuc);
            
            if(daoCaTruc.capNhatCaTruc(caTruc)){
                loadData();
                new MyDialog("Thành công", "Sửa ca làm việc thành công", Alert.AlertType.INFORMATION, btnCapNhat.getScene().getWindow()).ShowDialog();
            }
        
        } catch (Exception e) {
            new MyDialog("Lỗi", "Thông tin không đầy đủ", Alert.AlertType.ERROR, btnCapNhat.getScene().getWindow()).ShowDialog();
        }
    }

    @FXML
    private void changeTab(Event event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcMaCaTruc.setCellValueFactory(new PropertyValueFactory<>("maCaTruc"));
        tbcNgayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        tbcNgayKetThuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        loadData();
    }

    private void loadData() {
        dtNgayBDThem.setValue((new Date()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        dtTimKiem.setValue((new Date()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        dtNgayBDThemChanged(null);
        tbCaTruc.setItems(FXCollections.observableArrayList(daoCaTruc.getAllCaTruc()));
        if (tbCaTruc.getItems() != null && !tbCaTruc.getItems().isEmpty()) {
            btnCapNhat.setDisable(false);
            tbCaTruc.getSelectionModel().selectFirst();
            clickItemTableView(null);
        } else {
            btnCapNhat.setDisable(true);
        }
    }

    @FXML
    private void btnThemOnAction(ActionEvent event) {
        try {
            Date ngayBatDau = Date.from(dtNgayBDThem.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date ngayKetThuc = Date.from(dtNgayKTThem.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            CaTruc caTruc = new CaTruc(ngayBatDau, ngayKetThuc);
            if(!daoCaTruc.themCaTruc(caTruc))
                new MyDialog("Lỗi", "Thông tin không hợp lệ", Alert.AlertType.ERROR, btnCapNhat.getScene().getWindow()).ShowDialog();
            else{                
                loadData();
                new MyDialog("Thành công", "Thêm ca làm việc thành công", Alert.AlertType.INFORMATION, btnCapNhat.getScene().getWindow()).ShowDialog();
            }                
        } catch (Exception e) {
            e.printStackTrace();
            new MyDialog("Lỗi", "Thông tin không đầy đủ", Alert.AlertType.ERROR, btnCapNhat.getScene().getWindow()).ShowDialog();
        }

    }

    @FXML
    private void dtNgayBDThemChanged(ActionEvent event) {
        dtNgayKTThem.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(dtNgayBDThem.getValue()));
            }
        });
    }

    @FXML
    private void dtNgayBatDauSuaChanged(ActionEvent event) {
        dtNgayKetThucSua.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(dtNgayBatDauSua.getValue()));
            }
        });
    }
}
