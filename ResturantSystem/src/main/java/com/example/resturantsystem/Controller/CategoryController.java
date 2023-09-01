package com.example.resturantsystem.Controller;

import com.example.resturantsystem.CategoryListener;
import com.example.resturantsystem.MyListener;
import com.example.resturantsystem.model.Fruit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class CategoryController {
    @FXML
    private Label ctglbl;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(ctglbl.getText());
    }

    private CategoryListener myListener;

    public void setData(String catgName, CategoryListener myListener) {
        this.myListener = myListener;
        ctglbl.setText(catgName);
    }
}