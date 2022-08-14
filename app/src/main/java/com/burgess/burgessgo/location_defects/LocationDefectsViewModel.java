package com.burgess.burgessgo.location_defects;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.InspectionDefect;

public class LocationDefectsViewModel extends AndroidViewModel {
    private List<InspectionDefect> inspectionDefectList = new ArrayList<>();

    public LocationDefectsViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertInspectionDefect(InspectionDefect inspectionDefect) { inspectionDefectList.add(inspectionDefect); }
    public List<InspectionDefect> getInspectionDefectList() { return inspectionDefectList; }
    public void clearInspectionDefectList() { inspectionDefectList.clear(); }
}
