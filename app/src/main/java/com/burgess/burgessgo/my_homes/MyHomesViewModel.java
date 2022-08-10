package com.burgess.burgessgo.my_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.ActiveLocation;

public class MyHomesViewModel extends AndroidViewModel {
    private List<ActiveLocation> activeLocationList = new ArrayList<>();

    public MyHomesViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertActiveLocation(ActiveLocation activeLocation) {
        activeLocationList.add(activeLocation);
    }
    public List<ActiveLocation> getActiveLocationList() {
        return activeLocationList;
    }
    public void clearActiveLocationList() {
        activeLocationList.clear();
    }
}
