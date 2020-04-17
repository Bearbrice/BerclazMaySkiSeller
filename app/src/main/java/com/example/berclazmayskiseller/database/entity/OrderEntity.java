package com.example.berclazmayskiseller.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class OrderEntity {
    private String idOrder;
    private String orderDate;
    private String clientEmail;
    private String product_id;

    /* Constructors */
    public OrderEntity() {
    }

    public OrderEntity(@NonNull String orderDate, String clientEmail, String product_id) {
        this.orderDate = orderDate;
        this.clientEmail = clientEmail;
        this.product_id = product_id;
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
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return idOrder + ", " + orderDate + ", " + clientEmail + ", " + product_id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderDate", orderDate);
        result.put("clientEmail", clientEmail);
        result.put("product_id", product_id);
        return result;
    }
}
