package com.sejjoh.ikeeper.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sejjoh.ikeeper.iKeeperEntity;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */

@Database(entities = {iKeeperEntity.class},version = 1,exportSchema = false)

public abstract class iKeeperDatabase extends RoomDatabase {

    private static  iKeeperDatabase instance;
    public  abstract iKeeperDao mIKeeperDao();

    public  static synchronized  iKeeperDatabase getInstance(Context context){
        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    iKeeperDatabase.class,"iKeeper_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallBack)
                    .build();

        }
        return  instance;
    }
    private  static  RoomDatabase.Callback roomcallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTassk(instance).execute();

        }
    };
    private  static class PopulateDbAsyncTassk extends AsyncTask<Void,Void,Void>{

        private iKeeperDao mIKeeperDao;
        private PopulateDbAsyncTassk(iKeeperDatabase db){
            mIKeeperDao =db.mIKeeperDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mIKeeperDao.insertIkeeper(new iKeeperEntity("Java","Room example",7));
            mIKeeperDao.insertIkeeper(new iKeeperEntity("Room","Room Library",8));
            mIKeeperDao.insertIkeeper(new iKeeperEntity("AsyncTask","AsyncTask Classes",3));
            


            return null;
        }
    }

}
