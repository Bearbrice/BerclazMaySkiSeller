package com.example.berclazmayskiseller.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {

    /* Primary key */
    @PrimaryKey(autoGenerate = true)
    private int idProduct;

    /* Columns */
    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "color")
    private String color;

    /* Constructors */
    @Ignore
    public ProductEntity() {
    }

    public ProductEntity(@NonNull String productName, double price, String color) {
        this.productName = productName;
        this.price = price;
        this.color = color;
    }

    /* Methods */
    /* Getters + Setters */
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
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
}
