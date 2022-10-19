package com.bcis.chamena.model;

import java.util.List;

public class ProductSecond {
   public String category;
   public List<Product> products;

    public ProductSecond(String category, List<Product> products) {
        this.category = category;
        this.products = products;
    }
}
