package com.example.resturantsystem.model;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;

    private ArrayList<Product> products;

    public Category( String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }
    public Category(int id, String name, ArrayList<Product> products) {
        this.id=id;
        this.name = name;
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
