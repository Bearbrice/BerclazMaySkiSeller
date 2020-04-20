package com.example.berclazmayskiseller.database.repository;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.example.berclazmayskiseller.database.firebase.OrderListLiveData;
import com.example.berclazmayskiseller.database.firebase.OrderLiveData;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<OrderEntity> getOrder(final String orderId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("orders")
                .child(orderId);
        return new OrderLiveData(reference);
    }

    public LiveData<OrderEntity> getByOwner(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(owner)
                .child("orders");
        return new OrderLiveData(reference);
    }

    public LiveData<List<OrderEntity>> getByOwners(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(owner)
                .child("orders");
        return new OrderListLiveData(reference, owner);
    }

    public void insert(final OrderEntity order, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(order.getClientEmail())
                .child("orders");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("clients")

                // Search for the correct UID
                .child(order.getClientEmail())

                .child("orders")
                .child(key)
                .setValue(order, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final OrderEntity order, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(order.getClientEmail())
                .child("orders")
                .child(order.getClientEmail())
                .updateChildren(order.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final OrderEntity order, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(order.getClientEmail())
                .child("orders")
                .child(order.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
