package com.sejjoh.ikeeper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sejjoh.ikeeper.db.iKeeperRepository;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 */
public class iKeeperViewModel  extends AndroidViewModel {

private iKeeperRepository mRepository;
private LiveData<List<iKeeperEntity>> alliKeepers;

    public iKeeperViewModel(@NonNull Application application) {
        super(application);
        mRepository = new iKeeperRepository(application);
        alliKeepers = mRepository.getAlliKeepers();
    }
    public  void insert(iKeeperEntity iKeeperEntity){
        mRepository.insertiKeeper(iKeeperEntity);
    }
    public  void update(iKeeperEntity iKeeperEntity){
        mRepository.updateiKeeper(iKeeperEntity);
    }
    public  void delete(iKeeperEntity iKeeperEntity){
        mRepository.deleteiKeeper(iKeeperEntity);
    }
    public  void deleteAll(){
        mRepository.deleteAlliKeepers();
    }
    public LiveData<List<iKeeperEntity>> getAlliKeepers(){
        return  alliKeepers;
    }
}
