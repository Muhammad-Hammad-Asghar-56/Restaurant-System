package com.example.resturantsystem.Controller;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;
@FXML
private VBox MyVBox;
    @FXML
    private ImageView img;


//    private MyListener myListener;

    private GridPane subCtgGridPane;
    public void setData(String name,Float price) {
        this.nameLabel.setText(name);

        String formattedPrice = String.format("%.2f", price); // Format with 2 decimal places
        priceLable.setText("("+formattedPrice+"€)");
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);
    }
    public Label getPriceLable(){
        return priceLable;
    }

    public Label getNameLabel() {
        return nameLabel;
    }
}
