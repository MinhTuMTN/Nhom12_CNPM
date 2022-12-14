/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cook;

import com.controller.waiter.ChiTietDonHangController;
import com.dao.DAODonHang;
import com.entity.DonHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.main.App;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ViVi
 */
public class XemThongTinDonHangController implements Initializable {

    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private TableColumn<DonHang, String> tbcPhucVu;
    @FXML
    private TableColumn<DonHang, Integer> tbcMaDonHang;
    @FXML
    private TableColumn<DonHang, Number> tbcMaBan;
    @FXML
    private TableColumn<DonHang, Integer> tbcSoLuongMon;
    @FXML
    private TableColumn<DonHang, Float> tbcTongTien;
    @FXML
    private TableView<DonHang> tbDonHang;
    
    private Integer maDauBep;
    DAODonHang daoDonHang = new DAODonHang();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcMaDonHang.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMaDonHang()));
        tbcMaBan.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBan().getMaBan()));
        tbcPhucVu.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhucVu().getHoTen()));
        tbcSoLuongMon.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getChiTietDonHang().size()));
        tbcTongTien.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getSoTienThanhToan()));
    }    

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
    }

    private void loadData(){
        List<DonHang> orders = daoDonHang.getDanhSachDonHangDauBep(maDauBep);
        tbDonHang.setItems(FXCollections.observableArrayList(orders));
        if(orders != null)
            tbDonHang.getSelectionModel().selectFirst();
    }
    
    public void setMaDauBep(Integer maDauBep) {
        this.maDauBep = maDauBep;
        loadData();
    }

    @FXML
    private void tbDonHangClicked(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2){
            Stage stage = new Stage();
            stage.setTitle("Chi tiết đơn hàng");
            stage.initOwner(tbDonHang.getScene().getWindow());
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Images\\Logo\\logo_small.png");
            stage.getIcons().add(new Image(file.toURI().toString()));
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/view/" + "cook/ThucHienDonHang" + ".fxml"));
            Parent root = fxmlLoader.load();
        
            ThucHienDonHangController controller = fxmlLoader.getController();
            controller.setOrder(tbDonHang.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            
            stage.showAndWait();
            loadData();
        }
    }
    
}
