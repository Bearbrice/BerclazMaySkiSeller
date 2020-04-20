package com.example.berclazmayskiseller.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.database.entity.ProductEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListLiveData extends LiveData<List<ProductEntity>> {

    private static final String TAG = "ProductListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ProductListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toProductList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ProductEntity> toProductList(DataSnapshot snapshot) {
        List<ProductEntity> products = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ProductEntity entity = childSnapshot.getValue(ProductEntity.class);
            entity.setIdProduct(childSnapshot.getKey());
            products.add(entity);
        }
        return products;
    }

}