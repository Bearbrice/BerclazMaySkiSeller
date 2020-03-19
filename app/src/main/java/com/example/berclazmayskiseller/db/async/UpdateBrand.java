package com.example.berclazmayskiseller.db.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.entity.BrandEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

public class UpdateBrand extends AsyncTask<BrandEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateBrand(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(BrandEntity... params) {
        try {
            for (BrandEntity brand : params)
                database.brandDao().update(brand);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
