package com.bcis.chamena.model;

public class Order {
    public String orderBy;
    public Number quantity;
    public Number price;
    public String productId;
    public Number timestamp;
    public String productName;
    public String fullName;
    public  String imageUrl;

    public Order(String orderBy, Number quantity, Number price, String productId, Number timestamp, String productName, String fullName, String imageUrl) {
        this.orderBy = orderBy;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.timestamp = timestamp;
        this.productName = productName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }
}

