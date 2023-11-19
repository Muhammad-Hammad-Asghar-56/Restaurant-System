package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.CategoriesDL;
import com.example.resturantsystem.DL.OrderDL;
import com.example.resturantsystem.FeatureLstListener;
import com.example.resturantsystem.Misc.TxtFileWriter;
import com.example.resturantsystem.Misc.VirtualInput;
import com.example.resturantsystem.NumpadListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import com.example.resturantsystem.model.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TakeAwayController implements Initializable {

    @FXML
    private Label discLbl;

    @FXML
    private Label  userName;

    @FXML
    private Label priceLbl;

    @FXML
    private Label totalPriceLbl;

    @FXML
    private GridPane CtgGridPane;

    @FXML
    private GridPane subCtgGridPane;

    @FXML
    private TableView orderTable;
    @FXML
    private TableColumn<OrderItem, String> nameColumn;
    @FXML
    private TableColumn<OrderItem, Integer> qtyColumn;
    @FXML
    private TableColumn<OrderItem, Float> totalPriceColumn;
    @FXML
    private Label errorLabel;

    @FXML
    private TextArea commentBoxTxt;

    private User activeUser;
    private ArrayList<OrderFeature> selectedOrderFeature = new ArrayList<>();
    private VirtualInput myKeyBoard = new VirtualInput();
    private float freeProductPrice = 0.0f;
    private Float kGQty = 0f;
    private Order currentOrder;

    private NumpadListener freeProductPriceListener = new NumpadListener() {
        @Override
        public void onClickListener(String word) {
            freeProductPrice = Float.parseFloat(word);
        }
    };
    private NumpadListener kgQtyListener = new NumpadListener() {
        @Override
        public void onClickListener(String word) {
            kGQty = Float.parseFloat(word);
        }
    };
    private FeatureLstListener myFeatureListener = new FeatureLstListener() {
        @Override
        public void onclick(ArrayList<OrderFeature> featurelst) {
            selectedOrderFeature = featurelst;
        }
    };

    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    private void setSubCtgs(String ctg, ArrayList<Product> productLst) {

        if (subCtgGridPane != null) {
            subCtgGridPane.getChildren().clear(); // Clear the grid

            try {
                int column = 0;
                int row = 1;
                for (int i = 0; i < productLst.size(); i++) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/item.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController item = fxmlLoader.getController();


                    // Add click event to the single grid cell
                    int finalI = i;
                    item.setData(productLst.get(finalI).getName(), productLst.get(finalI).getPriceWithVatTax());

                    item.getNameLabel().setOnMouseClicked(event -> { // add items donot ask for the features
                        Product p = productLst.get(finalI);
                        addOrderItem(p);
                    });


                    item.getPriceLable().setOnMouseClicked(event -> { //  Ask for the Features
                        System.out.println("Clicked on Sub on: " + ctg);
                        if ((! productLst.get(finalI).getType().equals("Free")) &&  productLst.get(finalI).getFeatureLst().size() > 0) {
                            getFeatures( productLst.get(finalI));
                        }
                        Product p = productLst.get(finalI);
                        addOrderItem(p);
                    });

                    if (column == 4) {
                        column = 0;
                        row++;
                    }

                    subCtgGridPane.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    subCtgGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    subCtgGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    subCtgGridPane.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    subCtgGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    subCtgGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    subCtgGridPane.setMaxHeight(Region.USE_PREF_SIZE);


                    GridPane.setMargin(anchorPane, new Insets(0, 2, 2, 0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addOrderItem(Product p) {
        OrderItem newOrderItem = null;
        switch (p.getType()) {
            case "KG": // ask the KGs and the price will be zero
                myKeyBoard.showNumpad("KG Qty ", kgQtyListener);
                newOrderItem = new OrderItem(p, 0, kGQty, selectedOrderFeature);
                break;
            case "Free": // qty will be the 1 need to ask the price
                myKeyBoard.showNumpad("Product Price", freeProductPriceListener);
                newOrderItem = new OrderItem(p, freeProductPrice, 1, selectedOrderFeature);
                break;
            case "Simple": // donot need to ask the price and the qty they are 0 and 1 respectively
                newOrderItem = new OrderItem(p, 0, 1, selectedOrderFeature);
                break;
            default:
                // code block
                break;
        }
        if (orderItems == null) {
            return;
        }
        if(newOrderItem.getQty()==0){
            return;
        }
        currentOrder.insertOrderItem(newOrderItem);
        System.out.println(newOrderItem.toString());
        selectedOrderFeature = new ArrayList<>();
        updateTableView(this.currentOrder.getOrderItems());
    }

    private void getFeatures(Product p) {
        Dialog<Void> FeatureDialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/FeaturePopUp.fxml"));
        Pane keyboardPane;

        try {
            keyboardPane = loader.load();
            FeaturePopUp featurePopUpControls = loader.getController();
            featurePopUpControls.setData(p.getFeatureLst(), myFeatureListener);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        FeatureDialog.getDialogPane().setContent(keyboardPane);
        FeatureDialog.showAndWait();
    }

    public void setUser(User loginUser) {
        this.activeUser = loginUser;
        this.userName.setText(loginUser.getUserName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCategories();
        this.currentOrder = new Order(activeUser);

    }

    public void updateCategories() {
        int column = 0;
        int row = 1;
        ArrayList<Category> categories = CategoriesDL.getCategories();

        for (int i = 0; i < categories.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/resturantsystem/views/categoryTemplate.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CategoryController itemController = fxmlLoader.getController();
            itemController.setData(categories.get(i).getName());
            //  Add click event to each grid cell
            int finalI = i;
            anchorPane.setOnMouseClicked(event -> {
                setSubCtgs(categories.get(finalI).getName(), categories.get(finalI).getProducts());
            });

            if (column == 4) {
                column = 0;
                row++;
            }

            CtgGridPane.add(anchorPane, column++, row); //(child,column,row)
            //set grid width
            CtgGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            CtgGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
            CtgGridPane.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            CtgGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
            CtgGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            CtgGridPane.setMaxHeight(Region.USE_PREF_SIZE);


            GridPane.setMargin(anchorPane, new Insets(5));
        }
    }

    private void updateTableView(ArrayList<OrderItem> orderItems) {
        orderTable.getItems().clear();
        ObservableList<OrderItem> observableOrderItemList = FXCollections.observableArrayList(orderItems);


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("displayQty"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTable.setItems(observableOrderItemList);
        updatePriceLabels();
    }

    private void updatePriceLabels() {
        priceLbl.setText(String.valueOf(currentOrder.calculatePrice()));
        totalPriceLbl.setText(String.valueOf(currentOrder.calculatePriceWithDisc()));
        discLbl.setText(String.valueOf(currentOrder.calculateDisc()));
    }


    //                 Implement the Buttons
    @FXML
    void ApplyDiscAction(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            errorLabel.setText("No Entry Found ! Select any Entry");
            return;
        }

        final float[] disc = {0};
        NumpadListener discListener = new NumpadListener() {
            @Override
            public void onClickListener(String word) {
                disc[0] = Float.parseFloat(word);
                if (activeUser.getAuth_Disc() >= disc[0]) {
                    // Apply the discount to the selected item
                    OrderItem selectedItem = currentOrder.getOrderItems().get(selectedIndex);
                    selectedItem.setDisc(disc[0]);

                    // Update the TableView
                    updateTableView(currentOrder.getOrderItems());
                    return;
                }
                errorLabel.setText("Not Authorized to Disc. " + String.valueOf(disc[0]) + "%");
            }
        };

        myKeyBoard.showNumpad("Discount %", discListener);
    }


    @FXML
    void ShiftDown(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        this.currentOrder.shiftDown(selectedIndex);
        updateTableView(this.currentOrder.getOrderItems());
        orderTable.getSelectionModel().select(selectedIndex < orderTable.getItems().size() ? selectedIndex+1:selectedIndex);
    }

    @FXML
    void ShiftUpAction(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        this.currentOrder.shiftUp(selectedIndex);
        updateTableView(this.currentOrder.getOrderItems());
        orderTable.getSelectionModel().select(selectedIndex !=0 ? selectedIndex-1:selectedIndex);
    }

    @FXML
    void decreaseQtyAction(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        this.currentOrder.DescProductQty(selectedIndex, 1);
        updateTableView(this.currentOrder.getOrderItems());
        orderTable.getSelectionModel().select(selectedIndex);
    }

    @FXML
    void deleteOrderItemAction(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        this.currentOrder.deleteOrderItem(selectedIndex);
        updateTableView(this.currentOrder.getOrderItems());
        orderTable.getSelectionModel().select(selectedIndex);
    }

    @FXML
    void increaseQtyAction(MouseEvent event) {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        this.currentOrder.IncProductQty(selectedIndex, 1);
        updateTableView(this.currentOrder.getOrderItems());
        orderTable.getSelectionModel().select(selectedIndex);
    }

    @FXML
    void saveBtnAction(ActionEvent event) {
        Order newOrder = new Order(activeUser, commentBoxTxt.getText(), 0, currentOrder.getOrderItems());
        OrderDL.InsertOrder(activeUser, newOrder);
        resetPage();
    }

    private void resetPage() {
        this.selectedOrderFeature = new ArrayList<>();
        this.orderTable.getItems().clear();
        this.discLbl.setText("0");
        this.totalPriceLbl.setText("0");
        this.priceLbl.setText("0");
        this.commentBoxTxt.setText("");
        this.errorLabel.setText("");
        this.currentOrder.setOrderItems(new ArrayList<>());
    }

    @FXML
    void XAction(ActionEvent event) {
        System.out.println("X-Action");

        // Create a new stage
        Stage secondaryStage = new Stage();

        // Load the FXML content into the new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/XReport.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the scene for the new stage
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void EZAction(ActionEvent event) {
        System.out.println("EZ-Action");

        // Create a new stage
        Stage secondaryStage = new Stage();

        // Load the FXML content into the new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/resturantsystem/views/EZReport.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the scene for the new stage
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

