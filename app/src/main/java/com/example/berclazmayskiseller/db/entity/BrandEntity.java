package com.example.berclazmayskiseller.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "brands")
public class BrandEntity implements Comparable {

    /* Primary Key */
    @PrimaryKey(autoGenerate = true)
    private int idBrand;

    /* Columns */
    @ColumnInfo(name = "brandName")
    private String brandName;

    /* Constructor */
    public BrandEntity(@NonNull String brandName) {
        this.brandName = brandName;
    }

    /* Methods */
    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof BrandEntity)) return false;
        BrandEntity o = (BrandEntity) obj;
        return o.getBrandName().equals(this.getBrandName());
    }

    @Override
    public String toString() {
        return brandName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}