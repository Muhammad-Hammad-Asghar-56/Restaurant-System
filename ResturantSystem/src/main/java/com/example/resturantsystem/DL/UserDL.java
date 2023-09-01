package com.example.resturantsystem.DL;

import com.example.resturantsystem.Misc.DBHandler;
import com.example.resturantsystem.model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDL {
    private static ArrayList<User> users=new ArrayList<>();

    public static void InsertNewUser(String userName, String userPass, float auth_Disc, String role) {

        String query = "INSERT INTO User (role, username, userpass, active, auth_disc) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, role);
            statement.setString(2, userName);
            statement.setString(3, userPass);
            statement.setBoolean(4, true);
            statement.setFloat(5, auth_Disc);

            statement.executeUpdate();
            int generatedUserID;
            // Retrieve the generated keys (user ID)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedUserID = generatedKeys.getInt(1);

                } else {
                    throw new SQLException("Failed to get generated user ID.");
                }
            }
            User u1 = new User(generatedUserID,userName, userPass, auth_Disc, role);
            users.add(u1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void unActiveUser(int userID){
        /*
        *
        *       Update just the User active and update it to the "false"
        * */

        //        id, role, username, userpass, active, auth_disc
        String query = "UPDATE User SET active = ? WHERE id = ?";
        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setBoolean(1, false);
            statement.setInt(2, userID);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<User> getUsersWithMaster(){
        users=new ArrayList<>();
        try (Connection con = DBHandler.getDBConnection();
             Statement statement = con.createStatement()) {

            String query = "select * from user where active = true";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String role = resultSet.getString("role");
                String username = resultSet.getString("username");
                String userpass = resultSet.getString("userpass");
                boolean active = resultSet.getBoolean("active");
                float auth_disc = resultSet.getFloat("auth_disc");

                User user = new User(id, username, userpass, auth_disc, role);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public static ArrayList<User> getUsersLst(){
        users=new ArrayList<>();
        try (Connection con = DBHandler.getDBConnection();
             Statement statement = con.createStatement()) {

            String query = "select * from user where active = true and role != 'Master'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String role = resultSet.getString("role");
                String username = resultSet.getString("username");
                String userpass = resultSet.getString("userpass");
                boolean active = resultSet.getBoolean("active");
                float auth_disc = resultSet.getFloat("auth_disc");

                User user = new User(id, username, userpass, auth_disc, role);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public static void updateUserDetails(int userId,User newUserDetails) {
        /*
         *   Update Users Details
         * */
        //        id, role, username, userpass, active, auth_disc
        String query = "UPDATE User SET role = ?, username = ?, userpass = ?, auth_disc = ? WHERE id = ?";
        try (Connection con = DBHandler.getDBConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, newUserDetails.getRole());
            statement.setString(2, newUserDetails.getUserName());
            statement.setString(3, newUserDetails.getUserPass());
            statement.setFloat(4, newUserDetails.getAuth_Disc());
            statement.setInt(5, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
