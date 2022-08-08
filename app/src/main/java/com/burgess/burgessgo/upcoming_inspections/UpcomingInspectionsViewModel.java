package com.burgess.burgessgo.upcoming_inspections;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.Inspection;

public class UpcomingInspectionsViewModel extends AndroidViewModel {
    private List<Inspection> inspectionList = new ArrayList<>();

    public UpcomingInspectionsViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertInspection(Inspection inspection) {
        inspectionList.add(inspection);
    }
    public List<Inspection> getInspectionList() {
        return inspectionList;
    }
    public void clearInspectionList() {
        inspectionList.clear();
    }
}
