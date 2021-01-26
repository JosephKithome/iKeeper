package com.sejjoh.ikeeper.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sejjoh.ikeeper.KeeperEntity;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */
public class KeeperRepository {
    private KeeperDao mIKeeperDao;
    private LiveData<List<KeeperEntity>> allKeepers;

    public KeeperRepository(Application application){
        KeeperDatabase database = KeeperDatabase.getInstance(application);
         mIKeeperDao = database.mIKeeperDao();
         allKeepers =mIKeeperDao.getAllKeepers();
    }
    public  void insertKeeper(KeeperEntity KeeperEntity){
        new InsertIkeeperAsyncTask(mIKeeperDao).execute(KeeperEntity);

    }
    public  void updateKeeper(KeeperEntity KeeperEntity){
        new UpdateKeeperAsyncTask(mIKeeperDao).execute(KeeperEntity);

    }
    public void deleteKeeper(KeeperEntity KeeperEntity){
        new DeleteIkeeperAsyncTask(mIKeeperDao).execute(KeeperEntity);

    }
    public  void deleteAlliKeepers(){
        new DeleteAlliKeeperAsyncTask(mIKeeperDao).execute();

    }
    public  LiveData<List<KeeperEntity>> getAlliKeepers(){
        return allKeepers;
    }
    private  static  class InsertIkeeperAsyncTask extends AsyncTask<KeeperEntity,Void,Void>{

        private KeeperDao mIKeeperDao;
        private InsertIkeeperAsyncTask(KeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(KeeperEntity... iKeeperEntities) {
            mIKeeperDao.insertKeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class UpdateKeeperAsyncTask extends AsyncTask<KeeperEntity,Void,Void>{

        private KeeperDao mIKeeperDao;
        private UpdateKeeperAsyncTask(KeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(KeeperEntity... iKeeperEntities) {
            mIKeeperDao.updateKeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class DeleteIkeeperAsyncTask extends AsyncTask<KeeperEntity,Void,Void>{

        private KeeperDao mIKeeperDao;
        private DeleteIkeeperAsyncTask(KeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(KeeperEntity... iKeeperEntities) {
            mIKeeperDao.deleteKeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class DeleteAlliKeeperAsyncTask extends AsyncTask<Void,Void,Void>{

        private KeeperDao mIKeeperDao;
        private DeleteAlliKeeperAsyncTask(KeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mIKeeperDao.deleteAllKeepers();
            return null;
        }
    }
}
