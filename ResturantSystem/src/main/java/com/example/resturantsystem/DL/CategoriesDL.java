package com.example.resturantsystem.DL;

import com.example.resturantsystem.Misc.DBHandler;
import com.example.resturantsystem.model.Category;
import com.example.resturantsystem.model.Features;
import com.example.resturantsystem.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class CategoriesDL {
    private static ArrayList<Category> categories=new ArrayList<>();


    /*
    *               Categories
    **/
    public static boolean InsertNewCategory(String name) {
        String insertQuery = "INSERT INTO Categories (name, active) " +
                "SELECT ?, true " +
                "WHERE ? NOT IN (SELECT name FROM Categories)";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void InActiveCategory(int categoryId) {
        String query = "UPDATE categories SET active = ? WHERE id = ?";

        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setBoolean(1, false); // Set the active attribute to false
            preparedStatement.setInt(2, categoryId); // Set the category id

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Category inactivated successfully");
            } else {
                System.out.println("Failed to inactivate category");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Category> getCategories(){
        ArrayList<Category> categories=new ArrayList<>();
        String query = "Select * from categories ctg where ctg.active=true";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                ArrayList<Product> products = getProductLst(id);
                categories.add(new Category(id,name,products));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    /*
     *               Products
     **/
    public static boolean insertNewProduct(int categoryId, String productName, float price, float vatTax, String type, ArrayList<Features> featureList) {
        String query = "INSERT INTO Product (name, category_id, vatTax, active, price, type) " +
                "select ?, ?, ?, ?, ?, ? " +
                "where NOT EXISTS (Select 1 from restaurant.Product p1 where p1.name = ?)";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setFloat(3, vatTax);
            preparedStatement.setBoolean(4, true); // Assuming active is always true
            preparedStatement.setFloat(5, price);
            preparedStatement.setInt(6, getEnumTypeIdByName(type, "Product"));

            preparedStatement.setString(7, productName); // check

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generateProductId = generatedKeys.getInt(1);
                    for (Features f : featureList) {
                        linkFeatureWithProduct(generateProductId, f.getId());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // Indicates failure
    }

    public static void InActiveCategoryProduct(int categoryId, int productId) {
        String query = "UPDATE Product SET active = ? WHERE category_id = ? AND id = ?";

        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setBoolean(1, false); // Set the active attribute to false
            preparedStatement.setInt(2, categoryId); // Set the category id
            preparedStatement.setInt(3, productId); // Set the product id

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product inactivated successfully");
            } else {
                System.out.println("Failed to inactivate product");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UpdateProductDetails(int ctgId, int productId, Product newDetail) {
        String query = "UPDATE Product SET name = ?, vatTax = ?, price = ?, type = ? WHERE category_id = ? AND id = ?";

        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, newDetail.getName());
            preparedStatement.setFloat(2, newDetail.getVatTax());
            preparedStatement.setFloat(3, newDetail.getPrice());
            preparedStatement.setInt(4, getEnumTypeIdByName(newDetail.getType(), "Product")); // Assuming getEnumTypeIdByName returns an integer

            preparedStatement.setInt(5, ctgId); // Use setInt for category_id
            preparedStatement.setInt(6, productId); // Use setInt for id

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                deletePrevFeature(productId);
                System.out.println("Product Prev. link delete Successfully");
                for (Features f: newDetail.getFeatureLst()) {
                    Boolean result= linkFeatureWithProduct(productId,f.getId());
                }
            } else {
                System.out.println("Failed to Update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Product> getProductLst(int ctgId) {
        ArrayList<Product> productLst = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.vatTax, p.price, en.name AS type " +
                "FROM categories ctg " +
                "JOIN product p ON ctg.id = p.category_id " +
                "JOIN enumtype en ON en.id = p.type " +
                "WHERE p.active = true AND ctg.id = ?";

        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, ctgId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float vatTax = resultSet.getFloat("vatTax");
                float price = resultSet.getFloat("price");
                String type = resultSet.getString("type");
                ArrayList<Features> featuresLst = getFeatures(id);
                productLst.add(new Product(id, name, price, vatTax, featuresLst, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productLst;
    }


    /*
    *       Features
    */
    public static Features insertNewFeature(String featureName, float featurePrice) {
        String query = "INSERT INTO Feature (name, price) " +
                "VALUES (?, ?)";

        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, featureName);
            preparedStatement.setFloat(2, featurePrice);

            preparedStatement.executeUpdate();
            int id = 0;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1); // Get the ID of the inserted row
                }
            }
            return new Features(id, featureName, featurePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Features> getFeatures(int productId){

       ArrayList<Features> featureLst=new ArrayList<>();
       String query="SELECT F.id,F.name,F.price FROM linkedproductfeature as LPF \n" +
               "JOIN product p ON LPF.productId = p.Id \n" +
               "JOIN Feature f ON LPF.FeatureId = f.id \n" +
               "where p.Id = ? and f.active = true and p.active = true";

       Connection con = DBHandler.getDBConnection();
       try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
           preparedStatement.setInt(1, productId);

           ResultSet resultSet = preparedStatement.executeQuery();

           while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               float price = resultSet.getFloat("price");
               featureLst.add(new Features(id,name,price));
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return featureLst;
   }
    public static ArrayList<Features> getFeatures(){

        ArrayList<Features> featureLst=new ArrayList<>();
        String query="SELECT F.id,F.name,F.price FROM Feature F where F.active = 1" ;

        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                featureLst.add(new Features(id,name,price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featureLst;
    }
    private static void deletePrevFeature(int productId) {
        String query = "DELETE FROM LinkedProductFeature WHERE productId = ?";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            int rowsDeleted = preparedStatement.executeUpdate(); // Use executeUpdate instead of executeQuery
            System.out.println(rowsDeleted + " rows deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Features> getFeaturesExcept(int productId){
        ArrayList<Features> featureLst=new ArrayList<>();
        String query="select * from feature f where f.active=1 and f.id not in (select lpf.featureId from linkedproductfeature lpf where lpf.productId = ?)";

        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                featureLst.add(new Features(id,name,price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return featureLst;
    }
    private static boolean linkFeatureWithProduct(int productId, int featureId) {
        String query = "INSERT INTO LinkedProductFeature (productId, FeatureId)  VALUES (?, ?)";
        Connection con = DBHandler.getDBConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, featureId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Link created between product " + productId + " and feature " + featureId);
                return  true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static int getEnumTypeIdByName(String enumTypeName,String category) {
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
    public static void deleteFeature(int id){
        String query = "UPDATE feature SET active = 0 WHERE id = ?";
        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id); // Set the category id


            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Feature inactivated successfully");
            } else {
                System.out.println("Failed to inactivate Feature");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Features> getAllFeature(){
        String query="select * from feature where active=1";
        Connection con = DBHandler.getDBConnection();
        ArrayList<Features> featureLst=new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int Id= resultSet.getInt("id");
                String name= resultSet.getString("name");
                float price= resultSet.getFloat("price");
                featureLst.add(new Features(Id,name,price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return featureLst;
    }
}
