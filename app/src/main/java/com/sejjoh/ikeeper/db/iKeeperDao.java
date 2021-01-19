package com.sejjoh.ikeeper.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sejjoh.ikeeper.iKeeperEntity;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */
@Dao
public interface iKeeperDao {

    @Insert
    void  insertIkeeper(iKeeperEntity iKeeperEntity);

    @Update
    void updateIkeeper(iKeeperEntity iKeeperEntity);

    @Delete
    void deleteIkeeper(iKeeperEntity iKeeperEntity);

    @Query("DELETE FROM note_table")
    void  deleteAllIkeepers();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<iKeeperEntity>> getAllikeepers();


}
