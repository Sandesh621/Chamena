package com.bcis.chamena.model;

public class Product {
    public String addedBy;
   public String productName;
   public Number price;
    public  String category;
    public String productUrl;

    public Product(String addedBy, String productName, Number price, String category, String productUrl) {
        this.addedBy = addedBy;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.productUrl = productUrl;
    }
}
