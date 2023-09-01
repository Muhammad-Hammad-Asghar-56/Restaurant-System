package com.example.resturantsystem.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;
public class FeatureController {

    @FXML
    private VBox vBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;



    public void setDetails(String name, String price, String color,Paint textColor) {
        String StyleString="-fx-background-color : "+color+"; -fx-background-radius:10";
        vBox.setStyle(StyleString);
        nameLabel.setText(name);
        nameLabel.setTextFill(textColor);
        priceLabel.setText(price);
//        Light :  #DDE6ED
//         Dark : #27
    }
}
