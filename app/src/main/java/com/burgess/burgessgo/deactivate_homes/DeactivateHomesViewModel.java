package com.burgess.burgessgo.deactivate_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.Home;

public class DeactivateHomesViewModel extends AndroidViewModel {
    private List<Home> activeHomeList = new ArrayList<>();

    public DeactivateHomesViewModel(@NonNull Application application) {
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
