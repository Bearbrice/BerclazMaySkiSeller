package com.example.berclazmayskiseller.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.berclazmayskiseller.db.entity.BrandEntity;

import java.util.List;

@Dao
public interface BrandDao {

    @Query("SELECT * FROM brands WHERE brandName = :brandName")
    LiveData<BrandEntity> getByEmail(String brandName);

    @Query("SELECT * FROM brands")
    LiveData<List<BrandEntity>> getAll();

    @Insert
    void insert(BrandEntity brand) throws SQLiteConstraintException;

    @Update
    void update(BrandEntity brand);

    @Delete
    void delete(BrandEntity brand);

    @Query("DELETE FROM brands")
    void deleteAll();
}
