package com.example.berclazmayskiseller.database.repository;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.database.entity.ProductEntity;
import com.example.berclazmayskiseller.database.firebase.ProductListLiveData;
import com.example.berclazmayskiseller.database.firebase.ProductLiveData;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductRepository {

    private static ProductRepository instance;

    private ProductRepository() {
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    // Get product by name
    public LiveData<ProductEntity> getProduct(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("products")
                .child(name);
        return new ProductLiveData(reference);
    }

    // Get product by id
    public LiveData<ProductEntity> getProductById(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("products")
                .child(id);
        return new ProductLiveData(reference);
    }

    // Get all products
    public LiveData<List<ProductEntity>> getAllProducts() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("products");
        return new ProductListLiveData(reference);
    }

    // Insert
    public void insert(final ProductEntity product, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("products").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(id)
                .setValue(product, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    // Update
    public void update(final ProductEntity product, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(product.getIdProduct())
                .updateChildren(product.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    // Delete
    public void delete(final ProductEntity product, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(product.getIdProduct())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
