package com.burgess.burgessgo.request_home_access;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.BuilderPersonnel;
import data.models.Home;

public class RequestHomeAccessViewModel extends AndroidViewModel {
    private List<BuilderPersonnel> builderPersonnelList = new ArrayList<>();
    private List<Home> activeHomeList = new ArrayList<>();

    public RequestHomeAccessViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertBuilderPersonnel(BuilderPersonnel builderPersonnel) { builderPersonnelList.add(builderPersonnel); }
    public List<BuilderPersonnel> getBuilderPersonnelList() { return builderPersonnelList; }
    public void clearBuilderPersonnelList() { builderPersonnelList.clear(); }

    public void insertActiveHome(Home activeHome) { activeHomeList.add(activeHome); }
    public List<Home> getActiveHomeList() { return activeHomeList; }
    public void clearActiveHomeList() { activeHomeList.clear(); }
}
