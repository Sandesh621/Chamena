package com.bcis.chamena.model;

import java.util.Date;

public class Product {
    public String addedBy;
   public String productName;
   public Number price;
    public  String category;
    public String productUrl;
    public String imagePath;
   public String docId;
   public Number timestamp;

    public Product(String addedBy, String productName, Number price, String category, String productUrl,String imagePath) {
        this.addedBy = addedBy;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.productUrl = productUrl;
        this.imagePath=imagePath;
        timestamp = System.currentTimeMillis();
    }

    public Product(String addedBy, String productName, Number price, String category, String productUrl, String imagePath, String docId,Number timestamp) {
        this.addedBy = addedBy;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.productUrl = productUrl;
        this.imagePath = imagePath;
        this.docId = docId;
        this.timestamp= timestamp;
    }
}
