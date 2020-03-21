package com.example.berclazmayskiseller.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.async.client.CreateClient;
import com.example.berclazmayskiseller.db.async.client.DeleteClient;
import com.example.berclazmayskiseller.db.async.client.UpdateClient;
import com.example.berclazmayskiseller.db.entity.ClientEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;

import java.util.List;


public class ClientRepository {

    private static ClientRepository instance;

    private ClientRepository() {}

    public static ClientRepository getInstance() {
        if (instance == null) {
            synchronized (ClientRepository.class) {
                if (instance == null) {
                    instance = new ClientRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ClientEntity> getClient(final String clientId, Application application) {
        return ((BaseApp) application).getDatabase().clientDao().getById(clientId);
    }

    public LiveData<List<ClientEntity>> getAllClients(Context context) {
        return AppDatabase.getInstance(context).clientDao().getAll();
    }

    public void insert(final ClientEntity client, OnAsyncEventListener callback,
                       Application application) {
        new CreateClient(application, callback).execute(client);
    }

    public void update(final ClientEntity client, OnAsyncEventListener callback,
                       Application application) {
        new UpdateClient(application, callback).execute(client);
    }

    public void delete(final ClientEntity client, OnAsyncEventListener callback,
                       Application application) {
        new DeleteClient(application, callback).execute(client);
    }
}
