package com.example.resturantsystem.Misc;
import com.example.resturantsystem.DL.OrderDL;
import com.example.resturantsystem.DL.PdfDB;
import com.example.resturantsystem.model.Order;
import com.example.resturantsystem.model.OrderFeature;
import com.example.resturantsystem.model.OrderItem;
import com.example.resturantsystem.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TxtFileWriter {
    public static void writeCashierFile(User user, int orderId, Order order) {
        // Specify the absolute path to the "data" directory
        String directoryPath = System.getProperty("user.dir") + "\\data\\"; // On Windows, use double backslashes
        // Create the "data" directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = directoryPath + "cashier_" + orderId + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the order header
            writer.write("HL/"+user.getUserName()+"/"+ + orderId + "// \n");

            // Write order items
            for (OrderItem item : order.getOrderItems()) {

                String productDetail = "SL/" + item.getProduct().getName() + " //" +
                        item.getDisplayQty() + "/" +
                        getFormatedValue(item.getTotalPrice()) + "/" +
                        BusinessHandler.getBusinessVatNum() + "/"+
                        item.getProduct().getDisplayVatTax();
                writer.write(productDetail + " \n");
            }

            // Write order footer
            writer.write("CR/"+ order.calculatePriceWithDisc() +"/METRHTA \n");

            System.out.println("Order details written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int searchDiscID(float disc, String vatTax) {
        String query = "select id from tax where disc = ? and name = ? ";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setFloat(1, disc);
            preparedStatement.setString(2, vatTax);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public static String getFormatedValue(float value){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        if(value % 1 == 0.0f){ decimalFormat=new DecimalFormat("0"); }
        return decimalFormat.format(value);
    }
    public static void writeTaxFile(int orderID,Order order){
        String directoryPath = System.getProperty("user.dir") + "\\data\\"; // On Windows, use double backslashes

        // Create the "data" directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = directoryPath + "Tax_" + orderID + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
        StringBuilder invoiceText = new StringBuilder();
        SimpleDateFormat dataFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat codeDateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        // Header
        invoiceText.append("*START OF LEGAL INVOICE* \n");
        invoiceText.append("1234 Elm Street \n");
        invoiceText.append("City, Greece \n");
        invoiceText.append("Phone: (123) 456-7890 \n");
        invoiceText.append("Website: www.streetcafe.com \n");

        // Invoice Details
        invoiceText.append("**Invoice** \n");
        invoiceText.append("Invoice Number: ").append(orderID).append(" \n");
        invoiceText.append("Date: ").append(dataFormat.format(OrderDL.getOrderDate(orderID))).append(" \n");

        // Itemized List
        invoiceText.append(String.format("%-20s %-10s %-10s %-10s \n", "Item", "Quantity", "Price", "Tax"));
        invoiceText.append("-------------------------------------------------- \n");
        for (OrderItem item : order.getOrderItems()) {
            String str=String.format("%-20s %-10s %-10s %-10s",
                    item.getOnlyName(), item.getDisplayQty(),  getFormatedValue(item.getTotalPrice()), item.getProduct().getDisplayVatTax());
            invoiceText.append(str);
        }

        invoiceText.append("\n");
        invoiceText.append(String.format("Subtotal: %s € \n", getFormatedValue(order.calculatePrice())));
        invoiceText.append(String.format("Disc: %.2f € \n", order.calculateDisc()));
        invoiceText.append('\n');
        invoiceText.append(String.format("Total Amount: %s € \n",  getFormatedValue(order.calculatePriceWithDisc())));

        // Footer
        invoiceText.append("**Thank you for your purchase!** \n");

        invoiceText.append("*END OF LEGAL INVOICE* \n" +
                " " + "Marking \n" +
                "[<]"+ BusinessHandler.getBusinessVatNum()
                +";;;"+ codeDateFormat.format(OrderDL.getOrderDate(orderID))
                +";173;;"+ orderID
                +";" + getFormatedValue(order.calculatePriceWithoutVat())
                +";0.00;0.00;0.00;0.00;"+ getFormatedValue(order.calculateTaxPrice())
                +";0.00;0.00;0.00;"+getFormatedValue(order.calculatePriceWithTax())
                +";0[>]\n");
        writer.write(invoiceText.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String[]> getPerByCtg() {
        // Define your SQL query

        String query="SELECT t.id AS ID,t.disc 'Tax',SUM(p.price + p.price * p.VatTax / 100 - p.price * oi.disc / 100) as 'Total Price' FROM orderItems oi " +
                "JOIN orderfeature orf on oi.id=orf.orderItemId " +
                "JOIN Product p on p.id=oi.productID " +
                "RIGHT JOIN tax t on t.disc=p.vatTax " +
                "group by t.id,t.disc;";

        Connection con = DBHandler.getDBConnection();
        // Create a PreparedStatement
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String[]> perByCtg= new ArrayList<>();
            // Loop through the result set and print the data
            DecimalFormat df = new DecimalFormat("0.00");
            while (resultSet.next()) {

                int invoiceNumber = resultSet.getInt("ID");
                Float disc = resultSet.getFloat("Tax");
                double totalPrice = resultSet.getDouble("Total Price");

                System.out.println("Invoice Number: " + invoiceNumber);
                System.out.println("Disc: " + disc);
                System.out.println("Total Price: " + totalPrice);
                System.out.println("----------------------------------");
                perByCtg.add(new String[]{String.format("%.2f",disc), String.format("%.2f",totalPrice)});
            }

            // Close the resources
            resultSet.close();
            preparedStatement.close();
            con.close();


            if (perByCtg.size()>0){
                return perByCtg;
            }
            else{
                return null;
            }
        } catch(SQLException e)

        {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String[]> getPerByOrder(){
        // Define your SQL query

        String query="SELECT t.id,t.disc 'Tax', " +
                "        SUM(p.price + p.price * p.VatTax / 100 - p.price * oi.disc / 100) as 'Total Price', " +
                "        SUM(p.price  - p.price * oi.disc / 100) as 'price without vat', " +
                "        SUM(p.price * p.VatTax / 100) as 'only vat' " +
                "FROM orderItems oi " +
                "JOIN orderfeature orf on oi.id=orf.orderItemId " +
                "JOIN Product p on p.id=oi.productID " +
                "RIGHT JOIN tax t on t.disc=p.vatTax " +
                "group by t.id,t.disc; ";

        Connection con = DBHandler.getDBConnection();
        // Create a PreparedStatement
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String[]> perByCtg= new ArrayList<>();
            // Loop through the result set and print the data
            DecimalFormat df = new DecimalFormat("0.00");
            while (resultSet.next()) {

                int invoiceNumber = resultSet.getInt("Tax");
                Float totalPrice = resultSet.getFloat("Total Price");
                Float withoutVat = resultSet.getFloat("price without vat");
                Float onlyVat = resultSet.getFloat("only vat");
                if(totalPrice==0){
                    continue;
                }
                System.out.println("Tax : " + invoiceNumber);
                System.out.println("T-Price : " + totalPrice);
                System.out.println("Without Vat-Price : " + withoutVat);
                System.out.println("Only Vat Price : " + onlyVat);
                System.out.println("----------------------------------");
                perByCtg.add(new String[]{String.valueOf(invoiceNumber), String.format("%.2f",totalPrice), String.format("%.2f",withoutVat), String.format("%.2f",onlyVat)});
            }

            // Close the resources
            resultSet.close();
            preparedStatement.close();
            con.close();


            if (perByCtg.size()>0){
                return perByCtg;
            }
            else{
                return null;
            }
        } catch(SQLException e)

        {
            e.printStackTrace();
        }
        return null;

    }
    public static boolean GenerateEZPDF(String fileNamePrefix){
        // Specify the absolute path to the "data" directory
        String directoryPath = System.getProperty("user.dir") + "\\Reports\\"; // On Windows, use double backslashes

        // Create the "data" directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String fileName = directoryPath + fileNamePrefix  + currentDateTime.replace(' ','_').replace(':','-');

//        ArrayList<String[]> perByCtg = getPerByCtg();
        ArrayList<String[]> perByOrder = getPerByOrder();
        String[] orderNum=OrderDL.getMinAndMaxOrderPrintableForEZ();
        int seqId= PdfDB.getEZSeqNumber();
        Date[] dates=OrderDL.getMinAndMaxOrderDateForEZ();
        Date issueDate=new Date();
        PdfDB.insertEZReport(issueDate,Integer.parseInt(orderNum[0]),Integer.parseInt(orderNum[1]));
        ItextPDF.createEZPDF(perByOrder,orderNum,seqId,dates[0],dates[1],issueDate,fileName,"Z Report id : "+seqId);
        return true;
    }



    public static boolean GenerateXPDF(String fileNamePrefix){
        // Specify the absolute path to the "data" directory
        String directoryPath = System.getProperty("user.dir") + "\\Reports\\"; // On Windows, use double backslashes

        // Create the "data" directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date issueDate=new Date();
        String currentDateTime = dateTimeFormat.format(issueDate);

        String fileName = directoryPath + fileNamePrefix  + currentDateTime.replace(' ','_').replace(':','-');

//        ArrayList<String[]> perByCtg = getPerByCtg();
        ArrayList<String[]> perByOrder = getPerByOrder();

        String[] orderNum=OrderDL.getMinAndMaxOrderPrintableForX();
        Date[] dates = OrderDL.getMinAndMaxOrderDateForX();

        PdfDB.insertXReport(issueDate,Integer.parseInt(orderNum[0]),Integer.parseInt(orderNum[1]));
        ItextPDF.createXPDF(perByOrder,orderNum,dates[0],dates[1],issueDate,fileName,"X Report");
        return true;
    }
}
