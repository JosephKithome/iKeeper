package com.sejjoh.ikeeper.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sejjoh.ikeeper.KeeperEntity;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */

@Database(entities = {KeeperEntity.class},version = 1,exportSchema = false)

public abstract class KeeperDatabase extends RoomDatabase {

    private static KeeperDatabase instance;
    public  abstract KeeperDao mIKeeperDao();

    public  static synchronized KeeperDatabase getInstance(Context context){
        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    KeeperDatabase.class,"iKeeper_database")
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
            new PopulateDbAsyncTask(instance).execute();

        }
    };
    private  static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private KeeperDao mIKeeperDao;
        private PopulateDbAsyncTask(KeeperDatabase db){
            mIKeeperDao =db.mIKeeperDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mIKeeperDao.insertKeeper(new KeeperEntity("Java","Room example",7));
            mIKeeperDao.insertKeeper(new KeeperEntity("Room","Room Library",8));
            mIKeeperDao.insertKeeper(new KeeperEntity("AsyncTask","AsyncTask Classes",3));
            


            return null;
        }
    }

}
