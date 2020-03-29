package com.example.berclazmayskiseller.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.async.order.CreateOrder;
import com.example.berclazmayskiseller.db.async.order.DeleteOrder;
import com.example.berclazmayskiseller.db.async.order.UpdateOrder;
import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

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

    public LiveData<OrderEntity> getOrder(final int orderId, Context application) {
        return ((BaseApp) application).getDatabase().orderDao().getById(orderId);
    }

    public LiveData<List<OrderEntity>> getAllOrders(Context context) {
        return AppDatabase.getInstance(context).orderDao().getAll();
    }

    public LiveData<List<OrderEntity>> getByOwner(final String owner, Application application) {
        return ((BaseApp) application).getDatabase().orderDao().getOwned(owner);
    }

    public void insert(final OrderEntity order, OnAsyncEventListener callback,
                       Context application) {
        new CreateOrder(application, callback).execute(order);
    }

    public void update(final OrderEntity order, OnAsyncEventListener callback,
                       Context application) {
        new UpdateOrder(application, callback).execute(order);
    }

    public void delete(final OrderEntity order, OnAsyncEventListener callback,
                       Context application) {
        new DeleteOrder(application, callback).execute(order);
    }
}
