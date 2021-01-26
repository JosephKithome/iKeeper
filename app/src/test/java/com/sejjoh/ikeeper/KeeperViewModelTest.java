package com.sejjoh.ikeeper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sejjoh.ikeeper.db.KeeperRepository;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/**
 * created by joseph mulingwa kithome on
 */

@RunWith(MockitoJUnitRunner.class)
public class KeeperViewModelTest extends TestCase {

    @Mock
    Application application;

    @Mock
    KeeperViewModel mKeeperViewModel;


    private KeeperRepository mRepository;

    @Before
    public void setUp() {
        mRepository = new KeeperRepository(application);
    }

    @Test
    public void testGetAlliKeepers() {
        //trigger
        mKeeperViewModel.getAlliKeepers();

        //verify
        Assert.assertNotNull(mKeeperViewModel.alliKeepers);
    }
}