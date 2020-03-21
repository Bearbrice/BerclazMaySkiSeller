package com.example.berclazmayskiseller.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.berclazmayskiseller.db.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products WHERE productName = :productName")
    LiveData<ProductEntity> getByEmail(String productName);

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAll();

    @Insert
    void insert(ProductEntity product) throws SQLiteConstraintException;

    @Update
    void update(ProductEntity product);

    @Delete
    void delete(ProductEntity product);

    @Query("DELETE FROM products")
    void deleteAll();
}
