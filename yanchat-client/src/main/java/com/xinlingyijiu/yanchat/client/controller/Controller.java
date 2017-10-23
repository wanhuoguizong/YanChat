package com.xinlingyijiu.yanchat.client.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Button startBtn;
    @FXML
    private TextField userName;

//    public void show(){
//        System.out.println(userName.getCharacters().toString());
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startBtn.setOnMouseClicked(this::show);
    }

    public void show(MouseEvent mouseEvent) {
//        System.out.println(userName.getText());

    }
}
