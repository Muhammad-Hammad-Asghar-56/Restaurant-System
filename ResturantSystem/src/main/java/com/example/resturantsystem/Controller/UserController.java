package com.example.resturantsystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {
    @FXML
    private Label userName;


    public void setUserName(String UserName){
        userName.setText(UserName);
    }
}
