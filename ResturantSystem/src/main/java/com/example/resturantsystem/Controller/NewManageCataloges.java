package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.DL.OrderDL;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import com.example.resturantsystem.model.Category;
import com.example.resturantsystem.model.Features;
import com.example.resturantsystem.model.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewManageCataloges implements Initializable {


    @FXML
    private GridPane CtgGridPane;

    @FXML
    private GridPane productGridPane;

    @FXML
    private TextField categoryTxt;

    @FXML
    private Label errorLbl;

    @FXML
    private GridPane featureCtgGridPane;


    @FXML
    private TextField nameTxt;

    @FXML
    private HBox priceHBOX;

    @FXML
    private TextField priceTxt;

    @FXML
    private GridPane featureLstGridPane;

    @FXML
    private ComboBox<String> typeComboBox;


    @FXML
    private ComboBox<Float> vatTaxComboBox;


    private Category selectedCategory;
    private Product selectedProduct;
    private ArrayList<Features> selectedFeatureLst;
    private Features selectFeature;


    //                          Virtual keyboard Inputs
    private VirtualInput myKeyboard=new VirtualInput();
    private keyBoardListener categoryListerner;
    private NumpadListener productPriceListerner;
    private keyBoardListener productNameListerner;





    @FXML
    void AddNewFeatureBtnAction(ActionEvent event) {
        Dialog<Void> FeatureDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/AddFeature.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        FeatureDialog.getDialogPane().setContent(keyboardPane);
        FeatureDialog.showAndWait();
        updateFeatureLst(CategoriesDL.getFeatures());
    }

    @FXML
    void DeleteFeatureBtnAction(ActionEvent event) {
        if(selectFeature==null){errorLbl.setText("Select any Feature to Delete"); return;}
        CategoriesDL.deleteFeature(selectFeature.getId());
        updateFeatureLst(CategoriesDL.getFeatures());
        selectFeature=null;
    }

    @FXML
    void addNewProductBtnAction(ActionEvent event) {
        if (selectedCategory == null) {
            errorLbl.setText("Select Category to insert the product");
            return;
        }

        Dialog<Void> FeatureDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/AddProductPane.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            AddProductsController productController = loader.getController();
            productController.setCategory(this.selectedCategory);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        FeatureDialog.getDialogPane().setContent(keyboardPane);
        FeatureDialog.showAndWait();
        updateProductGrid(CategoriesDL.getProductLst(this.selectedCategory.getId()));
    }


    @FXML
    void ctgAddBtnAction(ActionEvent event) {
        if(categoryTxt.getText().isEmpty()){
            errorLbl.setText("Category field should not be empty");
            errorLbl.setTextFill(Paint.valueOf("Red"));
            return;
        }
        CategoriesDL.InsertNewCategory(categoryTxt.getText());
        updateCategoriesGrid();
    }

    @FXML
    void ctgDelBtnAction(ActionEvent event) {
        if(selectedCategory==null){errorLbl.setText("Select any Category to Delete"); return;}
        CategoriesDL.InActiveCategory(this.selectedCategory.getId());
        this.selectedCategory=null;
        resetPage();
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
    void handleNameClick(MouseEvent event) {
            myKeyboard.showKeyBoard(productNameListerner , nameTxt!=null ? nameTxt.getText():"");

    }

    @FXML
    void handlePriceClick(MouseEvent event) {
        myKeyboard.showNumpad("Enter Price",productPriceListerner);
    }

    @FXML
    void handleTypeChange(ActionEvent event) {
        priceHBOX.setVisible(true);
        if(typeComboBox.getValue() !=null && typeComboBox.getValue().equals("Free")){
            priceHBOX.setVisible(false);
        }
    }
    @FXML
    void ctgPressAction(MouseEvent event) {
        myKeyboard.showKeyBoard(categoryListerner,categoryTxt!=null?categoryTxt.getText():"");
    }
    @FXML
    void handleUpdateProduct(ActionEvent event) {
        System.out.println("Name : "+nameTxt.getText());
        System.out.println("Vat : "+vatTaxComboBox.getValue());
        System.out.println("type : "+typeComboBox.getValue());
        System.out.println("price : "+priceTxt.getText());
        if(typeComboBox.getValue().equals("Free")){
            if(nameTxt.getText().isEmpty()){
                errorLbl.setText("Please Fill the complete details");
                errorLbl.setTextFill(Paint.valueOf("RED"));
                return;
            }
        }
        else {
            if(nameTxt.getText().isEmpty() || priceTxt.getText().isEmpty()){
                errorLbl.setText("Please Fill the complete details");
                errorLbl.setTextFill(Paint.valueOf("RED"));
                return;
            }
        }
//        if(nameTxt.getText().isEmpty() || vatTaxComboBox.getValue()!=null || (typeComboBox.getValue().equals("Free") && priceTxt.getText().isEmpty()) || priceTxt.getText().isEmpty())
//        {
//            errorLbl.setText("Please Fill the complete details");
//            return;
//        }
        if(typeComboBox.getValue().isEmpty()){
            errorLbl.setText("Please Select any Product Type");
            return;
        }
        selectedProduct.setName(nameTxt.getText());
        selectedProduct.setVatTax(Float.valueOf(vatTaxComboBox.getValue()));
        selectedProduct.setType(typeComboBox.getValue());
        if(typeComboBox.getValue()=="Free"){
            selectedProduct.setPrice(0);
        }
        else{
            selectedProduct.setPrice(Float.valueOf(priceTxt.getText()));
        }
        selectedProduct.setFeatureLst(selectedFeatureLst);
        CategoriesDL.UpdateProductDetails(selectedCategory.getId(),selectedProduct.getId(),selectedProduct);



        resetPage();
        errorLbl.setText("");
    }

    @FXML
    void handleVatTaxClick(MouseEvent event) {

    }
    private void populateComboBox(){
        typeComboBox.getItems().clear();
        typeComboBox.setItems(FXCollections.observableArrayList(
                "Free",
                "KG",
                "Simple"
        ));
        typeComboBox.getSelectionModel().select(2);

        vatTaxComboBox.getItems().clear();
        vatTaxComboBox.setItems(FXCollections.observableArrayList(
                13.0f,24.0f
        ));
        vatTaxComboBox.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCategoriesGrid();
        populateComboBox();
        updateFeatureLst(CategoriesDL.getAllFeature());
        intializeListeners();
    }

    private void intializeListeners() {
        categoryListerner=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                categoryTxt.setText(word);
            }
        };

        productNameListerner=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                nameTxt.setText(word);
            }
        };

        productPriceListerner=new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                priceTxt.setText(word);
            }
        };
    }

    private void updateCategoriesGrid(){
        ArrayList<Category> categories= CategoriesDL.getCategories();
        int row = 1;
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
            if(selectedCategory != null && selectedCategory.getId() == c.getId()){
                ctgAnchor.setStyle("-fx-background-color: #526D82; -fx-background-radius: 10;");
            }
            ctgAnchor.setOnMouseClicked(event -> {
                selectedCategory=c;
                updateCategoriesGrid();
                updateProductGrid(CategoriesDL.getProductLst(c.getId()));
            });

            CtgGridPane.add(ctgAnchor, 0, row);
            row++;
        }
    }
    private void updateProductGrid(ArrayList<Product> products) {
        int row = 1;
        int col = 0;
        if (productGridPane != null) {
            productGridPane.getChildren().clear();
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
                if (col == 2) {
                    col = 0;
                    row++;
                }
                productGridPane.add(featureAnchor, col++, row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateEditPanel(Product selectedProduct){
        nameTxt.setText(selectedProduct.getName());
        priceHBOX.setVisible(true);
        if(selectedProduct.getType().equals("Free")){
            priceHBOX.setVisible(false);
        }
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        vatTaxComboBox.selectionModelProperty().equals(selectedProduct.getVatTax());
    }
    private ArrayList<Integer> insertInFeatureGrid(List<Features> insertfeaturesLst, boolean wantToClear, int row, int col) {

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


    private void updateFeatureLst(List<Features> insertfeaturesLst) {
        int row=1;
        int col=0;

        if(featureLstGridPane != null){
            featureLstGridPane.getChildren().clear();
        }
        try {
            for (Features f : insertfeaturesLst) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/feature.fxml"));

                AnchorPane featureAnchor = new AnchorPane();
                featureAnchor = fxmlLoader.load();

                FeatureController featureController = fxmlLoader.getController();
                featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                if(selectFeature!= null && selectFeature.getId()==f.getId()){
                    featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#526D82", Paint.valueOf("WHITE"));
                }
                featureAnchor.setOnMouseClicked(event -> {
                    selectFeature=f;
                    updateFeatureLst(CategoriesDL.getFeatures());
                });

                if (col == 2) {
                    col = 0;
                    row++;
                }

                featureLstGridPane.add(featureAnchor, col++, row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void resetPage(){
        selectedProduct=null;
        nameTxt.setText("");
        priceTxt.setText("");

        if(this.selectedCategory!=null){ updateProductGrid(CategoriesDL.getProductLst(this.selectedCategory.getId())); }
        else {productGridPane.getChildren().clear();}

        if(featureCtgGridPane != null){
            featureCtgGridPane.getChildren().clear();
        }
        updateCategoriesGrid();
    }
}
