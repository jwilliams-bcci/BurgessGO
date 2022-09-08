package com.burgess.burgessgo.schedule_inspection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.InspectionType;

public class ScheduleInspectionViewModel extends AndroidViewModel {
    private List<InspectionType> inspectionTypeList = new ArrayList<>();

    public ScheduleInspectionViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertInspectionType(InspectionType inspectionType) { inspectionTypeList.add(inspectionType); }
    public List<InspectionType> getInspectionTypeList() { return inspectionTypeList; }
    public void clearInspectionTypeList() { inspectionTypeList.clear(); }
    public void insertHelperInspectionType() { inspectionTypeList.add(0, new InspectionType(-1, "Select an inspection type.")); }
}
