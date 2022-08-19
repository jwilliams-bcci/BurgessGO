package com.burgess.burgessgo.share_transfer_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.BuilderPersonnel;
import data.models.Home;

public class ShareTransferHomesViewModel extends AndroidViewModel {
    private List<Home> activeHomeList = new ArrayList<>();
    private List<BuilderPersonnel> builderPersonnelList = new ArrayList<>();

    public ShareTransferHomesViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertActiveHome(Home activeHome) {
        activeHomeList.add(activeHome);
    }
    public List<Home> getActiveHomeList() {
        return activeHomeList;
    }
    public void clearActiveHomeList() {
        activeHomeList.clear();
    }
}
