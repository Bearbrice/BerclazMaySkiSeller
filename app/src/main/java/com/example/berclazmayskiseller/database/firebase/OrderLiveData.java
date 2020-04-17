package com.example.berclazmayskiseller.database.firebase;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OrderLiveData extends LiveData<OrderEntity> {

    private static final String TAG = "OrderLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final OrderLiveData.MyValueEventListener listener = new OrderLiveData.MyValueEventListener();

    public OrderLiveData(DatabaseReference ref) {
        reference = ref;
        owner = ref.getParent().getParent().getKey();
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
            OrderEntity entity = dataSnapshot.getValue(OrderEntity.class);
            entity.setIdOrder(dataSnapshot.getKey());
            entity.setClientEmail(owner);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
