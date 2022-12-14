package com.controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyDialog {
    private String title;
    private String headerText;
    private String contentText;
    private Window owner; 
    private Alert.AlertType alertType;

    public MyDialog(String title, String contentText, Alert.AlertType alertType) {
        this.title = title;
        this.contentText = contentText;
        this.alertType = alertType;
    }

    public MyDialog(String title, String headerText, String contentText, Alert.AlertType alertType, Window owner) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
        this.alertType = alertType;
    }

    public MyDialog(String title, String contentText, Alert.AlertType alertType, Window owner) {
        this.title = title;
        this.contentText = contentText;
        this.owner = owner;
        this.alertType = alertType;
    }
    
    public Optional<ButtonType> ShowDialog()
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initOwner(owner);
        return alert.showAndWait();
    }
}
