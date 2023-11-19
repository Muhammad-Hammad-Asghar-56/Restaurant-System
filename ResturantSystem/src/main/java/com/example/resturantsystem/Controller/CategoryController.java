package com.example.resturantsystem.Controller;

import com.example.resturantsystem.CategoryListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CategoryController {
    @FXML
    private Label ctglbl;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(ctglbl.getText());
    }

    private CategoryListener myListener;

    public void setData(String catgName) {
        ctglbl.setText(catgName);
    }

}