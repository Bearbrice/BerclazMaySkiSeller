package com.example.berclazmayskiseller.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import android.database.sqlite.SQLiteConstraintException;

import com.example.berclazmayskiseller.db.entity.ClientEntity;

import java.util.List;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM clients WHERE email = :id")
    LiveData<ClientEntity> getById(String id);

    @Query("SELECT * FROM clients")
    LiveData<List<ClientEntity>> getAll();

    @Insert
    long insert(ClientEntity client) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClientEntity> clients);

    @Update
    void update(ClientEntity client);

    @Delete
    void delete(ClientEntity client);

    @Query("DELETE FROM clients")
    void deleteAll();
}
