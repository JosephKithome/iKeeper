package com.sejjoh.ikeeper.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sejjoh.ikeeper.iKeeperEntity;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 * 18.01.2021
 */
public class iKeeperRepository {
    private iKeeperDao mIKeeperDao;
    private LiveData<List<iKeeperEntity>> alliKeepers;

    public iKeeperRepository(Application application){
        iKeeperDatabase database =iKeeperDatabase.getInstance(application);
         mIKeeperDao = database.mIKeeperDao();
         alliKeepers =mIKeeperDao.getAllikeepers();
    }
    public  void insertiKeeper(iKeeperEntity iKeeperEntity){
        new InsertIkeeperAsyncTask(mIKeeperDao).execute(iKeeperEntity);

    }
    public  void  updateiKeeper(iKeeperEntity iKeeperEntity){
        new UpdateIkeeperAsyncTask(mIKeeperDao).execute(iKeeperEntity);

    }
    public void  deleteiKeeper(iKeeperEntity iKeeperEntity){
        new DeleteIkeeperAsyncTask(mIKeeperDao).execute(iKeeperEntity);

    }
    public  void deleteAlliKeepers(){
        new DeleteAlliKeeperAsyncTask(mIKeeperDao).execute();

    }
    public  LiveData<List<iKeeperEntity>> getAlliKeepers(){
        return  alliKeepers;
    }
    private  static  class InsertIkeeperAsyncTask extends AsyncTask<iKeeperEntity,Void,Void>{

        private  iKeeperDao mIKeeperDao;
        private InsertIkeeperAsyncTask(iKeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(iKeeperEntity... iKeeperEntities) {
            mIKeeperDao.insertIkeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class UpdateIkeeperAsyncTask extends AsyncTask<iKeeperEntity,Void,Void>{

        private  iKeeperDao mIKeeperDao;
        private UpdateIkeeperAsyncTask(iKeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(iKeeperEntity... iKeeperEntities) {
            mIKeeperDao.updateIkeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class DeleteIkeeperAsyncTask extends AsyncTask<iKeeperEntity,Void,Void>{

        private  iKeeperDao mIKeeperDao;
        private DeleteIkeeperAsyncTask(iKeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(iKeeperEntity... iKeeperEntities) {
            mIKeeperDao.deleteIkeeper(iKeeperEntities[0]);
            return null;
        }
    }
    private  static  class DeleteAlliKeeperAsyncTask extends AsyncTask<Void,Void,Void>{

        private  iKeeperDao mIKeeperDao;
        private DeleteAlliKeeperAsyncTask(iKeeperDao iKeeperDao){
            this.mIKeeperDao = iKeeperDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mIKeeperDao.deleteAllIkeepers();
            return null;
        }
    }
}
