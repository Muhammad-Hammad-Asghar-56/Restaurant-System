package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.FeatureLstListener;
import com.example.resturantsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeaturePopUp implements Initializable {

    @FXML
    private Button SaveBtn;

    @FXML
    private GridPane featureGridPane;
    @FXML
    private GridPane typeGridPane;
    @FXML
    private Label lstLbl;
    private String selectedType;

    private ArrayList<OrderFeature> featureLst=new ArrayList<>();
    private ArrayList<Features> productFeatureList=new ArrayList<>();

    private FeatureLstListener myListener;


    @FXML
    void saveBtnAction(ActionEvent event) {
        myListener.onclick(featureLst);
        closeDialog();
    }

    private void populateType(){
        int column = 0;
        int row = 1;
        ArrayList<String> types = new ArrayList<>();
        types.add("with");
        types.add("without");
        types.add("only With");
        types.add("Small Dose");
        types.add("Large Dose");


        for (int i = 0; i < types.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/categoryTemplate.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CategoryController itemController = fxmlLoader.getController();
            itemController.setData(types.get(i));
            //  Add click event to each grid cell
            int finalI = i;
            anchorPane.setStyle("-fx-background-color: #27374D;-fx-background-radius:10");
            if(selectedType != null && selectedType.equals(types.get(finalI))){
                anchorPane.setStyle("-fx-background-color:#526D82;-fx-background-radius:10");
                updateFeatures();
            }
            anchorPane.setOnMouseClicked(event -> {
                    selectedType = types.get(finalI);
                    populateType();
            });

            typeGridPane.add(anchorPane, column++, row); //(child,column,row)
            //set grid width
            typeGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            typeGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
            typeGridPane.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            typeGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
            typeGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            typeGridPane.setMaxHeight(Region.USE_PREF_SIZE);


            GridPane.setMargin(anchorPane, new Insets(5));
        }
    }
    public void updateFeatures(){
        int row = 1;
        int col = 0;
        if (featureGridPane != null) {
            featureGridPane.getChildren().clear();
        }
        try {
            for (Features f : this.productFeatureList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/feature.fxml"));

                AnchorPane featureAnchor = new AnchorPane();
                featureAnchor = fxmlLoader.load();

                FeatureController featureController = fxmlLoader.getController();
                featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                if(selectedType != null && presentInOrderFeature(featureLst,f,selectedType)){
                    featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#9DB2BF", Paint.valueOf("WHITE"));
                }
                featureAnchor.setOnMouseClicked(event -> {
                    if (! presentInOrderFeature(featureLst,f,selectedType)) { // remove from selection
                        System.out.println("Add  " + f.getName());
                        handleAddFeature(selectedType,f);
                        featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#9DB2BF", Paint.valueOf("WHITE"));
                    } else {
                        System.out.println("Remove  " + f.getName());
                        featureLst=removeFromList(featureLst,f.getId());
                        featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                    }
                });


                if (col == 6) {
                    col = 0;
                    row++;
                }

                featureGridPane.add(featureAnchor, col++, row);
                featureGridPane.setMargin(featureAnchor, new Insets(2));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAddFeature(String type,Features reqFeature){
        for (OrderFeature of: featureLst) {
            if(of.getFeature().getId()==reqFeature.getId()){
                of.setType(type);
                updateLabel();
                return;
            }
        }
        featureLst.add(new OrderFeature(type,reqFeature));
        updateLabel();
    }
    private boolean presentInOrderFeature(ArrayList<OrderFeature> orderFeatureLst,Features f,String type) {
        for (OrderFeature of:orderFeatureLst) {
            if(of.getFeature().getId()==f.getId() && of.getType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public void setData(ArrayList<Features> features,FeatureLstListener myListener){
        this.myListener=myListener;
        this.productFeatureList=features;
    }
    @FXML
    private void closeDialog() {
        // Get the stage (dialog) and close it
        Stage stage = (Stage) SaveBtn.getScene().getWindow();
        stage.close();
    }
    private ArrayList<OrderFeature> removeFromList( ArrayList<OrderFeature> lst,int id){
        ArrayList<OrderFeature> newLst=new ArrayList<>();
        for (OrderFeature f:
                lst) {
            if(f.getFeature().getId() == id){
                continue;
            }
            newLst.add(f);
        }
        return newLst;
    }
    private void updateLabel(){
        StringBuilder labelTxt=new StringBuilder();
        for (OrderFeature of: featureLst ) {
            labelTxt.append(of.getType()+" - " +of.getFeature().getName()+'\n');
        }
        this.lstLbl.setText(labelTxt.toString());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateType();
        updateFeatures();
    }
    @FXML
    void closeBtnAction(ActionEvent event) {
        ((Stage) this.lstLbl.getScene().getWindow()).close();
    }
}
