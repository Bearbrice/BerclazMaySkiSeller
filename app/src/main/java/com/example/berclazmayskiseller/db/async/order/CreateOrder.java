package com.example.berclazmayskiseller.db.async.order;

import android.content.Context;
import android.os.AsyncTask;

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;

public class CreateOrder extends AsyncTask<OrderEntity, Void, Void> {

    private Context context;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateOrder(Context context, OnAsyncEventListener callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(OrderEntity... params) {
        try {
            for (OrderEntity order : params)
                ((BaseApp) context).getDatabase().orderDao()
                        .insert(order);
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