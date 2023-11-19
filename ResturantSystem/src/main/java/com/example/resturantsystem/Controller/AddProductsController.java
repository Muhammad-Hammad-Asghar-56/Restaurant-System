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
import javafx.stage.Stage;
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
    private ComboBox<Float> vatTaxComboBox;
    private AnchorPane selectedAnchor;
    private Category selectedCategory;

    private ArrayList<Category> categories;
    private ArrayList<Features> unselectedFeatures;
    private ArrayList<Features> selectedFeatures=new ArrayList<>();
    private keyBoardListener productNameListener;
    private NumpadListener productpriceListener;



    private VirtualInput myVirtualInput=new VirtualInput();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBox();
        unselectedFeatures = CategoriesDL.getFeatures();
        insertInFeatureGrid(unselectedFeatures);
        setUpListener();
    }
    public void setCategory(Category selectedCategory){
        this.selectedCategory=selectedCategory;
    }
    private void setCtg(Category selectedCategory){
        this.selectedCategory=selectedCategory;
    }
    private void setUpListener(){

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



    }
    private void setComboBox(){
        typeComboBox.setItems(FXCollections.observableArrayList(
                "Free",
                "KG",
                "Simple"
        ));
        typeComboBox.getSelectionModel().select(2);

        vatTaxComboBox.setItems(FXCollections.observableArrayList(
        13f,
            24f
        ));
        typeComboBox.getSelectionModel().select(1);
    }
    @FXML
    void ChangeTypeAction(InputMethodEvent event) {
    }

    @FXML
    void TypeSelectionAction(ActionEvent event) {
        setUpForProductType();
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
    void CloseBtnAction(ActionEvent event){
        Stage s=(Stage) productNameTxt.getScene().getWindow();
        s.close();
    }


    @FXML
    void saveAction(ActionEvent event) {
        if(productNameTxt.getText().isEmpty() || (typeComboBox.getValue()!="Free" && priceTxt.getText().isEmpty()) ){
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
                vatTaxComboBox.getValue(),
                typeComboBox.getValue(),
                selectedFeatures
        );
        if(result){
         this.CloseBtnAction(event);
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
