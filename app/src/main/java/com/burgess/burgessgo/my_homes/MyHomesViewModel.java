package com.burgess.burgessgo.my_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.Home;
import data.models.Inspection;

public class MyHomesViewModel extends AndroidViewModel {
    private List<Home> mHomeList = new ArrayList<>();
    private List<Inspection> inspectionList = new ArrayList<>();

    public MyHomesViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertActiveHome(Home home) {
        mHomeList.add(home);
    }
    public List<Home> getHomeList() {
        return mHomeList;
    }
    public void clearHomeList() {
        mHomeList.clear();
    }

    public void insertInspection(Inspection inspection) { inspectionList.add(inspection); }
    public List<Inspection> getInspectionList() { return inspectionList; }
    public void clearInspectionList() { inspectionList.clear(); }
}
