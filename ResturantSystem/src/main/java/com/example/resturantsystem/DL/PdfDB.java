package com.example.resturantsystem.DL;

import com.example.resturantsystem.Misc.DBHandler;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfDB {

    public static Integer getEZSeqNumber(){
        String query="select max(id)+1 seqNo from EZReport";
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = DBHandler.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Integer num= resultSet.getInt("seqNo");
                return num != null ? num: 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }
    public static Integer getXSeqNumber(){
        String query="select max(id)+1 seqNo from XReport";
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = DBHandler.getDBConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Integer num= resultSet.getInt("seqNo");
                return num != null ? num: 0;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static boolean insertEZReport(Date issueDate, int startedFrom, int endingOn) {
        String query = "INSERT INTO EZReport (issueDate, StartedFrom, EndingOn) VALUES (?, ?, ?)";
        Connection con = DBHandler.getDBConnection();
        DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dFormat.format(issueDate));
            preparedStatement.setInt(2, startedFrom);
            preparedStatement.setInt(3, endingOn);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // Return -1 if not successful
    }

    public static boolean insertXReport(Date issueDate, int startedFrom, int endingOn) {
        String query = "INSERT INTO XReport (issueDate, StartedFrom, EndingOn) VALUES (?, ?, ?)";
        Connection con = DBHandler.getDBConnection();
        DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, dFormat.format(issueDate));
            preparedStatement.setInt(2, startedFrom);
            preparedStatement.setInt(3, endingOn);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>1){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
