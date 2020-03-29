package com.example.berclazmayskiseller.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.berclazmayskiseller.db.entity.ClientEntity;
import com.example.berclazmayskiseller.db.entity.OrderEntity;

import java.util.List;


/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class ClientWithOrders {
    @Embedded
    public ClientEntity client;

    @Relation(parentColumn = "email", entityColumn = "owner", entity = OrderEntity.class)
    public List<OrderEntity> orders;
}