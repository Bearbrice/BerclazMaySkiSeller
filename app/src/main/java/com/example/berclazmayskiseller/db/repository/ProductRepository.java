package com.example.berclazmayskiseller.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.async.product.CreateProduct;
import com.example.berclazmayskiseller.db.async.product.DeleteProduct;
import com.example.berclazmayskiseller.db.async.product.UpdateProduct;
import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;

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

    public LiveData<ProductEntity> getProduct(final String name, Context context) {
        return AppDatabase.getInstance(context).productDao().getByName(name);
    }

    public LiveData<ProductEntity> getProductById(final int id, Context context) {
        return AppDatabase.getInstance(context).productDao().getById(id);
    }

    public LiveData<List<ProductEntity>> getAllProducts(Context context) {
        return AppDatabase.getInstance(context).productDao().getAll();
    }

    public void insert(final ProductEntity product, OnAsyncEventListener callback, Context context) {
        new CreateProduct(context, callback).execute(product);
    }

    public void update(final ProductEntity product, OnAsyncEventListener callback, Context context) {
        new UpdateProduct(context, callback).execute(product);
    }

    public void delete(final ProductEntity product, OnAsyncEventListener callback, Context context) {
        new DeleteProduct(context, callback).execute(product);
    }
}
