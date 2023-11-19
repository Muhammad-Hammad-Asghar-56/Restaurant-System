package com.example.resturantsystem.model;

import java.util.ArrayList;

public class Order {
    private int id;
    private User user;
    private String comments;
    private float disc;
    private float totalPrice;
    private ArrayList<OrderItem> orderItems=new ArrayList<>();
    private String DateTime;

    public Order(User user){
        this.user=user;
    }

    public Order(int id, User user, String comments, float disc, String DateTime,ArrayList<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.comments = comments;
        this.disc = disc;
        this.orderItems = orderItems;
        this.DateTime=DateTime;
    }
    public Order(User user, String comments, float disc, ArrayList<OrderItem> orderItems) {
        this.user = user;
        this.comments = comments;
        this.disc = disc;
        this.orderItems = orderItems;
    }
    public Order(int orderID,float disc,float totalPrice){
        this.disc=disc;
        this.totalPrice=totalPrice;
        this.id=orderID;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public float getDisc() {
        return disc;
    }

    public void setDisc(float disc) {
        this.disc = disc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void insertOrderItem(OrderItem newItem){
        this.orderItems.add(newItem);
    }

    public void deleteOrderItem(int at){
        this.orderItems.remove(at);
    }
    public void shiftUp(int index){
        if (index <= 0 || index >= orderItems.size()) {
            return; // Cannot shift up if at the first position or index is out of bounds
        }

        // Swap the item at 'index' with the item at 'index - 1'
        OrderItem currentItem = orderItems.get(index);
        orderItems.set(index, orderItems.get(index - 1));
        orderItems.set(index - 1, currentItem);
    }
    public void shiftDown(int index){
        if (index < 0 || index >= orderItems.size() - 1) {
            return; // Cannot shift down if at the last position or index is out of bounds
        }

        // Swap the item at 'index' with the item at 'index + 1'
        OrderItem currentItem = orderItems.get(index);
        orderItems.set(index, orderItems.get(index + 1));
        orderItems.set(index + 1, currentItem);
    }
    public void IncProductQty(int selectedIndex, int i) {
        this.orderItems.get(selectedIndex).incQty(i);
        System.out.println(this.orderItems.get(selectedIndex).getQty());
    }
    public void DescProductQty(int selectedIndex, int i) {
        this.orderItems.get(selectedIndex).descQty(i);
        System.out.println(this.orderItems.get(selectedIndex).getQty());
        if(this.orderItems.get(selectedIndex).getQty()==0){
            this.orderItems.remove(selectedIndex);
        }
    }
    public float calculatePrice(){
        float price=0;
        for (OrderItem oi:this.orderItems){
            price += oi.getTotalPriceWithoutDisc();
        }
        return price;
    }
    public float calculatePriceWithTax(){
        float price=0;
        for (OrderItem oi:this.orderItems){
            price += oi.getTotalPrice();
        }
        return price;
    }
    public float calculatePriceWithDisc(){
        float price=0;
        for (OrderItem oi:this.orderItems){
            price += oi.getTotalPrice();
        }
        return (price-(price*disc/100));
    }
    public float calculatePriceWithoutVat(){
        float price=0;
        for (OrderItem oi:this.orderItems){
            price = price+  oi.getPriceWithoutVatTax();
        }
        return price;
    }
    public float calculateDisc(){
        float price=0;
        for (OrderItem oi:this.orderItems){
            price +=oi.calculateDiscValue();
        }
        return price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float calculateTaxPrice() {
        float price=0f;
        for (OrderItem oi:this.orderItems) {
            price += oi.getProduct().getVatTaxPrice() * oi.getQty();
        }
        return price;
    }
}
