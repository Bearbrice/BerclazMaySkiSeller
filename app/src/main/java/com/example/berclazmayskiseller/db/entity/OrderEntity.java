package com.example.berclazmayskiseller.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "orders")
public class OrderEntity {

    /* Primary Key */
    @PrimaryKey(autoGenerate = true)
    private int idOrder;

    /* Columns */
    @ColumnInfo(name = "date")
    private Date date;

    /* Constructor */
    public OrderEntity(@NonNull Date date) {
        this.date = date;
    }

    /* Methods */
    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) return false;
//        if (obj == this) return true;
//        if (!(obj instanceof OrderEntity)) return false;
//        OrderEntity o = (OrderEntity) obj;
//        return o.getOrderName().equals(this.getOrderName());
//    }

//    @Override
//    public String toString() {
//        return orderName;
//    }
}