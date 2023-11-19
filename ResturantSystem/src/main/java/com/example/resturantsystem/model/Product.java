package com.example.resturantsystem.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private float price;
    private float vatTax;
    private ArrayList<Features> FeatureLst;
    private String Type;


    public Product(String name, float price, float vatTax, ArrayList<Features> featureLst, String type) {
        this.name = name;
        this.price = price;
        this.vatTax = vatTax;
        FeatureLst = featureLst;
        Type = type;
    }

    public Product(int id, String name, float price, float vatTax, ArrayList<Features> featureLst, String type) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.vatTax = vatTax;
        FeatureLst = featureLst;
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }
    public float getPriceWithVatTax(){
        return price + (price * vatTax / 100);
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public float getVatTax() {
        return vatTax;
    }
    public String getDisplayVatTax(){
        DecimalFormat decimalFormat = new DecimalFormat("0");
        return decimalFormat.format(this.vatTax)+'%';
    }
    public void setVatTax(float vatTax) {
        this.vatTax = vatTax;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public ArrayList<Features> getFeatureLst() {
        return FeatureLst;
    }

    public void setFeatureLst(ArrayList<Features> featureLst) {
        FeatureLst = featureLst;
    }
    public float getVatTaxPrice(){
        return this.price * this.vatTax / 100;
    }
}
