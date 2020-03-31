package com.example.berclazmayskiseller.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.berclazmayskiseller.db.entity.OrderEntity;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders WHERE idOrder = :id")
    LiveData<OrderEntity> getById(int id);

    @Query("SELECT * FROM orders WHERE client_email = :email")
    LiveData<OrderEntity> getByEmail(String email);

    @Query("SELECT * FROM orders")
    LiveData<List<OrderEntity>> getAll();

    @Query("SELECT * FROM orders WHERE client_email=:email")
    LiveData<List<OrderEntity>> getOwned(String email);

    @Insert
    long insert(OrderEntity order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrderEntity> orders);

    @Update
    void update(OrderEntity order);

    @Delete
    void delete(OrderEntity order);

    @Query("DELETE FROM orders")
    void deleteAll();

}
