package com.example.berclazmayskiseller.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class ProductEntity {

    private String idProduct;
    private String productName;
    private double price;
    private String color;

    /* Constructors */
    public ProductEntity() {
    }

    public ProductEntity(@NonNull String productName, double price, String color) {
        this.productName = productName;
        this.price = price;
        this.color = color;
    }

    /* Methods */
    /* Getters + Setters */
    @Exclude
    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /* Other methods */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ProductEntity)) return false;
        ProductEntity o = (ProductEntity) obj;
        return o.getProductName().equals(this.getProductName());
    }

    @Override
    public String toString() {
        return productName + ", " + price + ", " + color;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productName", productName);
        result.put("price", price);
        result.put("color", color);

        return result;
    }
}
