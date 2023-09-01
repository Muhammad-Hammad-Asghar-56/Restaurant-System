package com.example.resturantsystem.Misc;

import com.example.resturantsystem.Controller.NumpadController;
import com.example.resturantsystem.Controller.VirtualKeyboardController;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VirtualInput {
    public void showKeyBoard(keyBoardListener myKeyBoardListener){
        Dialog<Void> keyboardDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/keyboard.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            VirtualKeyboardController keyboardControls = loader.getController();
            keyboardControls.setKeyboardData(myKeyBoardListener, keyboardDialog);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        keyboardDialog.getDialogPane().setContent(keyboardPane);
        keyboardDialog.showAndWait();
    }
    public void showKeyBoard(keyBoardListener myKeyBoardListener,String text){
        Dialog<Void> keyboardDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/keyboard.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            VirtualKeyboardController keyboardControls = loader.getController();
            keyboardControls.setKeyboardData(myKeyBoardListener, keyboardDialog);
            keyboardControls.setPrevText(text);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        keyboardDialog.getDialogPane().setContent(keyboardPane);
        keyboardDialog.showAndWait();
    }
    public void showNumpad(String title,NumpadListener myNumpadListener){

        System.out.println("In the Numpad");
        Dialog<Void> keyboardDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/Numpad.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            NumpadController keyboardControls = loader.getController();

            keyboardControls.setKeyboardData(title,myNumpadListener, keyboardDialog);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        keyboardDialog.getDialogPane().setContent(keyboardPane);
        keyboardDialog.showAndWait();
    }
}
