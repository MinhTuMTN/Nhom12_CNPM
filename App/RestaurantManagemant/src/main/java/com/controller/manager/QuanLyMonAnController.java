/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller.manager;

import com.controller.MyDialog;
import com.dao.DAOMonAn;
import com.entity.MonAn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author MinhTuNguyen
 */
public class QuanLyMonAnController implements Initializable {

    @FXML
    private TableView<MonAn> tbMonAn;
    @FXML
    private TableColumn<MonAn, Integer> tbcMaMonAn;
    @FXML
    private TableColumn<MonAn, String> tbcTenMonAn;
    @FXML
    private TableColumn<MonAn, Float> tbcGiaTien;
    @FXML
    private TableColumn<MonAn, String> tbcHinhAnh;

    private ObservableList<MonAn> foods;

    @FXML
    private JFXButton btnCapNhat;
    @FXML
    private JFXTextField txtUMaMonAn;
    @FXML
    private JFXTextField txtUTenMonAn;
    @FXML
    private JFXTextField txtUGiaTien;
    @FXML
    private ImageView picUHinhAnh;
    @FXML
    private JFXButton btnThem;

    private DAOMonAn daoMonAn = new DAOMonAn();

    private final String dir = System.getProperty("user.dir") + "\\src\\main\\resources\\Images\\Food\\";
    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private JFXButton btnTimKiem;

    private File file;
    @FXML
    private JFXTextField txtITenMonAn;
    @FXML
    private ImageView picIHinhAnh;
    @FXML
    private JFXTextField txtIGiaTien;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcMaMonAn.setCellValueFactory(new PropertyValueFactory<>("maMonAn"));
        tbcTenMonAn.setCellValueFactory(new PropertyValueFactory<>("tenMonAn"));
        tbcGiaTien.setCellValueFactory(new PropertyValueFactory<>("giaTien"));
        tbcHinhAnh.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
        loadData();
    }

    private void loadData() {
        foods = FXCollections.observableArrayList(daoMonAn.getAll());
        tbMonAn.setItems(foods);
        tbMonAn.refresh();
        
        tbMonAn.getSelectionModel().selectFirst();
        clickItemTableView(null);
        File fileInsertImage = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Images" + "\\Icon\\upload-cat.gif");
        picIHinhAnh.setImage(new Image(fileInsertImage.toURI().toString()));
        txtITenMonAn.setText("");
        txtIGiaTien.setText("");
    }

    @FXML
    private void clickItemTableView(MouseEvent event) {
        file = null;
        if (tbMonAn.getSelectionModel().getSelectedItem() != null) {
            txtUMaMonAn.setText(String.valueOf(tbMonAn.getSelectionModel().getSelectedItem().getMaMonAn()));
            txtUTenMonAn.setText(String.valueOf(tbMonAn.getSelectionModel().getSelectedItem().getTenMonAn()));
            txtUGiaTien.setText(String.valueOf(tbMonAn.getSelectionModel().getSelectedItem().getGiaTien()));

            String imageName = tbMonAn.getSelectionModel().getSelectedItem().getHinhAnh();
            File file = new File(dir + imageName);
            Image image = new Image(file.toURI().toString());
            picUHinhAnh.setImage(image);
        }
    }

    @FXML
    private void btnTimKiemOnAction(ActionEvent event) {
        List<MonAn> foods = daoMonAn.findMonAn(txtTimKiem.getText().trim());
        tbMonAn.setItems(FXCollections.observableArrayList(foods));

        tbMonAn.getSelectionModel().selectFirst();
        clickItemTableView(null);
    }

    @FXML
    private void btnCapNhatOnAction(ActionEvent event) throws IOException {
        // Kiểm tra đầy đủ thông tin
        if(txtUGiaTien.getText().trim().equals("") || txtUTenMonAn.getText().trim().equals("")){
            new MyDialog("Lỗi", "Cập nhật không thành công", "Thông tin không đầy đủ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR).ShowDialog();
            return;
        }
            
        MonAn oldMonAn = daoMonAn.findById(Integer.valueOf(txtUMaMonAn.getText().trim()));
        MonAn newMonAn = new MonAn();
        try{
            newMonAn = new MonAn(txtUTenMonAn.getText().trim(), 
                    Float.valueOf(txtUGiaTien.getText().trim()), 
                    oldMonAn.getHinhAnh());
            newMonAn.setMaMonAn(oldMonAn.getMaMonAn());
            if(Float.valueOf(txtUGiaTien.getText().trim()) < 0){
                new MyDialog("Lỗi", "Cập nhật không thành công", "Thông tin không hợp lệ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR).ShowDialog();
                return;
            }
        }catch(Exception e){
            new MyDialog("Lỗi", "Cập nhật không thành công", "Thông tin không hợp lệ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR).ShowDialog();
            return;
        }
        
        if(file != null){            
            BufferedImage bImage = ImageIO.read(file);
            String name = file.getName();
            String extension = name.substring(1 + name.lastIndexOf(".")).toLowerCase();
            name = String.valueOf(System.currentTimeMillis() * 10000 + 621355968000000000L) + "." + extension;
            ImageIO.write(bImage, extension, new File(dir + name));
            newMonAn.setHinhAnh(name);
        }
        
        daoMonAn.updateMonAn(newMonAn);
        loadData();
        new MyDialog("Thành công", "Cập nhật món ăn thành công", Alert.AlertType.INFORMATION, btnThem.getScene().getWindow()).ShowDialog();
    }

    @FXML
    private void picUHinhAnhClicked(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");

        this.file = fileChooser.showOpenDialog(null);
        if (file != null) {
            if(event.getTarget() == picUHinhAnh)
                picUHinhAnh.setImage(new Image(file.toURI().toString()));
            else if(event.getTarget() == picIHinhAnh)
                picIHinhAnh.setImage(new Image(file.toURI().toString()));
        }
            
    }

    @FXML
    private void btnThemOnAction(ActionEvent event) throws IOException {
        if(txtIGiaTien.getText().trim().equals("") || txtITenMonAn.getText().trim().equals("") || file == null){
            (new MyDialog("Lỗi", "Thêm không thành công", "Thông tin không đầy đủ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR)).ShowDialog();
            return;
        }
        
        MonAn monAn = new MonAn();
        try{
            if(Float.valueOf(txtIGiaTien.getText().trim()) < 0 )
            {
                (new MyDialog("Lỗi", "Thêm không thành công", "Thông tin không hợp lệ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR)).ShowDialog();
                return;
            }
            monAn = new MonAn(txtITenMonAn.getText().trim(), 
                    Float.valueOf(txtIGiaTien.getText().trim()), 
                    "unknow");
        }catch(Exception e){
            (new MyDialog("Lỗi", "Thêm không thành công", "Thông tin không hợp lệ", btnThem.getScene().getWindow(), Alert.AlertType.ERROR)).ShowDialog();
            return;
        }
        if(file != null){            
            BufferedImage bImage = ImageIO.read(file);
            String name = file.getName();
            String extension = name.substring(1 + name.lastIndexOf(".")).toLowerCase();
            name = String.valueOf(System.currentTimeMillis() * 10000 + 621355968000000000L) + "." + extension;
            ImageIO.write(bImage, extension, new File(dir + name));
            monAn.setHinhAnh(name);
        }        
        
        
        daoMonAn.insertMonAn(monAn);
        loadData();
        (new MyDialog("Thành công", "Thêm món ăn thành công", Alert.AlertType.INFORMATION, btnThem.getScene().getWindow())).ShowDialog();
    }

    @FXML
    private void changeTab(Event event) {
        file = null;
    }

}
