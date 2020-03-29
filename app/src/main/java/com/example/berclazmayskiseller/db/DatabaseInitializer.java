package com.example.berclazmayskiseller.db;

import android.os.AsyncTask;
import android.util.Log;

import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.entity.ProductEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addProduct(final AppDatabase db, String productName, double price, String color) {
        ProductEntity product = new ProductEntity(productName, price, color);
        db.productDao().insert(product);
    }

    private static void addOrder(final AppDatabase db, String orderDate, String clientEmail, int product_id) {
        OrderEntity order = new OrderEntity(orderDate, clientEmail, product_id);
        db.orderDao().insert(order);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.productDao().deleteAll();

        addProduct(db, "Rossignol Super 2020", 800, "Green");
        addProduct(db, "Dynastar Ultra 2017", 300, "Blue");
        addProduct(db, "Head Cool 2018", 500, "Yellow");
        addProduct(db, "Descente Nice 2019", 600, "Red");
        addProduct(db, "Salomon Free 2015", 200, "Grey");

        db.orderDao().deleteAll();

        addOrder(db, "TODAY", "test@gmail.com" , 1);
        addOrder(db, "TODAY", "test@gmail.com" , 2);
        addOrder(db, "TODAY", "test@gmail.com" , 3);
        addOrder(db, "TODAY", "test@gmail.com" , 4);

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
