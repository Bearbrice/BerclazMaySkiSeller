package com.example.berclazmayskiseller;

import android.app.Application;

import com.example.berclazmayskiseller.database.repository.ClientRepository;
import com.example.berclazmayskiseller.db.AppDatabase;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {


    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ClientRepository getClientRepository() {
        return ClientRepository.getInstance();
    }

}