package com.burgess.burgessgo.non_passed_inspections;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.NonPassedInspection;

public class NonPassedInspectionsViewModel extends AndroidViewModel {
    private List<NonPassedInspection> inspectionList = new ArrayList<>();

    public NonPassedInspectionsViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertInspection(NonPassedInspection inspection) {
        inspectionList.add(inspection);
    }
    public List<NonPassedInspection> getInspectionList() {
        return inspectionList;
    }
    public void clearInspectionList() {
        inspectionList.clear();
    }
}
