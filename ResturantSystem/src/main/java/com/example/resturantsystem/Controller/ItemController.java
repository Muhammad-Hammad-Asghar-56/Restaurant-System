package com.example.resturantsystem.Controller;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.resturantsystem.model.Fruit;
import com.example.resturantsystem.MyListener;
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

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);

    }

    private Fruit fruit;
    private MyListener myListener;

    private GridPane subCtgGridPane;
    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;

        nameLabel.setText(fruit.getName());
        double price = fruit.getPrice();
        String formattedPrice = String.format("%.2f", price); // Format with 2 decimal places
//        this.subCtgGridPane=subCtgGridPane;
        priceLable.setText("("+formattedPrice+"€)");
        // Set wrap text for labels
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);
    }
}
