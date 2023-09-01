package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import com.example.resturantsystem.keyBoardListener;
import com.example.resturantsystem.model.Category;
import com.example.resturantsystem.model.Features;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductsController implements Initializable {

    @FXML
    private GridPane CtgGridPane;

    @FXML
    private GridPane featureGridPane;
    @FXML
    private Button featureBtn;

    @FXML
    private Button addCategoryBtn;

    @FXML
    private Label errorLbl;

    @FXML
    private TextField featureCostTxt;

    @FXML
    private TextField featureNameTxt;

    @FXML
    private ImageView insertFeature;

    @FXML
    private ImageView insertFeature1;

    @FXML
    private Label priceLbl;

    @FXML
    private TextField categoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField priceTxt1;

    @FXML
    private TextField productNameTxt;

    @FXML
    private Button saveProduct;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField vatTaxTxt;
    private AnchorPane selectedAnchor;
    private Category selectedCategory;

    private ArrayList<Category> categories;
    private ArrayList<Features> unselectedFeatures;
    private ArrayList<Features> selectedFeatures=new ArrayList<>();
    private keyBoardListener productNameListener;
    private NumpadListener productpriceListener;
    private NumpadListener productVatTaxListener;
    private keyBoardListener featureNameListener;
    private NumpadListener featureCostListener;
    private keyBoardListener ctgNameListener;


    private VirtualInput myVirtualInput=new VirtualInput();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBox();
        updateCategoriesGrid();
        unselectedFeatures = CategoriesDL.getFeatures();
        insertInFeatureGrid(unselectedFeatures);
        setUpListener();
    }
    private void setUpListener(){
        ctgNameListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                categoryTxt.setText(word);
            }
        };
        productNameListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                productNameTxt.setText(word);
            }
        };
        productpriceListener=new NumpadListener() {
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


        featureNameListener=new keyBoardListener() {
            @Override
            public void onClickListener(String word) {
                featureNameTxt.setText(word);
            }
        };
        featureCostListener=new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                featureCostTxt.setText(word);
            }
        };
    }
    private void setComboBox(){
        typeComboBox.setItems(FXCollections.observableArrayList(
                "Free",
                "KG",
                "Simple"
        ));
        typeComboBox.getSelectionModel().select(2);
    }
    @FXML
    void ChangeTypeAction(InputMethodEvent event) {
    }

    @FXML
    void TypeSelectionAction(ActionEvent event) {
        setUpForProductType();
    }
    @FXML
    void featureBtnAction(ActionEvent event) {
        if(!featureNameTxt.getText().isEmpty() && !featureCostTxt.getText().isEmpty()){
            float price=Float.parseFloat(featureCostTxt.getText());
            Features newFeature= CategoriesDL.insertNewFeature(featureNameTxt.getText(),price);
            if(newFeature == null){
                errorLbl.setText("Try Again");
            }
            else{
                unselectedFeatures = CategoriesDL.getFeatures();
                insertInFeatureGrid(unselectedFeatures);
                featureNameTxt.setText("");
                featureCostTxt.setText("");
            }
        }
    }

    @FXML
    void addCategoryAction(ActionEvent event) {
        if(!categoryTxt.getText().isEmpty()){
            if(! CategoriesDL.InsertNewCategory(categoryTxt.getText())){
                errorLbl.setText("Category name already Present ! Try different name");
            }
            else {
                updateCategoriesGrid();
                categoryTxt.setText("");
            }
        }
        else {
            errorLbl.setText("Category name should not be empty");
        }
    }

    @FXML
    void featureCostAction(MouseEvent event) {
        myVirtualInput.showNumpad("Feature Cost",featureCostListener);
    }

    @FXML
    void featureNameAction(MouseEvent event) {
        myVirtualInput.showKeyBoard(featureNameListener);
    }

    @FXML
    void productNameAction(MouseEvent event) {
        myVirtualInput.showKeyBoard(productNameListener);
    }

    @FXML
    void productPriceAction(MouseEvent event) {
        myVirtualInput.showNumpad("Product Price",productpriceListener);
    }

    @FXML
    void ctgAction(MouseEvent event) {
        myVirtualInput.showKeyBoard(ctgNameListener);
    }

    @FXML
    void vatTaxAction(MouseEvent event) {
        myVirtualInput.showNumpad("Product Vat%",productVatTaxListener);
    }
    @FXML
    void saveAction(ActionEvent event) {
        if(productNameTxt.getText().isEmpty() || vatTaxTxt.getText().isEmpty() || (typeComboBox.getValue()!="Free" && priceTxt.getText().isEmpty()) ){
            errorLbl.setText("OOPS!  Product Details must not be empty.");
            return;
        }
        if(selectedCategory == null){
            errorLbl.setText("OOPS!  You have not select the Category Yet.");
        }

            float price=0;
        if(typeComboBox.getValue()!="Free"){
        price=Float.parseFloat(priceTxt.getText());
        }

        Boolean result = CategoriesDL.insertNewProduct(
                selectedCategory.getId(),
                productNameTxt.getText(),
                price,
                Float.parseFloat(vatTaxTxt.getText()),
                typeComboBox.getValue(),
                selectedFeatures
        );
        if(result){

        productNameTxt.setText("");
        priceTxt.setText("");
        vatTaxTxt.setText("");
        selectedCategory=null;
        updateCategoriesGrid();
        unselectedFeatures = CategoriesDL.getFeatures();
        insertInFeatureGrid(unselectedFeatures);
        }
        else{
            errorLbl.setText("The product named as "+ productNameTxt.getText() +" is already Present in the Record");
        }
    }

    private void setUpForProductType(){
        priceLbl.setVisible(true);
        priceTxt.setVisible(true);
        if(typeComboBox.getValue().equals("Free")){
            priceTxt.setVisible(false);
            priceLbl.setVisible(false);
        }
    }
    private void updateCategoriesGrid(){
        categories=CategoriesDL.getCategories();
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

            ctgAnchor.setOnMouseClicked(event -> {
                if(selectedCategory != null){
                    selectedAnchor.setStyle("-fx-background-color: #DDE6ED; -fx-background-radius: 10;");
                }
                selectedAnchor = ctgAnchor;
                selectedAnchor.setStyle("-fx-background-color: #526D82; -fx-background-radius: 10;");
                selectedCategory=c;
            });

            CtgGridPane.add(ctgAnchor, col, row);

            if (col == 8) {
                row++;
                col = 0;
            } else {
                col++;
            }
        }
    }

    private boolean isPresentInSelectedFeature(int id) {
        for (Features f : selectedFeatures) {
            if (f.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void insertInFeatureGrid(List<Features> insertfeaturesLst) {
        unselectedFeatures = CategoriesDL.getFeatures();
        int row = 1;
        int col = 0;
        if (featureGridPane != null) {
            featureGridPane.getChildren().clear();
        }
        try {
        for (Features f : insertfeaturesLst) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/feature.fxml"));

            AnchorPane featureAnchor = new AnchorPane();
            featureAnchor = fxmlLoader.load();

            FeatureController featureController = fxmlLoader.getController();
            featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
            featureAnchor.setOnMouseClicked(event -> {
                if (! isPresentInSelectedFeature(f.getId())) { // remove from selection
                    System.out.println("Add  " + f.getName());
                    selectedFeatures.add(f);
                    unselectedFeatures = removeFromList(unselectedFeatures, f.getId());
                    featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#9DB2BF", Paint.valueOf("WHITE"));
                } else {
                    System.out.println("Remove  " + f.getName());
                    unselectedFeatures.add(f);
                    selectedFeatures = removeFromList(selectedFeatures, f.getId());
                    featureController.setDetails(f.getName(), String.valueOf(f.getPrice()), "#DDE6ED", Paint.valueOf("BLACK"));
                }


            });


            if (col == 6) {
                col = 0;
                row++;
            }

            featureGridPane.add(featureAnchor, col++, row);


        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<Features> removeFromList( ArrayList<Features> lst,int id){
        ArrayList<Features> newLst=new ArrayList<>();
        for (Features f:
             lst) {
            if(f.getId()==id){
                continue;
            }
            newLst.add(f);
        }
        return newLst;
    }

}
