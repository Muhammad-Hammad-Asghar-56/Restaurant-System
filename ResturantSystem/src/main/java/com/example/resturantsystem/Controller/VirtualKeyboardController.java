package com.example.resturantsystem.Controller;

import com.example.resturantsystem.keyBoardListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VirtualKeyboardController {
    @FXML
    private TextField textField;

    private boolean shiftPressed = false;

    private keyBoardListener MyKeyboardListner;
    private Dialog<?> dialog; // Store a reference to the dialog

    public void setKeyboardData(keyBoardListener myKeyboardListner,Dialog dialog){
        this.MyKeyboardListner=myKeyboardListner;
        this.dialog=dialog;
    }
    public void setPrevText(String text){
        textField.setText(text);
    }
    @FXML
    void onKeyButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        // Handle shift button
        if (buttonText.equals("Shift")) {
            shiftPressed = !shiftPressed;
            return;
        }

        // Handle backspace button
        if (buttonText.equals("<-")) {
            if (!textField.getText().isEmpty()) {
                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
            return;
        }

        if (buttonText.equals("Enter")) {
            // Handle Enter button
            MyKeyboardListner.onClickListener(textField.getText());
            closeDialog();
            return;
        }

        // Handle regular characters
        if(!shiftPressed){
            if("0123456789".contains(buttonText)){
                buttonText=buttonText.toLowerCase();
            }
            shiftPressed=false;
        }
        textField.setText(textField.getText() + buttonText);
    }
    @FXML
    private void closeDialog() {
        // Get the stage (dialog) and close it
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    // List of all the key buttons
    @FXML
    private Button[] keyButtons;
}
