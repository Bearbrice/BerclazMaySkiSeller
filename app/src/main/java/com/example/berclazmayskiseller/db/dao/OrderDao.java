package com.example.berclazmayskiseller.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.berclazmayskiseller.db.entity.OrderEntity;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders WHERE idOrder = :idOrder")
    LiveData<OrderEntity> getByEmail(int idOrder);

    @Query("SELECT * FROM orders")
    LiveData<List<OrderEntity>> getAll();

    @Insert
    void insert(OrderEntity order) throws SQLiteConstraintException;

    @Update
    void update(OrderEntity order);

    @Delete
    void delete(OrderEntity order);

    @Query("DELETE FROM orders")
    void deleteAll();
}
