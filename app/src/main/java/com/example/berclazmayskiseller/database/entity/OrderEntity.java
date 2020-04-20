package com.example.berclazmayskiseller.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class OrderEntity {
    private String idOrder;
    private String orderDate;
    private String clientEmail;
    private String productId;

    /* Constructors */
    public OrderEntity() {
    }

    public OrderEntity(@NonNull String orderDate, String clientEmail, String productId) {
        this.orderDate = orderDate;
        this.clientEmail = clientEmail;
        this.productId = productId;
    }

    /* Methods */
    /* Getters + Setters */
    @Exclude
    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Exclude
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

//    @Exclude
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return idOrder + ", " + orderDate + ", " + clientEmail + ", " + productId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderDate", orderDate);
        result.put("productId", productId);
        result.put("clientEmail", clientEmail);
        return result;
    }
}
