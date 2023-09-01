package com.example.resturantsystem.Controller;

import com.example.resturantsystem.CategoryListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.example.resturantsystem.model.*;
import com.example.resturantsystem.MyListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TakeAwayController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;


    @FXML
    private ScrollPane CtgScroll;
    @FXML
    private ScrollPane subCtgScroll;
    @FXML
    private GridPane CtgGridPane;

    @FXML
    private GridPane subCtgGridPane;

    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    private CategoryListener CtgListener;

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        fruit = new Fruit();
        fruit.setName("Kiwi Kiwi Kiwi");
        fruit.setPrice(2.99);
//        fruit.setImgSrc("/img/kiwi.png");
        fruit.setColor("6A7324");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Coconut");
        fruit.setPrice(3.99);
//        fruit.setImgSrc("/img/coconut.png");
        fruit.setColor("A7745B");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Peach");
        fruit.setPrice(1.50);
//        fruit.setImgSrc("/img/peach.png");
        fruit.setColor("F16C31");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Grapes");
        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/grapes.png");
        fruit.setColor("291D36");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Watermelon");
        fruit.setPrice(4.99);
//        fruit.setImgSrc("/img/watermelon.png");
        fruit.setColor("22371D");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Orange");
        fruit.setPrice(2.99);
//        fruit.setImgSrc("/img/orange.png");
        fruit.setColor("FB5D03");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("StrawBerry");
        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/strawberry.png");
        fruit.setColor("80080C");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Mango");
        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/mango.png");
        fruit.setColor("FFB605");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Cherry");
        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/cherry.png");
        fruit.setColor("5F060E");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Banana");
        fruit.setPrice(1.99);
//        fruit.setImgSrc("/img/banana.png");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
        double price = fruit.getPrice();
        String formattedPrice = String.format("%.2f", price); // Format with 2 decimal places
        fruitPriceLabel.setText(formattedPrice);
//        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
//        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    private void setSubCtgs(String ctg){
        System.out.println("From :"+ctg);
        subCtgGridPane.getChildren().clear(); // Clear the grid

        try {
            int column=0;
            int row=1;
            for (int i = 0; i < fruits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i) ,myListener);

                // Add click event to the single grid cell
                anchorPane.setOnMouseClicked(event -> {
                    System.out.println("Clicked on Sub on: " + ctg);
                });

                if (column == 4) {
                    column = 0;
                    row++;
                }

                subCtgGridPane.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                subCtgGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                subCtgGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                subCtgGridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                subCtgGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                subCtgGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                subCtgGridPane.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(0,2,2,0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void subCtgGridPane(Fruit fruit) {
        System.out.println("From Set Sub Ctg" + fruit.getName());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fruits.addAll(getData());
        if (fruits.size() > 0) {
//            setChosenFruit(fruits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    subCtgGridPane(fruit);
//                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/categoryTemplate.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CategoryController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i).getName(),CtgListener);
                //  Add click event to each grid cell
                int finalI = i;
                anchorPane.setOnMouseClicked(event -> {
                    setSubCtgs(fruits.get(finalI).getName());
                });

                if (column == 4) {
                    column = 0;
                    row++;
                }

                CtgGridPane.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                CtgGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                CtgGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                CtgGridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                CtgGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                CtgGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                CtgGridPane.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
