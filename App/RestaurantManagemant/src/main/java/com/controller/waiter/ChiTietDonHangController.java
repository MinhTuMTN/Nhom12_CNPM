/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.waiter;

import com.controller.MyDialog;
import com.dao.DAODonHang;
import com.dao.DAOMonAn;
import com.entity.ChiTietDonHang;
import com.entity.DonHang;
import com.entity.MonAn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ViVi
 */
public class ChiTietDonHangController implements Initializable {

    @FXML
    private JFXComboBox<MonAn> cbMonAn;
    @FXML
    private JFXButton btnThem;
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
    @FXML
    private JFXButton btnCapNhat;

    private DonHang order;
    private ObservableList<ChiTietDonHang> orderItems;
    @FXML
    private JFXTextField txtTenMonAnCapNhat;
    @FXML
    private JFXTextField txtSoLuongCapNhat;

    private int min = 0;
    @FXML
    private JFXTextField txtSoLuongThem;
    
    DAODonHang daoDonHang = new DAODonHang();
    @FXML
    private TableColumn<ChiTietDonHang, Float> tbcThanhTien;
    
    private void loadMonAn(){
        List<MonAn> monAnTrongDonHang = new ArrayList<>();
        for(ChiTietDonHang chiTietDonHang : orderItems)
            monAnTrongDonHang.add(chiTietDonHang.getMonAn());
        
        List<MonAn> tatCaMonAn = (new DAOMonAn().getAll());
        
        List<MonAn> monAnKhongCoTrongDonHang = tatCaMonAn.stream()
                                            .filter(i -> !monAnTrongDonHang.contains(i))
                                            .collect(Collectors.toList());
        cbMonAn.setItems(FXCollections.observableArrayList(monAnKhongCoTrongDonHang));
        if(monAnKhongCoTrongDonHang != null)
            cbMonAn.getSelectionModel().selectFirst();
    }
    
    public void setOrder(DonHang order) {
        this.order = order;
        orderItems = FXCollections.observableArrayList(order.getChiTietDonHang());
        tbChiTietDonHang.setItems(orderItems);
        
        if(orderItems != null){
            tbChiTietDonHang.getSelectionModel().selectFirst();
            tbChiTietDonHangClicked(null);
        }
        loadMonAn();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcMaMonAn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMonAn().getMaMonAn()));
        tbcTenMonAn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonAn().getTenMonAn()));
        tbcSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbcGiaTien.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMonAn().getGiaTien()));
        tbcThanhTien.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getMonAn().getGiaTien() * c.getValue().getSoLuong()));
    }

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
    }

    @FXML
    private void btnThemClicked(ActionEvent event) {
        MonAn monAn = cbMonAn.getSelectionModel().getSelectedItem();
        if(monAn == null){
            new MyDialog("Lỗi", "Chưa chọn món ăn", Alert.AlertType.ERROR, btnThem.getScene().getWindow()).ShowDialog();
            return;
        }
        
        daoDonHang.themChiTietDonHang(this.order, monAn, Integer.valueOf(txtSoLuongThem.getText().trim()));
        setOrder(daoDonHang.findDonHangById(this.order.getMaDonHang()));
    }

    @FXML
    private void btnCapNhatClicked(ActionEvent event) {
        MonAn monAn = null;
        try {
            monAn = tbChiTietDonHang.getSelectionModel().getSelectedItem().getMonAn();
        } catch (Exception e) {
        }
        if(monAn == null){
            new MyDialog("Lỗi", "Chưa chọn món ăn", Alert.AlertType.ERROR, btnThem.getScene().getWindow()).ShowDialog();
            return;
        }
        
        daoDonHang.capNhatChiTietDonHang(this.order, monAn, Integer.valueOf(txtSoLuongCapNhat.getText().trim()));
        setOrder(daoDonHang.findDonHangById(this.order.getMaDonHang()));
    }

    @FXML
    private void tbChiTietDonHangClicked(MouseEvent event) {
        ChiTietDonHang chiTietDonHang =tbChiTietDonHang.getSelectionModel().getSelectedItem();
        if(chiTietDonHang == null)
            return;
        
        txtTenMonAnCapNhat.setText(chiTietDonHang.getMonAn().getTenMonAn());
        txtSoLuongCapNhat.setText(String.valueOf(chiTietDonHang.getSoLuong()));
        min = tbChiTietDonHang.getSelectionModel().getSelectedItem().getSoLuong();
    }

    @FXML
    private void btnTruCapNhatClicked(ActionEvent event) {
        try{
            if(Integer.valueOf(txtSoLuongCapNhat.getText()) - 1 == 0 || Integer.valueOf(txtSoLuongCapNhat.getText()) - 1 < min)
                return;
            txtSoLuongCapNhat.setText(String.valueOf(Integer.valueOf(txtSoLuongCapNhat.getText()) - 1));
        }catch(Exception e){
            
        }        
    }

    @FXML
    private void btnCongCapNhatClicked(ActionEvent event) {
        try{
            txtSoLuongCapNhat.setText(String.valueOf(Integer.valueOf(txtSoLuongCapNhat.getText()) + 1));
        }catch(Exception e){
            
        }
    }

    @FXML
    private void btnTruThem(ActionEvent event) {
        try{
            if(Integer.valueOf(txtSoLuongThem.getText()) - 1 == 0)
                return;
            txtSoLuongThem.setText(String.valueOf(Integer.valueOf(txtSoLuongThem.getText()) - 1));
        }catch(Exception e){
            
        }   
    }

    @FXML
    private void btnCongThem(ActionEvent event) {
         try{
            txtSoLuongThem.setText(String.valueOf(Integer.valueOf(txtSoLuongThem.getText()) + 1));
        }catch(Exception e){
            
        }
    }

}
