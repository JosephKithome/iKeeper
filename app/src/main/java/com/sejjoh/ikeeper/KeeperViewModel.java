package com.sejjoh.ikeeper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sejjoh.ikeeper.db.KeeperRepository;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 */
public class KeeperViewModel extends AndroidViewModel {

private KeeperRepository mRepository;
public LiveData<List<KeeperEntity>> alliKeepers;

    public KeeperViewModel(@NonNull Application application) {
        super(application);
        mRepository = new KeeperRepository(application);
        alliKeepers = mRepository.getAlliKeepers();
    }
    public  void insert(KeeperEntity KeeperEntity){
        mRepository.insertKeeper(KeeperEntity);
    }
    public  void update(KeeperEntity KeeperEntity){
        mRepository.updateKeeper(KeeperEntity);
    }
    public  void delete(KeeperEntity KeeperEntity){
        mRepository.deleteKeeper(KeeperEntity);
    }
    public  void deleteAll(){
        mRepository.deleteAlliKeepers();
    }
    public LiveData<List<KeeperEntity>> getAlliKeepers(){
        return  alliKeepers;
    }
}
