package com.example.resturantsystem.Controller;


import com.example.resturantsystem.DL.UserDL;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import com.example.resturantsystem.model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import java.io.IOException;
import java.util.ArrayList;


public class ManageUserController {
    @FXML
    private TextField checkUserPassTxt;
    @FXML
    private Label titleLbl;
    @FXML
    private Button delBtn;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    private GridPane userGrid;

    @FXML
    private TextField userNameTxt;

    @FXML
    private TextField userPassTxt;
    @FXML
    private Label errorLbl;
    @FXML
    private TextField auth_DIsTxt;
    private User selectedUser;
    private keyBoardListener userPassListener;
    private keyBoardListener userNameListener;
    private keyBoardListener reEnterPassListener;
    private NumpadListener authDiscListener;

    private VirtualInput myVirtualListener=new VirtualInput();

    @FXML
    public void initialize() {
        // Add items to the ComboBox during initialization
        roleComboBox.setItems(FXCollections.observableArrayList(
                "Admin", "Waiter"
        ));

        userPassTxt.setText("");
        userNameTxt.setText("");
        auth_DIsTxt.setText("");
        checkUserPassTxt.setText("");

        roleComboBox.getSelectionModel().selectFirst();
        initilizeKeyBoardListener();
        setUserInGrid();
        SetUpForAddUser();
    }
    private void initilizeKeyBoardListener(){
        userPassListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                userPassTxt.setText(word);
            }
        };
        userNameListener= new keyBoardListener() {
            @Override
            public void onClickListener(String word) {

                userNameTxt.setText(word);
            }
        };
        reEnterPassListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                checkUserPassTxt.setText(word);
            }
        };
        authDiscListener=new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                auth_DIsTxt.setText(word);
            }
        };
    }
    private void SetUpForAddUser(){
        errorLbl.setText("");
        titleLbl.setText("Add New User");
        delBtn.setVisible(false);
    }
    private void SetUptoEditUser(){
        errorLbl.setText("");
        titleLbl.setText("Edit User");
        delBtn.setVisible(true);
    }
    private void setUserInGrid(){
        ArrayList<User> users= UserDL.getUsersLst();

        try {
            int column=0;
            int row=1;
            for (int i = 0; i < users.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/user.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                UserController userController = fxmlLoader.getController();
                userController.setUserName(users.get(i).getUserName());

                // Add click event to the single grid cell
                int finalI = i;
                anchorPane.setOnMouseClicked(event -> {
                    SetUptoEditUser();
                    selectedUser=users.get(finalI);
                    userNameTxt.setText(users.get(finalI).getUserName());
                    userPassTxt.setText(users.get(finalI).getUserPass());
                    auth_DIsTxt.setText(String.valueOf(users.get(finalI).getAuth_Disc()));
                });


                userGrid.add(anchorPane, 0, row++); //(child,column,row)
                //set grid width
                userGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                userGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                userGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                userGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                userGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                userGrid.setMaxHeight(Region.USE_PREF_SIZE);
                userGrid.setMargin(anchorPane, new Insets(0,0,0,0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void delBtnAction(ActionEvent event) {
        UserDL.unActiveUser(selectedUser.get_Id());
        this.initialize();
    }

    @FXML
    void saveBtnAction(ActionEvent event) {
        if(userNameTxt.getText().isEmpty() || userPassTxt.getText().isEmpty() || checkUserPassTxt.getText().isEmpty()){
            errorLbl.setText("Empty ! Fields Should be Filled");
            errorLbl.setTextFill(Paint.valueOf("Red"));
            return;
        }
        else if(! userPassTxt.getText().equals(checkUserPassTxt.getText())){
            errorLbl.setText("Password didn't match");
            errorLbl.setTextFill(Paint.valueOf("Red"));
            checkUserPassTxt.setText("");
            return;
        }
        else if (userPassTxt.getText().length() <=4){
            errorLbl.setText("Password is too Weak try to make Complex");
            errorLbl.setTextFill(Paint.valueOf("Red"));
            checkUserPassTxt.setText("");
            userPassTxt.setText("");
            return;
        }
        float auth_dis;
        try{
            auth_dis=Float.parseFloat(auth_DIsTxt.getText());
        }
        catch (Exception e){
            errorLbl.setText("Disc must contains the Integer or Floating Numbers");
            auth_DIsTxt.setText("");
            return;
        }
        if(auth_dis >= 100){
            errorLbl.setText("Percentage should not be greater than 100");
            auth_DIsTxt.setText("");
            return;
        }

        //  If every thing is fine then we can move to Save in the database
        if(selectedUser != null){ // user are in the "Edit User" mode
            selectedUser.setUserName(userNameTxt.getText());
            selectedUser.setUserPass(userPassTxt.getText());
            selectedUser.setRole(roleComboBox.getValue());
            selectedUser.setAuth_Disc(auth_dis);

            UserDL.updateUserDetails(selectedUser.get_Id(),selectedUser);
            setUserInGrid();
        }
        else { // User are in the "Add new User" Mode
            UserDL.InsertNewUser(userNameTxt.getText(),userPassTxt.getText(),auth_dis,roleComboBox.getValue());
        }
        this.initialize(); // Reinitialize Everything
    }


    @FXML
    void userNameMouseClick(MouseEvent event) {
        String text="";
        if(selectedUser != null){
            text=selectedUser.getUserName();
        }
        myVirtualListener.showKeyBoard(userNameListener,text);
    }

    @FXML
    void userPassMouseClick(MouseEvent event) {
        String text="";
        if(selectedUser != null){
            text=selectedUser.getUserPass();
        }
        myVirtualListener.showKeyBoard(userPassListener,text);
    }
    @FXML
    void confirmPassMouseClick(MouseEvent event) {
        myVirtualListener.showKeyBoard(reEnterPassListener);
    }
    @FXML
    void authDiscMouseClick(MouseEvent event){
        String text="";
        if(selectedUser != null){
            text=String.valueOf(selectedUser.getAuth_Disc());
        }
        myVirtualListener.showNumpad("Auth Disc",authDiscListener);
    }

}