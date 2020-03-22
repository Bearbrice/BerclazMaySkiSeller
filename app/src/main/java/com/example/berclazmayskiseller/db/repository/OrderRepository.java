package com.example.berclazmayskiseller.db.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.db.AppDatabase;

import com.example.berclazmayskiseller.db.async.order.DeleteOrder;
import com.example.berclazmayskiseller.db.async.order.UpdateOrder;
import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

import com.example.berclazmayskiseller.db.async.order.CreateOrder;

import java.util.List;

public class OrderRepository {

    private static OrderRepository instance;

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<OrderEntity> getOrder(final int email, Context context) {
        return AppDatabase.getInstance(context).orderDao().getByEmail(email);
    }

    public LiveData<List<OrderEntity>> getAllOrders(Context context) {
        return AppDatabase.getInstance(context).orderDao().getAll();
    }

    public void insert(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new CreateOrder(context, callback).execute(order);
    }

    public void update(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new UpdateOrder(context, callback).execute(order);
    }

    public void delete(final OrderEntity order, OnAsyncEventListener callback, Context context) {
        new DeleteOrder(context, callback).execute(order);
    }
}
