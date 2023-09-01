package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import com.example.resturantsystem.model.Category;
import com.example.resturantsystem.model.Features;
import com.example.resturantsystem.model.Fruit;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.resturantsystem.model.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class MasterProductController implements Initializable {

    @FXML
    private GridPane CtgGridPane;

    @FXML
    private ScrollPane catgScroll;

    @FXML
    private ScrollPane catgScroll1;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private VBox chosenFruitCard1;

    @FXML
    private Button ctgDelBtn;

    @FXML
    private Label errorLbl;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitNameLable1;

    @FXML
    private Button itemDeleteBtn;


    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private GridPane subCtgGridPane;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField vatTaxTxt;
    @FXML
    private HBox priceHBOX;
    @FXML
    private GridPane featureCtgGridPane;


    private Category selectedCategory;
    private Product selectedProduct;

    private keyBoardListener productNameListener;
    private NumpadListener productPriceListener;
    private NumpadListener productVatTaxListener;

    private ArrayList<Features> selectedFeatureLst;
    private VirtualInput myVirtualInput=new VirtualInput();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBox();
        resetPage();
    }
    private void resetPage(){
        selectedProduct=null;
        nameTxt.setText("");
        priceTxt.setText("");
        vatTaxTxt.setText("");
        if(subCtgGridPane != null){
            subCtgGridPane.getChildren().clear();
        }
        if(featureCtgGridPane != null){
            featureCtgGridPane.getChildren().clear();
        }
        updateCategoriesGrid();
        updateListener();
    }
    private void populateComboBox(){
        typeComboBox.getItems().clear();
        typeComboBox.setItems(FXCollections.observableArrayList(
                "Free",
                "KG",
                "Simple"
        ));
        typeComboBox.getSelectionModel().select(2);
    }
    private void updateListener(){
        productNameListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                nameTxt.setText(word);
            }
        };
        productPriceListener=new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                priceTxt.setText(word);
            }
        };
        productVatTaxListener=new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                vatTaxTxt.setText(word);
            }
        };
    }
    @FXML
    void handleCtgDel(ActionEvent event) {
        if(selectedCategory == null){
            errorLbl.setText("No one category has been selected");
            return;
        }
        CategoriesDL.InActiveCategory(selectedCategory.getId());
        updateCategoriesGrid();
    }

    @FXML
    void handleItemDel(ActionEvent event) {
        if(selectedCategory == null){
            errorLbl.setText("No one category has been selected");
            return;
        }
        if(selectedProduct == null){
            errorLbl.setText("No one category has been selected");
            return;
        }
        CategoriesDL.InActiveCategoryProduct(selectedCategory.getId(),selectedProduct.getId());
        resetPage();
    }
    @FXML
    void handleUpdateProduct(ActionEvent event){

        if(nameTxt.getText().isEmpty() || vatTaxTxt.getText().isEmpty() || (typeComboBox.getValue().equals("Free") && priceTxt.getText().isEmpty()) || priceTxt.getText().isEmpty())
        {
            errorLbl.setText("Please Fill the complete details");
            return;
        }
        if(typeComboBox.getValue().isEmpty()){
            errorLbl.setText("Please Select any Product Type");
            return;
        }
        selectedProduct.setName(nameTxt.getText());
        selectedProduct.setVatTax(Float.valueOf(vatTaxTxt.getText()));
        selectedProduct.setType(typeComboBox.getValue());
        if(typeComboBox.getValue()=="Free"){
            selectedProduct.setPrice(0);
        }
        else{
            selectedProduct.setPrice(Float.valueOf(priceTxt.getText()));
        }
        selectedProduct.setFeatureLst(selectedFeatureLst);
        CategoriesDL.UpdateProductDetails(selectedCategory.getId(),selectedProduct.getId(),selectedProduct);


        subCtgGridPane.getChildren().clear();
        resetPage();
    }
    @FXML
    void handleNameClick(MouseEvent event) {
        myVirtualInput.showKeyBoard(productNameListener);
    }

    @FXML
    void handlePriceClick(MouseEvent event) {
        myVirtualInput.showNumpad("Product Price",productVatTaxListener);
    }

    @FXML
    void handleTypeChange(ActionEvent event) {
        priceHBOX.setVisible(true);
        if(typeComboBox.getValue() !=null && typeComboBox.getValue().equals("Free")){
            priceHBOX.setVisible(false);
        }
    }

    @FXML
    void handleVatTaxClick(MouseEvent event) {
        myVirtualInput.showNumpad("Vat Tax %",productVatTaxListener);
    }

    private void updateCategoriesGrid(){
        ArrayList<Category> categories= CategoriesDL.getCategories();
        int row = 1;
        int col = 0;
        if (CtgGridPane != null) {
            CtgGridPane.getChildren().clear();
        }
        for (Category c : categories) {
            AnchorPane ctgAnchor = new AnchorPane();
            ctgAnchor.setStyle("-fx-background-color: #DDE6ED; -fx-background-radius: 10;");

            // Create and configure the label
            Label nameLabel = new Label(c.getName());
            nameLabel.setPadding(new Insets(10, 20, 10, 20));
            nameLabel.setAlignment(Pos.CENTER);

            ctgAnchor.getChildren().add(nameLabel);
            // Center-align the label both horizontally and vertically
            AnchorPane.setTopAnchor(nameLabel, 0.0);
            AnchorPane.setRightAnchor(nameLabel, 0.0);
            AnchorPane.setBottomAnchor(nameLabel, 0.0);
            AnchorPane.setLeftAnchor(nameLabel, 0.0);
            if(selectedCategory !=null && selectedCategory.getId() == c.getId()){
                ctgAnchor.setStyle("-fx-background-color: #526D82; -fx-background-radius: 10;");
            }
            ctgAnchor.setOnMouseClicked(event -> {
                selectedCategory=c;
                updateCategoriesGrid();
                updateProductGrid(CategoriesDL.getProductLst(c.getId()));
            });

            CtgGridPane.add(ctgAnchor, col, row);

            if (col == 2) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
    }
    private void updateProductGrid(ArrayList<Product> products) {

        int row = 1;
        int col = 0;
        if (subCtgGridPane != null) {
            subCtgGridPane.getChildren().clear();
        }
        try {
            for (Product p : products) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/feature.fxml"));

                AnchorPane featureAnchor = new AnchorPane();
                featureAnchor = fxmlLoader.load();

                FeatureController featureController = fxmlLoader.getController();
                featureController.setDetails(p.getName(), String.valueOf(p.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));

                featureAnchor.setOnMouseClicked(event -> {
                    selectedProduct=p;
                    updateEditPanel(p);
                    selectedFeatureLst=p.getFeatureLst();
                    ArrayList<Integer> newArr= insertInFeatureGrid(selectedFeatureLst,true,1,0);
                    insertInFeatureGrid(CategoriesDL.getFeaturesExcept(p.getId()),false,newArr.get(0),newArr.get(1));
                });
                if (col == 4) {
                    col = 0;
                    row++;
                }
                subCtgGridPane.add(featureAnchor, col++, row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<Integer> insertInFeatureGrid(List<Features> insertfeaturesLst,boolean wantToClear,int row,int col) {

        if (wantToClear && featureCtgGridPane != null) {
            featureCtgGridPane.getChildren().clear();
        }

        try {
            for (Features f : insertfeaturesLst) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/feature.fxml"));

                AnchorPane featureAnchor = new AnchorPane();
                featureAnchor = fxmlLoader.load();

                FeatureController featureController = fxmlLoader.getController();
                featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                if(wantToClear){
                    featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#9DB2BF", Paint.valueOf("BLACK"));
                }
                featureAnchor.setOnMouseClicked(event -> {
                    if(PresentInSelected(f.getId())){
                        removeFromSelectedFeature(f.getId());
                        featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                    }
                    else{
                        selectedFeatureLst.add(f);
                        featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#9DB2BF", Paint.valueOf("BLACK"));
                    }
                });

                if (col == 3) {
                    col = 0;
                    row++;
                }

                featureCtgGridPane.add(featureAnchor, col++, row);
            }
            ArrayList<Integer> newArr=new ArrayList<Integer>();
            newArr.add(row);
            newArr.add(col);
            return newArr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeFromSelectedFeature(int id) {
        ArrayList<Features> newFeatures=new ArrayList<>();
        for (Features f:selectedFeatureLst) {
            if(f.getId()==id){
                continue;
            }
            newFeatures.add(f);
        }
        selectedFeatureLst=newFeatures;
    }

    private boolean PresentInSelected(int id) {

        for (Features f:selectedFeatureLst ) {
            if(f.getId()==id){
             return true;
            }
        }
        return false;
    }

    private void updateEditPanel(Product selectedProduct){
        nameTxt.setText(selectedProduct.getName());
        priceHBOX.setVisible(true);
        if(selectedProduct.getType().equals("Free")){
            priceHBOX.setVisible(false);
        }
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        vatTaxTxt.setText(String.valueOf(selectedProduct.getVatTax()));
    }
}
