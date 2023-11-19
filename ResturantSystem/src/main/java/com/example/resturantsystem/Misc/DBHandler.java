package com.example.resturantsystem.Misc;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {

    private static final String CONFIG_FILE = "dbCredentials.txt";


    //    private  static String username="root";
    //    private  static String userpass="Allahpak~1";
    //    private  static String userpass="pcsouladmin";
    //    private  static String DBConnectionStr="jdbc:mysql://localhost:3306/Restaurant";

    public static Connection getDBConnection() {
        try {
            String[] dbConfig =readDatabaseConfig();
            String userName=dbConfig[0];
            String userPass=dbConfig[1];
            String DBConnectionStr=dbConfig[2];
            Connection con = DriverManager.getConnection(DBConnectionStr, userName, userPass);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static String[] readDatabaseConfig() {

        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
                String line = reader.readLine();
                if (line != null) {
                    return line.split(",");
                }
            } catch (IOException e) {
                System.out.println("DB Design is not  Found");
            }
        }
        return null;
    }

}
