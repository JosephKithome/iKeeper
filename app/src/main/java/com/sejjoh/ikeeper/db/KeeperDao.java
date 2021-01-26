package com.sejjoh.ikeeper.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sejjoh.ikeeper.KeeperEntity;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */
@Dao
public interface KeeperDao {

    @Insert
    void insertKeeper(KeeperEntity KeeperEntity);

    @Update
    void updateKeeper(KeeperEntity KeeperEntity);

    @Delete
    void deleteKeeper(KeeperEntity KeeperEntity);

    @Query("DELETE FROM note_table")
    void deleteAllKeepers();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<KeeperEntity>> getAllKeepers();


}
