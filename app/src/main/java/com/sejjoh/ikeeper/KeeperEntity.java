package com.sejjoh.ikeeper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */
@Entity(tableName = "note_table")
public class KeeperEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private  String title;
    private  String description;
    private  int  priority;

    public KeeperEntity(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
