package com.example.berclazmayskiseller.db.async.client;

import android.app.Application;
import android.os.AsyncTask;

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.db.entity.ClientEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;


public class CreateClient extends AsyncTask<ClientEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateClient(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ClientEntity... params) {
        try {
            for (ClientEntity client : params)
                ((BaseApp) application).getDatabase().clientDao()
                        .insert(client);
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
