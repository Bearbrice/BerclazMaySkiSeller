package com.example.berclazmayskiseller;

import android.app.Application;

import com.example.berclazmayskiseller.db.AppDatabase;
import com.example.berclazmayskiseller.db.repository.ClientRepository;
import com.example.berclazmayskiseller.db.repository.OrderRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ClientRepository getClientRepository() {
        return ClientRepository.getInstance();
    }

}