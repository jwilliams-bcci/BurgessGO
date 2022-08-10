package com.burgess.burgessgo.add_new_address;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import data.models.Community;
import data.models.Street;

public class AddNewAddressViewModel extends AndroidViewModel {
    private List<Community> communityList = new ArrayList<>();
    private List<String> stateList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<String> countyList = new ArrayList<>();
    private List<Street> streetList = new ArrayList<>();

    public AddNewAddressViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertCommunity(Community community) { communityList.add(community); }
    public List<Community> getCommunityList() { return communityList; }
    public void clearCommunityList() { communityList.clear(); }
    public void insertHelperCommunity() { communityList.add(0, new Community(-1, -1, "Select a community", -1, "", -1, "", "", "", "")); }
    public void insertAddNewCommunity() {
        communityList.add(new Community(-1, -1, "---------", -1, "", -1, "", "", "", ""));
        communityList.add(new Community(0, -1, "Create New Community", -1, "", -1, "", "", "", ""));
    }

    public void insertState(String state) { stateList.add(state); }
    public List<String> getStateList() { return stateList; }
    public void clearStateList() { stateList.clear(); }
    public void insertHelperState() { stateList.add(0, "Select a state"); }

    public void insertCity(String city) { cityList.add(city); }
    public List<String> getCityList() { return cityList; }
    public void clearCityList() { cityList.clear(); }
    public void insertHelperCity() { cityList.add(0, "Select a city"); }
    public void insertAddNewCity() {
        cityList.add("---------");
        cityList.add("Create New City");
    }

    public void insertCounty(String county) { countyList.add(county); }
    public List<String> getCountyList() { return countyList; }
    public void clearCountyList() { countyList.clear(); }
    public void insertHelperCounty() { countyList.add(0, "Select a county"); }
    public void insertAddNewCounty() {
        countyList.add("---------");
        countyList.add("Create New County");
    }

    public void insertStreet(Street street) { streetList.add(street); }
    public List<Street> getStreetList() { return streetList; }
    public void clearStreetList() { streetList.clear(); }
    public void insertHelperStreet() { streetList.add(0, new Street(-1, "Select a street", -1, -1)); }
    public void insertAddNewStreet() {
        streetList.add(new Street(-1, "---------", -1, -1));
        streetList.add(new Street(0, "Create New Street", -1, -1));
    }
}
