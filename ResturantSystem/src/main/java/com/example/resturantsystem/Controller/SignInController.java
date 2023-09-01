package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.UserDL;
import com.example.resturantsystem.keyBoardListener;
import com.example.resturantsystem.model.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SignInController implements Initializable {
    @FXML
    private Label errorLbl;

    @FXML
    private GridPane UserGrid;

    private Stage dashboardStage;
    private keyBoardListener userPasswordTxtListner;
    private String userPass = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userPasswordTxtListner = new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                userPass = word;
            }
        };
        setUserGrid();
    }

    private void setUserGrid() {
        ArrayList<User> userLst = UserDL.getUsersWithMaster();
        int row = 1;
        int col = 0;
        if (UserGrid != null) {
            UserGrid.getChildren().clear();
        }
        for (User u : userLst) {
            AnchorPane userAnchor = new AnchorPane();
            userAnchor.setStyle("-fx-background-color: #27374D; -fx-background-radius: 10;");

            // Create and configure the label
            Label nameLabel = new Label(u.getUserName());
            nameLabel.setPadding(new Insets(10, 20, 10, 20));
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setAlignment(Pos.CENTER);

            userAnchor.getChildren().add(nameLabel);
            // Center-align the label both horizontally and vertically
            AnchorPane.setTopAnchor(nameLabel, 0.0);
            AnchorPane.setRightAnchor(nameLabel, 0.0);
            AnchorPane.setBottomAnchor(nameLabel, 0.0);
            AnchorPane.setLeftAnchor(nameLabel, 0.0);

            userAnchor.setOnMouseClicked(event -> {
                Boolean isOk = checkUser(u);
                if (isOk) {
                    showLoggedINDashboard(u, event);
                }
            });

            UserGrid.add(userAnchor, col, row);

            if (col == 1) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
    }

    private void showLoggedINDashboard(User loginUser, Event event) {
        switch (loginUser.getRole()) {
            case "Master", "Admin":
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/resturantsystem/views/MasterDashboard.fxml"));

                    Scene dashboardScene = new Scene(root);
                    dashboardStage = new Stage();
                    dashboardStage.setTitle("Master Dashboard - Resturant");
                    dashboardStage.setScene(dashboardScene);
                    dashboardStage.setResizable(false); // Prevent resizing
                    ((Node) event.getSource()).getScene().getWindow().hide();

                    dashboardStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Waiter":
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/resturantsystem/views/TakeAwayDashboard.fxml"));

                    Scene dashboardScene = new Scene(root);
                    dashboardStage = new Stage();
                    dashboardStage.setTitle("Take Away Dashboard - Restaurant");
                    dashboardStage.setScene(dashboardScene);
                    dashboardStage.setResizable(false); // Prevent resizing
                    ((Node) event.getSource()).getScene().getWindow().hide();

                    dashboardStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }

    private boolean checkUser(User loginUser) {
        Dialog<Void> keyboardDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/keyboard.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            VirtualKeyboardController keyboardControls = loader.getController();
            keyboardControls.setKeyboardData(userPasswordTxtListner, keyboardDialog);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        keyboardDialog.getDialogPane().setContent(keyboardPane);
        keyboardDialog.showAndWait();
        System.out.println(loginUser.getUserPass().equals(userPass));
        if (loginUser.getUserPass().equals(userPass)) {
            return true;
        } else {
            errorLbl.setText("OOPs! Wrong Password Try Again");
            return false;
        }
    }
}