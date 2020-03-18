package com.example.berclazmayskiseller.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "skis", indices = {@Index(value = {"ski"}, unique = true)})
public class SkiEntity {

    /* Primary Key */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /* Columns */
    @ColumnInfo(name = "ski")
    private String skiName;

    /* Constructor */
    public SkiEntity(String skiName) {
        this.skiName = skiName;
    }

    /* Methods */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkiName() {
        return skiName;
    }

    public void setSkiName(String skiName) {
        this.skiName = skiName;
    }
}