package com.example.berclazmayskiseller;

import android.app.Application;

import com.example.berclazmayskiseller.database.repository.ClientRepository;
import com.example.berclazmayskiseller.database.repository.OrderRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    public ClientRepository getClientRepository() {
        return ClientRepository.getInstance();
    }

    public OrderRepository getOrderRepository() {
        return OrderRepository.getInstance();
    }

}