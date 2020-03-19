package com.example.berclazmayskiseller.db;

import android.os.AsyncTask;
import android.util.Log;

import com.example.berclazmayskiseller.db.entity.BrandEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addBrand(final AppDatabase db, final String brandName) {
        BrandEntity brand = new BrandEntity(brandName);
        db.brandDao().insert(brand);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.brandDao().deleteAll();

        addBrand(db, "Rossignol");
        addBrand(db, "Dynastar");
        addBrand(db, "K2");
        addBrand(db, "Head");
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
