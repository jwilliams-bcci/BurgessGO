package com.burgess.burgessgo.non_passed_inspection_details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NonPassedInspectionDetailsViewModel extends AndroidViewModel {
    private String reportUrl = "";

    public NonPassedInspectionDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void setReportUrl(String url) {
        reportUrl = url;
    }
    public String getReportUrl() {
        return reportUrl;
    }
}
