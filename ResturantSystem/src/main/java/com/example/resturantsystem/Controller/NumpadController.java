package com.example.resturantsystem.Controller;

import com.example.resturantsystem.NumpadListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NumpadController {

    @FXML
    private TextField textField;

    @FXML
    private Label titleLbl;

    private NumpadListener MyKeyboardListner;
    private Dialog<?> dialog; // Store a reference to the dialog

    public void setKeyboardData(String title,NumpadListener myKeyboardListner,Dialog dialog){
        this.titleLbl.setText(title);
        this.MyKeyboardListner=myKeyboardListner;
        this.dialog=dialog;
    }
    @FXML
    void onKeyButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();


        if (buttonText.equals("Done")) {
            MyKeyboardListner.onClickListener(textField.getText());
            closeDialog();
            return;
        }
        if(buttonText.equals("CE") ){
            textField.setText("");
            return;
        }

        if(buttonText.equals(".") && textField.getText().contains(".")){
            return;
        }
        textField.setText(textField.getText() + buttonText);
    }
    @FXML
    private void closeDialog() {
        // Get the stage (dialog) and close it
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeBtn(){
            this.closeDialog();
    }
    @FXML
    private Button[] keyButtons;

}
