package com.example.resturantsystem.Misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BusinessHandler {
    private static final String BUSINESS_CONFIG_FILE = "Business.txt";

    public static String getBusinessVatNum(){
        File configFile = new File(BUSINESS_CONFIG_FILE);
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(BUSINESS_CONFIG_FILE));
                String line = reader.readLine();
                if (line != null) {
                    return line.split(",")[0];
                }
            } catch (IOException e) {
                System.out.println("Business Details file Not found");
            }
        }
        return null;
    }
    public static String getBusinessTaxId(){
        File configFile = new File(BUSINESS_CONFIG_FILE);
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(BUSINESS_CONFIG_FILE));
                String line = reader.readLine();
                if (line != null) {
                    return line.split(",")[1];
                }
            } catch (IOException e) {
                System.out.println("Business Details file Not found");
            }
        }
        return null;
    }
}
