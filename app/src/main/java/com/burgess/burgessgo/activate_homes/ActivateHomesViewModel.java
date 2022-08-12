package com.burgess.burgessgo.activate_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.Home;

public class ActivateHomesViewModel extends AndroidViewModel {
    private List<Home> inactiveHomeList = new ArrayList<>();

    public ActivateHomesViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertInactiveHome(Home inactiveHome) {
        inactiveHomeList.add(inactiveHome);
    }
    public List<Home> getInactiveHomeList() {
        return inactiveHomeList;
    }
    public void clearInactiveHomeList() {
        inactiveHomeList.clear();
    }
}
