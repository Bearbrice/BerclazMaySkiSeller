package com.example.berclazmayskiseller.db.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.async.brand_unused.CreateBrand;
import com.example.berclazmayskiseller.db.async.brand_unused.DeleteBrand;
import com.example.berclazmayskiseller.db.async.brand_unused.UpdateBrand;
import com.example.berclazmayskiseller.db.entity.BrandEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

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

    public LiveData<BrandEntity> getBrand(final String email, Context context) {
        return AppDatabase.getInstance(context).brandDao().getByEmail(email);
    }

    public LiveData<List<BrandEntity>> getAllBrands(Context context) {
        //return AppDatabase.getInstance(context).brandDao().getAll();
        return AppDatabase.getInstance(context).brandDao().getAll();
    }

    public void insert(final BrandEntity brand, OnAsyncEventListener callback, Context context) {
        new CreateBrand(context, callback).execute(brand);
    }

    public void update(final BrandEntity brand, OnAsyncEventListener callback, Context context) {
        new UpdateBrand(context, callback).execute(brand);
    }

    public void delete(final BrandEntity brand, OnAsyncEventListener callback, Context context) {
        new DeleteBrand(context, callback).execute(brand);
    }
}

