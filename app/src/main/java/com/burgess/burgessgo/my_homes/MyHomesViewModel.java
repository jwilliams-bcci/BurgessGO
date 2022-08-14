package com.burgess.burgessgo.my_homes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.ActiveLocation;
import data.models.Inspection;

public class MyHomesViewModel extends AndroidViewModel {
    private List<ActiveLocation> activeLocationList = new ArrayList<>();
    private List<Inspection> inspectionList = new ArrayList<>();

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

    public void insertInspection(Inspection inspection) { inspectionList.add(inspection); }
    public List<Inspection> getInspectionList() { return inspectionList; }
    public void clearInspectionList() { inspectionList.clear(); }
}
