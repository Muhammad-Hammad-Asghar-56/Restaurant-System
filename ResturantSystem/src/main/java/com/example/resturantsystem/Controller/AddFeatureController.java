package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddFeatureController {
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private Label errorLbl;

    private keyBoardListener featureNameListerner=new keyBoardListener() {
        @Override
        public void onClickListener(String word) {
            nameTxt.setText(word);
        }
    };
    private NumpadListener featurePriceListerner=new NumpadListener() {
        @Override
        public void onClickListener(String word) {
            priceTxt.setText(word);
        }
    };

    private VirtualInput myKeyboard=new VirtualInput();

    @FXML
    void closeBtnAction(ActionEvent event) {
        Stage window=(Stage) nameTxt.getScene().getWindow();
        window.close();
    }

    @FXML
    void namePress(KeyEvent event) {
        myKeyboard.showKeyBoard(featureNameListerner);
    }

    @FXML
    void pricePress(KeyEvent event) {
        myKeyboard.showNumpad("Feature Price",featurePriceListerner);
    }

    @FXML
    void saveBtnAction(ActionEvent event) {
        if(this.nameTxt.getText().isEmpty()){ errorLbl.setText("Name should not be empty"); return;}
        if(this.priceTxt.getText().isEmpty()){ errorLbl.setText("Price should not be empty"); return;}
        CategoriesDL.insertNewFeature(this.nameTxt.getText(),Float.parseFloat(this.priceTxt.getText()));
        this.closeBtnAction(event);
    }
}
