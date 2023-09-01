package com.example.resturantsystem.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MasterDashboardController {
    @FXML
    private Button catalogueMenuBtn;

    @FXML
    private Pane childPane;

    @FXML
    private Button reportMenuBtn;

    @FXML
    private Button userMenuBtn;
    @FXML
    private Button addCatalogueBtn;

    @FXML
    void catalogueMenuAction(ActionEvent event) {
        loadContentIntoChildPane("EditProductPane.fxml");
    }

    @FXML
    void reportMenuAction(ActionEvent event) {

    }

    @FXML
    private void addCatalogueAction(ActionEvent event) {
        loadContentIntoChildPane("AddProductPane.fxml");
    }

    @FXML
    void userMenuAction(ActionEvent event) {
        loadContentIntoChildPane("ManageUser.fxml");
    }

    private void loadContentIntoChildPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/" + fxmlPath));
            Parent content = loader.load();

            // Set layout constraints to stretch the content
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            // Clear existing content and set the loaded content
            childPane.getChildren().clear();
            childPane.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
