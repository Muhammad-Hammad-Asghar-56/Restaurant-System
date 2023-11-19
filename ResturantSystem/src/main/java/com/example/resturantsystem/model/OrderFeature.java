package com.example.resturantsystem.model;

public class OrderFeature {
    private Features feature;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Features getFeature() {
        return feature;
    }

    public void setFeature(Features feature) {
        this.feature = feature;
    }

    public OrderFeature(String type,Features feature){
        this.type=type;
        this.feature=feature;
    }
    public float getPrice(){
        if(this.type.equals("without")){
            return 0;
        }
        return this.feature.getPrice();
    }
}
