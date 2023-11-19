package com.example.resturantsystem.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterDashboardController {
    @FXML
    private Button catalogueMenuBtn;

    @FXML
    private AnchorPane childPane;

    @FXML
    private Button userMenuBtn;

    @FXML
    void catalogueMenuAction(ActionEvent event) {
//        loadContentIntoChildPane("EditProductPane.fxml");
        loadContentIntoChildPane("NewManageCategory.fxml");
    }


    @FXML
    private void closeBtnAction(ActionEvent event) {
        ((Stage)catalogueMenuBtn.getScene().getWindow()).close();
    }

    @FXML
    void userMenuAction(ActionEvent event) {
        loadContentIntoChildPane("ManageUser.fxml");
    }

    private void loadContentIntoChildPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/" + fxmlPath));
            Parent content = loader.load();

            AnchorPane.setTopAnchor(content, 0.0);  // Set layout constraints to stretch the content
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            childPane.getChildren().clear(); // Clear existing content and set the loaded content
            childPane.getChildren().add(content);
            childPane=(AnchorPane) content;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
