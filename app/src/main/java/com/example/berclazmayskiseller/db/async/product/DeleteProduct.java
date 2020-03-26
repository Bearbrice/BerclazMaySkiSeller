package com.example.berclazmayskiseller.db.async.product;

import android.content.Context;
import android.os.AsyncTask;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

public class DeleteProduct extends AsyncTask<ProductEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteProduct(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ProductEntity... params) {
        try {
            for (ProductEntity product : params)
                database.productDao().delete(product);
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