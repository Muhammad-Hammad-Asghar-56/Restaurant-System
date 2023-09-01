package com.example.resturantsystem.Misc;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBHandler {

    private static final String CONFIG_FILE = "./databaseConfiguration.txt";
    private  static String username="root";
    private  static String userpass="Allahpak~1";
    private  static String DBConnectionStr="jdbc:mysql://localhost:3306/Restaurant";

    public static Connection getDBConnection() {
        try {
//            String[] dbConfig =readDatabaseConfig();
//            String userName=dbConfig[0];
//            String userPass=dbConfig[1];
//            String DBConnectionStr=dbConfig[2];
            Connection con = DriverManager.getConnection(DBConnectionStr, username, userpass);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setConfugrations(String userName,String Password,String DBString){
        username=userName;
        userpass=Password;
        DBConnectionStr=DBString;
    }
    public static void promptAndWriteDatabaseConfig() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE));
            writer.write(username + "," + password);
            writer.close();
            System.out.println("Configuration saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("Error writing to file");
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
