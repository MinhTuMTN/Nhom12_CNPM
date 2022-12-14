/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.cashier;

import com.controller.MyDialog;
import com.dao.DAODatTruoc;
import com.entity.DatTruoc;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ViVi
 */
public class TiepNhanController implements Initializable {

    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private Label lblHoTen;
    @FXML
    private Label lblNgaySinh;
    @FXML
    private Label lblSoDienThoai;
    @FXML
    private Label lblSoBan;
    @FXML
    private Label lblSoLuongNguoi;
    @FXML
    private Label lblThoiGianDatTruoc;
    @FXML
    private TableView<DatTruoc> tbDatTruoc;
    @FXML
    private TableColumn<DatTruoc, String> tbcHoTen;
    @FXML
    private TableColumn<DatTruoc, Date> tbcNgaySinh;
    @FXML
    private TableColumn<DatTruoc, String> tbcSoDienThoai;
    @FXML
    private TableColumn<DatTruoc, Number> tbcMaBan;
    @FXML
    private TableColumn<DatTruoc, Integer> tbcSoLuongNguoi;
    @FXML
    private TableColumn<DatTruoc, Date> tbcThoiGianDatTruoc;

    private ObservableList<DatTruoc> reservations;

    private DAODatTruoc daoDatTruoc = new DAODatTruoc();
    @FXML
    private JFXButton btnTiepNhan;
    @FXML
    private JFXButton btnTuChoi;
    
    public int maNhanVien;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcMaBan.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getBan().getMaBan()));
        tbcSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getSoDienThoai()));

        tbcNgaySinh.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKhachHang().getNgaySinh()));
        tbcNgaySinh.setCellFactory(column -> {
            TableCell<DatTruoc, Date> cell = new TableCell<DatTruoc, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });

        tbcHoTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getHoTen()));
        tbcSoLuongNguoi.setCellValueFactory(new PropertyValueFactory<>("soLuongNguoi"));
        tbcThoiGianDatTruoc.setCellValueFactory(new PropertyValueFactory<>("thoiGianDatTruoc"));
        tbcThoiGianDatTruoc.setCellFactory(column -> {
            TableCell<DatTruoc, Date> cell = new TableCell<DatTruoc, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });
        loadData();
    }

    private void loadData() {
        reservations = FXCollections.observableArrayList(daoDatTruoc.getAllDatTruocChoXacNhan());
        if (reservations != null) {
            tbDatTruoc.setItems(reservations);

            tbDatTruoc.getSelectionModel().selectFirst();
            clickItemTableView(null);

        }
    }

    @FXML
    private void clickItemTableView(MouseEvent event) {
        if (tbDatTruoc.getSelectionModel().getSelectedItem() != null) {
            DateFormat df1 = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MM/yy");
            DatTruoc item = tbDatTruoc.getSelectionModel().getSelectedItem();

            lblHoTen.setText(item.getKhachHang().getHoTen());
            lblNgaySinh.setText(df2.format(item.getKhachHang().getNgaySinh()));
            lblSoDienThoai.setText(item.getKhachHang().getSoDienThoai());
            lblSoBan.setText(item.getBan().getMaBan().toString());
            lblSoLuongNguoi.setText(item.getSoLuongNguoi().toString());
            lblThoiGianDatTruoc.setText(df1.format(item.getThoiGianDatTruoc()));
        }
    }

    @FXML
    private void btnTiepNhanClicked(ActionEvent event) {
        DatTruoc datTruoc = tbDatTruoc.getSelectionModel().getSelectedItem();

        if (datTruoc != null) {
            daoDatTruoc.chapNhanDatTruoc(datTruoc, maNhanVien);
            loadData();
            new MyDialog("Thành công", "Tiếp nhận đặt trước thành công", Alert.AlertType.INFORMATION, btnTiepNhan.getScene().getWindow()).ShowDialog();
        }
    }

    @FXML
    private void btnTuChoiClicked(ActionEvent event) {
        DatTruoc datTruoc = tbDatTruoc.getSelectionModel().getSelectedItem();

        if (datTruoc != null) {
            daoDatTruoc.tuChoiDatTruoc(datTruoc, maNhanVien);
            loadData();
            new MyDialog("Thành công", "Từ chối đặt trước thành công", Alert.AlertType.INFORMATION, btnTiepNhan.getScene().getWindow()).ShowDialog();
        }
    }
}
