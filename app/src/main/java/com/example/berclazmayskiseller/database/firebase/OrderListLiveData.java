package com.example.berclazmayskiseller.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderListLiveData extends LiveData<List<OrderEntity>> {

    private static final String TAG = "OrderListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final MyValueEventListener listener = new MyValueEventListener();

    public OrderListLiveData(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toOrders(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<OrderEntity> toOrders(DataSnapshot snapshot) {
        List<OrderEntity> orders = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            OrderEntity entity = childSnapshot.getValue(OrderEntity.class);
            entity.setIdOrder(childSnapshot.getKey());
            entity.setClientEmail(owner);
            orders.add(entity);
        }
        return orders;
    }
}