package com.example.berclazmayskiseller.db.TODELETESOON;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SkiDao {

    @Query("SELECT * FROM skis")
    List<SkiEntity> getAll();

    @Query("SELECT * FROM skis WHERE id IN (:skiIds)")
    List<SkiEntity> loadAllByIds(int[] skiIds);

    @Query("SELECT * FROM skis WHERE ski LIKE :skiName LIMIT 1")
    SkiEntity findByName(String skiName);

    @Insert
    void insertAll(SkiEntity... skis) throws SQLiteConstraintException;

    @Update
    void updateSkis(SkiEntity... skis);

    @Delete
    void delete(SkiEntity skis);

    @Query("DELETE FROM skis")
    void deleteAll();
}
