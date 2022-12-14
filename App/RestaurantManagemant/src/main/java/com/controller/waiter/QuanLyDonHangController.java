/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller.waiter;

import com.controller.MyDialog;
import com.dao.DAOBan;
import com.dao.DAODonHang;
import com.entity.Ban;
import com.entity.DatTruoc;
import com.entity.DonHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.main.App;
import static com.main.App.loadFXML;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author ViVi
 */
public class QuanLyDonHangController implements Initializable {

    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;
    @FXML
    private JFXButton btnThem;
    @FXML
    private RadioButton rbThuong;
    @FXML
    private RadioButton rbDatTruoc;
    @FXML
    private JFXComboBox<Ban> cbMaBan;

    private ObservableList<Ban> tables;

    private int maNhanVienPhucVu;
    @FXML
    private TableView<DonHang> tbDonHang;
    @FXML
    private TableColumn<DonHang, String> tbcHoTenKhachHang;
    @FXML
    private TableColumn<DonHang, String> tbcSoDienThoai;
    @FXML
    private TableColumn<DonHang, Number> tbcMaBan;
    @FXML
    private TableColumn<DonHang, Date> tbcThoiGianCheckIn;
    @FXML
    private TableColumn<DonHang, Float> tbcSoTienThanhToan;
    @FXML
    private TableColumn<DonHang, String> tbcTrangThaiDonHang;
    @FXML
    private TableColumn<DonHang, String> tbcDauBep;

    private ObservableList<DonHang> orders;

    private DAODonHang daoDonHang = new DAODonHang();
    private DAOBan daoBan = new DAOBan();
    @FXML
    private ToggleGroup groupRadio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcHoTenKhachHang.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonHang, String> cellData) {
                if(cellData.getValue().getKhachHang().getHoTen() == null)
                    return new SimpleStringProperty("");
                return new SimpleStringProperty(cellData.getValue().getKhachHang().getHoTen());
            }
        });
        tbcSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getSoDienThoai()));
        tbcMaBan.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getBan().getMaBan()));
        tbcThoiGianCheckIn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getThoiGianCheckIn()));
        tbcThoiGianCheckIn.setCellFactory(column -> {
            TableCell<DonHang, Date> cell = new TableCell<DonHang, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");

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

        tbcSoTienThanhToan.setCellValueFactory(new PropertyValueFactory<>("soTienThanhToan"));
        tbcTrangThaiDonHang.setCellValueFactory(new PropertyValueFactory<>("trangThaiDonHang"));
        groupRadio.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (groupRadio.getSelectedToggle() == rbThuong) {
                    tables = FXCollections.observableArrayList(daoBan.getListBanDangCoSan());
                } else {
                    tables = FXCollections.observableArrayList(daoBan.getListBanDatTruoc());
                }
                cbMaBan.setItems(tables);
                if(tables != null)
                    cbMaBan.getSelectionModel().select(0);
            }
        });
        tbcDauBep.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDauBep().getHoTen()));
        loadData();
    }

    private void loadData() {
        orders = FXCollections.observableArrayList(daoDonHang.getDanhSachDonHangDangChuanBi());
        if (orders != null) {
            tbDonHang.setItems(orders);
            tbDonHang.getSelectionModel().selectFirst();
        }
        groupRadio.selectToggle(rbThuong);
        groupRadio.selectToggle(rbDatTruoc);
    }

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
    }

    public void setMaNhanVienPhucVu(int maNhanVienPhucVu) {
        this.maNhanVienPhucVu = maNhanVienPhucVu;
    }

    @FXML
    private void btnThemClicked(ActionEvent event) {
        Ban banSelected = cbMaBan.getSelectionModel().getSelectedItem();
        if(banSelected == null){
            new MyDialog("Lỗi", "Chưa chọn bàn", Alert.AlertType.ERROR, btnThem.getScene().getWindow()).ShowDialog();
            return;
        }
        
        try {
            DonHang donHang = daoDonHang.insertDonHang(maNhanVienPhucVu, banSelected);
            loadData();
            new MyDialog("Thành công", "Thêm đơn hàng thành công", Alert.AlertType.INFORMATION, btnThem.getScene().getWindow()).ShowDialog();
                
        } catch (Exception e) {
            new MyDialog("Lỗi", "Thêm đơn hàng thất bại", Alert.AlertType.ERROR, btnThem.getScene().getWindow()).ShowDialog();
        }
    }

    @FXML
    private void tbDonHangClicked(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2){
            Stage stage = new Stage();
            stage.setTitle("Chi tiết đơn hàng");
            stage.initOwner(btnThem.getScene().getWindow());
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Images\\Logo\\logo_small.png");
            stage.getIcons().add(new Image(file.toURI().toString()));
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/view/" + "waiter/ChiTietDonHang" + ".fxml"));
            Parent root = fxmlLoader.load();
        
            ChiTietDonHangController controller = fxmlLoader.getController();
            controller.setOrder(tbDonHang.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            
            stage.showAndWait();
            loadData();
        }
    }
}
