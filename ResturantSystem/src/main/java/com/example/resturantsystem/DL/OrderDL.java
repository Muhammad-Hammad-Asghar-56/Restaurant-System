package com.example.resturantsystem.DL;

import com.example.resturantsystem.Misc.DBHandler;
import com.example.resturantsystem.Misc.TxtFileWriter;
import com.example.resturantsystem.model.*;

import java.sql.*;
import java.util.ArrayList;

public class OrderDL {
    private static ArrayList<Order> orders = new ArrayList<Order>();


    public static boolean InsertOrder(User user, Order newOrder) {
        // intialize the order in Order Table
        int OrderId = insertOrderDetails(newOrder.getUser().get_Id(), newOrder.getComments(), newOrder.getDisc());

        // intialize the orderItem
        for (OrderItem oi : newOrder.getOrderItems()) {
            insertOrderItem(OrderId, oi.getProduct(), oi.getLinkOrderFeature(), oi.getQty(), oi.getDisc());
        }
        TxtFileWriter.writeCashierFile(user, OrderId, newOrder);
        TxtFileWriter.writeTaxFile(OrderId, newOrder);
        // link with orderItem with the Feature.
        return true;
    }

    private static int insertOrderDetails(int userID, String comments, float disc)  {
        int generatedOrderId = -1; // Initialize with an invalid value
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = DBHandler.getDBConnection();
            // SQL query to insert a new order and retrieve the generated ID
            String sql = "INSERT INTO Orders (userId, comment, disc) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set the values for the parameters
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, comments);
            preparedStatement.setFloat(3, disc);

            // Execute the query
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedOrderId = generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedOrderId;
    }


    private static void insertOrderItem(int OrderId, Product product, ArrayList<OrderFeature> linkedFeatures, float qty, float dics) {
        // store list of the order Items
        // SQL query to insert a new OrderItem and retrieve the generated ID
        int generatedOrderItemId = -1;
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = DBHandler.getDBConnection();
            String sql = "INSERT INTO OrderItems (orderID, productID, qty, disc) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set the values for the parameters
            preparedStatement.setInt(1, OrderId);
            preparedStatement.setInt(2, product.getId());
            preparedStatement.setFloat(3, qty);
            preparedStatement.setFloat(4, dics);

            // Execute the query
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedOrderItemId = generatedKeys.getInt(1);
                for (OrderFeature of : linkedFeatures) {
                    insertFeature(generatedOrderItemId, of.getFeature().getId(), getEnumTypeIdByName(of.getType(), "Feature"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertFeature(int orderItemId, int featureId, int typeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection to the database
            connection = DBHandler.getDBConnection();

            String sql = "INSERT INTO OrderFeature (orderItemId, featureId, typeId) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the values for the parameters
            preparedStatement.setInt(1, orderItemId);
            preparedStatement.setInt(2, featureId);
            preparedStatement.setInt(3, typeID);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int getEnumTypeIdByName(String enumTypeName, String category) {
        String query = "SELECT id FROM EnumType WHERE name = ? and type = ?";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, enumTypeName);
            preparedStatement.setString(2, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Return -1 if not found
    }

    public static Date getOrderDate(int orderId){
        String query="select date from orders where id = ?";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Date(resultSet.getTimestamp("date").getTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return -1 if not found
    }

    public static String earliestPrintableDateFromX(){
        String query="select min(date) date from orders as o where o.date > (select max(issueDate) from xreport);";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("date");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "None"; // Return -1 if not found
    }
    public static String latestPrintableDateFromX(){
        String query="select max(date) date from orders where date > (select max(issueDate) from xreport)";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("date");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "None"; // Return -1 if not found
    }




    public static void zita_perByCategory(){
        String query="SELECT t.id,t.disc 'Tax',SUM(p.price + p.price * p.VatTax / 100 - p.price * oi.disc / 100) as 'Total Price' FROM orderItems oi " +
                "JOIN orderfeature orf on oi.id=orf.orderItemId " +
                "JOIN Product p on p.id=oi.productID " +
                "RIGHT JOIN tax t on t.disc=p.vatTax " +
                "group by t.id,t.disc;";
    }

    public static void zita_totalsvatofOrders(){
        String query="SELECT t.id,t.disc 'Tax', " +
                "        SUM(p.price + p.price * p.VatTax / 100 - p.price * oi.disc / 100) as 'Total Price', " +
                "        SUM(p.price  - p.price * oi.disc / 100) as 'price without vat', " +
                "        SUM(p.price * p.VatTax / 100) as 'only vat' " +
                "FROM orderItems oi " +
                "JOIN orderfeature orf on oi.id=orf.orderItemId " +
                "JOIN Product p on p.id=oi.productID " +
                "RIGHT JOIN tax t on t.disc=p.vatTax " +
                "group by t.id,t.disc; ";
    }
    public static String[] getMinAndMaxOrderPrintableForEZ(){
        String query = "SELECT " +
                "RIGHT(CONCAT('000000', CAST(MIN(id) AS CHAR(6))), 6) AS 'MIN'," +
                "RIGHT(CONCAT('000000', CAST(MAX(id) AS CHAR(6))), 6) AS 'MAX' " +
                "FROM " +
                "orders as o " +
                "WHERE " +
                "o.date > (select max(issueDate) from ezreport);";
        Connection con = DBHandler.getDBConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new String[]{resultSet.getString("MIN"),resultSet.getString("MAX")};
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return -1 if not found
    }
    public static String[] getMinAndMaxOrderPrintableForX(){
        String query = "SELECT " +
                "RIGHT(CONCAT('000000', CAST(MIN(id) AS CHAR(6))), 6) AS 'MIN'," +
                "RIGHT(CONCAT('000000', CAST(MAX(id) AS CHAR(6))), 6) AS 'MAX' " +
                "FROM " +
                "orders as o " +
                "WHERE " +
                "o.date > (select max(issueDate) from xreport);";
        Connection con = DBHandler.getDBConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new String[]{resultSet.getString("MIN"),resultSet.getString("MAX")};
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return -1 if not found
    }
    public static Date[] getMinAndMaxOrderDateForEZ() {
        String query = "SELECT " +
                "Min(date) AS 'MIN', " +
                "Max(date) AS 'MAX' " +
                "FROM " +
                "orders " +
                "WHERE " +
                "date > (select max(issueDate) from ezreport);";
        Connection con = DBHandler.getDBConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if(resultSet.getTimestamp("MIN") != null && resultSet.getTimestamp("MAX")!=null){
                Date minDate = new Date(resultSet.getTimestamp("MIN").getTime());
                Date maxDate = new Date(resultSet.getTimestamp("MAX").getTime());
                return new Date[]{minDate, maxDate};
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if not found
    }
    public static Date[] getMinAndMaxOrderDateForX() {
        String query = "SELECT " +
                "Min(date) AS 'MIN', " +
                "Max(date) AS 'MAX' " +
                "FROM " +
                "orders " +
                "WHERE " +
                "date > (select max(issueDate) from xreport);";
        Connection con = DBHandler.getDBConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if(resultSet.getTimestamp("MIN") != null && resultSet.getTimestamp("MAX")!=null){
                    Date minDate = new Date(resultSet.getTimestamp("MIN").getTime());
                    Date maxDate = new Date(resultSet.getTimestamp("MAX").getTime());
                    return new Date[]{minDate, maxDate};
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if not found
    }
}