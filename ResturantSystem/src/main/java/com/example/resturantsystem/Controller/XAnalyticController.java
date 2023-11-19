package com.example.resturantsystem.Controller;

import com.example.resturantsystem.DL.OrderDL;
import com.example.resturantsystem.Misc.TxtFileWriter;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class XAnalyticController implements Initializable {
    @FXML
    private Button closeBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField fromTxt;

    @FXML
    private TextField toTxt;
    @FXML
    private Label pdfErrorLbl;

    @FXML
    void cancelBtn(ActionEvent event) {
        Stage s=(Stage) closeBtn.getScene().getWindow();
        s.close();
    }

    @FXML
    void printPdf(ActionEvent event) {
        if(TxtFileWriter.GenerateXPDF("X_Report_")){
            pdfErrorLbl.setText("Done");
            pdfErrorLbl.setTextFill(Paint.valueOf("GREEN"));
//            OrderDL.updatePrintableStatus();
            toTxt.setText("");
            fromTxt.setText("");
        }
        else{
            pdfErrorLbl.setText("No Data Find to print out");
            pdfErrorLbl.setTextFill(Paint.valueOf("RED"));
        }
        confirmBtn.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDates();
    }

    private void setDates() {
        String from =OrderDL.earliestPrintableDateFromX();
        String to = OrderDL.latestPrintableDateFromX();
        if(from==null || to==null){
            confirmBtn.setDisable(true);
            this.fromTxt.setText("No Data Exist");
            this.toTxt.setText("No Data Exist");
            return;
        }
        this.fromTxt.setText(OrderDL.earliestPrintableDateFromX());
        this.toTxt.setText(OrderDL.latestPrintableDateFromX());
    }
}
