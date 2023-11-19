package com.example.resturantsystem.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderItem {
    private int id;
    private Product product;
    private float qty = 1.00f;
    private float disc=0;
    private float price=0;
    private String name;
    private String displayQty;
    private ArrayList<OrderFeature> linkOrderFeature = new ArrayList<>();

    public OrderItem(Product product,float price,float qty,ArrayList<OrderFeature> featureLst){
        this.qty=qty;
        this.product=product;
        this.linkOrderFeature=featureLst;
        this.price=price;
        this.disc=0;
        this.name=product.getName();
        System.out.println(getTotalPrice());
    }


    @Override
    public String toString() {
        StringBuilder name=new StringBuilder();
        name.append(this.product.getName());

        for (OrderFeature of: linkOrderFeature) {
             name.append("\n"+of.getType() +" "+of.getFeature().getName());
        }
        return name.toString();
    }

    public float getQty() {
        if(product.getType().equals("KG")){
                        // Create a DecimalFormat with the desired format
            DecimalFormat decimalFormat = new DecimalFormat("0,000");

            // Format the float with three decimal places and parse it back to float
            String formattedValue = decimalFormat.format(qty);
            float formattedFloat = Float.parseFloat(formattedValue);
            return formattedFloat;
        }
        return qty;
    }


    public void setQty(int qty) {
        this.qty = qty;
    }
    public void incQty(int byValue){
        this.qty += byValue;
    }
    public void descQty(int byValue){
        this.qty -= byValue;
        if (this.qty < 0) {
            qty=0;
        }
    }
    public float getTotalPriceWithoutDisc(){
        float calculatePrice = price;
        for (OrderFeature f :this.linkOrderFeature ) {
            calculatePrice += f.getPrice();
        }

        calculatePrice += product.getPriceWithVatTax();
        calculatePrice = calculatePrice * qty;
        return calculatePrice;
    }
    public float getTotalPrice(){
        float calculatePrice = price;
        for (OrderFeature f :this.linkOrderFeature ) {
            calculatePrice += f.getPrice();
        }
        calculatePrice += product.getPriceWithVatTax();
        calculatePrice = calculatePrice * qty;
        float pay = calculatePrice - (calculatePrice*disc/100);
        return pay;
    }
    public float calculateDiscValue(){
        float calculatePrice = price;
        for (OrderFeature f :this.linkOrderFeature ) {
            calculatePrice += f.getPrice();
        }
        calculatePrice += product.getPriceWithVatTax();
        calculatePrice = calculatePrice * qty;
        return (calculatePrice*disc/100);
    }
    public float getPriceWithoutVatTax(){
        float calculatePrice = price;
        for (OrderFeature f :this.linkOrderFeature ) {
            calculatePrice += f.getPrice();
        }
        calculatePrice += product.getPrice();
        calculatePrice = calculatePrice * qty;

        return calculatePrice;
    }
    public float getDisc() {
        return disc;
    }

    public void setDisc(float disc) {
        this.disc = disc;
    }

    public String getName() {
        return toString();
    }
    public String getOnlyName(){
        return this.name;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product= product;
    }
    public ArrayList<OrderFeature> getLinkOrderFeature(){
        return linkOrderFeature;
    }

    public String getDisplayQty() {
        if(product.getType().equals("KG")){
            DecimalFormat decimalFormat = new DecimalFormat("0.000");
            if(qty%1 == 0.0){
                decimalFormat = new DecimalFormat("0.0");
            }
            String formattedValue = decimalFormat.format(qty);
            return formattedValue;
        }

        return String.valueOf((Math.round(qty)));
    }
}
