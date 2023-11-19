package com.example.resturantsystem;

import com.example.resturantsystem.model.Features;
import com.example.resturantsystem.model.Order;
import com.example.resturantsystem.model.OrderFeature;

import java.util.ArrayList;

public interface FeatureLstListener {
    public void onclick(ArrayList<OrderFeature> featurelst);
}
