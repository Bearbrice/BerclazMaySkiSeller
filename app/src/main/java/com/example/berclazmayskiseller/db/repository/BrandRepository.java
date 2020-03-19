package com.example.berclazmayskiseller.db.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.async.CreateBrand;
import com.example.berclazmayskiseller.db.async.DeleteBrand;
import com.example.berclazmayskiseller.db.async.UpdateBrand;
import com.example.berclazmayskiseller.db.entity.BrandEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

import java.util.List;

public class BrandRepository {

    private static BrandRepository instance;

    private BrandRepository() {
    }

    public static BrandRepository getInstance() {
        if (instance == null) {
            synchronized (BrandRepository.class) {
                if (instance == null) {
                    instance = new BrandRepository();
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

