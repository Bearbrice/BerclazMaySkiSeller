package com.example.berclazmayskiseller.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        foreignKeys =
        @ForeignKey(
                entity = BrandEntity.class,
                parentColumns = "idBrand",
                childColumns = "brand_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"brand_id"}
                )}
)
//@Entity(tableName = "products")
public class ProductEntity {

    /* Primary Key */
    @PrimaryKey(autoGenerate = true)
    private int idProduct;

    /* Columns */
    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "color")
    private String color;

    /* Constructor */
    public ProductEntity(@NonNull String productName) {
        this.productName = productName;
    }

    /* Methods */
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
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
        return productName;
    }
}