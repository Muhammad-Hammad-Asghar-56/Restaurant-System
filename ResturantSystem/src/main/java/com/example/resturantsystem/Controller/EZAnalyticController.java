package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.OrderDL;
import com.example.resturantsystem.DL.PdfDB;
import com.example.resturantsystem.Misc.DBHandler;
import com.example.resturantsystem.Misc.TxtFileWriter;
import com.example.resturantsystem.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class EZAnalyticController implements Initializable {
    @FXML
    private Button closeBtn;

    @FXML
    private Button confirmBtn;
    @FXML
    private Label errorLbl;

    @FXML
    private TableView<Order> reportTable;
    @FXML
    private TableColumn<Order, Float> discTableCol;

    @FXML
    private TableColumn<Order, Integer> invoiceTableCol;

    @FXML
    private TableColumn<Order, Float> priceTableCol;


    @FXML
    void cancelBtn(ActionEvent event) {
            Stage s= (Stage) confirmBtn.getScene().getWindow();
            s.close();
    }

    @FXML
    void printPdf(ActionEvent event) {
        if(TxtFileWriter.GenerateEZPDF("EZ_Report_")){
            errorLbl.setText("Done");
            errorLbl.setTextFill(Paint.valueOf("GREEN"));
        }
        else{
            errorLbl.setText("No Data Find to print out");
            errorLbl.setTextFill(Paint.valueOf("RED"));
        }
        confirmBtn.setDisable(true);
    }
    private ObservableList<Order> updateTable() {
        // Define your SQL query
        String query="select mainQuery.orderId 'Invoice Number',sum(mainQuery.Disc)'Disc',sum(mainQuery.TotalPrice) 'Total Price',sum(mainQuery.TotalPay) 'Total Pay',mainQuery.orderDate "+
                "from ( "+
                "	select o.id 'orderID',(subQuery2.totalPrice*o.disc/100) 'Disc' ,subQuery2.totalPrice 'TotalPrice', "+
                "    subQuery2.totalPrice - (subQuery2.totalPrice*o.disc/100) 'TotalPay' "+
                "    ,o.date 'orderDate'  "+
                "    from orders o join ( "+
                "		Select  "+
                "		oi.orderId "+
                "		, (p.price*oi.qty) + COALESCE(subQuery1.featurePrice, 0) totalPrice "+
                "		from orderItems oi "+
                "		left join ( "+
                "			select orf.orderItemId 'OrderItemId',sum(f.price) 'featurePrice' from orderfeature orf "+
                "			join feature f on orf.featureId=f.id "+
                "			where orf.typeId <>  2 "+
                "			group by orf.orderItemId "+
                "		) as subQuery1 on subQuery1.OrderItemId = oi.id "+
                "	join (select id, price + (price*vatTax/100) price,vatTax from product) p on p.id = oi.productId "+
                "	) subQuery2 on  subQuery2.orderId=o.id "+
                ") mainQuery  "+
                "where mainQuery.orderDate > (select max(issueDate) from ezreport) "+
                "group by mainQuery.orderId,mainQuery.OrderDate ";



        Connection con = DBHandler.getDBConnection();
        // Create a PreparedStatement
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Create an ObservableList to store rows of data
            ObservableList<Order> data = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int invoiceNumber = resultSet.getInt("Invoice Number");
                int totalPrice = resultSet.getInt("Total Price");
                int discPercentage = resultSet.getInt("Disc");
                int totalPay = resultSet.getInt("Total Pay");

                Order displayItem=new Order(invoiceNumber,discPercentage,totalPrice);

               data.add(displayItem);
            }
            resultSet.close();
            preparedStatement.close();
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private boolean refreshTable() {
        ObservableList<Order> data = updateTable();
        // set the items of the TableView
        reportTable.setItems(data);

        // set up the columns and map the values of the product attributes to the cells
        invoiceTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        priceTableCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        discTableCol.setCellValueFactory(new PropertyValueFactory<>("disc"));
        return data.size() > 0 ? true : false;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(refreshTable()){
            confirmBtn.setDisable(false);
            return;
        }
        confirmBtn.setDisable(true);
    }
}
