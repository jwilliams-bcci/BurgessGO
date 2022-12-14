package data.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Home implements Parcelable {
    private int LocationId;
    private String Community;
    private String Address;
    private int InspectionCount;
    private int InspectionCountRemaining;
    private String StreetName;
    private int MAXBuilderPersonnelIDRequestingAccess;
    private boolean IsSelected;
    private List<BuilderPersonnel> BuilderPersonnelList = new ArrayList<>();

    public Home(){}

    public Home(int locationId, String community, String address, List<BuilderPersonnel> builderPersonnelList, int builderPersonnelRequestingAccess, boolean isSelected) {
        LocationId = locationId;
        Community = community;
        Address = address;
        BuilderPersonnelList = builderPersonnelList;
        MAXBuilderPersonnelIDRequestingAccess = builderPersonnelRequestingAccess;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Home(Parcel in) {
        LocationId = in.readInt();
        Community = in.readString();
        Address = in.readString();
        InspectionCount = in.readInt();
        InspectionCountRemaining = in.readInt();
        StreetName = in.readString();
        MAXBuilderPersonnelIDRequestingAccess = in.readInt();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(LocationId);
        parcel.writeString(Community);
        parcel.writeString(Address);
        parcel.writeInt(InspectionCount);
        parcel.writeInt(InspectionCountRemaining);
        parcel.writeString(StreetName);
        parcel.writeInt(MAXBuilderPersonnelIDRequestingAccess);
    }

    //region GETTERS AND SETTERS
    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<BuilderPersonnel> getBuilderPersonnelList() {
        return BuilderPersonnelList;
    }

    public void setBuilderPersonnelList(List<BuilderPersonnel> builderPersonnelList) {
        BuilderPersonnelList = builderPersonnelList;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public int getMAXBuilderPersonnelIDRequestingAccess() {
        return MAXBuilderPersonnelIDRequestingAccess;
    }

    public void setMAXBuilderPersonnelIDRequestingAccess(int builderPersonnelIdRequestingAccess) {
        MAXBuilderPersonnelIDRequestingAccess = builderPersonnelIdRequestingAccess;
    }

    public int getInspectionCount() {
        return InspectionCount;
    }

    public void setInspectionCount(int inspectionCount) {
        InspectionCount = inspectionCount;
    }

    public int getInspectionCountRemaining() {
        return InspectionCountRemaining;
    }

    public void setInspectionCountRemaining(int inspectionCountRemaining) {
        InspectionCountRemaining = inspectionCountRemaining;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }
//endregion
}